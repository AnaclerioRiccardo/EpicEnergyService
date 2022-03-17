package it.epicenergy.testweb;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class TestFatturaClienteControllerWeb {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser
	final void testVisualizzaFatture() throws Exception {
		 mockMvc.perform(MockMvcRequestBuilders.get("/web/fatture")).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void testShowFormAggiungi() throws Exception {
		 mockMvc.perform(MockMvcRequestBuilders.get("/web/mostraFormAggiungiFattura")).andExpect(status().isOk());	
	}

}
