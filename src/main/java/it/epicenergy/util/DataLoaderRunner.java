package it.epicenergy.util;

import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;

import it.epicenergy.model.Cliente;
import it.epicenergy.model.Comune;
import it.epicenergy.model.Fattura;
import it.epicenergy.model.Indirizzo;
import it.epicenergy.model.Provincia;
import it.epicenergy.model.TipoCliente;
import it.epicenergy.repository.ClienteRepository;
import it.epicenergy.repository.ComuneRepository;
import it.epicenergy.repository.IndirizzoRepository;
import it.epicenergy.repository.ProvinciaRepository;
import it.epicenergy.service.FatturaService;

@Component
public class DataLoaderRunner implements CommandLineRunner {

	@Autowired
	private ProvinciaRepository provinciaRepo;
	
	@Autowired
	private ComuneRepository comuneRepo;
	
	@Autowired
	private IndirizzoRepository indirizzoRepo;
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private FatturaService fatturaService;

	@Override
	public void run(String... args) throws Exception {
		//Parto con l'inserire i comuni e contemporaneamente salvo le province di cui essi fanno parte
		initComune();
		//dopodiche aggiorno le informazioni delle province che avevo gia' aggiunto con i comuni ed aggiungo quelle mancanti
		initProvince();
		//aggiungo qualche indirizzo
		initIndirizzi();
		//aggiungo qualche cliente
		initClienti();
		//aggiungo qualche fattura
		initFatture();
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
		    	nome=rimpiazza(valore[1]);
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
	
	
	private void initIndirizzi() throws Exception {
		Indirizzo i1 = new Indirizzo();
		i1.setCap("26020");
		i1.setCivico("30");
		i1.setLocalita("Campagna");
		i1.setVia("Chiesa");
		i1.setComune(comuneRepo.findById(1l).get());
		indirizzoRepo.save(i1);
		
		Indirizzo i2 = new Indirizzo();
		i2.setCap("26020");
		i2.setCivico("42");
		i2.setLocalita("Campagna");
		i2.setVia("Grumello");
		i2.setComune(comuneRepo.findById(600l).get());
		indirizzoRepo.save(i2);
		
		Indirizzo i3 = new Indirizzo();
		i3.setCap("26100");
		i3.setCivico("12");
		i3.setLocalita("Citta");
		i3.setVia("Dante");
		i3.setComune(comuneRepo.findById(1000l).get());
		indirizzoRepo.save(i3);
		
		Indirizzo i4 = new Indirizzo();
		i4.setCap("22222");
		i4.setCivico("15");
		i4.setLocalita("Marittima");
		i4.setVia("Mare");
		i4.setComune(comuneRepo.findById(1500l).get());
		indirizzoRepo.save(i4);
		
		Indirizzo i5 = new Indirizzo();
		i5.setCap("00015");
		i5.setCivico("1B");
		i5.setLocalita("Montagna");
		i5.setVia("Dei massi");
		i5.setComune(comuneRepo.findById(2000l).get());
		indirizzoRepo.save(i5);
	}
	
	private void initClienti() throws Exception {
		Cliente c1 = new Cliente();
		c1.setCognomeContatto("Anaclerio");
		c1.setNomeContatto("Riccardo");
		c1.setDataInserimento(LocalDate.parse("2000-01-01"));
		c1.setDataUltimoContatto(LocalDate.parse("2000-02-02"));
		c1.setEmail("riccardo@gmail.com");
		c1.setIndirizzoSedeLegale(indirizzoRepo.getById(1l));
		c1.setIndirizzoSedeOperativa(indirizzoRepo.getById(1l));
		c1.setPartitaIva("0000000000");
		c1.setPec("riccardo@pec.com");
		c1.setRagioneSociale("Google");
		c1.setTelefono("123456789");
		c1.setTelefonoContatto("987654321");
		c1.setTipo(TipoCliente.SPA);
		clienteRepo.save(c1);
		
		Cliente c2 = new Cliente();
		c2.setCognomeContatto("Rossi");
		c2.setNomeContatto("Mario");
		c2.setDataInserimento(LocalDate.parse("2020-01-01"));
		c2.setDataUltimoContatto(LocalDate.parse("2020-02-02"));
		c2.setEmail("mariorossi@gmail.com");
		c2.setIndirizzoSedeLegale(indirizzoRepo.getById(2l));
		c2.setIndirizzoSedeOperativa(indirizzoRepo.getById(3l));
		c2.setPartitaIva("11111111111");
		c2.setPec("mariorossi@pec.com");
		c2.setRagioneSociale("SuperMario");
		c2.setTelefono("111222333");
		c2.setTelefonoContatto("333222111");
		c2.setTipo(TipoCliente.PA);
		clienteRepo.save(c2);
		
		Cliente c3 = new Cliente();
		c3.setCognomeContatto("Verdi");
		c3.setNomeContatto("Luigi");
		c3.setDataInserimento(LocalDate.parse("2021-10-20"));
		c3.setDataUltimoContatto(LocalDate.parse("2021-12-12"));
		c3.setEmail("luigiverdi@libero.com");
		c3.setIndirizzoSedeLegale(indirizzoRepo.getById(4l));
		c3.setIndirizzoSedeOperativa(indirizzoRepo.getById(4l));
		c3.setPartitaIva("22222222222");
		c3.setPec("luigiverdi@pec.com");
		c3.setRagioneSociale("LuigisMansion");
		c3.setTelefono("444555666");
		c3.setTelefonoContatto("666555444");
		c3.setTipo(TipoCliente.SAS);
		clienteRepo.save(c3);
		
		Cliente c4 = new Cliente();
		c4.setCognomeContatto("Rosa");
		c4.setNomeContatto("Peach");
		c4.setDataInserimento(LocalDate.parse("2015-02-28"));
		c4.setDataUltimoContatto(LocalDate.parse("2018-11-12"));
		c4.setEmail("peachrosa@hotmail.com");
		c4.setIndirizzoSedeLegale(indirizzoRepo.getById(3l));
		c4.setIndirizzoSedeOperativa(indirizzoRepo.getById(3l));
		c4.setPartitaIva("33333333333");
		c4.setPec("peachrosa@pec.com");
		c4.setRagioneSociale("CastelloPeach");
		c4.setTelefono("777888999");
		c4.setTelefonoContatto("999888777");
		c4.setTipo(TipoCliente.SRL);
		clienteRepo.save(c4);
	}
	
	private void initFatture() throws Exception {
		Fattura f1 = new Fattura();
		f1.setAnno(2022);
		f1.setData(LocalDate.parse("2022-01-01"));
		f1.setImporto(new BigDecimal("1000"));
		f1.setNumero(1);
		f1.setStato("pagato");
		f1.setCliente(clienteRepo.findById(1l).get());
		fatturaService.save(f1);
		
		Fattura f2 = new Fattura();
		f2.setAnno(2022);
		f2.setData(LocalDate.parse("2022-02-02"));
		f2.setImporto(new BigDecimal("2000"));
		f2.setNumero(2);
		f2.setStato("pagato");
		f2.setCliente(clienteRepo.findById(1l).get());
		fatturaService.save(f2);
		
		Fattura f3 = new Fattura();
		f3.setAnno(2003);
		f3.setData(LocalDate.parse("2003-03-03"));
		f3.setImporto(new BigDecimal("3000"));
		f3.setNumero(3);
		f3.setStato("non pagato");
		f3.setCliente(clienteRepo.findById(3l).get());
		fatturaService.save(f3);
		
		Fattura f4 = new Fattura();
		f4.setAnno(2004);
		f4.setData(LocalDate.parse("2004-04-04"));
		f4.setImporto(new BigDecimal("1400"));
		f4.setNumero(4);
		f4.setStato("non pagato");
		f4.setCliente(clienteRepo.findById(4l).get());
		fatturaService.save(f4);
		
		Fattura f5 = new Fattura();
		f5.setAnno(2022);
		f5.setData(LocalDate.parse("2022-02-02"));
		f5.setImporto(new BigDecimal("1600"));
		f5.setNumero(5);
		f5.setStato("non pagato");
		f5.setCliente(clienteRepo.findById(4l).get());
		fatturaService.save(f5);
	}
	
	//rimpiazzo il - con uno spazio
	private String rimpiazza(String nome) {
		return nome.replace('-', ' ');
	}

}
