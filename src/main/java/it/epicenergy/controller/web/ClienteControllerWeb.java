package it.epicenergy.controller.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.epicenergy.exception.EpicEnergyException;
import it.epicenergy.model.Cliente;
import it.epicenergy.model.Indirizzo;
import it.epicenergy.service.ClienteService;
import it.epicenergy.service.IndirizzoService;

@Controller
@RequestMapping("/web")
public class ClienteControllerWeb {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private IndirizzoService indirizzoService;
	
	
	@GetMapping("/index")
	public ModelAndView homePage() {
		return new ModelAndView("index");
	}
	
	@GetMapping("/clienti")
	public ModelAndView getAllClienti(Pageable pageable) {
		ModelAndView viewClienti = new ModelAndView("visualizzaClienti");
		Page<Cliente> clienti = clienteService.findAll(pageable);
		viewClienti.addObject("clienti", clienti);
		return viewClienti;
	}
	
	@GetMapping("/eliminaCliente/{id}")
	public ModelAndView cancellaCliente(@PathVariable Long id, Pageable pageable) {
		clienteService.delete(id);
		return getAllClienti(pageable);
	}
	
	@GetMapping("/mostraFormAggiungiCliente")
	public ModelAndView showFormAggiungi() {
		return new ModelAndView("aggiungiCliente", "cliente", new Cliente());
	}
	
	@PostMapping("/aggiungiCliente")
	public ModelAndView aggiungiCliente(@ModelAttribute("cliente") Cliente cliente, Pageable pageable) {
		try {
			//Setto l'indirizzo della sede legale
			String viaLegale = cliente.getIndirizzoSedeLegale().getVia();
			String civicoLegale = cliente.getIndirizzoSedeLegale().getCivico();
			String capLegale = cliente.getIndirizzoSedeLegale().getCap();
			Indirizzo indirizzoLegale = indirizzoService.findByViaAndCivicoAndCap(viaLegale, civicoLegale, capLegale);
			cliente.setIndirizzoSedeLegale(indirizzoLegale);
			//Setto l'indirizzo della sede operativa
			String viaOperativa = cliente.getIndirizzoSedeOperativa().getVia();
			String civicoOperativa = cliente.getIndirizzoSedeOperativa().getCivico();
			String capOperativa = cliente.getIndirizzoSedeOperativa().getCap();
			Indirizzo indirizzoOperativa = indirizzoService.findByViaAndCivicoAndCap(viaOperativa, civicoOperativa, capOperativa);
			cliente.setIndirizzoSedeOperativa(indirizzoOperativa);
			//Salvo il cliente
			clienteService.save(cliente);
			return getAllClienti(pageable);
		} catch(EpicEnergyException ex) {
			return new ModelAndView("errore", "msg", ex.getMessage());
		}
	}
	
	@GetMapping("/mostraFormAggiornaCliente/{id}")
	public ModelAndView showFormAggiorna(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("aggiornaCliente", "cliente", new Cliente());
		mv.addObject("cliente", clienteService.findById(id).get());
		return mv;
	}
	
	@PostMapping("/aggiornaCliente/{id}")
	public ModelAndView aggiornaCliente(@PathVariable Long id, @ModelAttribute("cliente") Cliente cliente, Pageable pageable) {
		try {
			//Setto l'indirizzo della sede legale
			String viaLegale = cliente.getIndirizzoSedeLegale().getVia();
			String civicoLegale = cliente.getIndirizzoSedeLegale().getCivico();
			String capLegale = cliente.getIndirizzoSedeLegale().getCap();
			Indirizzo indirizzoLegale = indirizzoService.findByViaAndCivicoAndCap(viaLegale, civicoLegale, capLegale);
			cliente.setIndirizzoSedeLegale(indirizzoLegale);
			//Setto l'indirizzo della sede operativa
			String viaOperativa = cliente.getIndirizzoSedeOperativa().getVia();
			String civicoOperativa = cliente.getIndirizzoSedeOperativa().getCivico();
			String capOperativa = cliente.getIndirizzoSedeOperativa().getCap();
			Indirizzo indirizzoOperativa = indirizzoService.findByViaAndCivicoAndCap(viaOperativa, civicoOperativa, capOperativa);
			cliente.setIndirizzoSedeOperativa(indirizzoOperativa);
			//Salvo il cliente
			cliente.setId(id);
			clienteService.update(cliente, id);
			return getAllClienti(pageable);
		} catch(EpicEnergyException ex) {
			return new ModelAndView("errore", "msg", ex.getMessage());
		}
	}
	

}
