package it.epicenergy.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import it.epicenergy.exception.EpicEnergyException;
import it.epicenergy.model.Role;
import it.epicenergy.model.Roles;
import it.epicenergy.model.User;
import it.epicenergy.repository.RoleRepository;
import it.epicenergy.repository.UserRepository;



@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}
	
	public Optional<User> findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	public User save(User user){
		Set<Role> ruoli = new HashSet<>();
		//setto i ruoli
		if(user.getRoles().isEmpty() || user.getRoles()==null) {
			ruoli.add(roleRepository.findByRoleName(Roles.ROLE_USER).get());
		} else {
			for(Role r : user.getRoles()) {
				if(r.getRoleName().equals(Roles.ROLE_ADMIN)) {
					ruoli.add(roleRepository.findByRoleName(Roles.ROLE_ADMIN).get());
				} else {
					ruoli.add(roleRepository.findByRoleName(Roles.ROLE_USER).get());
				}
			}
		}
		user.setRoles(ruoli);
		//crypto la password
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		user.setPassword(bCrypt.encode(user.getPassword()));
		//controllo sull'email
		if(userRepository.existsByEmail(user.getEmail())) {
			throw new EpicEnergyException("Email gia' presente");
		}
		if(!isValid(user.getEmail())) {
			throw new EpicEnergyException("Email non corretta");
		}
		return userRepository.save(user);
	}

	private boolean isValid(String email) {
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
}
