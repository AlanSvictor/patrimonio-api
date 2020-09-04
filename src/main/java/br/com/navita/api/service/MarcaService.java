package br.com.navita.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.navita.api.entities.Marca;

public interface MarcaService {
		
	Marca persistir(Marca marca);
	
	Optional<Marca> buscarPorId(Long id);

	Optional<Marca> buscarPorNome(String nome);
	
	void removerMarca(Long id);
	
	Page<Marca> buscarTodos(PageRequest pageRequest);
}
