package com.howtodoinjava.demo.resource;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AcceptController {
	
	/*@RequestMapping(value = "/accept", method = {RequestMethod.POST, RequestMethod.GET})
	public String accept() {
		System.out.print("\nHello");
		return "approve.html";
	}*/
	
	@GetMapping("/accept")
	public String greeting(@RequestHeader("Cookie") String language, HttpServletResponse response) {
	    System.out.print("\n" + language);
	    response.addHeader("Cookie", language);
	    return "approve.html";
	}
}
