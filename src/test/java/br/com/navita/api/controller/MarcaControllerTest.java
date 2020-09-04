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
import br.com.navita.api.dtos.MarcaDto;
import br.com.navita.api.dtos.PatrimonioDto;
import br.com.navita.api.entities.Marca;
import br.com.navita.api.service.MarcaService;
import br.com.navita.api.service.PatrimonioService;

@ContextConfiguration(classes = PatrimonioApplication.class)
@SpringBootTest
@ActiveProfiles("test")
public class MarcaControllerTest {
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mvc;

	@Autowired
	private MarcaService marcaService;
	
	@Autowired
	private PatrimonioService patrimonioService;

	@BeforeEach
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	@Bean
	public void validaPost() throws Exception {
		MarcaDto marcaDto = new MarcaDto();
		marcaDto.setNome("Ford");
		
		mvc.perform(MockMvcRequestBuilders.post("/api/marcas")
				.content(asJsonString(marcaDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		Optional<Marca> marca = this.marcaService.buscarPorId(1L);
		Assert.assertTrue(marca.isPresent());
	}

	
	@Test
	public void validarGet() throws Exception {

		MarcaDto marcaDto = new MarcaDto();
		marcaDto.setNome("Fiat");
		
		Marca marca = this.marcaService.persistir(marcaDto.toEntity());
		
		mvc.perform(MockMvcRequestBuilders.get("/api/marcas/" + marca.getId())
				.accept(MediaType.APPLICATION_JSON))
	      		.andDo(print())
	      		.andExpect(status().isOk())
	      		.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(marcaDto.getNome()));

	}
		
	@Test
	public void DeleteMarcaComPatrimonioCadastrado() throws Exception 
	{
		MarcaDto marcaDto = new MarcaDto();
		marcaDto.setNome("KIA");
		Marca marca = this.marcaService.persistir(marcaDto.toEntity());
		
		PatrimonioDto patrimonioDto = new PatrimonioDto();
		patrimonioDto.setNome("Carro adm");
		patrimonioDto.setMarca(marca);
		patrimonioDto.setMarcaId(marca.getId());
		patrimonioDto.setDescricao("Teste");
		this.patrimonioService.persistir(patrimonioDto.toEntity());
				
		mvc.perform(MockMvcRequestBuilders.delete("/api/marcas/"+ marca.getId()))
	        .andExpect(status().isBadRequest());
	}	
	
	@Test
	public void validarDelete() throws Exception 
	{
		MarcaDto marcaDto = new MarcaDto();
		marcaDto.setNome("chevrolet");
		Marca marca = this.marcaService.persistir(marcaDto.toEntity());
				
		mvc.perform(MockMvcRequestBuilders.delete("/api/marcas/"+ marca.getId()))
	        .andExpect(status().isOk());
	}
		
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
