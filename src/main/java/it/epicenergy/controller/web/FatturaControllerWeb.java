package it.epicenergy.controller.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.epicenergy.exception.EpicEnergyException;
import it.epicenergy.model.Cliente;
import it.epicenergy.model.Fattura;
import it.epicenergy.service.ClienteService;
import it.epicenergy.service.FatturaService;

@Controller
@RequestMapping("/web")
public class FatturaControllerWeb {

	@Autowired
	private FatturaService fatturaService;
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/fatture")
	public ModelAndView getAllFatture(Pageable pageable) {
		Page<Fattura> fatture = fatturaService.findAll(pageable);
		return new ModelAndView("visualizzaFatture", "fatture", fatture);
	}
	
	@GetMapping("/eliminaFattura/{id}")
	public ModelAndView cancellaCliente(@PathVariable Long id, Pageable pageable) {
		fatturaService.delete(id);
		return getAllFatture(pageable);
	}
	
	@GetMapping("/mostraFormAggiungiFattura")
	public ModelAndView showFormAggiungi(Pageable pageable) {
		Page<Cliente> clienti = clienteService.findAll(pageable);
		ModelAndView mv = new ModelAndView("aggiungiFattura", "fattura", new Fattura());
		mv.addObject("clienti", clienti);
		return mv;
	}
	
	@PostMapping("/aggiungiFattura")
	public ModelAndView aggiungiCliente(@ModelAttribute("fattura") Fattura fattura, Pageable pageable) {
		try {
			fatturaService.save(fattura);
			return getAllFatture(pageable);
		}catch(EpicEnergyException ex) {
			return new ModelAndView("errore", "msg", ex.getMessage());
		}
	}
	
	@GetMapping("/mostraFormAggiornaFattura/{id}")
	public ModelAndView showFormAggiorna(@PathVariable Long id, Pageable pageable) {
		ModelAndView mv = new ModelAndView("aggiornaFattura", "fattura", new Fattura());
		mv.addObject("fattura", fatturaService.findById(id).get());
		mv.addObject("clienti", clienteService.findAll(pageable));
		return mv;
	}
		
	@PostMapping("/aggiornaFattura/{id}")
	public ModelAndView aggiornaCliente(@PathVariable Long id, @ModelAttribute("fattura") Fattura fattura, Pageable pageable) {
		try {
			fatturaService.update(fattura, id);
			return getAllFatture(pageable);
		} catch(EpicEnergyException ex) {
			return new ModelAndView("errore", "msg", ex.getMessage());
		}
	}

}
