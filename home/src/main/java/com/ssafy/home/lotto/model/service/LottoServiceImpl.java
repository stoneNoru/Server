package com.ssafy.home.lotto.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.lotto.dto.Lotto;
import com.ssafy.home.lotto.model.mapper.LottoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LottoServiceImpl implements LottoService {
	
	private final LottoMapper lottoMapper;

	@Override
	public List<Lotto> selectEndLotto() {
		return lottoMapper.selectEndLotto();
	}

	@Override
	public Lotto findById(String id) {
		return lottoMapper.findById(id);
	}

	@Override
	public List<Lotto> findNewLotto() {
		return lottoMapper.findNewLotto();
	}

	@Override
	public List<Lotto> findCurrentLotto() {
		return lottoMapper.findCurrentLotto();
	}

}
