package it.epicenergy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Indirizzo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String via;
	private String civico;	//String perche esiste 1A come numero civico
	private String localita;
	private String cap;	//String perche esistono cap che iniziano per 0
	
	@ManyToOne
	//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	private Comune comune;

}
