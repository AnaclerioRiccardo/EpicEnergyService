package it.epicenergy.security;

import java.util.List;

import lombok.Data;

@Data
public class LoginResponse {
	
	private final String type = "Bearer";
	private String token;
	private List<String> roles;

}
