package it.epicenergy.controller.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.epicenergy.model.Indirizzo;
import it.epicenergy.service.IndirizzoService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class IndirizzoController {

	@Autowired
	private IndirizzoService indirizzoService;
	
	@GetMapping("/indirizzo")
	@Operation(summary = "Restituisce una lista di tutti gli indirizzi")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Indirizzo>> findAll(Pageable pageable){
		Page<Indirizzo> indirizzi = indirizzoService.findAll(pageable);
		if(indirizzi.hasContent()) {
			return new ResponseEntity<>(indirizzi, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/indirizzo/{id}")
	@Operation(summary = "Restituisce l'indirizzo con l'id passato com parametro")
	@ApiResponse(responseCode = "200", description = "Indirizzo trovato")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Indirizzo> findById(@PathVariable Long id){
		Optional<Indirizzo> indirizzo = indirizzoService.findById(id);
		if(indirizzo.isPresent()) {
			return new ResponseEntity<>(indirizzo.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/indirizzo/{id}")
	@Operation(summary = "Cancella l'indirizzo con l'id passato com parametro")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id){
		Optional<Indirizzo> indirizzo = indirizzoService.findById(id);
		if(indirizzo.isPresent()) {
			indirizzoService.delete(id);
			return new ResponseEntity<>("Indirizzo cancellato", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Indirizzo non trovato", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("indirizzo")
	@Operation(summary = "Inserimento indirizzo", description = "del comune inserire solamente l'id (deve esistere)")
	@ApiResponse(responseCode = "200", description = "Indirizzo inserito")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Indirizzo> save(@RequestBody Indirizzo indirizzo){
		Indirizzo ind = indirizzoService.save(indirizzo);
		return new ResponseEntity<>(ind, HttpStatus.OK);
	}
	
	@PutMapping("indirizzo/{id}")
	@Operation(summary = "modifica indirizzo", description = "del comune inserire solamente l'id (deve esistere)")
	@ApiResponse(responseCode = "200", description = "Indirizzo aggiornato")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Indirizzo> update(@RequestBody Indirizzo indirizzo, @PathVariable Long id){
		Indirizzo ind = indirizzoService.update(indirizzo, id);
		return new ResponseEntity<>(ind, HttpStatus.OK);
	}
	
}
