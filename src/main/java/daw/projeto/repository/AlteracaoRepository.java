package daw.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import daw.projeto.model.Alteracao;
import daw.projeto.model.Dispositivo;

public interface AlteracaoRepository extends JpaRepository<Alteracao, Long> {
	//List<Alteracao> findByNomeContainingIgnoreCase (String nome);
	List<Alteracao> findByDescricaoContainingIgnoreCase (String descricao);
	List<Alteracao> findByDispositivo (Dispositivo dispositivo);
}
