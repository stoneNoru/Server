package com.ssafy.home.lotto.model.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssafy.home.lotto.dto.Lotto;
import com.ssafy.home.lotto.dto.LottoBookmarkDto;
@Repository
public interface LottoMapper {
	public List<Lotto> selectEndLotto(String userId);

	public Lotto findById(String id);
	
	public List<Lotto> findNewLotto(String userId);
	
	public List<Lotto> findCurrentLotto(String userId);
	
	int registBookmark(LottoBookmarkDto bookmark);
	
	List<Lotto> findBookmarkDetailsByUserId(String id);
	
	int deleteBookmark(String userId, String houseManageNo);
	
	int findBookmarkByIdAndNo(String houseManageNo, String id);
}
