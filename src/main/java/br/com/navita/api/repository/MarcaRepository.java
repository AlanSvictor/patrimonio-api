package br.com.navita.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.navita.api.entities.Marca;

@Repository
@Transactional(readOnly = true)
public interface MarcaRepository extends JpaRepository<Marca, Long> {

	Optional<Marca> findById(Long id);
	
	Optional<Marca> findByNome(String nome);
	
}
