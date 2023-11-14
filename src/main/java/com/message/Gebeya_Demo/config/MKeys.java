package com.message.Gebeya_Demo.config;

import javax.crypto.SecretKey;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class MKeys {
	protected static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	protected static final String JWT_KEY = Encoders.BASE64.encode(key.getEncoded());
}
