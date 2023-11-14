package com.message.Gebeya_Demo.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.message.Gebeya_Demo.Model.UserModel;
import io.jsonwebtoken.Jwts;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	Logger LOGGER = Logger.getLogger("AuthenticationFilter.class");

	private AuthenticationManager authenticationManager;
	private String accessToken;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		//setFilterProcessesUrl("/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			UserModel user = new ObjectMapper().readValue(request.getInputStream(), UserModel.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),
					user.getPhoneNumber(), new ArrayList<>()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication authentication) {

		String token = Jwts.builder().setSubject(String.valueOf(authentication.getPrincipal()))
				.setExpiration(new Date(System.currentTimeMillis() + 604_800_000)).signWith(MKeys.key).compact();
		LOGGER.info("Authorization-------->  " + token);
		this.setAccessToken("Bearer " + token);
		response.addHeader("Authorization", "Bearer " + token);
	}

	public void setAccessToken(String token) {
		this.accessToken = token;

	}

	public String getAccessToken() {
		return this.accessToken;

	}

}
