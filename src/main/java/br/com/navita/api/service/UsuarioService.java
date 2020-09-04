package br.com.navita.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.navita.api.entities.Usuario;

public interface UsuarioService {

	Usuario persistir(Usuario usuario);
	
	Optional<Usuario> buscaPorId(Long id);
	
	Optional<Usuario> buscaPorEmail(String email);
	
	void removerUsuario(Long id);
	
	Page<Usuario> bucarTodos(PageRequest pageRequest);
	
}
