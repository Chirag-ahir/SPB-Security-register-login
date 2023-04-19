package com.spb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.spb.entity.UserDTO;
import com.spb.repository.UserRepository;

//STEP2: Create a class CustomUserDetailsService which implements UserDetailsService
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		try {

			// Getting object of user from DB based on email of user
			UserDTO user = userRepository.findByEmail(email);

			if (user==null) {
				throw new UsernameNotFoundException("Sorry! User not available with this email.");
			} else {
				return new CustomUserDetails(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
