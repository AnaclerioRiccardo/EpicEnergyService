package it.epicenergy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicenergy.model.Indirizzo;

public interface IndirizzoRepository extends JpaRepository<Indirizzo, Long> {

	Optional<Indirizzo> findByViaAndCivicoAndCapAndLocalita(String via, String civico, String cap, String localita);

}
