package br.com.navita.api.dtos;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.navita.api.entities.Marca;

public class MarcaDto {
	
	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	private String nome;
	
	public MarcaDto() {	
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "MarcaDto [id=" + this.id + ", nome=" + this.nome + "]";
	}
	
	public static MarcaDto buildDto(Marca marca) {
		MarcaDto marcaDto = new MarcaDto();
		marcaDto.setId(marca.getId());
		marcaDto.setNome(marca.getNome());
		
		return marcaDto;		
	}
	
	public Marca toEntity() {
		Marca marca = new Marca();
		marca.setNome(this.nome);
		
		return marca;
	}
	
}
