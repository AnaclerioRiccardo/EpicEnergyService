package it.epicenergy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicenergy.model.Provincia;
import it.epicenergy.repository.ProvinciaRepository;

@Service
public class ProvinciaService {

	@Autowired
	private ProvinciaRepository provinciaRepo;
	
	
	public Page<Provincia> findAll(Pageable pageable) {
		return provinciaRepo.findAll(pageable);
	}
	
	public Optional<Provincia> findById(Long id){
		return provinciaRepo.findById(id);
	}

}
