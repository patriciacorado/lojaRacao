package br.unitins.petshop.model;

public class Animal {
	private Integer id;
	private Porte porte;//pequeno, medio ou grade.
	private Categoria categoria;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Porte getPorte() {
		return porte;
	}
	public void setPorte(Porte porte) {
		this.porte = porte;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
}
