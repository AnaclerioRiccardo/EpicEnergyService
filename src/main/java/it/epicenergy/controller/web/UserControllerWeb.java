package it.epicenergy.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.epicenergy.exception.EpicEnergyException;
import it.epicenergy.model.User;
import it.epicenergy.service.UserService;

@Controller
@RequestMapping("/auth")
public class UserControllerWeb {

	@Autowired
	private UserService userService;
	
	@GetMapping("/index")
	public ModelAndView index() {
		return new ModelAndView("index");
	}
	
	
	@GetMapping("/mostraFormRegistrazione")
	public ModelAndView registrazione() {
		return new ModelAndView("registrazione", "utente", new User());
	}
	
	@PostMapping("/registraUtente")
	public ModelAndView registraUtente(@ModelAttribute("utente") User utente) {
		try {
			userService.save(utente);
			return index();
		} catch(EpicEnergyException ex) {
			return new ModelAndView("errore", "msg", ex.getMessage());
		}
	}
	
	
}
