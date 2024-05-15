package com.ssafy.home.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.home.util.ResultDto;
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
import com.ssafy.home.user.dto.User;
import com.ssafy.home.user.model.service.UserService;
import com.ssafy.home.util.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
	private final UserService userService;
//	private final JwtGenerator jwtGenerator;
//	private final JWTProvider jwtProvider;
	private final JWTUtil jwtUtil;
	
	//로그아웃
	@GetMapping("/logout")
	public ResponseEntity<?> removeToken(@RequestHeader("authorization") HttpHeaders tokenHeader) {
		log.info("logout");
		User userInfo = null;

		List<String> authorizationHeader = tokenHeader.get(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
			String token = authorizationHeader.get(0);
			log.info(token);
			String id = jwtUtil.getUserId(token);
			try {
				userService.deleteRefreshToken(id);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			// Authorization 헤더가 없는 경우에 대한 처리
			return ResponseEntity.unprocessableEntity().body(ResultDto.res(HttpStatus.UNAUTHORIZED.value(), "인증에 실패했습니다."));
		}

		return ResponseEntity.ok(ResultDto.res(HttpStatus.OK.value(), "로그아웃에 성공했습니다."));
	}

	
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody User user, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String message = "";
		String token = request.getHeader("refreshToken");
		log.debug("token : {}, memberDto : {}", token, user);
		if (jwtUtil.checkToken(token)) {
			if (token.equals(userService.getRefreshToken(user.getId()))) {
				String accessToken = jwtUtil.createAccessToken(user.getId());
				log.debug("token : {}", accessToken);
				message  = "정상적으로 access token 재발급!!!";

				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			message = "refresh token 도 사용 불가!!!!!!!";
			status = HttpStatus.UNAUTHORIZED;
		}
		return ResponseEntity.ok(ResultDto.res(status.value(), message, resultMap));
	}
	

	//로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		
		User userInfo = userService.login(user);

		if(userInfo==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 회원입니다.");
		

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String accessToken = jwtUtil.createAccessToken(userInfo.getId());
			String refreshToken = jwtUtil.createRefreshToken(userInfo.getId());
			
			userService.saveRefreshToken(userInfo.getId(), refreshToken);
			resultMap.put("access-token", accessToken);
			resultMap.put("refresh-token", refreshToken);
			
			System.out.println(resultMap.get("access-token"));
			System.out.println(resultMap.get("refresh-token"));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResultDto.res(HttpStatus.NOT_FOUND.value(), "로그인에 실패 했습니다."));
		}

		
		return ResponseEntity.ok(ResultDto.res(HttpStatus.OK.value(), "로그인에 성공했습니다.", resultMap));
	}

	//회원가입
	@PostMapping
	public ResponseEntity<?> regist(@RequestBody User user) {
		try {
			String token = userService.regist(user);

			if (token != null) {
				return ResponseEntity.ok(new ResultDto(HttpStatus.CREATED.value(), "회원가입에 성공했습니다.", token));
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(new ResultDto(HttpStatus.CONFLICT.value(), "회원가입에 실패했습니다."));
			}
		} catch (Exception ex) {
			return ResponseEntity.status(DuplicateHttpStatus.DUPLICATE.value()).body(new ResultDto(DuplicateHttpStatus.DUPLICATE.value(), "중복된 회원이 존재합니다."));
		}
	}



	//중복 체크
	@GetMapping("/check")
	public ResponseEntity<?> duplicateCheck(@RequestParam(required = false) String email,
											@RequestParam(required = false) String id,
											@RequestParam(required = false) String nickname) {
		if (email != null) {
			User user = new User();
			user.setEmail(email);
			int result = userService.findUser(user);
			if (result > 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new ResultDto(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 이메일입니다."));
			}
		}

		if (id != null) {
			User user = new User();
			user.setId(id);
			int result = userService.findUser(user);
			if (result > 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new ResultDto(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 아이디입니다."));
			}
		}

		if (nickname != null) {
			User user = new User();
			user.setNickname(nickname);
			int result = userService.findUser(user);
			if (result > 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new ResultDto(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 닉네임입니다."));
			}
		}

		return ResponseEntity.ok().build();
	}



	//내정보 조회
	@GetMapping
	public ResponseEntity<?> getUserInfo(@RequestHeader("authorization") HttpHeaders tokenHeader) {
		User userInfo = null;

		List<String> authorizationHeader = tokenHeader.get(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
			String token = authorizationHeader.get(0);
			log.info(token);
			String id = jwtUtil.getUserId(token);
			userInfo = userService.findById(id);

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(new ResultDto(HttpStatus.UNAUTHORIZED.value(), "인증에 실패했습니다."));
		}

		if (userInfo == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResultDto(HttpStatus.NOT_FOUND.value(), "유저 정보를 찾을 수 없습니다."));
		}

		return ResponseEntity.ok(userInfo);
	}




	//내정보 수정
	@PutMapping
	public ResponseEntity<?> updateUserInfo(@RequestBody User user, @RequestHeader("authorization") HttpHeaders tokenHeader) {
		try {
			int result = userService.updateUser(user);
			if (result > 0) {
				return ResponseEntity.ok().body(new ResultDto(HttpStatus.OK.value(), "수정에 성공했습니다."));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResultDto(HttpStatus.NOT_FOUND.value(), "수정에 실패했습니다."));
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResultDto(HttpStatus.NOT_FOUND.value(), "수정에 실패했습니다."));
		}
	}


	//내정보 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable String id) {
		try {
			int result = userService.deleteUser(id);
			if (result > 0) {
				return ResponseEntity.ok().body(new ResultDto(HttpStatus.OK.value(), "삭제에 성공했습니다."));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResultDto(HttpStatus.NOT_FOUND.value(), "삭제에 실패했습니다."));
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResultDto(HttpStatus.NOT_FOUND.value(), "삭제에 실패했습니다."));
		}
	}


}
