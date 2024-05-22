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
	public List<Lotto> selectEndLotto(String userId) {
		return lottoMapper.selectEndLotto(userId);
	}

	@Override
	public Lotto findById(String id) {
		return lottoMapper.findById(id);
	}

	@Override
	public List<Lotto> findNewLotto(String userId) {
		return lottoMapper.findNewLotto(userId);
	}

	@Override
	public List<Lotto> findCurrentLotto(String userId) {
		return lottoMapper.findCurrentLotto(userId);
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

	@Override
	public int findBookmarkByIdAndNo(String houseManageNo, String id) {
		return lottoMapper.findBookmarkByIdAndNo(houseManageNo, id);
	}

	
	
}
