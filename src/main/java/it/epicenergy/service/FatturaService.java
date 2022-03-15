package it.epicenergy.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicenergy.exception.EpicEnergyException;
import it.epicenergy.model.Cliente;
import it.epicenergy.model.Fattura;
import it.epicenergy.repository.ClienteRepository;
import it.epicenergy.repository.FatturaRepository;

@Service
public class FatturaService {

	@Autowired
	private FatturaRepository fatturaRepo;
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	public Page<Fattura> findAll(Pageable pageable){
		return fatturaRepo.findAll(pageable);
	}
	
	public Page<Fattura> findByClienteId(Long clienteId, Pageable pageable) {
		return fatturaRepo.findByClienteId(clienteId, pageable);
	}
	
	public Page<Fattura> findByStato(String stato, Pageable pageable) {
		return fatturaRepo.findByStato(stato, pageable);
	}
	
	public Page<Fattura> findByData(LocalDate data, Pageable pageable){
		return fatturaRepo.findByData(data, pageable);
	}
	
	public Page<Fattura> findByAnno(Integer anno, Pageable pageable){
		return fatturaRepo.findByAnno(anno, pageable);
	}
	
	public Page<Fattura> findByImportoBetween(BigDecimal val1, BigDecimal val2,Pageable pageable){
		if(val1.compareTo(val2)==1) {
			return fatturaRepo.findByImportoBetween(val2, val1, pageable); 	//val1>val2
		} else {
			return fatturaRepo.findByImportoBetween(val1, val2, pageable);	//val2>val1
		}
	}
	
	public Optional<Fattura> findById(Long id){
		return fatturaRepo.findById(id);
	}
	
	public void delete(Long id) {
		Fattura fattura = findById(id).get();
		Cliente c = clienteRepo.findById(fattura.getCliente().getId()).get();
		List<Fattura> fatture = new ArrayList<>();
		for(Fattura f: c.getFatture()) {
			if(f.getId()!=id)
				fatture.add(f);
		}
		c.setFatture(fatture);
		fatturaRepo.deleteById(id);
		//aggiorno il fatturato annuale
		if(fattura.getAnno()==LocalDate.now().getYear()) {
			c.calcolaFatturatoAnnuale();
			clienteRepo.save(c);
		}
	}

	public Fattura save(Fattura fattura) {
		//controllo che'importo non sia negativo
		if(fattura.getImporto().signum()==-1) {
			throw new EpicEnergyException("L'importo non puo' essere negativo");
		}
		//Controllo che il cliente esista
		Optional<Cliente> cliente = clienteRepo.findById(fattura.getCliente().getId());
		if(!cliente.isPresent()) {
			throw new EpicEnergyException("Il cliente con id :"+fattura.getCliente().getId()+" nn esiste");
		}
		fattura.setCliente(cliente.get());
		fatturaRepo.save(fattura);
		//aggiorno l'importo totale del cliente
		if(fattura.getAnno()==LocalDate.now().getYear()) {
			cliente.get().calcolaFatturatoAnnuale();
			clienteRepo.save(cliente.get());
		}
		return fattura;
	}

	public Fattura update(Fattura fattura, Long id) {
		//controllo che'importo non sia negativo
		if(fattura.getImporto().signum()==-1) {
			throw new EpicEnergyException("L'importo non puo' essere negativo");
		}
		//Controllo che il cliente esista
		Optional<Cliente> cliente = clienteRepo.findById(fattura.getCliente().getId());
		if(!cliente.isPresent()) {
			throw new EpicEnergyException("Il cliente con id :"+fattura.getCliente().getId()+" nn esiste");
		}
		Fattura f = findById(id).get();
		f.setCliente(cliente.get());
		f.setAnno(fattura.getAnno());
		f.setData(fattura.getData());
		f.setImporto(fattura.getImporto());
		f.setNumero(fattura.getNumero());
		f.setStato(fattura.getStato());
		fatturaRepo.save(f);
		//aggiorno l'importo totale del cliente
		if(fattura.getAnno()==LocalDate.now().getYear()) {
			cliente.get().calcolaFatturatoAnnuale();
			clienteRepo.save(cliente.get());
		}
		return f;
	}

}
