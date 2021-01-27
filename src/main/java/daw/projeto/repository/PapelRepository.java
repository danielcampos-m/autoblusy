package daw.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import daw.projeto.model.Papel;

public interface PapelRepository extends JpaRepository<Papel, Long> {
	
}