package com.ssafy.home.apt.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.apt.dto.AptBookmarkDto;
import com.ssafy.home.apt.dto.DealDto;
import com.ssafy.home.apt.dto.PositionDto;
import com.ssafy.home.apt.dto.ResultBookmarkDto;
import com.ssafy.home.apt.dto.SearchDto;
import com.ssafy.home.apt.model.service.HomeService;
import com.ssafy.home.exception.UnAuthorizedException;
import com.ssafy.home.status.DuplicateHttpStatus;
import com.ssafy.home.user.dto.User;
import com.ssafy.home.util.AuthorizationUtils;
import com.ssafy.home.util.ResultDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/home")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

	private final HomeService homeService;
	private final AuthorizationUtils authorizationUtils;

	//키워드를 통한 목록 조회
	@GetMapping
	public ResponseEntity<?> selectByKeyword(@RequestParam(required = false) String keyword) {
		List<SearchDto> search = homeService.selectByKeyword(keyword);

		if(search == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResultDto.res(HttpStatus.NOT_FOUND.value(), "검색에 실패했습니다."));
		}

		return ResponseEntity.ok().body(ResultDto.res(HttpStatus.OK.value(), "검색에 성공했습니다.", search));
	}

	@GetMapping("/search")
	public ResponseEntity<?> selectByPosition(
			@RequestParam double east,
			@RequestParam double west,
			@RequestParam double south,
			@RequestParam double north
			) {
		PositionDto position = PositionDto.builder()
				.east(east)
				.west(west)
				.south(south)
				.north(north)
				.build();

		List<DealDto> list = homeService.selectByPosition(position);

		return ResponseEntity.ok().body(ResultDto.res(HttpStatus.OK.value(), "검색에 성공했습니다.", list));
	}

	//아파트코드로 거래내역 조회
	@GetMapping("/{aptCode}")
	public ResponseEntity<?> selectByNo(@PathVariable String aptCode) {
		List<DealDto> deal = homeService.selectByCode(aptCode);
		
		log.debug(deal.get(0).toString());

		return ResponseEntity.ok().body(ResultDto.res(HttpStatus.OK.value(), "검색에 성공했습니다.", deal));
	}

	@GetMapping("/bookmarks/{aptCode}")
	public ResponseEntity<?> getBookmark(@RequestHeader("authorization") HttpHeaders tokenHeader, @PathVariable String aptCode) {
		User userInfo = null;

		try {
			userInfo = authorizationUtils.getUserInfoFromToken(tokenHeader);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResultDto(HttpStatus.NOT_FOUND.value(), "유저 정보를 찾을 수 없습니다."));
		} catch (UnAuthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(new ResultDto(HttpStatus.UNAUTHORIZED.value(), "인증에 실패했습니다."));
		}

		int result = homeService.findBookmarkByIdAndAptCode(aptCode, userInfo.getId());
		
		return ResponseEntity.ok().body(ResultDto.res(HttpStatus.OK.value(), "북마크 조회에 성공했습니다.", result));
	}
	


	@PostMapping("/bookmarks")
	public ResponseEntity<?> registBookmark(@RequestHeader("authorization") HttpHeaders tokenHeader, @RequestBody AptBookmarkDto bookmark) {
		log.debug(bookmark.toString());
		User userInfo = null;

		try {
			userInfo = authorizationUtils.getUserInfoFromToken(tokenHeader);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResultDto(HttpStatus.NOT_FOUND.value(), "유저 정보를 찾을 수 없습니다."));
		} catch (UnAuthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(new ResultDto(HttpStatus.UNAUTHORIZED.value(), "인증에 실패했습니다."));
		}

		bookmark.setUserId(userInfo.getId());
		int result;
		try {
			result = homeService.registBookmark(bookmark);
		} catch (Exception e) {
			return ResponseEntity.status(DuplicateHttpStatus.DUPLICATE.value())
					.body(new ResultDto(DuplicateHttpStatus.DUPLICATE.value(), "북마크 등록에 실패했습니다."));
		}

		if(result == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new ResultDto(HttpStatus.BAD_REQUEST.value(), "북마크 등록에 실패했습니다."));
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(ResultDto.res(HttpStatus.CREATED.value(), "북마크 등록에 성공했습니다."));
	}


	@GetMapping("/bookmarks")
	public ResponseEntity<?> findBookmarks(@RequestHeader("authorization") HttpHeaders tokenHeader) {
		User userInfo = null;

		try {
			userInfo = authorizationUtils.getUserInfoFromToken(tokenHeader);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResultDto(HttpStatus.NOT_FOUND.value(), "유저 정보를 찾을 수 없습니다."));
		} catch (UnAuthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(new ResultDto(HttpStatus.UNAUTHORIZED.value(), "인증에 실패했습니다."));
		}


		List<DealDto> list = homeService.findBookmarkDetailsByUserId(userInfo.getId());

		return ResponseEntity.status(HttpStatus.OK.value()).body(new ResultDto(HttpStatus.OK.value(), "성공적으로 조회하였습니다.", list));
	}

	@DeleteMapping("/bookmarks/{id}")
	public ResponseEntity<?> deleteBookmark(@RequestHeader("authorization") HttpHeaders tokenHeader, @PathVariable String id) {
		User userInfo = null;

		try {
			userInfo = authorizationUtils.getUserInfoFromToken(tokenHeader);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResultDto(HttpStatus.NOT_FOUND.value(), "유저 정보를 찾을 수 없습니다."));
		} catch (UnAuthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(new ResultDto(HttpStatus.UNAUTHORIZED.value(), "인증에 실패했습니다."));
		}

		int result = homeService.deleteBookmark(userInfo.getId(), id);
		if(result==0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResultDto(HttpStatus.NOT_FOUND.value(), "삭제하는데 실패 했습니다."));			
		}

		return ResponseEntity.status(HttpStatus.OK.value()).body(new ResultDto(HttpStatus.OK.value(), "성공적으로 삭제하였습니다."));
	}





}
