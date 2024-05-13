package com.ssafy.home.lotto.controller;

import java.io.IOException;
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
	
	@GetMapping
	public ResponseEntity<?> getSubscription() {
		
		List<Lotto> list;
		try {
			list = lottoService.selectAll();
		} catch (IOException e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getLotto(@PathVariable String id) {
		
		Lotto lotto;
		try {
			lotto = lottoService.findById(id);
		} catch (IOException e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(lotto);
	}
	
	@GetMapping
	public ResponseEntity<?> getLottoBySido(@RequestParam String sido) {
		
		List<Lotto> list;
		try {
			list = lottoService.findBySido(sido);
		} catch (IOException e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(list);
	}

}
