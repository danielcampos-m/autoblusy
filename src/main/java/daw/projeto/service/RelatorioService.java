package daw.projeto.service;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class RelatorioService {

	private static final Logger logger = LoggerFactory.getLogger(RelatorioService.class);

	@Autowired
	private DataSource dataSource;
	
//	public byte[] gerarRelatorioAlteracoes() {
//		logger.info("Entrou em RelatorioAlteracoes");
//		InputStream arquivoJasper = getClass()
//			.getResourceAsStream("relatorios/RegistroAlteracoes.jasper");
//		try (Connection conexao = dataSource.getConnection()){
//			try {
//				JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasper, null, conexao);
//				return JasperExportManager.exportReportToPdf(jasperPrint);
//			} catch (JRException e) {
//				logger.error("Problemas na geracao do PDF do relatório: " + e);
//			}
//		} catch (SQLException e) {
//			logger.error("Problemas na obtenção de uma conexão com o BD na geração de relatório: " + e);
//		}
//
//		return null;
//	}
	
	public byte[] gerarRelatorioAlteracoes() {
		logger.info("Entrou em gerarRelatorioAlteracoes");
		
		try (Connection conexao = dataSource.getConnection()) {
				try {
					ClassPathResource cpr = new ClassPathResource("relatorios/RegistroAlteracoes.jasper");
					InputStream arquivoJasper = cpr.getInputStream();

					String urlRelatorio = cpr.getURL().toString();
					String diretorioRelatorios = urlRelatorio.substring(0,
							urlRelatorio.lastIndexOf("/") + 1);
					logger.debug("diretorioRelatorios: {}", diretorioRelatorios);

					Map<String, Object> parametros = new HashMap<>();
					parametros.put("SUBREPORT_DIR", diretorioRelatorios);

					JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasper, parametros, conexao);
					return JasperExportManager.exportReportToPdf(jasperPrint);
				} catch (java.io.IOException e) {
					logger.error("Erro encontrando o caminho do subreport: " + e);
				} catch (JRException e) {
					logger.error("Problemas na geracao do PDF do relatório: " + e);
				}
			} catch (SQLException e) {
				logger.error("Problemas na obtenção de uma conexão com o BD na geração de relatório: " + e);
			}
			return null;
	}

}