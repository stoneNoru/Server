package com.ssafy.autosave.lotto.model.mapper;

import com.ssafy.autosave.lotto.dto.Lotto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LottoMapper {
    void SaveAll(List<Lotto> lottoList);
}
