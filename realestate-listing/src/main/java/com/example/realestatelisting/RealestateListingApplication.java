package com.example.realestatelisting;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.realestatelisting.models.Role;
import com.example.realestatelisting.models.User;
import com.example.realestatelisting.repository.RoleRepositoy;
import com.example.realestatelisting.repository.UserRepository;

@SpringBootApplication
public class RealestateListingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealestateListingApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepositoy roleRepositoy, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args ->{

			if(roleRepositoy.findByAuthority("ADMIN").isPresent()){
				return;
			}

			Role adminRole = roleRepositoy.save(new Role("ADMIN"));
			roleRepositoy.save(new Role("USER"));
			roleRepositoy.save(new Role("AGENT"));
			roleRepositoy.save(new Role("BUYER"));
			roleRepositoy.save(new Role("SELLER"));


			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			User admin = new User((long) 1, "admin", passwordEncoder.encode("admin"), roles, "admin", "admin@gmail.com", "1234567890");

			userRepository.save(admin);
		};
	}
}
