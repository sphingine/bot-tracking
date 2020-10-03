package com.marlabs.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


import com.marlabs.controller.AuthenticationController;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource({"classpath:application-dev.properties"})
@ContextConfiguration(classes = {Application.class})
public class ContextLoadTest {

	@Autowired
	AuthenticationController authenticationController;
	
	@Test
	public void contextLoads() {
		assertThat(authenticationController).isNotNull();
	}
}
