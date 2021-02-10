package daw.projeto.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import daw.projeto.model.Dispositivo;
import daw.projeto.model.Usuario;
//import daw.projeto.model.Usuario;
import daw.projeto.service.DispositivoService;
import daw.projeto.service.UsuarioService;

@Controller
public class DispositivoController {
	private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private DispositivoService ds;
	
	@Autowired
	private UsuarioService us;
	
	@PostMapping(value = {"/dispositivo"})
	public ModelAndView dispositivo(Long d,String status, RedirectAttributes atributos) {
		logger.info(">>>>>>>>>>>Entrou em dispositivo");
		logger.info("id passado: {}", d);
		logger.info("string passado: {}", status);
		
		boolean result = ds.doDispositivo(d, status);
		
		if(!result) {
			logger.info("DISPOSITIVO NÃO ACESSIVEL");
			atributos.addFlashAttribute("mensagem", "Dispositivo não acessivel.");
		}else {
			logger.info("CONCLUIDO");
		}
		
		return new ModelAndView("redirect:/Controle");
	}
	
	@RequestMapping(value = "newDispositivo", method = RequestMethod.GET)
	public ModelAndView direcionarDispositivo(Dispositivo dispositivo) {
		ModelAndView mv = new ModelAndView("cadastrodispositivo");
		List<Usuario> usuarios = us.listarUsuarios();
		mv.addObject("todosUsuarios",usuarios);
		return mv;
	}
	
	@PostMapping(value = {"/NovoDispositivo"})
	public ModelAndView newDispositivo(@Valid Dispositivo dispositivo, BindingResult result, RedirectAttributes atributos){
		ModelAndView mv = new ModelAndView();
		logger.info("Entrou em inserirNovoDispositivo");
		logger.debug("Dispositivo recebido para inserir: {}", dispositivo);
		if (result.hasErrors()) {
			logger.debug("O dispositivo recebido para inserir não é válido");
			logger.debug("Erros encontrados:");
			for(FieldError erro : result.getFieldErrors()) {
				logger.debug("{}", erro);
			}
			List<Usuario> usuarios = us.listarUsuarios();
			mv.addObject("todosUsuarios",usuarios);
			logger.info("Encaminhando para a view CadastroDispositivo");
			mv.setViewName("cadastrodispositivo"); 
		} else {
			logger.info("Redirecionando para a URL /Admin");
			ds.salvar(dispositivo);
			atributos.addFlashAttribute("mensagem", "Dispositivo inserido com sucesso!");
			mv.setViewName("redirect:/Admin"); 
		}
		return mv;
	}
	
	@PostMapping(value = {"/removerDispositivo"})
	public ModelAndView removerDispositvo(Long iddispositivo) {
		logger.info("Id do Dispositivo recebido para remoção: {}", iddispositivo);
		ds.removerDispositivoPorId(iddispositivo);
		
		return new ModelAndView("redirect:/Admin");
	}
	
	@PostMapping(value = {"/editarDispositivo"})
	public ModelAndView editarDispositivo(Long id) {
		logger.info("Id no controller: {}",id);
		ModelAndView mv = new ModelAndView("editardispositivo");
		Dispositivo dispositivo = ds.achar(id);
		mv.addObject("dispositivo",dispositivo);
		return mv;
	}
	
	@PostMapping(value = {"/dispositivoEditado"})
	public String dispositivoEditado(@Valid Dispositivo dispositivo, BindingResult result, RedirectAttributes atributos) {
		logger.info("Dispositivo a ser editado: {}",dispositivo);
		
		if (result.hasErrors()) {
			logger.debug("O dispositivo recebido para edição não é válido");
			logger.debug("Erros encontrados:");
			for(FieldError erro : result.getFieldErrors()) {
				logger.debug("{}", erro);
			}
			logger.info("Encaminhando para a view alterar Dispositivo");
			return "editardispositivo";
		} else {
			logger.info("Redirecionando para a URL /Admin");
			ds.salvar(dispositivo);
			atributos.addFlashAttribute("mensagem", "Dispositivo alterado com sucesso!");
			return "redirect:/Admin";
		}

	}
}
