package it.epicenergy.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.epicenergy.model.Provincia;
import it.epicenergy.service.ProvinciaService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class ProvinciaController {

	@Autowired
	private ProvinciaService provinciaService;

	
	@GetMapping("/provincia")
	@Operation(summary = "Lista di tutte le province")
	@ApiResponse(responseCode = "200", description = "Restituisce la lista di tutte le province")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Provincia>> findAll(Pageable pageable){
		if(provinciaService.findAll(pageable).hasContent()) {
			return new ResponseEntity<>(provinciaService.findAll(pageable), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/provincia/{id}")
	@Operation(summary = "Restituisce una provincia")
	@ApiResponse(responseCode = "200", description = "Restituisce la provincia con l'id passato come parametro")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Provincia> findById(@PathVariable Long id){
		if(provinciaService.findById(id).isPresent()) {
			return new ResponseEntity<>(provinciaService.findById(id).get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	
}
