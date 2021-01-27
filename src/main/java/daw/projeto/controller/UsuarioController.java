package daw.projeto.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import daw.projeto.model.Alteracao;
import daw.projeto.model.Dispositivo;
import daw.projeto.model.Papel;
import daw.projeto.model.Usuario;
import daw.projeto.repository.PapelRepository;
import daw.projeto.service.AlteracaoService;
import daw.projeto.service.DispositivoService;
import daw.projeto.service.UsuarioService;

@Controller
public class UsuarioController {

	private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
	
	@Autowired
	private PapelRepository pr;
	
	@Autowired
	private UsuarioService us;
	
	@Autowired
	private DispositivoService ds;
	
	@Autowired
	private AlteracaoService as;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping(value = {"/Controle"} )
	public ModelAndView controle(HttpSession session){
		logger.trace("Entrou em controle");
		ModelAndView mv = new ModelAndView("controle");
		
		//Listar dispositivos
		List<Dispositivo> dispositivos = us.listarDispositivos();

		mv.addObject("dispositivos",dispositivos);

		//logger.info("Dispositivos a se listar: {}\n", dispositivos);
		return (mv);
	}
	
	@GetMapping(value = {"/Admin"} )
	public ModelAndView admin() {
		logger.info("Entrou em Admin");

		ModelAndView mv = buildAdmin();		
		
		return mv;	
	}
	
	@RequestMapping(value = "Usuario", method = RequestMethod.GET)
	public String direcionarCadastro(Usuario usuario) {
		
		//Usuario usuario = new Usuario();
		logger.info("Direcionando para cadastro de usuario...");
		return "cadastrousuario";
	}
	
	@PostMapping(value = {"Usuario"} )
	public String Usuario(@Valid Usuario usuario, BindingResult result, RedirectAttributes atributos) {
		logger.trace("Entrou em inserirNovoUsuario");
		logger.debug("Usuario recebido para inserir: {}", usuario);
		if (result.hasErrors()) {
			logger.debug("O usuario recebido para inserir não é válido");
			logger.debug("Erros encontrados:");
			for(FieldError erro : result.getFieldErrors()) {
				logger.debug("{}", erro);
			}
			logger.info("Encaminhando para a view CadastroUsuario");
			return "cadastrousuario";
		} else {
			if (!usuario.getPapeis().isEmpty()) {
				usuario.setAtivo(true);
				usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
				us.salvar(usuario);
				atributos.addFlashAttribute("mensagem", "Contato inserido com sucesso!");
				logger.info("Redirecionando para a URL /Admin");
				return "redirect:/Admin";
			} else {
				logger.error("Nenhum papel colocado no usuario");
				atributos.addFlashAttribute("mensagem", "Deve-se escolher um papel para o usuario");
				logger.info("Encaminhando para a view CadastroUsuario");
				return "redirect:/Usuario";
			}
		}
	}

	
	@PostMapping(value = {"/removerUsuario"} )
	public ModelAndView removerUsuario(Long id) {
			logger.info("Id recebido: {}", id);
			logger.info("Entrou em remover usuario");
			us.removerPorId(id);
		return new ModelAndView ("redirect:/Admin");
	}
	
	@PostMapping(value = {"/editarUsuario"} )
	public ModelAndView editarUsuario(Long id) {
		logger.info("Id no controller: {}",id);
		ModelAndView mv = new ModelAndView("editarusuario");
		Usuario usuario = us.achar(id);
		mv.addObject("Usuario",usuario);
		
		return mv;
	}
	
	@PostMapping(value = {"/usuarioEditado"} )
	public ModelAndView usuarioEditado(Usuario usuario) {
		logger.info("usuario a ser editado: {}",usuario);
		us.salvar(usuario);
		logger.info("usuario editado com sucesso!");
		return new ModelAndView("redirect:/Admin");
	}
	
	 public ModelAndView buildAdmin() {
		 logger.info("Entrou em buildAdmin");

			ModelAndView mv = new ModelAndView("admin");
			
			//Contrução da página
			List<Papel> papeis = pr.findAll();
			List<Usuario> usuarios = us.mostrarUsuarios();
			List<Dispositivo> dispositivos = ds.ListarTodosDispositivos();
			List<Alteracao> alteracoes = as.ListarTodasAlteracoes();
			
			Dispositivo dispositivo = new Dispositivo();
			//logger.debug("Usuarios encontrados para mostrar {}", usuarios);
			//logger.debug("Papeis encontrados para mostrar {}", papeis);
			mv.addObject("todasAlteracoes",alteracoes);
			mv.addObject("todosUsuarios", usuarios);
			mv.addObject("todosPapeis", papeis);
			mv.addObject("todosDispositivos", dispositivos);
			mv.addObject("Dispositivo", dispositivo);
		 return mv;
	 }
	
}