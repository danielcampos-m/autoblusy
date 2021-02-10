package daw.projeto.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daw.projeto.model.Alteracao;
import daw.projeto.model.Dispositivo;
import daw.projeto.model.Usuario;
import daw.projeto.repository.AlteracaoRepository;

@Service
public class AlteracaoService {
	private static final Logger logger = LoggerFactory.getLogger(AlteracaoService.class);
	
	@Autowired
	private AlteracaoRepository ar;
	
	@Autowired
	private DispositivoService ds;
	
	@Transactional
	public boolean reportarAlteracao(String descricao, Dispositivo dispositivo) {
			logger.info("Entrou em alteracao");
			Alteracao newalt = new Alteracao();
			
			newalt.setDescricao(descricao);
			newalt.setDispositivo(dispositivo);
			newalt.setData(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
			ar.save(newalt);
			logger.info("SALVANDO ALTERACAO");
			
		return true;
	}
	
	@Transactional 
	public List<Alteracao> ListarTodasAlteracoes(){
		List<Alteracao> alteracoes = ar.findAll();
		return alteracoes;
	}
	
	@Transactional
	public List<Alteracao> acharAlteracaoDeUsuario(Usuario usuario){
		List<Dispositivo> dispositivos = ds.acharDispositivosPorUsuario(usuario);
		List<Alteracao> alteracoes = new ArrayList();
		for(Dispositivo d:dispositivos) {
			for(Alteracao a:ar.findByDispositivo(d)) {
				alteracoes.add(a);
			}
		}
		return alteracoes;
	}
}
