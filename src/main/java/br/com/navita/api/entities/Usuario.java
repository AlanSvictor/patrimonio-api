package br.com.navita.api.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import br.com.navita.api.dtos.UsuarioDto;

@Entity
@Table(name= "usuario")
public class Usuario {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "email", nullable = false, unique=true)
	private String email;
	
	@Column(name = "senha", nullable = false)
	private String senha;
	
	@Column(name = "data_criacao", nullable = false)
	private LocalDateTime dataCriacao;
	
	@Column(name = "data_atualizacao", nullable = false)
	private LocalDateTime dataAtualizacao;
	
	public Usuario() {
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}
	
	@PreUpdate
    public void preUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
     
    @PrePersist
    public void prePersist() {
        final var atual = LocalDateTime.now();
        dataCriacao = atual;
        dataAtualizacao = atual;
    }
	
	@Override
	public String toString() {
		return "Usuario [id=" + this.id + ", nome=" + this.nome + ", email=" + this.email + ", senha=" + this.senha + ", dataCriacao=" + this.dataCriacao 
				+ ", dataAtualizacao" + this.dataAtualizacao + "]";
	}

	public void atualizaDadosUsuario(UsuarioDto usuarioDto) {
		this.setNome(usuarioDto.getNome());
		this.setEmail(usuarioDto.getNome());
		this.setSenha(usuarioDto.getSenha());
		
	}
}
