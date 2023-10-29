package com.message.Gebeya_Demo.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

	@Value("${lan}")
	String item = "nothing";

	@ResponseBody
	@GetMapping("/")
	public String get() {
		return item;
	}

}
