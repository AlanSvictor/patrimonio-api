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

import br.com.navita.api.dtos.UsuarioDto;
import br.com.navita.api.entities.Usuario;
import br.com.navita.api.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;


	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
		
	public UsuarioController() {
	}

	@GetMapping
	public ResponseEntity<Page<?>> buscaTodos(@RequestParam(value = "pag", defaultValue = "0") int pag) {
		log.info("Buscando todas os usuarios");
		
		Page<Usuario> usuarios = this.usuarioService.bucarTodos(PageRequest.of(pag, this.qtdPorPagina));
		Page<UsuarioDto> usuarioDto = usuarios.map(usuario -> UsuarioDto.buildDto(usuario));

		if (usuarioDto.isEmpty()) {			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}		
		return ResponseEntity.ok(usuarioDto);		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> buscaPorId(@PathVariable("id") long id) {
		log.info("Buscando usuario por id: {}", id);
		
		Optional<Usuario> usuario = this.usuarioService.buscaPorId(id);
		if (usuario.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado usuário para o id: " + id);
		}
		return ResponseEntity.ok(UsuarioDto.buildDto(usuario.get()));	
	}	

	@PostMapping
	public ResponseEntity<?> post(@Valid @RequestBody UsuarioDto usuarioDto) {
		log.info("Persistindo usuario:", usuarioDto.toString());
		Optional<Usuario> usuarioOptional = this.usuarioService.buscaPorEmail(usuarioDto.getEmail());
		if (usuarioOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Já cadastrado usuário com o email informado!");
		}		
		
		return ResponseEntity.ok(UsuarioDto.buildDto(usuarioService.persistir(usuarioDto.toEntity())));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> put(@PathVariable("id") Long id, @Valid @RequestBody UsuarioDto usuarioDto){
		log.info("Atualizando usuario:", usuarioDto.toString());
		Optional<Usuario> usuarioOptional = this.usuarioService.buscaPorId(id);
		if (!usuarioOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado usuario cadastrado para o id: " + usuarioDto.getId());
		}
				
		usuarioOptional.get().atualizaDadosUsuario(usuarioDto);
		Usuario usuario = this.usuarioService.persistir(usuarioOptional.get());	
		return ResponseEntity.ok(UsuarioDto.buildDto(usuario));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> remover(@PathVariable("id") Long id) {
		log.info("Removendo usuario: {}", id);
		Optional<Usuario> usuarioOptional = this.usuarioService.buscaPorId(id);
		if (!usuarioOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado usuario cadastrado para o id: " + id);
		}
		
		this.usuarioService.removerUsuario(id);
		return ResponseEntity.ok().build();		
	}
}
