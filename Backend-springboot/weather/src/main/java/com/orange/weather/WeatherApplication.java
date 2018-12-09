package com.orange.weather;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.orange.weather.model.Role;
import com.orange.weather.model.RoleName;
import com.orange.weather.model.User;
import com.orange.weather.repository.RoleRepository;
import com.orange.weather.repository.UserRepository;

@SpringBootApplication
public class WeatherApplication implements CommandLineRunner{

	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {

		Role roleUser = new Role(1, RoleName.ROLE_USER);
		Role roleAdmin = new Role(2, RoleName.ROLE_ADMIN);
		
		Set<Role> roles = new HashSet<>();
		roles.add(roleUser);
		roles.add(roleAdmin);
		
		roles.stream().forEach(role -> roleRepo.saveAndFlush(role));
		
		User admin = new User("Admin", "admin@weather.com", encoder.encode("123456"), "+201276063525");
		admin.setRoles(roles);
		
		userRepo.saveAndFlush(admin);
	}
}
