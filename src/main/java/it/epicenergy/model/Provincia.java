package it.epicenergy.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private String regione;
	@OneToMany(mappedBy = "provincia", fetch = FetchType.EAGER)
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	Set<Comune> comuni;
	
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
}
