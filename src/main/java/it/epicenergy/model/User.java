package it.epicenergy.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@Table(name = "utente")	//rinomino la tabella xk su postgress user e' una keyword di sistema
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true, nullable = false)
	private String userName;
	@Column(nullable = false)
	private String password;
	@Column(unique = true, nullable = false)
	private String email;
	private String nome;
	private String cognome;
	private boolean isActive = true; 
	
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@ManyToMany
	@JoinTable(name = "user_role",
	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();
	
}
