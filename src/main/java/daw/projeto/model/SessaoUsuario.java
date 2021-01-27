package daw.projeto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Scope;
//import org.springframework.context.annotation.ScopedProxyMode;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.WebApplicationContext;

public class SessaoUsuario {
	
	private static final Logger logger = LoggerFactory.getLogger(SessaoUsuario.class);
	
	private Usuario usuario;
	
	public SessaoUsuario() {
		logger.debug("Criou a Sessao do Usuario");
	}

	private List<Dispositivo> dispositivos = new ArrayList<>();

	public List<Dispositivo> getDispositivos() {
		logger.debug("Pegou os dispositivos da sessão");
		return Collections.unmodifiableList(dispositivos);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return " usuario: " + usuario + "\ndispositivos: " + dispositivos;
	}
	
	
//
//	public void adicionarItem(int quantidade, Produto produto) {
//		logger.debug("Adicionou/Alterou um item no Carrinho de compras {} {}.", quantidade, produto);
//		boolean alterou = false;
//		for (Item i : itens) {
//			if (i.getProduto().equals(produto)) {
//				i.setQuantidade(quantidade);
//				alterou = true;
//			}
//		}
//		
//		if (!alterou) {
//			itens.add(new Item(quantidade, produto));
//		}
//	}

//	public void removerItem(Produto produto) {
//		logger.debug("Removeu o item do produto {} do Carrinho de compras.", produto);
//		boolean achou = false;
//		Item item = null;
//		for (Item i : itens) {
//			if (i.getProduto().equals(produto)) {
//				item = i;
//				achou = true;
//			}
//		}
//		if (achou) {
//			itens.remove(item);
//		}
//	}
//	
//	public void limpar() {
//		logger.debug("Limpou o Carrinho de compras.");
//		itens = new ArrayList<>();
//	}
//	
//	public double getValor() {
//		double total = 0.0;
//		for (Item i : itens) {
//			total += i.getValor();
//		}
//		logger.debug("Pegou o valor total {} do Carrinho de compras.", total);
//		return total;
//	}
}