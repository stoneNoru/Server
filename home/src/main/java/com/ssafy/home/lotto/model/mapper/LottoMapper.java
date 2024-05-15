package com.ssafy.home.lotto.model.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssafy.home.lotto.dto.Lotto;
@Repository
public interface LottoMapper {
	public List<Lotto> selectAll(String sido);

	public Lotto findById(String id);
	
	public List<Lotto> findNewLotto();
	
	public List<Lotto> findCurrentLotto();	
}
