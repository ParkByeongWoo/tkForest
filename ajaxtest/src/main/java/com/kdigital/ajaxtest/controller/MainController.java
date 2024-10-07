package com.kdigital.ajaxtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping({"/", ""})
	public String index() {
		return "index";
	}
	
	@GetMapping("/guestbookpage")
	public String guestbook(Model model) {		
		return "guest/guestbook";
	}
}
