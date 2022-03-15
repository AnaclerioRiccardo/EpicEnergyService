package it.epicenergy.testrest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.epicenergy.model.Fattura;
import it.epicenergy.repository.ClienteRepository;


@SpringBootTest
@AutoConfigureMockMvc
class TestFatturaController {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ClienteRepository clienteRepo;

	@Test
	@WithMockUser
	final void testGetAll() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/fattura")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void testGetById() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/fattura/1")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void findByClienteId() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/fattura/cliente/1")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void findByStato() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/fattura/stato/pagato")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void findByAnno() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/fattura/anno/2022")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void findByImportoBetween() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/fattura/importo/500/2000")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testDeleteById() throws Exception {
		this.mockMvc.perform(delete("http://localhost:8080/api/fattura/1")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPost() throws Exception {
		String body = "{\r\n"
				+ "  \"anno\": 2022,\r\n"
				+ "  \"data\": \"2022-02-01\",\r\n"
				+ "  \"importo\": 880,\r\n"
				+ "  \"numero\": 10,\r\n"
				+ "  \"stato\": \"PA\",\r\n"
				+ "  \"cliente\": {\r\n"
				+ "    \"id\": 3\r\n"
				+ "  }\r\n"
				+ "}";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/fattura")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isOk()).andReturn();
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPut() throws Exception {
		String body ="{\r\n"
				+ "  \"anno\": 2020,\r\n"
				+ "  \"data\": \"2020-02-01\",\r\n"
				+ "  \"importo\": 880,\r\n"
				+ "  \"numero\": 10,\r\n"
				+ "  \"stato\": \"PA\",\r\n"
				+ "  \"cliente\": {\r\n"
				+ "    \"id\": 3\r\n"
				+ "  }\r\n"
				+ "}";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/fattura/1")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isOk()).andReturn();
	}

}
