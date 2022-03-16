package it.epicenergy.testrest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class TestClienteController {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@WithMockUser
	final void testGetAll() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/cliente")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void findAllByOrderByFatturatoAnnuale() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/cliente/ordina/fatturatoannuale")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void findAllByOrderByDataInserimento() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/cliente/ordina/datainserimento")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void findAllByOrderByDataUltimoContatto() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/cliente/ordina/dataultimocontatto")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void findAllByOrderByIndirizzoSedeLegaleComuneProvinciaNome() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/cliente/ordina/provinciasedelegale")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void findByFatturatoAnnualeBetweenMinMax() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/cliente/fatturatoannuale/500/2000")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void findByFatturatoAnnualeBetweenMaxMin() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/cliente/fatturatoannuale/2000/500")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void findByDataUltimoContatto() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/cliente/dataultimocontatto/2000-02-02")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void findByRagioneSocialeLike() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/cliente/ragionesociale/oogl")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void testGetById() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/cliente/1")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testDeleteById() throws Exception {
		this.mockMvc.perform(delete("http://localhost:8080/api/cliente/1")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPost() throws Exception {
		String body = "{\r\n"
				+ "  \"tipo\": \"PA\",\r\n"
				+ "  \"ragioneSociale\": \"string\",\r\n"
				+ "  \"partitaIva\": \"string\",\r\n"
				+ "  \"email\": \"string@gmail.com\",\r\n"
				+ "  \"dataInserimento\": \"2022-03-14\",\r\n"
				+ "  \"dataUltimoContatto\": \"2022-03-14\",\r\n"
				+ "  \"pec\": \"string@pec.com\",\r\n"
				+ "  \"telefono\": \"98765465\",\r\n"
				+ "  \"nomeContatto\": \"string\",\r\n"
				+ "  \"cognomeContatto\": \"string\",\r\n"
				+ "  \"telefonoContatto\": \"+39161616\",\r\n"
				+ "  \"indirizzoSedeLegale\": {\r\n"
				+ "    \"id\": 1\r\n"
				+ "    },\r\n"
				+ "  \"indirizzoSedeOperativa\": {\r\n"
				+ "    \"id\": 1\r\n"
				+ "  },\r\n"
				+ "  \"fatture\": [\r\n"
				+ "    {\r\n"
				+ "      \"anno\": 2022,\r\n"
				+ "      \"data\": \"2022-03-14\",\r\n"
				+ "      \"importo\": 1000,\r\n"
				+ "      \"numero\": 6,\r\n"
				+ "      \"stato\": \"pagato\"\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/cliente")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isOk()).andReturn();
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPut() throws Exception {
		String body ="{\r\n"
				+ "  \"tipo\": \"PA\",\r\n"
				+ "  \"ragioneSociale\": \"Microsoft\",\r\n"
				+ "  \"partitaIva\": \"string\",\r\n"
				+ "  \"email\": \"microsoft@gmail.com\",\r\n"
				+ "  \"dataInserimento\": \"2022-03-14\",\r\n"
				+ "  \"dataUltimoContatto\": \"2022-03-14\",\r\n"
				+ "  \"pec\": \"microdoft@pec.com\",\r\n"
				+ "  \"telefono\": \"222555888\",\r\n"
				+ "  \"nomeContatto\": \"Toad\",\r\n"
				+ "  \"cognomeContatto\": \"string\",\r\n"
				+ "  \"telefonoContatto\": \"222555888\",\r\n"
				+ "  \"indirizzoSedeLegale\": {\r\n"
				+ "    \"id\": 2\r\n"
				+ "    },\r\n"
				+ "  \"indirizzoSedeOperativa\": {\r\n"
				+ "    \"id\": 2\r\n"
				+ "  },\r\n"
				+ "  \"fatture\": [\r\n"
				+ "    {\r\n"
				+ "      \"anno\": 2022,\r\n"
				+ "      \"data\": \"2022-03-14\",\r\n"
				+ "      \"importo\": 500,\r\n"
				+ "      \"numero\": 6,\r\n"
				+ "      \"stato\": \"pagato\"\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/cliente/2")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isOk()).andReturn();
	}

}
