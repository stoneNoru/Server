package com.ssafy.home.lotto.controller;

import java.util.List;

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
	
	
	//전체 조회
	@GetMapping
	public ResponseEntity<?> selectAll(@RequestParam(required=false) String sido) {
		
		List<Lotto> list = lottoService.selectAll(sido);
		
		return ResponseEntity.ok().body(list);
	}
	
	//아이디로 조회
	@GetMapping("/{id}")
	public ResponseEntity<?> getLotto(@PathVariable String id) {
		
		Lotto lotto = lottoService.findById(id);
		
		
		return ResponseEntity.ok().body(lotto);
	}
	
	
	@GetMapping("/new")
	public ResponseEntity<?> getNewLotto() {
		
		List<Lotto> lotto = lottoService.findNewLotto();
		
		
		return ResponseEntity.ok().body(lotto);
	}

	@GetMapping("/current")
	public ResponseEntity<?> getCurrentLotto() {
		
		List<Lotto>  lotto = lottoService.findCurrentLotto();
		
		
		return ResponseEntity.ok().body(lotto);
	}

}
