package it.epicenergy.testrest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class TestAutenticazione {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithAnonymousUser
	final void getAllAutoryWhenUserAnonymous() throws Exception {
		this.mockMvc.perform(get("/api/cliente")).andExpect(status().isForbidden());
	}
	
	@Test
	@WithAnonymousUser
	final void testLoginPubblica() throws Exception {
		this.mockMvc.perform(get("/auth/login")).andExpect(status().isMethodNotAllowed());
	}
	
	   @Test
	    public void utenteRealeGetTokenAndAuthentication() throws Exception {
	    	String username = "admin";
	    	String password = "admin";
	    	// crea direttamente la stringa JSON con le credenziali da passare all'endpoint di login 
	    	String body = "{\"userName\":\"" + username + "\", \"password\":\"" 
	    			+ password + "\"}";
	    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isOk()).andReturn();
	    	String response = result.getResponse().getContentAsString();
	    }

}
