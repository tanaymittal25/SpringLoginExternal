package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping(method= {RequestMethod.POST, RequestMethod.GET})
public class LoginController {
	
	@RequestMapping("/login")
	public String login() {
		return "login.html";
	}

}
