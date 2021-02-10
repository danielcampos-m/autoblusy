package daw.projeto.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import daw.projeto.service.RelatorioService;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

	private static final Logger logger = LoggerFactory.getLogger(RelatorioController.class);
	
	@Autowired
	private RelatorioService relatorioService;
	
	@GetMapping("/todasalteracoes")
	public ResponseEntity<byte[]> gerarRelatorioAlteracoes() {
		logger.info("Entrou em gerarRelatorioSimplesTodasAlteracoess");
		logger.debug("Gerando relatório de alteracoes");
		
		byte[] relatorio = relatorioService.gerarRelatorioAlteracoes();
		
		logger.debug("Relatório Alteracoes");
		logger.debug("Retornando o relatório de Alteracoes");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=RelatorioAlteracoes.pdf")
				.body(relatorio);
	}
	
}
