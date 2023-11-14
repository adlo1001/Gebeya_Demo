package com.message.Gebeya_Demo.config;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	Logger LOGGER = Logger.getLogger("AuthorizationFilter.class");

	public AuthorizationFilter(AuthenticationManager authenticationManager) {

		super(authenticationManager);

	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

		String header = request.getHeader("Authorization");
		try {
			if (header == null || !header.startsWith("Bearer")) {
				filterChain.doFilter(request, response);
				return;
			}
			UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			filterChain.doFilter(request, response);
		} catch (Exception e) {

			throw new RuntimeException(e);
		}
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		if (token != null) {
			String user = Jwts.parserBuilder()
					// .setSigningKey(SignatureAlgorithm.HS512,AuthenticationFilter.JWT_KEY.getBytes())
					.setSigningKey(MKeys.JWT_KEY).build().parseClaimsJws(token.replace("Bearer", "")).getBody()
					.getSubject();

			logger.info(user);
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}

}
