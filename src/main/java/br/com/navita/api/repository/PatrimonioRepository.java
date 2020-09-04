package br.com.navita.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.navita.api.entities.Marca;
import br.com.navita.api.entities.Patrimonio;

@Repository
@Transactional(readOnly = true)
public interface PatrimonioRepository extends JpaRepository<Patrimonio, Long> {

	Optional<Patrimonio> findById(Long id);
	
	Optional<Patrimonio> findByNome(String nome);

	Optional<Patrimonio> findByMarca(Marca marca);
}
