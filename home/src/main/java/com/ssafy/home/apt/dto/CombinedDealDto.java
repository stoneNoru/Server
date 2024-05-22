package com.ssafy.home.apt.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CombinedDealDto {
    private List<DealDto> dealList;
    private int bookmark;
}
