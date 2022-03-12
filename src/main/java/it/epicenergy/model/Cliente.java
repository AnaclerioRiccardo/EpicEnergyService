package it.epicenergy.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private TipoCliente tipo;
	private String ragioneSociale;
	private String partitaIva;
	@Column(unique = true)
	private String email;
	private LocalDate dataInserimento;
	private LocalDate dataUltimoContratto;
	private BigDecimal fatturatoAnnuale = new BigDecimal("0");	//lo setto a 0 al momento della creazione
	@Column(unique = true)
	private String pec;
	private String telefono;
	private String nomeContatto;
	private String cognomeContatto;
	private String telefonoContatto;
	
	@OneToOne
	private Indirizzo indirizzoSedeLegale;
	
	@OneToOne
	private Indirizzo indirizzoSedeOperativa;
	
	@OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	private List<Fattura> fatture = new ArrayList<>();
	
	//Metodi
	/*
	 * Calcola il fatturato totale dell'anno corrente
	 * 
	 * Metodo da richiamare ogni volta che viene fatta un operazione sulle fatture
	 * che sia una POST unaPUT o una DELETE
	 * ed anche nel caso di creazione della creazione o modifica del cliente in cascata
	*/
	public BigDecimal calcolaFatturatoAnnuale() {
		BigDecimal totale = new BigDecimal("0");
		if(fatture.isEmpty()) {
			return fatturatoAnnuale = totale;
		}
		for(Fattura f: fatture) {
			if(f.getAnno().equals(LocalDate.now().getYear()))
				totale = totale.add(f.getImporto());
		}
		return fatturatoAnnuale = totale;
	}
	
	public void aggiungiFattura(Fattura fattura) {
		fatture.add(fattura);
		calcolaFatturatoAnnuale();
	}
	
}
