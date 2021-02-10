package daw.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import daw.projeto.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	List<Usuario> findByNomeContainingIgnoreCase (String nome);
	Usuario findByNome (String nome);
	//Usuario findByNomeUsuario (String nomeUsuario);
}
