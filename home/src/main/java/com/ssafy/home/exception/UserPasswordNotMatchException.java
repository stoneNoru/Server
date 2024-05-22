package com.ssafy.home.exception;

import org.apache.ibatis.javassist.NotFoundException;

public class UserPasswordNotMatchException extends NotFoundException {
	private static final long serialVersionUID = 2L;

	public UserPasswordNotMatchException() {
		super("비밀번호가 일치하지 않습니다.");
	}
}
