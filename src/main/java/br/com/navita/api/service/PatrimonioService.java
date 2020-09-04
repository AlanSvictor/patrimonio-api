package br.com.navita.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.navita.api.entities.Marca;
import br.com.navita.api.entities.Patrimonio;

public interface PatrimonioService {
	
	Patrimonio persistir (Patrimonio patrimonio);
	
	Optional<Patrimonio> buscarPorId(Long id);
	
	Optional<Patrimonio> buscarPorNome(String nome);
	
	Optional<Patrimonio> buscarPorMarca(Marca marca);
	
	void removerPatrimonio(Long id);
	
	Page<Patrimonio> buscarTodos(PageRequest pageRequest);

}
