package it.epicenergy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicenergy.exception.EpicEnergyException;
import it.epicenergy.model.Cliente;
import it.epicenergy.model.Comune;
import it.epicenergy.model.Indirizzo;
import it.epicenergy.repository.ClienteRepository;
import it.epicenergy.repository.ComuneRepository;
import it.epicenergy.repository.IndirizzoRepository;

@Service
public class IndirizzoService {

	@Autowired
	private IndirizzoRepository indirizzoRepo;
	
	@Autowired
	private ComuneRepository comuneRepo;
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	//Metodi
	public Page<Indirizzo> findAll(Pageable pageable){
		return indirizzoRepo.findAll(pageable);
	}
	
	public Optional<Indirizzo> findById(Long id){
		return indirizzoRepo.findById(id);
	}
	
	public Indirizzo findByViaAndCivicoAndCap(String via, String civico, String cap) {
		if(!isCapValid(cap))
			throw new EpicEnergyException("Il cap deve essere numerico");
		Optional<Indirizzo> indirizzo = indirizzoRepo.findByViaAndCivicoAndCap(via, civico, cap);
		if(!indirizzo.isPresent()) {
			throw new EpicEnergyException("Indirizzo non trovato");
		}
		return indirizzo.get();
	}

	public void delete(Long id) {
		//Devo cancellare l'indirizzo in tutti i clienti 
		Indirizzo ind = findById(id).get();
		List<Cliente> clienti = clienteRepo.findAll();
		if(!clienti.isEmpty()) {
			for(Cliente c: clienti) {
				if(c.getIndirizzoSedeLegale().equals(ind)) {
					c.setIndirizzoSedeLegale(null);
				}
				if(c.getIndirizzoSedeOperativa().equals(ind)) {
					c.setIndirizzoSedeOperativa(null);
				}
			}
		}
		//cancellazione effettiva dell'indirizzo
		indirizzoRepo.delete(ind);
	}

	public Indirizzo save(Indirizzo indirizzo) {
		Optional<Comune> comune = comuneRepo.findById(indirizzo.getComune().getId());
		//Controllo che il comune esista
		if(!comune.isPresent()) {
			throw new EpicEnergyException("L'id del comune inserito non esiste");
		}
		//Controllo che il cap sia numerico
		if(!isCapValid(indirizzo.getCap())) {
			throw new EpicEnergyException("Il cap deve essere numerico");
		}
		indirizzo.setComune(comune.get());
		return indirizzoRepo.save(indirizzo);
	}

	public Indirizzo update(Indirizzo indirizzo, Long id) {
		Optional<Indirizzo> ind = indirizzoRepo.findById(id);
		//Controllo che l'indirizzo esista
		if(!ind.isPresent()) {
			throw new EpicEnergyException("Non esiste nessun indirizzo con id: "+id);
		}
		Optional<Comune> comune = comuneRepo.findById(indirizzo.getComune().getId());
		//Controllo che il comune esista
		if(!comune.isPresent()) {
			throw new EpicEnergyException("L'id del comune inserito non esiste");
		}
		//Controllo che il cap sia numerico
		if(!isCapValid(indirizzo.getCap())) {
			throw new EpicEnergyException("Il cap deve essere numerico");
		}
		Indirizzo update = ind.get();
		update.setCap(indirizzo.getCap());
		update.setCivico(indirizzo.getCivico());
		update.setLocalita(indirizzo.getLocalita());
		update.setVia(indirizzo.getVia());
		update.setComune(comune.get());
		return indirizzoRepo.save(update);
	}
	
	private boolean isCapValid(String cap) {
		for (char c : cap.toCharArray()) {
			if (!Character.isDigit(c))                
				return false;
		}
		return true;
	}
	

}
