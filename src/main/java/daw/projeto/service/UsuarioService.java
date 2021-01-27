package daw.projeto.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

//import daw.projeto.controller.UsuarioController;
import daw.projeto.model.Dispositivo;
import daw.projeto.model.Usuario;
import daw.projeto.repository.DispositivoRepository;
import daw.projeto.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private DispositivoRepository dr;
	
	@Autowired
	private UsuarioRepository ur;
	
	@Transactional
	public List<Dispositivo> listarDispositivos(){
		//Pegando nome de usuario logado
		logger.info("Entrou em listar dispositivos");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String nome;

		if (principal instanceof UserDetails) {
		    nome = ((UserDetails)principal).getUsername();
		} else {
		    nome = principal.toString();
		}
		
		//logger.info("Chamando metodo findbynome do repository");
		//Pegando id do usuario pelo nome logado
		Usuario useratual = ur.findByNome(nome);
		//logger.info("Usuario retornado {}",useratual);
		
		//logger.info("Chamando metodo findbyuser do rep");
		//Pegando lista de dispositivos pelo nome consultado
		List<Dispositivo> dispositivos = dr.findByUsuario(useratual);
		
		//logger.info("Retornando...");
		return (dispositivos);
	}
	
	@Transactional
	public void salvar(Usuario usuario) {
		logger.info("ENTROU NO SERVICE SALVAR");
		Usuario newuser = new Usuario();
		logger.info("CRIOU OBJETO");
		newuser = usuario;
		logger.info("SALVANDO...");
		ur.save(newuser);
		logger.info("DEPOIS DO SERVICE SALVAR");
	}
	
	@Transactional
	public List<Usuario> mostrarUsuarios(){
		List<Usuario> todosUsuarios = ur.findAll();
		
		return (todosUsuarios);
	}
	
	@Transactional
	public void removerPorId(Long id) {
		logger.info("Entrou em service!");
		logger.info("Declarando optional com id: {}", id);
		Optional<Usuario> usuario = ur.findById(id);
		logger.info("Declarando lista de dispositivos associados");
		List<Dispositivo> dispositivos = dr.findByUsuario(usuario.get());
		logger.info("Removendo dipositivos associados...");
		for(Dispositivo d: dispositivos) {
			dr.deleteById(d.getId());
		}
		logger.info("Removendo usuario finalmente: ");
		ur.deleteById(id);
		logger.info("Usuario removido com sucesso!");
	}
	
	@Transactional
	public Usuario achar(Long id) {
		logger.info("Id no service: {}", id);
		Optional<Usuario> user = ur.findById(id);
		return user.get();
		
	}
}
