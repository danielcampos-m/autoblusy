package daw.projeto.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import daw.projeto.model.SessaoUsuario;

@Controller
public class SessaoController {

	private static final Logger logger = LoggerFactory.getLogger(SessaoController.class);
	
	
	
	@GetMapping(value = {"/meusdispositivos"} )
	public ModelAndView meusdispositivos(HttpSession sessao) {
		logger.trace("Entrou em meus dispositivos");
		ModelAndView mv = new ModelAndView("meusdispositivos");
		SessaoUsuario user = (SessaoUsuario)sessao.getAttribute("user");
		if(user == null) {
			user = new SessaoUsuario();
			sessao.setAttribute("user", user);
		}
//		List<Usuario> usuarios = ur.findAll();
//		mv.addObject("dispositivos",dispositivos);
//		logger.info("Usuarios encontrados: {}", usuarios);
		logger.info("Criada nova sessão {}", user);
		return mv;
	}
	
}