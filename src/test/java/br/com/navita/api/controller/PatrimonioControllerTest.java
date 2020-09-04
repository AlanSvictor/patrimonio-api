package br.com.navita.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import br.com.navita.api.entities.Patrimonio;
import br.com.navita.api.service.MarcaService;
import br.com.navita.api.service.PatrimonioService;

@ContextConfiguration(classes = PatrimonioApplication.class)
@SpringBootTest
@ActiveProfiles("test")
public class PatrimonioControllerTest {
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mvc;
	
	@Autowired
	private PatrimonioService patrimonioService;
	
	@Autowired
	private MarcaService marcaService;

	@BeforeEach
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void validaPost() throws Exception {
		
		MarcaDto marcaDto = new MarcaDto();
		marcaDto.setNome("Scania");
		Marca marca = this.marcaService.persistir(marcaDto.toEntity());
		
		PatrimonioDto patrimonioDto = new PatrimonioDto();
		patrimonioDto.setNome("Caminhão ADM");
		patrimonioDto.setDescricao("Teste");
		patrimonioDto.setMarca(marca);
		patrimonioDto.setMarcaId(marca.getId());
		
		mvc.perform(MockMvcRequestBuilders.post("/api/patrimonios")
				.content(asJsonString(patrimonioDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		Optional<Patrimonio> patrimonio = this.patrimonioService.buscarPorId(1L);
		Assert.assertTrue(patrimonio.isPresent());
	}

	
	@Test
	public void validarGet() throws Exception {

		MarcaDto marcaDto = new MarcaDto();
		marcaDto.setNome("Volvo");
		Marca marca = this.marcaService.persistir(marcaDto.toEntity());
		
		PatrimonioDto patrimonioDto = new PatrimonioDto();
		patrimonioDto.setNome("Caminhão-2");
		patrimonioDto.setDescricao("Teste");
		patrimonioDto.setMarca(marca);
		patrimonioDto.setMarcaId(marca.getId());
		Patrimonio pat = this.patrimonioService.persistir(patrimonioDto.toEntity());
		
		mvc.perform(MockMvcRequestBuilders.get("/api/patrimonios/" + pat.getId())
				.accept(MediaType.APPLICATION_JSON))
	      		.andDo(print())
	      		.andExpect(status().isOk())
	      		.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(patrimonioDto.getNome()));

	}		
	
	@Test
	public void validarDelete() throws Exception 
	{
		MarcaDto marcaDto = new MarcaDto();
		marcaDto.setNome("Toyota");
		Marca marca = this.marcaService.persistir(marcaDto.toEntity());
		
		PatrimonioDto patrimonioDto = new PatrimonioDto();
		patrimonioDto.setNome("Caminhão-3");
		patrimonioDto.setDescricao("Teste");
		patrimonioDto.setMarca(marca);
		patrimonioDto.setMarcaId(marca.getId());
		Patrimonio pat = this.patrimonioService.persistir(patrimonioDto.toEntity());
				
		mvc.perform(MockMvcRequestBuilders.delete("/api/patrimonios/"+ pat.getId()))
	        .andExpect(status().isOk());
	}

	@Test
	public void validarPut() throws Exception {

		MarcaDto marcaDto = new MarcaDto();
		marcaDto.setNome("Yamaha");
		Marca marca = this.marcaService.persistir(marcaDto.toEntity());
		
		PatrimonioDto patrimonioDto = new PatrimonioDto();
		patrimonioDto.setNome("PessoaTeste");
		patrimonioDto.setDescricao("teste put");
		patrimonioDto.setMarcaId(marca.getId());
		
		mvc.perform(MockMvcRequestBuilders.put("/api/patrimonios/"+ 1)
				.content(asJsonString(patrimonioDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(patrimonioDto.getNome()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(patrimonioDto.getDescricao()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.marcaId").value(patrimonioDto.getMarcaId()));
	}
	
	
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}	

}
