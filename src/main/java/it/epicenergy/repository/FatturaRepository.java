package it.epicenergy.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.epicenergy.model.Fattura;

public interface FatturaRepository extends JpaRepository<Fattura, Long> {
	
	Page<Fattura> findByClienteId(Long id, Pageable pageable);
	Page<Fattura> findByStato(String stato, Pageable pageable);
	Page<Fattura> findByData(LocalDate data, Pageable pageable);
	Page<Fattura> findByAnno(Integer anno, Pageable pageable);
	Page<Fattura> findByImportoBetween(BigDecimal val1, BigDecimal val2,Pageable pageable);

}
