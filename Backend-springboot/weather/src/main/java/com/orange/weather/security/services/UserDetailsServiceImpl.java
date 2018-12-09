package com.orange.weather.security.services;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.orange.weather.model.User;
import com.orange.weather.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	UserRepository userRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		
		logger.info(" loadUserByUsername function is called ..");
		logger.debug(" loadUserByUsername function parameters are .." + mail);
		
		User user = userRepo.findByMail(mail).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with email : " + mail));
		
		return UserPrinciple.build(user);
	}

}
