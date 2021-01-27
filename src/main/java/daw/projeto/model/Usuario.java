package daw.projeto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="usuarios")
public class Usuario implements Serializable{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1344232450235083080L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "nome")
	@NotBlank(message = "O nome é obrigatório")
	@Size(min = 5, max = 255, message = "O nome deve ter entre 5 e 255 caracteres")
	private String nome;
	@Column(name = "senha")
	@NotBlank(message = "A senha é obrigatória")
	@Size(min = 6, max = 255, message = "A senha deve ter entre 6 e 255 caracteres")
	private String senha;
	@Column(name = "ativo")
	private boolean ativo;
	@ManyToMany
	@JoinTable(name="papel_usuarios",
			joinColumns=@JoinColumn(name = "usuarios_id"),	
	        inverseJoinColumns=@JoinColumn(name="papel_id"))
	private List<Papel> papeis = new ArrayList<>();
	

	public Usuario() {
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	public boolean isAtivo() {
		return ativo;
	}


	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}


	public List<Papel> getPapeis() {
		return papeis;
	}


	public void setPapeis(List<Papel> papeis) {
		this.papeis = papeis;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((papeis == null) ? 0 : papeis.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (ativo != other.ativo)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (papeis == null) {
			if (other.papeis != null)
				return false;
		} else if (!papeis.equals(other.papeis))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return " id: " + id + "\nnome: " + nome + "\nsenha: " + senha + "\nativo: " + ativo;
	}

	
}
