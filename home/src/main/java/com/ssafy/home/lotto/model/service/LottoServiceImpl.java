package com.ssafy.home.lotto.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.lotto.dto.Lotto;
import com.ssafy.home.lotto.dto.LottoBookmarkDto;
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

	@Override
	public int registBookmark(LottoBookmarkDto bookmark) {
		return lottoMapper.registBookmark(bookmark);
	}

	@Override
	public List<Lotto> findBookmarkDetailsByUserId(String id) {
		return lottoMapper.findBookmarkDetailsByUserId(id);
	}

	@Override
	public int deleteBookmark(String userId, String houseManageNo) {
		return lottoMapper.deleteBookmark(userId, houseManageNo);
	}

	
	
}
