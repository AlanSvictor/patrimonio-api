package br.com.navita.api.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.navita.api.entities.Usuario;
import br.com.navita.api.repository.UsuarioRepository;
import br.com.navita.api.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);
	
	@Autowired
	private UsuarioRepository usuarioRepository;


	@Override
	public Usuario persistir(Usuario usuario) {
		log.info("Persistindo usuario {}", usuario.toString());
		return this.usuarioRepository.save(usuario);
	}

	@Override
	public Optional<Usuario> buscaPorId(Long id) {
		log.info("Buscando usuario pelo ID:{}", id);
		return this.usuarioRepository.findById(id);
	}

	@Override
	public Optional<Usuario> buscaPorEmail(String email) {
		log.info("Buscando usuario pelo e-mail:{}", email);
		return this.usuarioRepository.findByEmail(email);
	}

	@Override
	public void removerUsuario(Long id) {
		log.info("Removendo usuario pelo ID:{}", id);
		this.usuarioRepository.deleteById(id);
	}

	@Override
	public Page<Usuario> bucarTodos(PageRequest pageRequest) {
		log.info("Buscando todos os usuarios");
		return this.usuarioRepository.findAll(pageRequest);
	}

}
