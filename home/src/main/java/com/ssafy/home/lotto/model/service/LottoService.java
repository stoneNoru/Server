package com.ssafy.home.lotto.model.service;

import java.io.IOException;
import java.util.List;

import com.ssafy.home.lotto.dto.Lotto;

public interface LottoService {
	public List<Lotto> selectAll() throws IOException;

	public Lotto findById(String id) throws IOException;

	public List<Lotto> findBySido(String sido) throws IOException;
	
}
