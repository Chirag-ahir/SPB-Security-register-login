package com.spb.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spb.entity.UserDTO;
import com.spb.repository.UserRepository;

@Controller
@RequestMapping("/user") // URL After "/user/**" is not accessible without login
public class HomeController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/home")
	public String home(Principal p, Model m) {

		String email = p.getName(); //getting value of email from login page using principal
		UserDTO user = userRepository.findByEmail(email); //getting user object from DB with this email

		m.addAttribute("fullname", user.getFullname());
		m.addAttribute("address", user.getAddress());
		m.addAttribute("email", user.getEmail());

		return "home";
		
	}
}
