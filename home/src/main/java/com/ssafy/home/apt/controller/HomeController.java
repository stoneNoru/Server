package com.ssafy.home.apt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.apt.dto.DealDto;
import com.ssafy.home.apt.dto.PositionDto;
import com.ssafy.home.apt.dto.SearchDto;
import com.ssafy.home.apt.model.service.HomeService;
import com.ssafy.home.util.ResultDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/home")
@CrossOrigin("*")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping
    public ResponseEntity<?> selectByKeyword(@RequestParam(required = false) String keyword) {
        List<SearchDto> search = homeService.selectByKeyword(keyword);
        
        if(search == null) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResultDto.res(HttpStatus.NOT_FOUND.value(), "검색에 실패했습니다."));
        }

        return ResponseEntity.ok().body(ResultDto.res(HttpStatus.OK.value(), "검색에 성공했습니다.", search));
    }
    
    @GetMapping("/search")
    public ResponseEntity<?> selectByPosition(
    		@RequestParam double east,
    		@RequestParam double west,
    		@RequestParam double south,
    		@RequestParam double north
    		) {
    	PositionDto position = PositionDto.builder()
    		    .east(east)
    		    .west(west)
    		    .south(south)
    		    .north(north)
    		    .build();
    	
    	List<DealDto> list = homeService.selectByPosition(position);
    	
        return ResponseEntity.ok().body(ResultDto.res(HttpStatus.OK.value(), "검색에 성공했습니다.", list));
    }
    
    @GetMapping("/{aptCode}")
    public ResponseEntity<?> selectByNo(@PathVariable String aptCode) {
    	List<DealDto> deal = homeService.selectByCode(aptCode);
    	
    	return ResponseEntity.ok().body(ResultDto.res(HttpStatus.OK.value(), "검색에 성공했습니다.", deal));
    }
    
    
    
    
    
}
