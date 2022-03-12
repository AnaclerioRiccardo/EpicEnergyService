package it.epicenergy.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import it.epicenergy.model.Role;
import it.epicenergy.model.Roles;
import it.epicenergy.model.User;
import it.epicenergy.repository.RoleRepository;
import it.epicenergy.repository.UserRepository;


@Component
public class AddUserSpringRunner implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		
		Role role1 = new Role();
		role1.setRoleName(Roles.ROLE_ADMIN);
		User user1 = new User();
		Set<Role> roles = new HashSet<>(); 
		roles.add(role1);
		user1.setUserName("admin");
		user1.setPassword(bCrypt.encode("admin"));
		user1.setEmail("admin@domain.com");
		user1.setNome("Riccardo");
		user1.setCognome("Anaclerio");
		user1.setRoles(roles);
		user1.setActive(true);
		
		roleRepository.save(role1);
		userRepository.save(user1);
		
		Role role2 = new Role();
		role2.setRoleName(Roles.ROLE_USER);
		User user2 = new User();
		Set<Role> roles2 = new HashSet<>(); 
		roles2.add(role2);
		user2.setUserName("user");
		user2.setPassword(bCrypt.encode("user"));
		user2.setEmail("user@domain.com");
		user2.setNome("Mario");
		user2.setCognome("Rossi");
		user2.setRoles(roles2);
		user2.setActive(true);
		
		roleRepository.save(role2);
		userRepository.save(user2);

	}

}
