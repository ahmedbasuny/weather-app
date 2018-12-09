package com.orange.weather.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orange.weather.model.Role;
import com.orange.weather.model.RoleName;
import com.orange.weather.model.User;
import com.orange.weather.model.login.JwtResponse;
import com.orange.weather.model.login.LoginForm;
import com.orange.weather.model.login.ResponseMessage;
import com.orange.weather.model.login.SignUpForm;
import com.orange.weather.repository.RoleRepository;
import com.orange.weather.repository.UserRepository;
import com.orange.weather.security.jwt.JwtProvider;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthUserController {

	private static final Logger logger = LoggerFactory.getLogger(AuthUserController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;
	
	/**
	 * Login to the system
	 * @param	login model (username and password).  
	 * @return 	JWT response model contains user data and valid token. with http status ok.
	 * @author Ahmed Basuny 
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		logger.info(" authenticateUser function is called ..");
		logger.debug(" authenticateUser function parameters are .." + loginRequest.toString());

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getMail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
	}

	/**
	 * to register.
	 * @param	user data(name, mail, phone, password)  
	 * @return resource of custom message contains message of success and http ok status.
	 * @author Ahmed Basuny 
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {

		logger.info(" registerUser function is called ..");
		logger.debug(" registerUser function parameters are .." + signUpRequest.toString());

		if (userRepository.existsByMail(signUpRequest.getMail())) {
			return new ResponseEntity<>(new ResponseMessage("Registeration Failed, Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		User user = new User(signUpRequest.getName(), signUpRequest.getMail(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getPhone());

		Set<String> userRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		userRoles.forEach(role -> {
			
			switch (role) {
			case "admin":
				Role adminRole = roleRepository.findByRoleName(RoleName.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Admin Role not found !"));
				roles.add(adminRole);
				break;

			default:
				Role userRole = roleRepository.findByRoleName(RoleName.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("User Role not found !"));
				roles.add(userRole);
			}
		});

		user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}
}
