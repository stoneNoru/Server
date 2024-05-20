package com.ssafy.home.exception;

import org.apache.ibatis.javassist.NotFoundException;

public class UserNotFoundException extends NotFoundException {
	private static final long serialVersionUID = 2L;

	public UserNotFoundException() {
		super("존재하지 않는 유저입니다.");
	}
}
