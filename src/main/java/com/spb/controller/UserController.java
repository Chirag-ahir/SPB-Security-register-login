package com.spb.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.spb.entity.UserDTO;
import com.spb.repository.UserRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute UserDTO user, HttpSession session) {
		
		//checking if email alredy in db or not
		UserDTO storedUserDetails = userRepository.findByEmail(user.getEmail());
		if(storedUserDetails != null) {
			//throw new RuntimeException("Record alredy exists");
			session.setAttribute("message", "User alredy exists with this email. Please use unique email.");
			return "redirect:/";
		}
		else {
			// sending encrypted password into db
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setRole("ROLE_USER");
			
			userRepository.save(user);
			session.setAttribute("message", "User Registerd Successfully");
			return "redirect:/";			
		}
		
	}

}
