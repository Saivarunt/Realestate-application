package com.example.realestatelisting;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.realestatelisting.models.Permissions;
import com.example.realestatelisting.models.Role;
import com.example.realestatelisting.models.RolesPermissions;
import com.example.realestatelisting.models.User;
import com.example.realestatelisting.models.UserPermissions;
import com.example.realestatelisting.repository.PermissionsRepository;
import com.example.realestatelisting.repository.RoleRepositoy;
import com.example.realestatelisting.repository.RolesPermissionsRepository;
import com.example.realestatelisting.repository.UserPermissionsRepository;
import com.example.realestatelisting.repository.UserRepository;

@SpringBootApplication
public class RealestateListingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealestateListingApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepositoy roleRepositoy, UserRepository userRepository, PasswordEncoder passwordEncoder, 
	PermissionsRepository permissionsRepository, RolesPermissionsRepository rolesPermissionsRepository, UserPermissionsRepository userPermissionsRepository) {
		return args ->{

			if(roleRepositoy.findByAuthority("ADMIN").isPresent()){
				return;
			}

			Role adminRole = roleRepositoy.save(new Role("ADMIN"));
			Role userRole = roleRepositoy.save(new Role("USER"));
			Role agentRole = roleRepositoy.save(new Role("AGENT"));
			Role buyerRole = roleRepositoy.save(new Role("BUYER"));
			Role sellerRole = roleRepositoy.save(new Role("SELLER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			User admin = new User((long) 1, "admin", passwordEncoder.encode("admin"), roles, "admin", "admin@gmail.com", "1234567890");

			userRepository.save(admin);
			
			permissionsRepository.save(new Permissions(null, "READ_USER"));
			permissionsRepository.save(new Permissions(null, "READ"));
			permissionsRepository.save(new Permissions(null, "DELETE"));
			permissionsRepository.save(new Permissions(null, "SELL_PROPERTY"));
			permissionsRepository.save(new Permissions(null, "UPDATE_PROPERTY"));
			permissionsRepository.save(new Permissions(null, "UPDATE_PROFILE"));
			permissionsRepository.save(new Permissions(null, "BUY_PROPERTY"));

			permissionsRepository.findAll().stream().forEach(val-> {
				rolesPermissionsRepository.save(new RolesPermissions(null, (Permissions) val, adminRole));
				userPermissionsRepository.save(new UserPermissions(null, (Permissions) val, admin));

				if(val.getPermission().equalsIgnoreCase("READ") || val.getPermission().equalsIgnoreCase("UPDATE_PROFILE")){
					System.out.println("");
					rolesPermissionsRepository.save(new RolesPermissions(null, (Permissions) val, userRole));
				}
				else if(val.getPermission().equalsIgnoreCase("SELL_PROPERTY") || val.getPermission().equalsIgnoreCase("UPDATE_PROPERTY")){
					rolesPermissionsRepository.save(new RolesPermissions(null, (Permissions) val, sellerRole));
				}
				else if(val.getPermission().equalsIgnoreCase("BUY_PROPERTY")){
					rolesPermissionsRepository.save(new RolesPermissions(null, (Permissions) val, buyerRole));
				}
			});
			
		};
	}
}
