package br.com.navita.api.dtos;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.navita.api.entities.Marca;
import br.com.navita.api.entities.Patrimonio;

public class PatrimonioDto {
		
	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	private String nome;
	
	
	private Long marcaId;

	private String descricao;
	
	private Long tombo;
	
	@JsonIgnore
	private Marca marca;
	
	public PatrimonioDto() {
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

	public Long getMarcaId() {
		return marcaId;
	}

	public void setMarcaId(Long marcaId) {
		this.marcaId = marcaId;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getTombo() {
		return tombo;
	}

	public void setTombo(Long tombo) {
		this.tombo = tombo;
	}
	
	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	@Override
	public String toString() {
		return "PatrimonioDto [id=" + id + ", nome=" + this.nome + ", marcaId=" + this.marcaId
				+ ", descricao=" + this.descricao + ", tombo=" + this.tombo  + "]";
	}	
	
	public static PatrimonioDto buildDto(Patrimonio patrimonio)	{
		PatrimonioDto patrimonioDto = new PatrimonioDto();
		patrimonioDto.setId(patrimonio.getId());
		patrimonioDto.setNome(patrimonio.getNome());
		patrimonioDto.setMarcaId(patrimonio.getMarca().getId());
		patrimonioDto.setDescricao(patrimonio.getDescricao());
		patrimonioDto.setTombo(patrimonio.getTombo());
		return patrimonioDto;
	}
	
	public Patrimonio toEntity() {
		
		Patrimonio patrimonio = new Patrimonio();
		patrimonio.setNome(this.nome);
		patrimonio.setMarca(this.marca);
		patrimonio.setDescricao(this.descricao);
		return patrimonio;
	}	
}
