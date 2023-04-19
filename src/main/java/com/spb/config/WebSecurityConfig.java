package com.spb.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//STEP 3: Create WebSecurityConfig class which extends WebSecurityConfigurerAdapter.
@Configurable
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new CustomUserDetailsService();
	}
	
	@Bean
	public BCryptPasswordEncoder getPassword() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider daoProvider() {
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
		dao.setUserDetailsService(getUserDetailsService());
		dao.setPasswordEncoder(getPassword());
		return dao;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests()
			.antMatchers("/user/**").hasRole("USER") //person need to login for accessing all page after /user/**
			.antMatchers("/**").permitAll() //person can access only index page where login register visible 
			.and()
			.formLogin()
			.loginPage("/login") //user will see this login page
			.loginProcessingUrl("/dologin")  //form action from login page
			.defaultSuccessUrl("/user/home") //if login successfull user go to this home page
			.and()
			.csrf()
			.disable();
	}
	
	
}
