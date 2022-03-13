package it.epicenergy.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicenergy.exception.EpicEnergyException;
import it.epicenergy.model.Cliente;
import it.epicenergy.model.Indirizzo;
import it.epicenergy.repository.ClienteRepository;
import it.epicenergy.repository.IndirizzoRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private IndirizzoRepository indirizzoRepo;

	//Metodi
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteRepo.findAll(pageable);
	}

	public Optional<Cliente> findById(Long id) {
		return clienteRepo.findById(id);
	}

	public void delete(Long id) {
		clienteRepo.delete(findById(id).get());	
	}

	public Cliente save(Cliente cliente) {
		//Controllo che l'email sia valida
		if(!isEmailValid(cliente.getEmail())) {
			throw new EpicEnergyException("Email non valida");
		}
		//controlo che l'email sia unica
		Optional<Cliente> cEmail = clienteRepo.findByEmail(cliente.getEmail());
		if(cEmail.isPresent()) {
			throw new EpicEnergyException("Email gia' presente");
		}
		//controlo che la pec sia unica
		Optional<Cliente> cPec = clienteRepo.findByPec(cliente.getPec());
		if(cPec.isPresent()) {
			throw new EpicEnergyException("Pec gia' presente");
		}
		//setto i due indirizzi
		Optional<Indirizzo> indirizzoLegale = indirizzoRepo.findById(cliente.getIndirizzoSedeLegale().getId());
		if(indirizzoLegale.isPresent()) {
			cliente.setIndirizzoSedeLegale(indirizzoLegale.get());
		} else {
			throw new EpicEnergyException("Indirizzo non presente");
		}
		Optional<Indirizzo> indirizzoOperativo = indirizzoRepo.findById(cliente.getIndirizzoSedeOperativa().getId());
		if(indirizzoOperativo.isPresent()) {
			cliente.setIndirizzoSedeLegale(indirizzoLegale.get());
		} else {
			throw new EpicEnergyException("Indirizzo non presente");
		}
		//setto il fatturato annuale
		cliente.calcolaFatturatoAnnuale();
		return clienteRepo.save(cliente);
	}
	
	public Cliente update(Cliente cliente, Long id) {
		//Controllo che l'email sia valida
		if(!isEmailValid(cliente.getEmail())) {
			throw new EpicEnergyException("Email non valida");
		}
		//controlo che l'email sia unica
		Optional<Cliente> cEmail = clienteRepo.findByEmail(cliente.getEmail());
		if(cEmail.isPresent()) {
			throw new EpicEnergyException("Email gia' presente");
		}
		//controlo che la pec sia unica
		Optional<Cliente> cPec = clienteRepo.findByPec(cliente.getPec());
		if(cPec.isPresent()) {
			throw new EpicEnergyException("Pec gia' presente");
		}
		//inizio a settare i campi
		Cliente c = findById(id).get();
		//setto i due indirizzi
		Optional<Indirizzo> indirizzoLegale = indirizzoRepo.findById(cliente.getIndirizzoSedeLegale().getId());
		if(indirizzoLegale.isPresent()) {
			c.setIndirizzoSedeLegale(indirizzoLegale.get());
		} else {
			throw new EpicEnergyException("Indirizzo non presente");
		}
		Optional<Indirizzo> indirizzoOperativo = indirizzoRepo.findById(cliente.getIndirizzoSedeOperativa().getId());
		if(indirizzoOperativo.isPresent()) {
			c.setIndirizzoSedeLegale(indirizzoLegale.get());
		} else {
			throw new EpicEnergyException("Indirizzo non presente");
		}
		c.setCognomeContatto(cliente.getCognomeContatto());
		c.setDataInserimento(cliente.getDataInserimento());
		c.setDataUltimoContratto(cliente.getDataUltimoContratto());
		c.setEmail(cliente.getEmail());
		c.setFatture(cliente.getFatture());
		c.setNomeContatto(cliente.getNomeContatto());
		c.setPartitaIva(cliente.getPartitaIva());
		c.setPec(cliente.getPec());
		c.setRagioneSociale(cliente.getRagioneSociale());
		c.setTelefono(cliente.getTelefono());
		c.setTelefonoContatto(cliente.getTelefonoContatto());
		c.setTipo(cliente.getTipo());
		c.calcolaFatturatoAnnuale();
		return clienteRepo.save(c);
	}
	
	//controlla che l'email sia valida(ad es: sia presente la @)
	private boolean isEmailValid(String email) {
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
