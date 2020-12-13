package br.unitins.petshop.model;

import java.time.LocalDate;

public class Racao{
	private Integer id;
	private String marca;
	private Animal animal;
	private FaixaEtaria faixaetaria;
	private Double peso;
	private Double preco;
	private Integer estoque;
	private LocalDate dataValidade;
	private String foto;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public Animal getAnimal() {
		if(this.animal==null)
			this.animal = new Animal();
		return animal;
	}
	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
	public FaixaEtaria getFaixaetaria() {
		return faixaetaria;
	}
	public void setFaixaetaria(FaixaEtaria faixaetaria) {
		this.faixaetaria = faixaetaria;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public Integer getEstoque() {
		return estoque;
	}
	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}
	public LocalDate getDataValidade() {
		return dataValidade;
	}
	public void setDataValidade(LocalDate dataValidade) {
		this.dataValidade = dataValidade;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	}
