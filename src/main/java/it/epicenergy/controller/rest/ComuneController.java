package it.epicenergy.controller.rest;

import java.util.Optional;

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
import it.epicenergy.model.Comune;
import it.epicenergy.service.ComuneService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class ComuneController {
	
	@Autowired
	private ComuneService comuneService;

	@GetMapping("/comune")
	@Operation(summary = "Restituisce una lista di tutti i comuni")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Comune>> findAll(Pageable pageable){
		Page<Comune> comuni = comuneService.findAll(pageable);
		if(comuni.hasContent()) {
			return new ResponseEntity<>(comuni, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/comune/{id}")
	@Operation(summary = "Restituisce il comune con l'id passato come parametro")
	@ApiResponse(responseCode = "200", description = "Comune trovato")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Comune> findAll(@PathVariable Long id){
		Optional<Comune> comune = comuneService.findById(id);
		if(comune.isPresent()) {
			return new ResponseEntity<>(comune.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

}
