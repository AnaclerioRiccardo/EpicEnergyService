package it.epicenergy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicenergy.exception.EpicEnergyException;
import it.epicenergy.model.Comune;
import it.epicenergy.model.Indirizzo;
import it.epicenergy.repository.ComuneRepository;
import it.epicenergy.repository.IndirizzoRepository;

@Service
public class IndirizzoService {

	@Autowired
	private IndirizzoRepository indirizzoRepo;
	
	@Autowired
	private ComuneRepository comuneRepo;
	
	//Metodi
	public Page<Indirizzo> findAll(Pageable pageable){
		return indirizzoRepo.findAll(pageable);
	}
	
	public Optional<Indirizzo> findById(Long id){
		return indirizzoRepo.findById(id);
	}

	public void delete(Long id) {
		indirizzoRepo.delete(findById(id).get());
	}

	public Indirizzo save(Indirizzo indirizzo) {
		Optional<Comune> comune = comuneRepo.findById(indirizzo.getComune().getId());
		//Controllo che il comune esista
		if(!comune.isPresent()) {
			throw new EpicEnergyException("L'id del comune inserito non esiste");
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
		Indirizzo update = ind.get();
		update.setCap(indirizzo.getCap());
		update.setCivico(indirizzo.getCivico());
		update.setLocalita(indirizzo.getLocalita());
		update.setVia(indirizzo.getVia());
		update.setComune(comune.get());
		return indirizzoRepo.save(update);
	}
	

}
