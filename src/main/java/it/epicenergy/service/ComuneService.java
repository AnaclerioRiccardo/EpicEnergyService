package it.epicenergy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicenergy.model.Comune;
import it.epicenergy.repository.ComuneRepository;

@Service
public class ComuneService {

	@Autowired
	private ComuneRepository comuneRepo;
	
	public Page<Comune> findAll(Pageable pageable){
		return comuneRepo.findAll(pageable);
	}
	
	public Optional<Comune> findById(Long id){
		return comuneRepo.findById(id);
	}

}
