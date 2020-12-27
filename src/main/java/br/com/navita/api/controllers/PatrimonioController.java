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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.navita.api.dtos.PatrimonioDto;
import br.com.navita.api.entities.Marca;
import br.com.navita.api.entities.Patrimonio;
import br.com.navita.api.service.MarcaService;
import br.com.navita.api.service.PatrimonioService;

@RestController
@RequestMapping("/api/patrimonios")
@CrossOrigin(origins = "*")
public class PatrimonioController {
	
	private static final Logger log = LoggerFactory.getLogger(PatrimonioController.class);
	
	@Autowired
	private PatrimonioService patrimonioService;

	@Autowired
	private MarcaService marcaService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	public PatrimonioController() {
	}

	@GetMapping
	public ResponseEntity<Page<?>> buscaTodos(@RequestParam(value = "pag", defaultValue = "0") int pag) {
		log.info("Buscando todas os patrimonios");
		
		Page<Patrimonio> patrimonios = this.patrimonioService.buscarTodos(PageRequest.of(pag, this.qtdPorPagina));
		Page<PatrimonioDto> patrimonioDto = patrimonios.map(patrimonio -> PatrimonioDto.buildDto(patrimonio));

		if (patrimonioDto.isEmpty()) {			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}		
		return ResponseEntity.ok(patrimonioDto);		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> buscaPorId(@PathVariable("id") long id) {
		log.info("Buscando patrimonio por id: {}", id);
		
		Optional<Patrimonio> patrimonio = this.patrimonioService.buscarPorId(id);
		if (!patrimonio.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado patrimônio para o id: " + id);
		}
		return ResponseEntity.ok(PatrimonioDto.buildDto(patrimonio.get()));	
	}	

	@PostMapping
	public ResponseEntity<?> post(@Valid @RequestBody PatrimonioDto patrimonioDto) {
		log.info("Persistindo patrimonio:", patrimonioDto.toString());

		Optional<Marca> marca = this.marcaService.buscarPorId(patrimonioDto.getMarcaId());
		if (!marca.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado marca cadastrada para o id: " + patrimonioDto.getMarcaId());
		}
		patrimonioDto.setMarca(marca.get());
		return ResponseEntity.ok(PatrimonioDto.buildDto(patrimonioService.persistir(patrimonioDto.toEntity())));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> put(@PathVariable("id") Long id, @Valid @RequestBody PatrimonioDto patrimonioDto){
		log.info("Atualizando patrimonio:", patrimonioDto.toString());
		Optional<Patrimonio> patrimonioOptional = this.patrimonioService.buscarPorId(id);
		if (!patrimonioOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado patrimonio cadastrado para o id: " + patrimonioDto.getId());
		}
		
		Optional<Marca> marca = this.marcaService.buscarPorId(patrimonioDto.getMarcaId());
		if (!marca.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado marca cadastrada para o id: " + patrimonioDto.getMarcaId());
		}
		patrimonioDto.setMarca(marca.get());
		
		patrimonioOptional.get().atualizaDadosPatrimonio(patrimonioDto);
		Patrimonio patrimonio = this.patrimonioService.persistir(patrimonioOptional.get());	
		return ResponseEntity.ok(PatrimonioDto.buildDto(patrimonio));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> remover(@PathVariable("id") Long id) {
		log.info("Removendo patrimonio: {}", id);
		Optional<Patrimonio> patrimonioOptional = this.patrimonioService.buscarPorId(id);
		if (!patrimonioOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado patrimonio cadastrado para o id: " + id);
		}
		
		this.patrimonioService.removerPatrimonio(id);
		return ResponseEntity.ok().build();		
	}
}
