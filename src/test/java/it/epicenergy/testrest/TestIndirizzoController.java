package it.epicenergy.testrest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import it.epicenergy.model.Comune;
import it.epicenergy.model.Indirizzo;
import it.epicenergy.repository.ComuneRepository;

@SpringBootTest
@AutoConfigureMockMvc
class TestIndirizzoController {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ComuneRepository comuneRepo;
	
	@Test
	@WithMockUser
	final void testGetAll() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/indirizzo")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void testGetById() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/indirizzo/1")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testDeleteById() throws Exception {
		this.mockMvc.perform(delete("http://localhost:8080/api/indirizzo/1")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPost() throws Exception {		
		String body ="{\r\n"
				+ "  \"via\": \"string\",\r\n"
				+ "  \"civico\": \"string\",\r\n"
				+ "  \"localita\": \"string\",\r\n"
				+ "  \"cap\": \"12345\",\r\n"
				+ "  \"comune\": {\r\n"
				+ "    \"id\": 1,\r\n"
				+ "    \"nome\": \"string\"\r\n"
				+ "  }\r\n"
				+ "}";
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/indirizzo")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(body))
    			.andExpect(status().isCreated()).andReturn();
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPut() throws Exception {
		String body = "{\r\n"
				+ "  \"via\": \"chiesa\",\r\n"
				+ "  \"civico\": \"30\",\r\n"
				+ "  \"localita\": \"string\",\r\n"
				+ "  \"cap\": \"26020\",\r\n"
				+ "  \"comune\": {\r\n"
				+ "    \"id\": 1,\r\n"
				+ "    \"nome\": \"string\"\r\n"
				+ "  }\r\n"
				+ "}";
	    
	    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/indirizzo/2")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(body))
    			.andExpect(status().isOk()).andReturn();
	}

}
