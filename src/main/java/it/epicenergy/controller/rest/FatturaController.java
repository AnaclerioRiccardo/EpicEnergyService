package it.epicenergy.controller.rest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
import it.epicenergy.model.Fattura;
import it.epicenergy.service.FatturaService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class FatturaController {

	@Autowired
	private FatturaService fatturaService;
	
	@GetMapping("/fattura")
	@Operation(summary = "Restituisce una lista di tutte le fatture")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Fattura>> findAll(Pageable pageable){
		Page<Fattura> fatture = fatturaService.findAll(pageable);
		if(fatture.hasContent()) {
			return new ResponseEntity<>(fatture, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/fattura/cliente/{clienteId}")
	@Operation(summary = "Restituisce una lista di tutte le fatture del cliente con l'id passato come parametro")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Fattura>> findByClienteId(@PathVariable Long clienteId, Pageable pageable){
		Page<Fattura> fatture = fatturaService.findByClienteId(clienteId, pageable);
		if(fatture.hasContent()) {
			return new ResponseEntity<>(fatture, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/fattura/stato/{stato}")
	@Operation(summary = "Restituisce una lista di tutte le fatture con lo stato passato come parametro")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Fattura>> findByStato(@PathVariable String stato, Pageable pageable){
		Page<Fattura> fatture = fatturaService.findByStato(stato, pageable);
		if(fatture.hasContent()) {
			return new ResponseEntity<>(fatture, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/fattura/data/{data}")
	@Operation(summary = "Restituisce una lista di tutte le fatture con la data passata come parametro")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Fattura>> findByData(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate data, Pageable pageable){
		Page<Fattura> fatture = fatturaService.findByData(data, pageable);
		if(fatture.hasContent()) {
			return new ResponseEntity<>(fatture, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/fattura/anno/{anno}")
	@Operation(summary = "Restituisce una lista di tutte le fatture con l'anno passato come parametro")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Fattura>> findByAnno(@PathVariable Integer anno, Pageable pageable){
		Page<Fattura> fatture = fatturaService.findByAnno(anno, pageable);
		if(fatture.hasContent()) {
			return new ResponseEntity<>(fatture, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/fattura/importo/{val1}/{val2}")
	@Operation(summary = "Restituisce una lista di tutte le fatture con l'anno passato come parametro")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Fattura>> findByImportoBetween(@PathVariable BigDecimal val1, @PathVariable BigDecimal val2,Pageable pageable){
		Page<Fattura> fatture = fatturaService.findByImportoBetween(val1, val2, pageable);
		if(fatture.hasContent()) {
			return new ResponseEntity<>(fatture, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/fattura/{id}")
	@Operation(summary = "Restituisce la fattura con l'id passato come input")
	@ApiResponse(responseCode = "200", description = "fattura trovato")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Fattura> findById(@PathVariable Long id){
		Optional<Fattura> fattura = fatturaService.findById(id);
		if(fattura.isPresent()) {
			return new ResponseEntity<>(fattura.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/fattura/{id}")
	@Operation(summary = "Cancella la fattura con l'id passato com parametro", description = "Cancella solamente la fattura")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id){
		Optional<Fattura> fattura = fatturaService.findById(id);
		if(fattura.isPresent()) {
			fatturaService.delete(id);
			return new ResponseEntity<>("Fattura eliminata correttamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Fattura non trovata", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/fattura")
	@Operation(summary = "Inserimento di una nuova fattura", description = "Il cliente prende quello associato all'id "
			+ "(deve esistere), l'anno non va inserito, prende quello associato alla data")
	@ApiResponse(responseCode = "200", description = "Fattura inserita")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Fattura> save(@RequestBody Fattura fattura){
		Fattura f = fatturaService.save(fattura);
		return new ResponseEntity<>(f, HttpStatus.OK);
	}
	
	@PutMapping("fattura/{id}")
	@Operation(summary = "Modifica fattura", description = "Il cliente prende quello associato all'id "
			+ "(deve esistere),  l'anno non va inserito, prende quello associato alla data")
	@ApiResponse(responseCode = "200", description = "Fattura aggiornata")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Fattura> update(@RequestBody Fattura fattura, @PathVariable Long id){
		if(fatturaService.findById(id).isPresent()) {
			Fattura f = fatturaService.update(fattura, id);
			return new ResponseEntity<>(f, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

}
