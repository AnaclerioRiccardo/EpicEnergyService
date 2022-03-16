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
import it.epicenergy.model.Comune;
import it.epicenergy.model.Indirizzo;
import it.epicenergy.service.ClienteService;
import it.epicenergy.service.ComuneService;
import it.epicenergy.service.IndirizzoService;

@Controller
@RequestMapping("/web")
public class ClienteControllerWeb {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private IndirizzoService indirizzoService;
	
	@Autowired
	private ComuneService comuneService;
	
	
	@GetMapping("/homepage")
	public ModelAndView homePage() {
		return new ModelAndView("homepage");
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
	public ModelAndView showFormAggiungi(Pageable pageable) {
		ModelAndView mv = new ModelAndView("aggiungiCliente", "cliente", new Cliente()); 
		mv.addObject("comuni", comuneService.findAllByOrderByNome());
		return mv;
	}
	
	@PostMapping("/aggiungiCliente")
	public ModelAndView aggiungiCliente(@ModelAttribute("cliente") Cliente cliente, Pageable pageable) {
		try {
			//Setto l'indirizzo della sede legale
			String viaLegale = cliente.getIndirizzoSedeLegale().getVia();
			String civicoLegale = cliente.getIndirizzoSedeLegale().getCivico();
			String capLegale = cliente.getIndirizzoSedeLegale().getCap();
			String localitaLegale = cliente.getIndirizzoSedeLegale().getLocalita();
			Optional<Indirizzo> indirizzoLegale = indirizzoService.findByViaAndCivicoAndCapAndLocalita(viaLegale, civicoLegale, capLegale, localitaLegale);
			//Controllo se l'indirizzo esiste gia' altrimenti lo creo
			if(indirizzoLegale.isPresent()) {
				cliente.setIndirizzoSedeLegale(indirizzoLegale.get());
			} else {
				Indirizzo il = new Indirizzo();
				il.setCap(capLegale);
				il.setCivico(civicoLegale);
				il.setVia(viaLegale);
				il.setLocalita(localitaLegale);
				Comune comuneLegale = comuneService.findById(cliente.getIndirizzoSedeLegale().getComune().getId()).get();
				il.setComune(comuneLegale);
				indirizzoService.save(il);
				cliente.setIndirizzoSedeLegale(il);
			}
			//Setto l'indirizzo della sede operativa
			String viaOperativa = cliente.getIndirizzoSedeOperativa().getVia();
			String civicoOperativa = cliente.getIndirizzoSedeOperativa().getCivico();
			String capOperativa = cliente.getIndirizzoSedeOperativa().getCap();
			String localitaOperativa = cliente.getIndirizzoSedeOperativa().getLocalita();
			Optional<Indirizzo> indirizzoOperativa = indirizzoService.findByViaAndCivicoAndCapAndLocalita(viaOperativa, civicoOperativa, capOperativa, localitaOperativa);
			//Controllo se l'indirizzo esiste gia' altrimenti lo creo
			if(indirizzoOperativa.isPresent()) {
				cliente.setIndirizzoSedeOperativa(indirizzoOperativa.get());
			} else {
				Indirizzo io = new Indirizzo();
				io.setCap(capOperativa);
				io.setCivico(civicoOperativa);
				io.setLocalita(localitaOperativa);
				io.setVia(viaOperativa);
				Comune comuneOperativa = comuneService.findById(cliente.getIndirizzoSedeOperativa().getComune().getId()).get();
				io.setComune(comuneOperativa);
				indirizzoService.save(io);
				cliente.setIndirizzoSedeOperativa(io);
			}
			//Salvo il cliente
			clienteService.save(cliente);
			return getAllClienti(pageable);
		} catch(EpicEnergyException ex) {
			return new ModelAndView("errore", "msg", ex.getMessage());
		}
	}
	
	@GetMapping("/mostraFormAggiornaCliente/{id}")
	public ModelAndView showFormAggiorna(@PathVariable Long id, Pageable pageable) {
		ModelAndView mv = new ModelAndView("aggiornaCliente", "cliente", new Cliente());
		mv.addObject("cliente", clienteService.findById(id).get());
		mv.addObject("comuni", comuneService.findAllByOrderByNome());
		return mv;
	}
	
	@PostMapping("/aggiornaCliente/{id}")
	public ModelAndView aggiornaCliente(@PathVariable Long id, @ModelAttribute("cliente") Cliente cliente, Pageable pageable) {
		try {
			//Setto l'indirizzo della sede legale
			String viaLegale = cliente.getIndirizzoSedeLegale().getVia();
			String civicoLegale = cliente.getIndirizzoSedeLegale().getCivico();
			String capLegale = cliente.getIndirizzoSedeLegale().getCap();
			String localitaLegale = cliente.getIndirizzoSedeLegale().getLocalita();
			Optional<Indirizzo> indirizzoLegale = indirizzoService.findByViaAndCivicoAndCapAndLocalita(viaLegale, civicoLegale, capLegale, localitaLegale);
			//Controllo se l'indirizzo esiste gia' altrimenti lo creo
			if(indirizzoLegale.isPresent()) {
				cliente.setIndirizzoSedeLegale(indirizzoLegale.get());
			} else {
				Indirizzo il = new Indirizzo();
				il.setCap(capLegale);
				il.setCivico(civicoLegale);
				il.setVia(viaLegale);
				il.setLocalita(localitaLegale);
				Comune comuneLegale = comuneService.findById(cliente.getIndirizzoSedeLegale().getComune().getId()).get();
				il.setComune(comuneLegale);
				indirizzoService.save(il);
				cliente.setIndirizzoSedeLegale(il);
			}
			//Setto l'indirizzo della sede operativa
			String viaOperativa = cliente.getIndirizzoSedeOperativa().getVia();
			String civicoOperativa = cliente.getIndirizzoSedeOperativa().getCivico();
			String capOperativa = cliente.getIndirizzoSedeOperativa().getCap();
			String localitaOperativa = cliente.getIndirizzoSedeOperativa().getLocalita();
			Optional<Indirizzo> indirizzoOperativa = indirizzoService.findByViaAndCivicoAndCapAndLocalita(viaOperativa, civicoOperativa, capOperativa, localitaOperativa);
			//Controllo se l'indirizzo esiste gia' altrimenti lo creo
			if(indirizzoOperativa.isPresent()) {
				cliente.setIndirizzoSedeOperativa(indirizzoOperativa.get());
			} else {
				Indirizzo io = new Indirizzo();
				io.setCap(capOperativa);
				io.setCivico(civicoOperativa);
				io.setLocalita(localitaOperativa);
				io.setVia(viaOperativa);
				Comune comuneOperativa = comuneService.findById(cliente.getIndirizzoSedeOperativa().getComune().getId()).get();
				io.setComune(comuneOperativa);
				indirizzoService.save(io);
				cliente.setIndirizzoSedeOperativa(io);
			}
			//Salvo il cliente
			cliente.setId(id);
			clienteService.update(cliente, id);
			return getAllClienti(pageable);
		} catch(EpicEnergyException ex) {
			return new ModelAndView("errore", "msg", ex.getMessage());
		}
	}
	
	@GetMapping("/ordinaFatturatoAnnuale")
	public ModelAndView ordinaFatturatoAnnuale(Pageable pageable) {
		ModelAndView viewClienti = new ModelAndView("visualizzaClienti");
		Page<Cliente> clienti = clienteService.findAllByOrderByFatturatoAnnuale(pageable);
		viewClienti.addObject("clienti", clienti);
		return viewClienti;
	}
	
	@GetMapping("/ordinaDataInserimento")
	public ModelAndView ordinaDataInserimento(Pageable pageable) {
		ModelAndView viewClienti = new ModelAndView("visualizzaClienti");
		Page<Cliente> clienti = clienteService.findAllByOrderByDataInserimento(pageable);
		viewClienti.addObject("clienti", clienti);
		return viewClienti;
	}
	
	@GetMapping("/ordinaDataUltimoContatto")
	public ModelAndView ordinaDataUltimoContatto(Pageable pageable) {
		ModelAndView viewClienti = new ModelAndView("visualizzaClienti");
		Page<Cliente> clienti = clienteService.findAllByOrderByDataUltimoContatto(pageable);
		viewClienti.addObject("clienti", clienti);
		return viewClienti;
	}
	
	@GetMapping("/ordinaProvinciaSedeLegale")
	public ModelAndView ordinaProvinciaSedeLegale(Pageable pageable) {
		ModelAndView viewClienti = new ModelAndView("visualizzaClienti");
		Page<Cliente> clienti = clienteService.findAllByOrderByIndirizzoSedeLegaleComuneProvinciaNome(pageable);
		viewClienti.addObject("clienti", clienti);
		return viewClienti;
	}

}
