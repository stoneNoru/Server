package com.ssafy.home.util;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.ssafy.home.exception.UnAuthorizedException;
import com.ssafy.home.exception.UserNotFoundException;
import com.ssafy.home.user.dto.User;
import com.ssafy.home.user.model.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationUtils {
	private final UserService userService;
	private final JWTUtil jwtUtil;
	
	public User getUserInfoFromToken(HttpHeaders tokenHeader) throws UserNotFoundException, UnAuthorizedException {
	    List<String> authorizationHeader = tokenHeader.get(HttpHeaders.AUTHORIZATION);
	    if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
	        String token = authorizationHeader.get(0);
	        log.debug(token);
	        String userId = jwtUtil.getUserId(token);
	        User userInfo = userService.findById(userId);
	        if (userInfo != null) {
	            return userInfo;
	        } else {
	            throw new UserNotFoundException();
	        }
	    } else {
	        throw new UnAuthorizedException();
	    }
	}

}
