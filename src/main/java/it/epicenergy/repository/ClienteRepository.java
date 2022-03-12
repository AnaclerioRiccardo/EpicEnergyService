package it.epicenergy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicenergy.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
