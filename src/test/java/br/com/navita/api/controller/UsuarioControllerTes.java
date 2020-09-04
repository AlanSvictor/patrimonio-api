package br.com.navita.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.navita.api.PatrimonioApplication;
import br.com.navita.api.dtos.UsuarioDto;
import br.com.navita.api.entities.Usuario;
import br.com.navita.api.service.UsuarioService;

@ContextConfiguration(classes = PatrimonioApplication.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioControllerTes {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mvc;
	
	@Autowired
	private UsuarioService usuarioService;

	@BeforeEach
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@Bean
	public void validaPost() throws Exception {
		
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setNome("Teste");
		usuarioDto.setEmail("teste@teste.com");
		usuarioDto.setSenha("$10$uD.Vz8t.JjgS8ACEIRKlD.RV6wiToJoIOnSnOofDcQ5VvGIYmJ77G");

		mvc.perform(MockMvcRequestBuilders.post("/api/usuarios")
				.content(asJsonString(usuarioDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		Optional<Usuario> usuario = this.usuarioService.buscaPorId(1L);
		Assert.assertTrue(usuario.isPresent());
	}

	
	@Test
	public void validarGet() throws Exception {

		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setNome("Teste");
		usuarioDto.setEmail("teste@test.com");
		usuarioDto.setSenha("$10$uD.Vz8t.JjgS8ACEIRKlD.RV6wiToJoIOnSnOofDcQ5VvGIYmJ77G");
		Usuario usuario = this.usuarioService.persistir(usuarioDto.toEntity());
		
		mvc.perform(MockMvcRequestBuilders.get("/api/usuarios/" + usuario.getId())
				.accept(MediaType.APPLICATION_JSON))
	      		.andDo(print())
	      		.andExpect(status().isOk())
	      		.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(usuarioDto.getNome()));

	}		
	
	@Test
	public void validarDelete() throws Exception 
	{
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setNome("Teste");
		usuarioDto.setEmail("teste@testes.com");
		usuarioDto.setSenha("$10$uD.Vz8t.JjgS8ACEIRKlD.RV6wiToJoIOnSnOofDcQ5VvGIYmJ77G");
		Usuario usuario = this.usuarioService.persistir(usuarioDto.toEntity());
				
		mvc.perform(MockMvcRequestBuilders.delete("/api/usuarios/"+ usuario.getId()))
	        .andExpect(status().isOk());
	}
	
	@Test
	public void validarPut() throws Exception {

		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setNome("teste-teste");
		usuarioDto.setEmail("teste234s@gmail.com");
		usuarioDto.setSenha("123");
		
		mvc.perform(MockMvcRequestBuilders.put("/api/usuarios/"+ 1)
				.content(asJsonString(usuarioDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(usuarioDto.getNome()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.senha").value(usuarioDto.getSenha()));
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}	
	
	
}
