package it.epicenergy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class EpicEnergyExceptionHandler extends ResponseEntityExceptionHandler {

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError){
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	
	@ExceptionHandler(EpicEnergyException.class)
	protected ResponseEntity<Object> handleCategoriaException(EpicEnergyException ex){
		ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST);
		return buildResponseEntity(apiError);
	}
}
