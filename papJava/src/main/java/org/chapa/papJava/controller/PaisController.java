package org.chapa.papJava.controller;

import java.util.List;

import org.chapa.papJava.entities.Pais;
import org.chapa.papJava.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaisController {
	@Autowired
	private PaisRepository paisRepository;		//Conexion con la interfaz que gestiona la BBDD
												//que necesitamos en cPost

	@GetMapping("/pais/r")
	public String r(
			ModelMap m					/* Es el empaquetado de paises que va a recibir la vista */
			) {
		/* Lista de paises que recibimos del repositorio de paises (interfaz) */
		List<Pais> paises = paisRepository.findAll();
		
		/* ModelMap con países que Spring pondrá a disposición de la
			vista que despliegue (pais/r en este caso) */
		m.put("paises", paises);
		m.put("view", "/pais/r");
		return "_t/frame";
	}
	
	
	@GetMapping("/pais/c")
	public String c(ModelMap m) {
		m.put("view", "/pais/c");
		return "_t/frame";
	}
	
	
	@PostMapping("/pais/c")
	public String cPost(@RequestParam("nombre") String nombre) { /* El nombre de pais que se recibe del form de país/c */
		String returnLocation = "";
		try {
			paisRepository.save(new Pais(nombre));	/* Guardado del país cuyo nombre llega por POST */
			returnLocation = "redirect:/pais/r";
		} catch (Exception e) {
			returnLocation = "redirect:/errorDisplay?msg=El país " + nombre + " ya existe.";
		}
		return returnLocation;
	}
}
