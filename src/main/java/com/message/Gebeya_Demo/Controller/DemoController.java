package com.message.Gebeya_Demo.Controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.message.Gebeya_Demo.Model.UserModel;
import com.message.Gebeya_Demo.config.AuthenticationFilter;
import com.message.Gebeya_Demo.config.CustomAuthenticationProvider;
import com.message.Gebeya_Demo.util.ErrorMessage;

import io.micrometer.core.instrument.config.validate.ValidationException;

@RestController
public class DemoController {

	@Autowired
	MessageSource messageResource;

	private AuthenticationManager authenticationManager;

	Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

	AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager);
	@Value("${lan}")
	String item = "nothing";

	@GetMapping("/item")
	public ResponseEntity<String> get() {
		if (!item.equalsIgnoreCase("un"))
			return new ResponseEntity(item, HttpStatus.OK);
		// return item;
		else
			throw new RuntimeException("Wrong text");
	}

	/*
	 * @ResponseStatus(HttpStatus.BAD_REQUEST)
	 * 
	 * @ExceptionHandler(RuntimeException.class) public ErrorMessage
	 * exceptionHandler(RuntimeException e) { return new ErrorMessage("Wrong text!",
	 * HttpStatus.BAD_REQUEST.name()); }
	 */

	@ResponseBody
	@GetMapping("/hi")
	public String SayHi() {
		return "Hi";
	}

	@ResponseBody
	@GetMapping("/login")
	public String getLogin() {
		return "No Login";
	}

	@PostMapping("/login")
	public ResponseEntity<UserModel> getUserModel(@RequestBody UserModel userModel) {
		System.out.println("----------------------------------->");
		try {

			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userModel.getUserName(), userModel.getPhoneNumber()));

			return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, authenticationFilter.getAccessToken())
					.body(userModel);

		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping("/welcome")
	public String SayWelcome(@RequestHeader(name = "accept-language", required = false) Locale locale) {
		return messageResource.getMessage("welcome", null, locale);
	}
}