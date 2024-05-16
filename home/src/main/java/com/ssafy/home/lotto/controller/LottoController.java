package com.ssafy.home.lotto.controller;

import java.util.List;

import com.ssafy.home.util.ResultDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.lotto.dto.Lotto;
import com.ssafy.home.lotto.model.service.LottoService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/applies")
@CrossOrigin("*")
@RequiredArgsConstructor
public class LottoController {
	
	private final LottoService lottoService;
	
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
	public ResponseEntity<?> selectEndLotto() {
		List<Lotto> list = lottoService.selectEndLotto();
		return ResponseEntity.ok(ResultDto.res(HttpStatus.OK.value(), "종료된 청약 조회 성공", list));
	}

	@GetMapping("/new")
	public ResponseEntity<?> getNewLotto() {
		List<Lotto> lotto = lottoService.findNewLotto();
		return ResponseEntity.ok(ResultDto.res(HttpStatus.OK.value(), "최신 청약 조회 성공", lotto));
	}


	@GetMapping("/current")
	public ResponseEntity<?> getCurrentLotto() {
		List<Lotto> lotto = lottoService.findCurrentLotto();
		return ResponseEntity.ok(ResultDto.res(HttpStatus.OK.value(), "진행 중인 청약 조회 성공", lotto));
	}


}
