package it.epicenergy.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.epicenergy.model.Fattura;
import it.epicenergy.service.FatturaService;

@Controller
@RequestMapping("/web")
public class FatturaControllerWeb {

	@Autowired
	private FatturaService fatturaService;
	
	@GetMapping("/fatture")
	public ModelAndView getAllFatture(Pageable pageable) {
		Page<Fattura> fatture = fatturaService.findAll(pageable);
		return new ModelAndView("visualizzaFatture", "fatture", fatture);
	}

}
