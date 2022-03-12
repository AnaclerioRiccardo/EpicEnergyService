package it.epicenergy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicenergy.model.Cliente;
import it.epicenergy.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepo;

	public Page<Cliente> findAll(Pageable pageable) {
		return clienteRepo.findAll(pageable);
	}

	public Optional<Cliente> findById(Long id) {
		return clienteRepo.findById(id);
	}

	public void delete(Long id) {
		clienteRepo.delete(findById(id).get());	
	}

}
