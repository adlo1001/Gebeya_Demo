package com.message.Gebeya_Demo.config;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/*
 * I have used here if my name and my password is present in in the application.properties
 * Here you Autowire your mobile banking interface and check if phone number exists in the table
 * go to authenticate method and modify that. 
 * */

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Value("${user}")
	String username = "nothing";

	@Value("${pswrd}")
	String password = "nothing";

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String u = authentication.getName();
		String p = authentication.getCredentials().toString();
		if (username.equals("nothing") && password.equals("nothing")) {
			throw new BadCredentialsException(
					"Wrong Input:  Please add username and password in the application.properties");
		}
		return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
	}

	@Override
	public boolean supports(Class<?> authentication) {

		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
