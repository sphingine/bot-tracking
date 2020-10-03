package com.marlabs.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marlabs.configuration.ContextLoadTest;
import com.marlabs.constants.Constants;
import com.marlabs.service.impl.AuthenticationServiceImpl;

public class AuthenticationControllerTest extends ContextLoadTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	AuthenticationServiceImpl authenticationService;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}


	@Test
	public void whenRequestQuery() throws Exception {
		Map<String, String> responseBody = new HashMap<String, String>();
		responseBody.put("username", "username");
		responseBody.put("password", "password");

		ObjectMapper objectMapper = new ObjectMapper();
		String response = objectMapper.writeValueAsString(responseBody);

		mockMvc.perform(MockMvcRequestBuilders.post("/request-query")
				.contentType(MediaType.APPLICATION_JSON)
				.content(response))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"));
	}
	
	@Test
	public void whenRequestQuery_WrongRequestBody() throws Exception {
		Map<String, String> responseBody = new HashMap<String, String>();
		responseBody.put("password", "password");

		ObjectMapper objectMapper = new ObjectMapper();
		String response = objectMapper.writeValueAsString(responseBody);

		mockMvc.perform(MockMvcRequestBuilders.post("/request-query")
				.contentType(MediaType.APPLICATION_JSON)
				.content(response))
		.andExpect(status().isConflict());
	}
	

	@Test
	public void whenPostAuthentication_wrongJWT() throws Exception {
		Map<String, String> requestBody = new HashMap<String, String>();
		requestBody.put("username", "username");
		requestBody.put("password", "password");
		requestBody.put("query", "Please sum the numbers 93,92,18");
		requestBody.put("sum", "203");
		String jwtToken = "jwtToken";
		Cookie cookie = new Cookie("jwt", jwtToken);

		ObjectMapper objectMapper = new ObjectMapper();
		String response = objectMapper.writeValueAsString(requestBody);

		mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(response).cookie(cookie))
		.andExpect(status().isForbidden());
	}
	
	
	@Test
	public void whenPostAuthentication_wrongRequestBody() throws Exception {
		Map<String, String> requestBody = new HashMap<String, String>();
		requestBody.put("username", "username");
		requestBody.put("password", "password");
		requestBody.put("sum", "203");
		String jwtToken = "jwtToken";
		Cookie cookie = new Cookie("jwt", jwtToken);

		ObjectMapper objectMapper = new ObjectMapper();
		String response = objectMapper.writeValueAsString(requestBody);

		mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(response).cookie(cookie))
		.andExpect(status().isConflict());
	}
	

	@Test
	public void whenPostAuthentication() throws Exception {
		Map<String, String> responseBody = new HashMap<String, String>();
		responseBody.put("username", "username");
		responseBody.put("password", "password");

		ObjectMapper objectMapper = new ObjectMapper();
		String response = objectMapper.writeValueAsString(responseBody);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/request-query")
				.contentType(MediaType.APPLICATION_JSON)
				.content(response))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		JSONObject json = new JSONObject(actualResponseBody);
		String query = json.getString("query");

		String values = query.replace(Constants.STATEMENT, "");
		int result = 0;
		String[] randomNumbers = values.split(Constants.COMMA);
		for (String i : randomNumbers) {
			result += Integer.parseInt(i);
		}

		Map<String, String> requestBodyForAuthenticate = new HashMap<String, String>();
		requestBodyForAuthenticate.put("username", "username");
		requestBodyForAuthenticate.put("password", "password");
		requestBodyForAuthenticate.put("query", query);
		requestBodyForAuthenticate.put("sum", Integer.toString(result));
		String responseForAuthenticate = 
				objectMapper.writeValueAsString(requestBodyForAuthenticate);


		String jwtToken = "dXNlcm5hbWVwYXNzd29yZA==";
		Cookie cookie = new Cookie("jwt", jwtToken);

		mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(responseForAuthenticate).cookie(cookie))
		.andExpect(status().isOk());

	}
	
	
	@Test
	public void whenPostAuthentication_WrongSum() throws Exception {
		Map<String, String> responseBody = new HashMap<String, String>();
		responseBody.put("username", "username");
		responseBody.put("password", "password");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String response = objectMapper.writeValueAsString(responseBody);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/request-query")
				.contentType(MediaType.APPLICATION_JSON)
				.content(response))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn();

		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		JSONObject json = new JSONObject(actualResponseBody);
		String query = json.getString("query");

		String values = query.replace(Constants.STATEMENT, "");
		int result = 0;
		String[] randomNumbers = values.split(Constants.COMMA);
		for (String i : randomNumbers) {
			result += Integer.parseInt(i);
		}

		Map<String, String> requestBodyForAuthenticate = new HashMap<String, String>();
		requestBodyForAuthenticate.put("username", "username");
		requestBodyForAuthenticate.put("password", "password");
		requestBodyForAuthenticate.put("query", query);
		requestBodyForAuthenticate.put("sum", Integer.toString(result+10));
		String responseForAuthenticate = 
				objectMapper.writeValueAsString(requestBodyForAuthenticate);


		String jwtToken = "dXNlcm5hbWVwYXNzd29yZA==";
		Cookie cookie = new Cookie("jwt", jwtToken);

		mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(responseForAuthenticate).cookie(cookie))
		.andExpect(status().isBadRequest());

	}
}
