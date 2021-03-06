package org.chapa.papJava.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class Persona {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String nombre;
	private String password;
	

	public Persona() {
		this.nombre = "Juanito";
	}
	
	public Persona (String nombre, String password) {
		this.nombre = nombre;
		this.password = encriptar(password);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = encriptar(password);
	}
	
	//Método que codifica las contraseñas de los usuarios
	private String encriptar(String password) {
		return (new BCryptPasswordEncoder()).encode(password);
	}
}
