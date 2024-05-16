package com.ssafy.home.apt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionDto {
	private double east;
	private double west;
	private double south;
	private double north;
}
