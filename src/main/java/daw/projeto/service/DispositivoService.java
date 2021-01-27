package daw.projeto.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
//import java.util.Scanner;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fazecast.jSerialComm.SerialPort;

import daw.projeto.model.Dispositivo;
import daw.projeto.repository.DispositivoRepository;

@Service
public class DispositivoService {
	private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	private SerialPort comPort;
	
	@Autowired
	private DispositivoRepository dr;
	
	@Autowired
	private AlteracaoService as;
	
	@Transactional
	public boolean doDispositivo(Long id, String status) {
			//Achando dispositivo
			Optional<Dispositivo> dispositivo = dr.findById(id);
			logger.info("Dispositivos: {}",dispositivo);
			
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println(">>>>>>>>> CONTROLE DE ARDUINO");
//			Scanner sc = new Scanner(System.in);
			System.out.println("Endereco retornado pelo banco: "+dispositivo.get().getEndereco());
			System.out.println("Portas seriais disponiveis: ");
			
			for(int i=0;i<SerialPort.getCommPorts().length;i++) {
				System.out.println(i+"-"+SerialPort.getCommPorts()[i].getSystemPortName());
			}
			
			//SerialPort comPort = SerialPort.getCommPorts()[1];
			
			boolean checa = false;
			for(int i=0;i<SerialPort.getCommPorts().length;i++) {
//				String quick = "/dev/"+SerialPort.getCommPorts()[i].getSystemPortName();
				if(SerialPort.getCommPorts()[i].getSystemPortName().equals(dispositivo.get().getEndereco())) {
					System.out.println("AS PORTAS BATEM");
					comPort = SerialPort.getCommPort(dispositivo.get().getEndereco());
					checa = true;
				}
			}
			if(!checa) {
				return false;
			}

			System.out.println("Porta selecionada: "+ comPort.getSystemPortName());
			
			comPort.setComPortParameters(9600, 8, 1, 0);
			
			//ABRIR CONEXÃO
			//System.out.println(comPort.isOpen());
			System.out.println(comPort.isOpen());
			comPort.openPort();

			if(comPort.isOpen()) {
				System.out.println("\nAbrindo conexão...");
				System.out.println("Conexão aberta\n");
				
				System.out.println("Status atual: "+ status);
				System.out.println("Escrevendo...\n");
				
				int n = 0;
				if(status.equals("OFF")) {
					n = 1;
					//pout.flush();
					System.out.println("Enviando sinal para ligar...\n");
				}else {
					n = 2;
					System.out.println("Enviando sinal para desligar...\n");
				}
				
				try {
					while(comPort.getInputStream().available() < 1) {

						//comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 100, 0);
						
						comPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
						try{Thread.sleep(5);} catch(Exception e){}
						OutputStream pout = comPort.getOutputStream();
						try {
							pout.write(n);
						}catch(Exception e) {}
					}

					//Leitura
					try{Thread.sleep(5);} catch(Exception e){}
					comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);
					InputStream in = comPort.getInputStream();
					
					try {
						System.out.println("Inicio da leitura: "+comPort.getInputStream().available());
						}catch (Exception e) { e.printStackTrace(); }
					
					try
					{
						while(comPort.getInputStream().available() != 0) {
					      System.out.print((char)in.read());
						}
					   in.close();
					   
					} catch (Exception e) { e.printStackTrace(); }
					
					try {
						System.out.println("Fim da leitura: "+comPort.getInputStream().available());
						}catch (Exception e) { e.printStackTrace(); }
					}catch (Exception e) { e.printStackTrace(); }
					//FIM LEITURA
				
				comPort.closePort();
			}else {
				System.out.println("NÃO FOI POSSIVEL ABRIR A CONEXÃO");
				comPort.closePort();
			}
			comPort.closePort();
			System.out.println("\nFechando conexão...");
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			/////////////////////////////////////////////////////////////////////////////////////
			Dispositivo newDispositivo = dispositivo.get();
			
			dr.save(newDispositivo);
			logger.info("DISPOSITIVO ALTERADO NO BANCO");

			if(status.equals("OFF")) {
				newDispositivo.setStatus("ON");
				as.reportarAlteracao("Turning on",newDispositivo);
			}else {
				newDispositivo.setStatus("OFF");
				as.reportarAlteracao("Turning off",newDispositivo);
			}	
		return true;
	}
	
	@Transactional
	public List<Dispositivo> ListarTodosDispositivos(){
			
			List<Dispositivo> todosDispositivos = dr.findAll();
		return (todosDispositivos);
	}
	
	@Transactional
	public void salvar(Dispositivo dispositivo) {
		dr.save(dispositivo);
		logger.info("Dispositivo salvo com sucesso");
	}
	
	@Transactional
	public void removerDispositivoPorId(Long id) {
		
		dr.deleteById(id);
		logger.debug("Dispositivo removido com sucesso!");
	}
	
	@Transactional
	public Dispositivo achar(Long id) {
		logger.info("Id no service: {}", id);
		Optional<Dispositivo> dispositivo = dr.findById(id);
		return dispositivo.get();
	}
}
