package com.marlabs.util;

import java.util.Base64;

import org.springframework.stereotype.Component;


@Component
public class CookieUtil {

	public String generteJWTtoken(String username, String password) {
		String jwtToken = Base64.getEncoder().encodeToString(username.concat(password).getBytes());
		return jwtToken;
	}
}
