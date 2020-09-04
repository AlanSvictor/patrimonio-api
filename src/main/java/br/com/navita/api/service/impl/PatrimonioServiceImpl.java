package br.com.navita.api.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.navita.api.entities.Marca;
import br.com.navita.api.entities.Patrimonio;
import br.com.navita.api.repository.PatrimonioRepository;
import br.com.navita.api.service.PatrimonioService;

@Service
public class PatrimonioServiceImpl implements PatrimonioService {

	private static final Logger log = LoggerFactory.getLogger(PatrimonioServiceImpl.class);
	
	@Autowired
	private PatrimonioRepository patrimonioRepository;
	
	@Override
	public Patrimonio persistir(Patrimonio patrimonio) {
		log.info("Persistindo patrimonio: {}", patrimonio.toString());
		return this.patrimonioRepository.save(patrimonio);
	}

	@Override
	public Optional<Patrimonio> buscarPorId(Long id) {
		log.info("Bucando patrimonio pelo id:{}", id);
		return this.patrimonioRepository.findById(id);
	}

	@Override
	public Optional<Patrimonio> buscarPorNome(String nome) {
		log.info("Buscando um patrimonio pelo nome: {}", nome);
		return this.patrimonioRepository.findByNome(nome);
	}

	@Override
	public void removerPatrimonio(Long id) {
		log.info("Removendo patrimonio pelo id:{}", id);
		this.patrimonioRepository.deleteById(id);
	}

	@Override
	public Optional<Patrimonio> buscarPorMarca(Marca marca) {
		log.info("Buscando um patrimonio pela marca: {}", marca.getNome());
		return this.patrimonioRepository.findByMarca(marca);
	}

	@Override
	public Page<Patrimonio> buscarTodos(PageRequest pageRequest) {
		log.info("Buscando todos patrimonios");
		return this.patrimonioRepository.findAll(pageRequest);
	}	

}
