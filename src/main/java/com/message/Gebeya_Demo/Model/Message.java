package com.message.Gebeya_Demo.Model;

import org.springframework.stereotype.Component;

@Component
public class Message {

	
	private int id;
	
	private String message;

	public Message() {
		super();

	}

	public Message(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Message [message=" + message + "]";
	}

}
