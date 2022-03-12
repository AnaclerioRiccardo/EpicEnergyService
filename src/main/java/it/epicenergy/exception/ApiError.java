package it.epicenergy.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
	
	//Attributi
	private String message;
	private HttpStatus status;

}
