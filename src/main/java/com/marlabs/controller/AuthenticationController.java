package com.marlabs.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marlabs.service.impl.AuthenticationServiceImpl;
import com.marlabs.util.CookieUtil;

@RestController
public class AuthenticationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	AuthenticationServiceImpl authenticationService;

	@Autowired
	CookieUtil cookieUtil;

	/**
	 * Controller method which is a post call with the credentials to request the 
	 * integers that needs to be added.
	 * 
	 * @param httpRequest
	 * @param httpResponse
	 */
	@PostMapping("/request-query")
	public void requestQuery(HttpServletRequest httpRequest, 
			HttpServletResponse httpResponse) {
		try {
			String requestBody = httpRequest.getReader().lines().collect(
					Collectors.joining(System.lineSeparator()));
			JSONObject json = new JSONObject(requestBody);
			String username = json.getString("username");
			String password = json.getString("password");
			String jwtToken = cookieUtil.generteJWTtoken(username, password);
			String query = authenticationService.getAuthenticationQuery(jwtToken);

			//Setting up the cookie for validation
			Cookie cookie = new Cookie("jwt", jwtToken);
			cookie.setMaxAge(60);
			cookie.setSecure(true);
			cookie.setHttpOnly(true);
			httpResponse.addCookie(cookie);

			//Creating the Json Request body
			Map<String, String> responseBody = new HashMap<>();
			responseBody.put("query", query);
			ObjectMapper objectMapper = new ObjectMapper();
			String response = objectMapper.writeValueAsString(responseBody);

			httpResponse.setContentType("application/json;charset=UTF-8");
			httpResponse.getWriter().write(response);

		} catch (IOException|JSONException|NoSuchAlgorithmException e) {
			httpResponse.setStatus(HttpStatus.CONFLICT.value());
			LOGGER.error(e.getMessage());
		} catch (Exception e) {
			httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			LOGGER.error(e.getMessage());
		}
	}


	/**
	 * Controller method which authenticates the Sum from the user. 
	 * 
	 * @param httpRequest
	 * @param httpResponse
	 * @param jwt
	 */
	@PostMapping("/authenticate")
	public void postAuthentication(HttpServletRequest httpRequest, 
			HttpServletResponse httpResponse,
			@CookieValue(value="jwt") String jwt) {

		try {
			String  requestBody = httpRequest.getReader().lines().collect(
					Collectors.joining(System.lineSeparator()));

			JSONObject json = new JSONObject(requestBody);
			String username = json.getString("username");
			String password = json.getString("password");
			String query = json.getString("query");
			int sum = Integer.parseInt(json.getString("sum"));

			if (! jwt.equalsIgnoreCase(cookieUtil.generteJWTtoken(username, password))) {
				httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
			} else { 
				boolean isValid = authenticationService.validateAuthenticationQuery(jwt, query, sum);
				
				Cookie cookie = new Cookie("jwt", null);
				cookie.setMaxAge(0);
				cookie.setSecure(true);
				cookie.setHttpOnly(true);
				httpResponse.addCookie(cookie);
				httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());		

				if (isValid) {
					httpResponse.setStatus(HttpStatus.OK.value());
				} 
			}
		} catch (IOException|JSONException e) {
			httpResponse.setStatus(HttpStatus.CONFLICT.value());
			LOGGER.error(e.getMessage());
		}  catch (Exception e) {
			httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			LOGGER.error(e.getMessage());
		}
	}
}