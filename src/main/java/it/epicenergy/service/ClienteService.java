package it.epicenergy.service;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import it.epicenergy.model.Fattura;
import it.epicenergy.model.Indirizzo;
import it.epicenergy.repository.ClienteRepository;
import it.epicenergy.repository.FatturaRepository;
import it.epicenergy.repository.IndirizzoRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private IndirizzoRepository indirizzoRepo;
	
	@Autowired
	private FatturaRepository fatturaRepo;

	//Metodi
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteRepo.findAll(pageable);
	}
	
	public Page<Cliente> findAllByOrderByFatturatoAnnuale(Pageable pageable){
		return clienteRepo.findAllByOrderByFatturatoAnnuale(pageable);
	}
	
	public Page<Cliente> findAllByOrderByDataInserimento(Pageable pageable){
		return clienteRepo.findAllByOrderByDataInserimento(pageable);
	}
	
	public Page<Cliente> findAllByOrderByDataUltimoContatto(Pageable pageable){
		return clienteRepo.findAllByOrderByDataUltimoContatto(pageable);
	}
	
	public Page<Cliente> findAllByOrderByIndirizzoSedeLegaleComuneProvinciaNome(Pageable pageable){
		return clienteRepo.findAllByOrderByIndirizzoSedeLegaleComuneProvinciaNome(pageable);
	}
	
	public Page<Cliente> findByFatturatoAnnualeBetween(BigDecimal val1, BigDecimal val2,Pageable pageable){
		if(val1.compareTo(val2)==1) {
			return clienteRepo.findByFatturatoAnnualeBetween(val2, val1, pageable);	//val1>val2
		} else {
			return clienteRepo.findByFatturatoAnnualeBetween(val1, val2, pageable);	//val2>val1
		}
	}
	
	public Page<Cliente> findByDataInserimento(LocalDate dataInserimento, Pageable pageable){
		return clienteRepo.findByDataInserimento(dataInserimento, pageable);
	}
	
	public Page<Cliente> findByDataUltimoContatto(LocalDate dataUltimoContatto, Pageable pageable){
		return clienteRepo.findByDataUltimoContatto(dataUltimoContatto, pageable);
	}
	
	public Page<Cliente> findByRagioneSocialeLike(String ragioneSociale, Pageable pageable){
		return clienteRepo.findByRagioneSocialeLike("%"+ragioneSociale+"%", pageable);
	}

	public Optional<Cliente> findById(Long id) {
		return clienteRepo.findById(id);
	}

	public void delete(Long id) {
		clienteRepo.delete(findById(id).get());	
	}

	public Cliente save(Cliente cliente) {
		//Controllo che la ragione sociale sia valido
		if(clienteRepo.findByRagioneSociale(cliente.getRagioneSociale()).isPresent()) {
			throw new EpicEnergyException("Ragione Sociale gia' presente");
		}
		//Controllo che l'email sia valida
		if(!isEmailValid(cliente.getEmail())) {
			throw new EpicEnergyException("Email non valida");
		}
		//controlo che l'email sia unica
		Optional<Cliente> cEmail = clienteRepo.findByEmail(cliente.getEmail());
		if(cEmail.isPresent()) {
			throw new EpicEnergyException("Email gia' presente");
		}
		//Controllo che i numeri di telefono siano validi
		if(!isTelValid(cliente.getTelefono())) {
			throw new EpicEnergyException("Telefono non valido");
		}
		if(!isTelValid(cliente.getTelefonoContatto())) {
			throw new EpicEnergyException("Telefono del contatto non valido");
		}
		//Controlo che la pec sia unica
		Optional<Cliente> cPec = clienteRepo.findByPec(cliente.getPec());
		if(cPec.isPresent()) {
			throw new EpicEnergyException("Pec gia' presente");
		}
		//Controllo che la pec sia valida
		if(!isEmailValid(cliente.getPec())) {
			throw new EpicEnergyException("Pec non valida");
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
		//Controllo che la Pec sia valida
		if(!isEmailValid(cliente.getPec())) {
			throw new EpicEnergyException("Pec non valida");
		}
		//Controllo che i numeri di telefono siano validi
		if(!isTelValid(cliente.getTelefono())) {
			throw new EpicEnergyException("Telefono non valido");
		}
		if(!isTelValid(cliente.getTelefonoContatto())) {
			throw new EpicEnergyException("Telefono del contatto non valido");
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
		List<Fattura> fatture = fatturaRepo.findAllByClienteId(id);
		c.setCognomeContatto(cliente.getCognomeContatto());
		c.setDataInserimento(cliente.getDataInserimento());
		c.setDataUltimoContatto(cliente.getDataUltimoContatto());
		c.setEmail(cliente.getEmail());
		c.setFatture(fatture);
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
	
	//Controlla che il numero telefonico sia valido
	private boolean isTelValid(String tel) { 
		if(tel.charAt(0)!='+' && !Character.isDigit(tel.charAt(0)))
			return false;
		for (int i=1; i<tel.length(); i++) {                          
			if (!Character.isDigit(tel.charAt(i)))                
				return false;                    
		}         
		return true;     
	}

}
