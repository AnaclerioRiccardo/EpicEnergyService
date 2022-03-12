package it.epicenergy.util;

import java.io.FileReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;

import it.epicenergy.model.Comune;
import it.epicenergy.model.Provincia;
import it.epicenergy.repository.ComuneRepository;
import it.epicenergy.repository.ProvinciaRepository;

@Component
public class DataLoaderRunner implements CommandLineRunner {

	@Autowired
	private ProvinciaRepository provinciaRepo;
	
	@Autowired
	private ComuneRepository comuneRepo;

	@Override
	public void run(String... args) throws Exception {
		initComune();
		initProvince();

	}
	
	private void initComune() throws Exception {
		try (CSVReader csvReader = new CSVReader(new FileReader("comuni-italiani.csv"));) {
		    String[] values = null;
		    csvReader.readNext();	//nel csv ho la prima riga l'intestazione, quindi la salto
		    Optional<Provincia> p;
		    Provincia provincia;
		    while ((values = csvReader.readNext()) != null) {
		    	p = provinciaRepo.findById(Long.valueOf(values[0]));
		    	if(p.isPresent()) {
		    		comuneRepo.save(new Comune(rimpiazza(values[2]), p.get()));
		    	} else {
		    		//se la provincia non esiste la salvo
		    		provincia = p.get();
		    		provincia.setId(Long.valueOf(values[0]));
		    		provincia.setNome(rimpiazza(values[3]));
		    		provinciaRepo.save(provincia);
		    		comuneRepo.save(new Comune(values[2], provincia));
		    	}
		    }
		}
	}
	
	private void initProvince() throws Exception {
		try (CSVReader csvReader = new CSVReader(new FileReader("province-italiane.csv"));) {
		    String[] values = null;
		    csvReader.readNext();	//nel csv ho la prima riga l'intestazione, quindi la salto
		    Optional<Provincia> p;
		    Provincia provincia;
		    String nome;
		    while ((values = csvReader.readNext()) != null) {
		    	nome=values[1];
		    	p = provinciaRepo.findByNomeLike("%"+nome+"%");
		    	if(p.isPresent()) {
		    		//se la provincia e' presente gli setto i valori
		    		provincia = p.get();
		    		provincia.setSigla(values[0]);
		    		provincia.setRegione(values[2]);
		    		provinciaRepo.save(provincia);
		    	} else {
		    		//altrimenti la salvo
		    		provinciaRepo.save(new Provincia(values[0], values[1], values[2]));
		    	}
		    }
		}
	}
	
	//rimpiazzo il - con una spazio
	private String rimpiazza(String nome) {
		return nome.replace('-', ' ');
	}

}
