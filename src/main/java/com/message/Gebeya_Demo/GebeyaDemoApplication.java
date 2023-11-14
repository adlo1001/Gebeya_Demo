package com.message.Gebeya_Demo;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;


@SpringBootApplication
public class GebeyaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GebeyaDemoApplication.class, args);

	}

	@Bean
	public LocaleResolver localResolver() {
		SessionLocaleResolver sesstionLocaleResolver = new SessionLocaleResolver();
		sesstionLocaleResolver.setDefaultLocale(Locale.US);
		return sesstionLocaleResolver;
	}

}
