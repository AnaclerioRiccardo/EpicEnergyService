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
import it.epicenergy.exception.EpicEnergyException;
import it.epicenergy.model.Cliente;
import it.epicenergy.service.ClienteService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/cliente")
	@Operation(summary = "Restituisce una lista di tutti i clienti")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> findAll(Pageable pageable){
		Page<Cliente> clienti = clienteService.findAll(pageable);
		if(clienti.hasContent()) {
			return new ResponseEntity<>(clienti, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/cliente/ordina/fatturatoannuale")
	@Operation(summary = "Restituisce una lista ordinata di tutti i clienti per fatturato annuale")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> findAllByOrderByFatturatoAnnuale(Pageable pageable){
		Page<Cliente> clienti = clienteService.findAllByOrderByFatturatoAnnuale(pageable);
		if(clienti.hasContent()) {
			return new ResponseEntity<>(clienti, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/cliente/ordina/datainserimento")
	@Operation(summary = "Restituisce una lista ordinata di tutti i clienti per data inserimento")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> findAllByOrderByDataInserimento(Pageable pageable){
		Page<Cliente> clienti = clienteService.findAllByOrderByDataInserimento(pageable);
		if(clienti.hasContent()) {
			return new ResponseEntity<>(clienti, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/cliente/ordina/dataultimocontatto")
	@Operation(summary = "Restituisce una lista ordinata di tutti i clienti per data ultimo contatto")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> findAllByOrderByDataUltimoContatto(Pageable pageable){
		Page<Cliente> clienti = clienteService.findAllByOrderByDataUltimoContatto(pageable);
		if(clienti.hasContent()) {
			return new ResponseEntity<>(clienti, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/cliente/ordina/provinciasedelegale")
	@Operation(summary = "Restituisce una lista ordinata di tutti i clienti per provincia sede legale")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> findAllByOrderByIndirizzoSedeLegaleComuneProvinciaNome(Pageable pageable){
		Page<Cliente> clienti = clienteService.findAllByOrderByIndirizzoSedeLegaleComuneProvinciaNome(pageable);
		if(clienti.hasContent()) {
			return new ResponseEntity<>(clienti, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/cliente/fatturatoannuale/{val1}/{val2}")
	@Operation(summary = "Restituisce una lista di tutti i clienti con un fatturato annuale compreo nel range")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> findByFatturatoAnnualeBetween(@PathVariable BigDecimal val1, @PathVariable BigDecimal val2,Pageable pageable){
		Page<Cliente> clienti = clienteService.findByFatturatoAnnualeBetween(val1, val2, pageable);
		if(clienti.hasContent()) {
			return new ResponseEntity<>(clienti, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/cliente/datainserimento/{dataInserimento}")
	@Operation(summary = "Restituisce una lista di tutti i clienti con la data di inserimento passata come parametro")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> findByDataInserimento(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dataInserimento, Pageable pageable){
		Page<Cliente> clienti = clienteService.findByDataInserimento(dataInserimento, pageable);
		if(clienti.hasContent()) {
			return new ResponseEntity<>(clienti, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/cliente/dataultimocontatto/{dataUltimoContatto}")
	@Operation(summary = "Restituisce una lista di tutti i clienti con la data ultimo contatto passata come parametro")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> findByDataUltimoContatto(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dataUltimoContatto, Pageable pageable){
		Page<Cliente> clienti = clienteService.findByDataUltimoContatto(dataUltimoContatto, pageable);
		if(clienti.hasContent()) {
			return new ResponseEntity<>(clienti, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/cliente/ragionesociale/{ragioneSociale}")
	@Operation(summary = "Restituisce una lista di tutti i clienti con la ragione sociale passata come parametro")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Cliente>> findByRagioneSocialeLike(@PathVariable String ragioneSociale, Pageable pageable){
		Page<Cliente> clienti = clienteService.findByRagioneSocialeLike(ragioneSociale, pageable);
		if(clienti.hasContent()) {
			return new ResponseEntity<>(clienti, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/cliente/{id}")
	@Operation(summary = "Restituisce il cliente con l'id passato come input")
	@ApiResponse(responseCode = "200", description = "cliente trovato")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Cliente> findById(@PathVariable Long id){
		Optional<Cliente> cliente = clienteService.findById(id);
		if(cliente.isPresent()) {
			return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/cliente/{id}")
	@Operation(summary = "Cancella il cliente con l'id passato com parametro", description = "cancella la fattura in cascata")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id){
		Optional<Cliente> cliente = clienteService.findById(id);
		if(cliente.isPresent()) {
			clienteService.delete(id);
			return new ResponseEntity<>("Cliente cancellato", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Cliente non trovato", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/cliente")
	@Operation(summary = "Inserimento di un nuovo cliente", description = "Per i due indirizzi prende quelli esistenti "
			+ "associati all'id passato; il fatturato annuale viene calcolato in base alle fatture; "
			+ "le fatture inserite vengono aggiunte in cascata")
	@ApiResponse(responseCode = "200", description = "cliente inserito")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Cliente> save(@RequestBody Cliente cliente){
		Cliente c = clienteService.save(cliente);
		return new ResponseEntity<>(c, HttpStatus.OK);
	}
	
	@PutMapping("cliente/{id}")
	@Operation(summary = "Modifica cliente", description = "Per i due indirizzi prende quelli esistenti "
			+ "associati all'id degli indirizzi passato; il fatturato annuale viene calcolato in base alle fatture; "
			+ "le fatture inserite vengono aggiunte in cascata")
	@ApiResponse(responseCode = "200", description = "Indirizzo aggiornato")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Cliente> update(@RequestBody Cliente cliente, @PathVariable Long id){
		if(clienteService.findById(id).isPresent()) {
			Cliente c = clienteService.update(cliente, id);
			return new ResponseEntity<>(c, HttpStatus.OK);
		} else {
			throw new EpicEnergyException("Non esiste nessun cliente con l'id: "+id);
		}
	}

}
