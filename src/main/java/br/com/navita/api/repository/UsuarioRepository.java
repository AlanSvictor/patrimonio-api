package br.com.navita.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.navita.api.entities.Usuario;

@Repository
@Transactional(readOnly = true)
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findById(Long id);
	
	Optional<Usuario> findByEmail(String email);
	
}
