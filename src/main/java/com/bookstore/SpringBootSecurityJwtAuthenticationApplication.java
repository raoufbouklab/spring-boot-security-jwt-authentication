package com.bookstore;

import com.bookstore.model.ERole;
import com.bookstore.model.Role;
import com.bookstore.model.User;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class SpringBootSecurityJwtAuthenticationApplication {
	private final Logger logger = LoggerFactory.getLogger(SpringBootSecurityJwtAuthenticationApplication.class);

	@Bean
	CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository) {

		return args -> {
			var role1 = new Role(ERole.ROLE_ADMIN);
			var role2 = new Role(ERole.ROLE_USER);
			var role3 = new Role(ERole.ROLE_MODERATOR);

			roleRepository.save(role1);
			roleRepository.save(role2);
			roleRepository.save(role3);
			roleRepository.findAll().forEach(role -> logger.info(role.toString()));
			userRepository.save(new User("raoufb", "raouf.test@test.ca", "1234", Set.of(role1, role2, role3)));
			userRepository.save(new User("ramzib", "ramzi.test@test.ca", "1234", Set.of(role2)));
			userRepository.save(new User("houdab", "houda.test@test.ca", "1234", Set.of(role3)));
			userRepository.findAll().forEach(user -> logger.info(user.toString()));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityJwtAuthenticationApplication.class, args);
	}

}
