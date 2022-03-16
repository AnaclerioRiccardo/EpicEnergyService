package it.epicenergy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Comune {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@ManyToOne
	@JsonIgnoreProperties("comuni")
	private Provincia provincia;
	
	//Costruttore
	public Comune(String nome, Provincia provincia) {
		this.nome = nome;
		this.provincia=provincia;
	}
	
	@Override
	public String toString() {
		return "Id: "+id+" Nome: "+nome;
	}

}
