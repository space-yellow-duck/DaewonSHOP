package com.space_yellow_duck.miniproject.model;

import java.util.Map;

public class PasswordValidater {
	public Map<String, Object> validate(String password){
		// 1. 길이 체크
		if (password.length() < 8 || password.length() > 20) {
			return Map.of("success",false,"error_msg","비밀번호는 8~20자여야 합니다.");
		}


		// 3. 허용되지 않은 문자 체크
		if (!password.matches("^[A-Za-z0-9!@#$%^&*]+$")) {
			return Map.of("success",false,"error_msg","허용되지 않은 문자가 포함되어 있습니다. 영문자,숫자,특수문자 ! @ # $ % ^ & * 를 사용해서 만들어주세요");
		}

		// 4. 문자 종류 체크
		if (!password.matches("[A-Za-z]")) {
			return Map.of("success",false,"error_msg","영문자를 포함해야 합니다.");
		}

		if (!password.matches("\\d")){
			return Map.of("success",false,"error_msg", "숫자를 포함해야 합니다.");
		}
//
//		if (!/[!@#$%^&*]/.test(password)) {
//			return "특수문자를 포함해야 합니다.";
//		}
		return Map.of();
	}
	
	public String removeSpace(String password) {
		return password.replaceAll("\\s", "");
	}
}
