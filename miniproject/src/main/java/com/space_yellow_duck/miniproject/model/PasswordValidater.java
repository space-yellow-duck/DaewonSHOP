package com.space_yellow_duck.miniproject.model;


public class PasswordValidater {
	public void validate(String password){
		// 1. 길이 체크
		if (password.length() < 8 || password.length() > 20) {
			throw new IllegalArgumentException("비밀번호는 8자리 이상 20자리 이하만 생성할 수 있습니다.");
		}
		
		if (password.matches(".*\\s.*")) {
		    throw new IllegalArgumentException("비밀번호에 공백을 사용할 수 없습니다.");
		}

		// 3. 허용되지 않은 문자 체크
		if (!password.matches("^[A-Za-z0-9!@#$%^&*]+$")) {
			throw new IllegalArgumentException("허용되지 않은 문자가 포함되어 있습니다. 영문자,숫자,특수문자 ! @ # $ % ^ & * 를 사용해서 만들어주세요");
		}

		// 4. 문자 종류 체크
		if (!password.matches(".*[A-Za-z].*")) {
			throw new IllegalArgumentException("영문자를 포함해야 합니다.");
		}

		if (!password.matches(".*\\d.*")){
			throw new IllegalArgumentException("숫자를 포함해야 합니다.");
		}

		if (!password.matches(".*[!@#$%^&*].*")) {
			throw new IllegalArgumentException("특수문자를 포함해야 합니다.");
		}
	}
	
}
