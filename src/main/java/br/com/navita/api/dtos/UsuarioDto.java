package br.com.navita.api.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.navita.api.entities.Usuario;

public class UsuarioDto {

	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	private String nome;
	
	@NotEmpty(message = "e-mail não pode ser vazio.")
	@Email(message="Email inválido.")
	private String email;

	@NotEmpty(message = "senha não pode ser vazia.")
	private String senha;

	public UsuarioDto(){
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

	@Override
	public String toString() {
		return "Usuario [id=" + this.id + ", nome=" + this.nome + ", email="
				+ this.email + "]";
	}
	
	public static UsuarioDto buildDto(Usuario usuario) {
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setId(usuario.getId());
		usuarioDto.setNome(usuario.getNome());
		usuarioDto.setSenha(usuario.getSenha());
		usuarioDto.setEmail(usuario.getEmail());
		return usuarioDto;
	}
	
	public Usuario toEntity() {
		Usuario usuario = new Usuario();
		usuario.setNome(this.nome);
		usuario.setEmail(this.email);
		usuario.setSenha(this.senha);
		return usuario;
		
	}
	
}
