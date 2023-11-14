package com.message.Gebeya_Demo.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.message.Gebeya_Demo.config.CustomAuthenticationProvider;
/**
 * Add all your public end points down there. Private endpoints must be access using Bear authorization.
 * For that purpose. Add one endpoint /login with a post method. Do exactly as you see in the controller. 
 * 
 * 
 * */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationProvider authProvider;

	private static final String[] AUTH_WHITELIST = { "/login", "/swagger-ui/**", "/v3/api-docs/**",
			"/swagger-resources", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**" };

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {

		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Enable CORS and disable CSRF
		http = http.cors().and().csrf().disable();

		// Set session management to stateless
		http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

		// Set unauthorized requests exception handler
		http = http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
		}).and();

		// Set permissions on endpoints
		http.authorizeRequests()
				// Our public endpoints
				.antMatchers(AUTH_WHITELIST).permitAll().antMatchers("/api/public/**").permitAll().antMatchers("/item")
				.permitAll().antMatchers(HttpMethod.GET, "/api/transactions/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/deposit/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/withdrawal/").permitAll()
				.antMatchers(HttpMethod.GET, "/api/transfer/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/balanceinquiry").permitAll()
				.antMatchers(HttpMethod.POST, "/api/shortstatement").permitAll()
				// Our private endpoints
				.anyRequest().authenticated().and().addFilter(new AuthenticationFilter(authenticationManager()))
				.addFilter(new AuthorizationFilter(authenticationManager())).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.authenticationProvider(authProvider);
	}

	// Used by Spring Security if CORS is enabled.
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
