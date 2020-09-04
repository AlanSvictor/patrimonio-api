package br.com.navita.api.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.navita.api.dtos.MarcaDto;
import br.com.navita.api.entities.Marca;
import br.com.navita.api.entities.Patrimonio;
import br.com.navita.api.service.MarcaService;
import br.com.navita.api.service.PatrimonioService;

@RestController
@RequestMapping("/api/marcas")
@CrossOrigin(origins = "*")
public class MarcaController {
	
	private static final Logger log = LoggerFactory.getLogger(MarcaController.class);
	
	@Autowired
	private MarcaService marcaService;
	
	@Autowired
	private PatrimonioService patrimonioService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	public MarcaController() {
	}
	
	@GetMapping
	public ResponseEntity<Page<?>> buscaTodos(@RequestParam(value = "pag", defaultValue = "0") int pag) {
		log.info("Buscando todas as marca");
		
		Page<Marca> marcas = this.marcaService.buscarTodos(PageRequest.of(pag, this.qtdPorPagina));
		Page<MarcaDto> marcaDto = marcas.map(marca -> MarcaDto.buildDto(marca));

		if (marcaDto.isEmpty()) {			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}		
		return ResponseEntity.ok(marcaDto);		
	}
	

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> buscaPorId(@PathVariable("id") long id) {
		log.info("Buscando marca por id: {}", id);
		
		Optional<Marca> marca = marcaService.buscarPorId(id);
		if (!marca.isPresent()) {			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Marca não encontrada!");
		}		
		return ResponseEntity.ok(MarcaDto.buildDto(marca.get()));		
	}
	
	@GetMapping(value = "/marca/{nome}")
	public ResponseEntity<?> buscaPorId(@PathVariable("nome") String nome) {
		log.info("Buscando marca por nome: {}", nome);
		
		Optional<Marca> marca = marcaService.buscarPorNome(nome);
		if (!marca.isPresent()) {			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Marca não encontrada!");
		}		
		return ResponseEntity.ok(MarcaDto.buildDto(marca.get()));		
	}
	
	@PostMapping
	public ResponseEntity<?> post(@Valid @RequestBody MarcaDto marcaDto) {
		log.info("Persistindo marca:", marcaDto.toString());
		
		Optional<Marca> marca = marcaService.buscarPorNome(marcaDto.getNome());
		if (marca.isPresent()) {			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Marca já cadastrada para o nome: " + marcaDto.getNome());
		}
		return ResponseEntity.ok(MarcaDto.buildDto(marcaService.persistir(marcaDto.toEntity())));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> remover(@PathVariable("id") Long id) {
		log.info("Removendo marca: {}", id);
		Optional<Marca> marcaOptional = this.marcaService.buscarPorId(id);
		if (!marcaOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado marca cadastrada para o id: " + id);
		}
		
		Optional<Patrimonio> patrimonio = this.patrimonioService.buscarPorMarca(marcaOptional.get());
		if (patrimonio.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não é possível remover a marca: "
					+ marcaOptional.get().getNome() + " pois exitem patrimônios vinculados!");
		}
			
		this.marcaService.removerMarca(id);
		return ResponseEntity.ok().build();		
	}
	
}
