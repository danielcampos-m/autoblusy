package daw.projeto.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import daw.projeto.model.Dispositivo;
import daw.projeto.model.Usuario;
import daw.projeto.service.DispositivoService;

@Controller
public class DispositivoController {
	private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private DispositivoService ds;
	
	@PostMapping(value = {"/dispositivo"})
	public ModelAndView dispositivo(Long d,String status) {
		logger.info(">>>>>>>>>>>Entrou em dispositivo");
		logger.info("id passado: {}", d);
		logger.info("string passado: {}", status);
		
		boolean result = ds.doDispositivo(d, status);
		
		if(!result) {
			logger.info("DISPOSITIVO NÃO ACESSIVEL");
		}else {
			logger.info("CONCLUIDO");
		}
		return new ModelAndView("redirect:/Controle");
	}
	
	@RequestMapping(value = "newDispositivo", method = RequestMethod.GET)
	public String direcionarDispositivo(Dispositivo dispositivo) {
		
		//Usuario usuario = new Usuario();
		logger.info("Direcionando para cadastro de dispositivo...");
		return "cadastrodispositivos";
	}
	
	@PostMapping(value = {"/NovoDispositivo"})
	public ModelAndView novoDispositivo(Dispositivo dispositivo) {
//		ModelAndView mv = new ModelAndView();
		logger.info("ENTROU EM NOVO DISPOSITIVO");
		logger.info("Dispositivo recebido: {}",dispositivo);
		
		ds.salvar(dispositivo);
		return new ModelAndView("redirect:/Admin");
//		return mv;
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
		mv.addObject("Dispositivo",dispositivo);
		return mv;
	}
	
	@PostMapping(value = {"/dispositivoEditado"})
	public ModelAndView dispositivoEditado(Dispositivo dispositivo) {
		logger.info("Dispositivo a ser editado: {}",dispositivo);
		ds.salvar(dispositivo);
		logger.info("Dispositivo editado com sucesso!");
		return new ModelAndView("redirect:/Admin");
	}
}
