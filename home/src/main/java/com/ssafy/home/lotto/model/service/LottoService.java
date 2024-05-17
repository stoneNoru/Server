package com.ssafy.home.lotto.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.home.lotto.dto.Lotto;
import com.ssafy.home.lotto.dto.LottoBookmarkDto;

public interface LottoService {
	public List<Lotto> selectEndLotto();

	public Lotto findById(String id);

	public List<Lotto> findNewLotto();
	
	public List<Lotto> findCurrentLotto();

	int registBookmark(LottoBookmarkDto bookmark);
	
	List<Lotto> findBookmarkDetailsByUserId(String id);
	
	int deleteBookmark(String userId, String houseManageNo);
}
