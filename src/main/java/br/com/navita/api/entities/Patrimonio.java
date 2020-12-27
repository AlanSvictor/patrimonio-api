package br.com.navita.api.entities;

import java.time.LocalDateTime;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import br.com.navita.api.dtos.PatrimonioDto;

@Entity
@Table(name = "patrimonio")
public class Patrimonio {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "marca_id", nullable = false)
	private Marca marca;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "tombo", nullable = false)
	private Long tombo;			
	
	@Column(name = "data_criacao", nullable = false)
	private LocalDateTime dataCriacao;
	
	@Column(name = "data_atualizacao", nullable = false)
	private LocalDateTime dataAtualizacao;
	
	public Patrimonio(){		
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
	
	public Marca getMarca() {
		return marca;
	}
	
	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	
	public Long getTombo() {
		return tombo;
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
        final LocalDateTime atual = LocalDateTime.now();
        dataCriacao = atual;
        dataAtualizacao = atual;
        tombo = new Random().nextInt(11) + 1L; 
    }
	
	@Override
	public String toString() {
		return "Patrimonio [id=" + this.id + ", nome=" + this.nome + ", marcaId=" + this.marca + ", descricao=" + this.descricao
				+ ", tombo=" + this.tombo + ", dataCriacao=" + this.dataCriacao 
				+ ", dataAtualizacao" + this.dataAtualizacao + "]";
	}

	public void atualizaDadosPatrimonio(PatrimonioDto patrimonioDto) {
		this.setNome(patrimonioDto.getNome());
		this.setMarca(patrimonioDto.getMarca());
		this.setDescricao(patrimonioDto.getDescricao());
	}	

}
