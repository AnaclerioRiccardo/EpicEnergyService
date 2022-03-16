package it.epicenergy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicenergy.model.Comune;

public interface ComuneRepository extends JpaRepository<Comune, Long> {
	
	List<Comune> findAllByOrderByNome();

}
