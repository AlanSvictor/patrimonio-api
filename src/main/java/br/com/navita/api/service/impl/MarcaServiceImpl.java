package br.com.navita.api.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.navita.api.entities.Marca;
import br.com.navita.api.repository.MarcaRepository;
import br.com.navita.api.service.MarcaService;

@Service
public class MarcaServiceImpl implements MarcaService{

	private static final Logger log = LoggerFactory.getLogger(MarcaServiceImpl.class);
	
	@Autowired
	private MarcaRepository marcaRepository;

	@Override
	public Marca persistir(Marca marca) {
		log.info("Persistindo marca: {}", marca.toString());
		return this.marcaRepository.save(marca);
	}

	@Override
	public Optional<Marca> buscarPorId(Long id) {
		log.info("Buscando marca id: {}", id);
		return this.marcaRepository.findById(id);
	}

	@Override
	public Optional<Marca> buscarPorNome(String nome) {
		log.info("Buscando marca por nome: {}", nome);
		return this.marcaRepository.findByNome(nome);
	}

	@Override
	public void removerMarca(Long id) {
		log.info("Removendo marca id: {}", id);
		this.marcaRepository.deleteById(id);		
	}

	@Override
	public Page<Marca> buscarTodos(PageRequest pageRequest) {
		log.info("Buscando todos marcas");
		return this.marcaRepository.findAll(pageRequest);
	}	
	
}
