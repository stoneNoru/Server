package com.ssafy.home.user.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.home.user.dto.MailDto;
import com.ssafy.home.user.dto.User;
import com.ssafy.home.user.dto.UserPwDto;
import com.ssafy.home.user.model.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendEmailservice {

	@Autowired
	private JavaMailSender mailSender;
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private final UserMapper userMapper;
	private static final String FROM_ADDRESS = "dltjrqja1@chungbuk.ac.kr";


	public MailDto createMailAndChargePassword(UserPwDto requestDto) {
		String str = getTempPassword();
		MailDto dto = new MailDto();
		dto.setAddress(requestDto.getEmail());
		dto.setTitle(requestDto.getEmail()+"님의 임시비밀번호 안내 이메일 입니다.");
		dto.setMessage("안녕하세요. 임시비밀번호 안내 관련 메일 입니다." + "[" + requestDto.getId() + "]" + "님의 임시 비밀번호는 "
				+ str + " 입니다.");
		updatePassword(str,requestDto);
		return dto;
	}

	public void updatePassword(String str, UserPwDto requestDto) {
		String pw = passwordEncoder.encode(str);
		User user = userMapper.findById(requestDto.getId());
		user.setPassword(pw);

		userMapper.updateUser(user);

	}

	public String getTempPassword() {
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		String str = "";

		int idx = 0;
		for (int i=0; i<10; i++) {
			idx = (int) (charSet.length * Math.random());
			str += charSet[idx];
		}
		return str;
	}

	public void mailSend(MailDto mailDto) {
		System.out.println("이메일 전송 완료");
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mailDto.getAddress());
		message.setFrom(FROM_ADDRESS);
		message.setSubject(mailDto.getTitle());
		message.setText(mailDto.getMessage());

		mailSender.send(message);
	}
}
