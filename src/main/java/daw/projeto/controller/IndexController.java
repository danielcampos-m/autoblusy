package daw.projeto.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@GetMapping(value = {"/", "/index.html"} )
	public ModelAndView index() {
		logger.trace("Entrou em index");
		logger.trace("Encaminhando para controle de dispositivos");
		return new ModelAndView("redirect:/Controle");
	}
	
	@GetMapping("/login")
	public String login() {
		logger.trace("Entrou em login");
		logger.trace("Encaminhando para a view login");
		return "login";
	}
	
}