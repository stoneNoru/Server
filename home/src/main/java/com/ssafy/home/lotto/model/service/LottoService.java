package com.ssafy.home.lotto.model.service;

import java.util.List;

import com.ssafy.home.lotto.dto.Lotto;

public interface LottoService {
	public List<Lotto> selectEndLotto();

	public Lotto findById(String id);

	public List<Lotto> findNewLotto();
	
	public List<Lotto> findCurrentLotto();

}
