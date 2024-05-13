package com.ssafy.home.user.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.config.DuplicateHttpStatus;
import com.ssafy.home.config.auth.dto.JwtToken;
import com.ssafy.home.security.JwtGenerator;
import com.ssafy.home.user.dto.User;
import com.ssafy.home.user.model.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
	private final UserService userService;
	private final JwtGenerator jwtGenerator;

	//로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user, HttpServletRequest req, HttpServletResponse rep) {
		System.out.println(user);
		User userInfo = userService.login(user);

		if(userInfo==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 회원입니다.");

		
		JwtToken jwtToken = jwtGenerator.generateToken(userInfo.getId(), "ROLE_USER");
		
		Cookie cookie = new Cookie("access_token", jwtToken.getAccessToken());
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24 * 1); // 유효기간 1일
		cookie.setHttpOnly(false);
		
		rep.addCookie(cookie);
		
//		System.out.println(cookie.toString());
		return ResponseEntity
				.ok()
				.body(userInfo);
	}

	//회원가입
	@PostMapping
	public ResponseEntity<?> regist(@RequestBody User user) {
		try {
			int result = userService.regist(user);

			if (result > 0) {
				return ResponseEntity.ok().body("회원가입에 성공했습니다.");
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("회원가입에 실패했습니다.");
			}
		} catch (Exception ex) {
			// 예외 처리 로직
			return ResponseEntity.status(DuplicateHttpStatus.DUPLICATE.value()).body("중복된 회원이 존재합니다.");
		}
	}

	//중복 체크
	@GetMapping("/check")
	public ResponseEntity<?> duplicateCheck(@RequestParam(required = false) String email,
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String nickname) {
		//이메일 중복체크
		if (email != null) {
			User user = new User();
			user.setEmail(email);
			int result = userService.findUser(user);
			if (result > 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 이메일입니다.");
			}
		}

		//아이디 중복체크
		if (id != null) {
			User user = new User();
			user.setId(id);
			int result = userService.findUser(user);
			if (result > 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 아이디입니다.");
			}
		}

		//닉네임 중복체크
		if (nickname != null) {
			User user = new User();
			user.setNickname(nickname);
			int result = userService.findUser(user);
			if (result > 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 닉네임입니다.");
			}
		}

		return ResponseEntity.ok().build();
	}

	//내정보 조회
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserInfo(@RequestHeader HttpHeaders header, @PathVariable String id) {
		System.out.println("header : " + header.getFirst("Authorization"));
		User userInfo = userService.findById(id);

		if(userInfo == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저 정보를 찾을 수 없습니다.");
		}

		return ResponseEntity.ok(userInfo);
	}


	//내정보 수정
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUserInfo(@RequestBody User user, @PathVariable String id) {
		try {
			int result = userService.updateUser(user);
			if (result > 0) {
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("수정에 실패했습니다.");
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("수정에 실패했습니다.");
		}
	}

	//내정보 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable String id) {
		try {
			int result = userService.deleteUser(id);
			if (result > 0) {
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제에 실패했습니다.");
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제에 실패했습니다.");
		}
	}
}
