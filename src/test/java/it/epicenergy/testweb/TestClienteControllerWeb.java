package it.epicenergy.testweb;

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
class TestClienteControllerWeb {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser
	final void testVisualizzaClienti() throws Exception {
		 mockMvc.perform(MockMvcRequestBuilders.get("/web/clienti")).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void testOrdinaFatturatoAnnuale() throws Exception {
		 mockMvc.perform(MockMvcRequestBuilders.get("/web/ordinaFatturatoAnnuale")).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void testOrdinaDataInserimento() throws Exception {
		 mockMvc.perform(MockMvcRequestBuilders.get("/web/ordinaDataInserimento")).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void testOrdinaDataUltimoContatto() throws Exception {
		 mockMvc.perform(MockMvcRequestBuilders.get("/web/ordinaDataUltimoContatto")).andExpect(status().isOk());	
	}
		
	@Test
	@WithMockUser
	final void testOrdinaProvinciaSedeLegale() throws Exception {
		 mockMvc.perform(MockMvcRequestBuilders.get("/web/ordinaProvinciaSedeLegale")).andExpect(status().isOk());	
	}

}
