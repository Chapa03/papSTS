package org.chapa.papJava.controller;

import javax.servlet.http.HttpSession;

import org.chapa.papJava.entities.Persona;
import org.chapa.papJava.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	
	@Autowired
	private PersonaRepository personaRepository;
	
	@GetMapping("/info")
	public String info(HttpSession s, ModelMap m) {

		String mensaje = s.getAttribute("_mensaje") != null ? (String) s.getAttribute("_mensaje")
				: "Pulsa para volver a home";
		String severity = s.getAttribute("_severity") != null ? (String) s.getAttribute("_severity") : "info";
		String link = s.getAttribute("_link") != null ? (String) s.getAttribute("_link") : "/";

		s.removeAttribute("_mensaje");
		s.removeAttribute("_severity");
		s.removeAttribute("_link");

		m.put("mensaje", mensaje);
		m.put("severity", severity);
		m.put("link", link);

		m.put("view", "/_t/info");
		return "/_t/frame";
	}

	
	@GetMapping("/login")
	public String login(ModelMap m) {
		m.put("view", "home/login");
		return "_t/frame";
	}
	
	@PostMapping("/login")
	public String loginPost(
			@RequestParam("nombre") String nombre,
			@RequestParam("password") String password,
			HttpSession s
			) {
		String returnLocation = "redirect:/";
		//Bean de persona recuperado de la bbdd por el nombre
		Persona persona = personaRepository.getByNombre(nombre);
		//Try porque si getPassword no encuentra la pass, salta excepción
		//findPassword devolvería null en ese caso, sin lanzar excepción
		try {
			//Si la contraseña introducida coincide con la de la base de datos
			if (new BCryptPasswordEncoder().matches(password, persona.getPassword())) {
				//Asociar a la sesión un atributo llamado persona que contiene el bean persona
				s.setAttribute("persona", persona);		
			} else {
				returnLocation = "redirect:/error?msg=Contraseña incorrecta";
			}
		} catch (Exception e) {
			returnLocation = "redirect:/error?msg=Usuario incorrecto";
		}
		
		return returnLocation;
	}
	
	@GetMapping("/logout")
	public String logout(
			HttpSession s
			) {
		s.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/")
	public String index(ModelMap m) {
		m.put("view", "home/index");
		return "_t/frame";
	}
}
