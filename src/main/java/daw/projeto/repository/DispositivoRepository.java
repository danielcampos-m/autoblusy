package daw.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import daw.projeto.model.Dispositivo;
import daw.projeto.model.Usuario;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {
	List<Dispositivo> findByNomeContainingIgnoreCase (String nome);
	List<Dispositivo> findByUsuario (Usuario usuario);
	//Dispositivo findByIdContainigIgnoreCase (Long id);
}
