package it.epicenergy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicenergy.model.Fattura;

public interface FatturaRepository extends JpaRepository<Fattura, Long> {

}
