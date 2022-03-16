package it.epicenergy.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Fattura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer anno;
	private LocalDate data;
	private BigDecimal importo;
	private Integer numero;
	private String stato;
	
	@ManyToOne
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	private Cliente cliente;
	
	@Override
	public String toString(){
		return "Id: "+id+" Numero: "+numero+" Anno: "+anno+" Data: "+data+" Importo: "+importo+" Stato: "+stato;
	}
}
