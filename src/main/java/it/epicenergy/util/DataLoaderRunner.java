package it.epicenergy.util;

import java.io.FileReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Lettuce.Cluster.Refresh;
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
		//Parto con l'inserire i comuni e contemporaneamente salvo le province di cui essi fanno parte
		initComune();
		//dopodiche aggiorno le informazioni delle province che avevo gia' aggiunto con i comuni ed aggiungo quelle mancanti
		initProvince();

	}
	
	private void initComune() throws Exception {
		try (CSVReader csvReader = new CSVReader(new FileReader("comuni-italiani.csv"));) {
		    String[] values = null;
		    csvReader.readNext();	//nel csv ho la prima riga l'intestazione, quindi la salto
		    Optional<Provincia> p;
		    String[] valore;
		    Provincia provincia;
		    while ((values = csvReader.readNext()) != null) {
		    	valore = values[0].split(";");		//nel file csv i valori sono separati da ;
		    	p = provinciaRepo.findByCodiceProvincia(Integer.valueOf(valore[0]));
		    	if(p.isPresent()) {
		    		comuneRepo.save(new Comune(rimpiazza(valore[2]), p.get()));
		    	} else {
		    		//se la provincia non esiste la salvo
		    		provincia = new Provincia();
		    		provincia.setCodiceProvincia(Integer.valueOf(valore[0]));
		    		provincia.setNome(rimpiazza(valore[3]));
		    		provinciaRepo.save(provincia);
		    		comuneRepo.save(new Comune(valore[2], provincia));
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
		    String[] valore;
		    while ((values = csvReader.readNext()) != null) {
		    	valore = values[0].split(";");		//nel file csv i valori sono separati da ;
		    	nome=valore[1];
		    	p = provinciaRepo.findByNomeLike("%"+nome+"%");
		    	if(p.isPresent()) {
		    		//se la provincia e' presente gli setto i valori
		    		provincia = p.get();
		    		provincia.setSigla(valore[0]);
		    		provincia.setRegione(valore[2]);
		    		provinciaRepo.save(provincia);
		    	} else {
		    		//altrimenti la salvo
		    		provinciaRepo.save(new Provincia(valore[0], valore[1], valore[2]));
		    	}
		    }
		}
	}
	
	//rimpiazzo il - con uno spazio
	private String rimpiazza(String nome) {
		return nome.replace('-', ' ');
	}

}
