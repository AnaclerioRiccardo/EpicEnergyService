package it.epicenergy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Provincia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String sigla;
	@Column(unique = true)
	private String nome;
	@Column(unique = true)
	private Integer codiceProvincia;
	private String regione;

	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@OneToMany(mappedBy = "provincia")
	List<Comune> comuni  = new ArrayList<>();
	
	//Costruttore
	public Provincia(String sigla, String nome, String regione) {
		this.sigla=sigla;
		this.nome=nome;
		this.regione=regione;
	}
	
	//Metodi
	public void aggiungiComune(Comune comune) {
		comuni.add(comune);
	}
	
	@Override
	public String toString() {
		return "Id: "+id+" Nome: "+nome+" Sigla: "+sigla+" Codice provincia: "+codiceProvincia+" Regione: "+regione+"\n";
	}
}
