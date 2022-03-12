package it.epicenergy.controller.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.epicenergy.model.User;
import it.epicenergy.service.UserService;


@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> findById(@PathVariable(required = true) Long id) {
		Optional<User> find = userService.findById(id);
		
		if (find.isPresent()) {
			return new ResponseEntity<>(find.get().getUserName(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Utente non trovato", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/user")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Regista un nuovo utente")
	@ApiResponse(responseCode = "200", description = "Utente inserito")
	public ResponseEntity<String> save(@RequestBody User user){
		Optional<User> find = userService.findByUserName(user.getUserName());	
		if (find.isPresent()) {
			return new ResponseEntity<>("Utente gia' presente", HttpStatus.BAD_REQUEST);	
		} else {
			userService.save(user);
			return new ResponseEntity<>(user.getUserName(), HttpStatus.OK);
		}
	}

}
