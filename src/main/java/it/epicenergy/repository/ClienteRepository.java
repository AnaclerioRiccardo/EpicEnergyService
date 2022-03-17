package it.epicenergy.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.epicenergy.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByRagioneSociale(String ragioneSociale);
	Optional<Cliente> findByEmail(String email);
	Optional<Cliente> findByPec(String pec);
	//Ordinamenti
	Page<Cliente> findAllByOrderByFatturatoAnnuale(Pageable pageable);
	Page<Cliente> findAllByOrderByDataInserimento(Pageable pageable);
	Page<Cliente> findAllByOrderByDataUltimoContatto(Pageable pageable);
	Page<Cliente> findAllByOrderByIndirizzoSedeLegaleComuneProvinciaNome(Pageable pageable);
	//Filtraggi
	Page<Cliente> findByFatturatoAnnualeBetween(BigDecimal val1, BigDecimal val2,Pageable pageable);
	Page<Cliente> findByDataInserimento(LocalDate dataInserimento, Pageable pageable);
	Page<Cliente> findByDataUltimoContatto(LocalDate dataUltimoContatto, Pageable pageable);
	Page<Cliente> findByRagioneSocialeLike(String ragioneSociale, Pageable pageable);
	Optional<Cliente> findByTelefono(String telefono);
	Optional<Cliente> findByTelefonoContatto(String telefono);
	

}
