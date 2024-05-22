package com.ssafy.home.lotto.controller;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.exception.UnAuthorizedException;
import com.ssafy.home.exception.UserNotFoundException;
import com.ssafy.home.lotto.dto.Lotto;
import com.ssafy.home.lotto.dto.LottoBookmarkDto;
import com.ssafy.home.lotto.model.service.LottoService;
import com.ssafy.home.status.DuplicateHttpStatus;
import com.ssafy.home.user.dto.User;
import com.ssafy.home.util.AuthorizationUtils;
import com.ssafy.home.util.ResultDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/applies")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class LottoController {
	
	private final LottoService lottoService;
    private final AuthorizationUtils authorizationUtils;
	
	//아이디로 조회
	@GetMapping("/{id}")
	public ResponseEntity<?> getLotto(@PathVariable String id) {
		Lotto lotto = lottoService.findById(id);
		if (lotto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResultDto.res(HttpStatus.NOT_FOUND.value(), "해당 청약을 찾을 수 없습니다."));
		}
		return ResponseEntity.ok(ResultDto.res(HttpStatus.OK.value(), "청약 조회 성공", lotto));
	}

	//종료된 거 조회
	@GetMapping("/end")
	public ResponseEntity<?> selectEndLotto(@RequestHeader("authorization") HttpHeaders tokenHeader) {
		User userInfo = null;

		try {
			userInfo = authorizationUtils.getUserInfoFromToken(tokenHeader);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResultDto(HttpStatus.NOT_FOUND.value(), "유저 정보를 찾을 수 없습니다."));
		} catch (UnAuthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(new ResultDto(HttpStatus.UNAUTHORIZED.value(), "인증에 실패했습니다."));
		}
		List<Lotto> list = lottoService.selectEndLotto(userInfo.getId());
		return ResponseEntity.ok(ResultDto.res(HttpStatus.OK.value(), "종료된 청약 조회 성공", list));
	}

	@GetMapping("/new")
	public ResponseEntity<?> getNewLotto(@RequestHeader("authorization") HttpHeaders tokenHeader) {
		User userInfo = null;

		try {
			userInfo = authorizationUtils.getUserInfoFromToken(tokenHeader);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResultDto(HttpStatus.NOT_FOUND.value(), "유저 정보를 찾을 수 없습니다."));
		} catch (UnAuthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(new ResultDto(HttpStatus.UNAUTHORIZED.value(), "인증에 실패했습니다."));
		}
		List<Lotto> lotto = lottoService.findNewLotto(userInfo.getId());
		return ResponseEntity.ok(ResultDto.res(HttpStatus.OK.value(), "최신 청약 조회 성공", lotto));
	}


	@GetMapping("/current")
	public ResponseEntity<?> getCurrentLotto(@RequestHeader("authorization") HttpHeaders tokenHeader) {
		User userInfo = null;

		try {
			userInfo = authorizationUtils.getUserInfoFromToken(tokenHeader);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResultDto(HttpStatus.NOT_FOUND.value(), "유저 정보를 찾을 수 없습니다."));
		} catch (UnAuthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(new ResultDto(HttpStatus.UNAUTHORIZED.value(), "인증에 실패했습니다."));
		}
		List<Lotto> lotto = lottoService.findCurrentLotto(userInfo.getId());
		return ResponseEntity.ok(ResultDto.res(HttpStatus.OK.value(), "진행 중인 청약 조회 성공", lotto));
	}

	@PostMapping("/bookmarks")
    public ResponseEntity<?> registBookmark(@RequestHeader("authorization") HttpHeaders tokenHeader, @RequestBody LottoBookmarkDto bookmark) throws UserNotFoundException, UnAuthorizedException {
    	User userInfo = authorizationUtils.getUserInfoFromToken(tokenHeader);
    	
		bookmark.setUserId(userInfo.getId());
		
		int result = lottoService.registBookmark(bookmark);

		return ResponseEntity.status(HttpStatus.CREATED).body(ResultDto.res(HttpStatus.CREATED.value(), "북마크 등록에 성공했습니다."));
    }
    
	@GetMapping("/bookmarks/{houseManageNo}")
	public ResponseEntity<?> getBookmark(@RequestHeader("authorization") HttpHeaders tokenHeader, @PathVariable String houseManageNo) {
		User userInfo = null;

		try {
			userInfo = authorizationUtils.getUserInfoFromToken(tokenHeader);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResultDto(HttpStatus.NOT_FOUND.value(), "유저 정보를 찾을 수 없습니다."));
		} catch (UnAuthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(new ResultDto(HttpStatus.UNAUTHORIZED.value(), "인증에 실패했습니다."));
		}

		int result = lottoService.findBookmarkByIdAndNo(houseManageNo, userInfo.getId());
		
		return ResponseEntity.ok().body(ResultDto.res(HttpStatus.OK.value(), "북마크 조회에 성공했습니다.", result));
	}
    
    @GetMapping("/bookmarks")
    public ResponseEntity<?> findBookmarks(@RequestHeader("authorization") HttpHeaders tokenHeader) throws NotFoundException {
    	User userInfo = null;
    	
    	userInfo = authorizationUtils.getUserInfoFromToken(tokenHeader);
		
		List<Lotto> list = lottoService.findBookmarkDetailsByUserId(userInfo.getId());
		
        return ResponseEntity.status(HttpStatus.OK.value()).body(new ResultDto(HttpStatus.OK.value(), "성공적으로 조회하였습니다.", list));
    }
    
    @DeleteMapping("/bookmarks/{id}")
    public ResponseEntity<?> deleteBookmark(@RequestHeader("authorization") HttpHeaders tokenHeader, @PathVariable String id) throws UserNotFoundException, UnAuthorizedException {
    	User userInfo = authorizationUtils.getUserInfoFromToken(tokenHeader);
		
		int result = lottoService.deleteBookmark(userInfo.getId(), id);
		if(result==0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResultDto(HttpStatus.NOT_FOUND.value(), "삭제하는데 실패 했습니다."));			
		}
		
        return ResponseEntity.status(HttpStatus.OK.value()).body(new ResultDto(HttpStatus.OK.value(), "성공적으로 삭제하였습니다."));
    }
	

}
