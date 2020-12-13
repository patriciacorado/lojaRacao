package br.unitins.petshop.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.application.Util;
import br.unitins.petshop.dao.RacaoDAO;
import br.unitins.petshop.model.Racao;

@Named
@ViewScoped
public class ConsultaProdutoController implements Serializable{

	
	private static final long serialVersionUID = -7064857362220414218L;

	private Integer tipoFiltro;
	private String filtro;
	private List<Racao> listaProduto;
	
	public void novoProduto() {
		Util.redirect("cadastroproduto.xhtml");
	}
	
	public void pesquisar() {
		RacaoDAO dao = new RacaoDAO();
		try {
			setListaProduto(dao.obterListaProduto(tipoFiltro, filtro));
		} catch (Exception e) {
			e.printStackTrace();
			setListaProduto(null);
		}
	}
	
	public void editar(Racao produto) {
		RacaoDAO dao = new RacaoDAO();
		Racao editarProduto = null;
		try {
			editarProduto = dao.obterUm(produto);
		} catch (Exception e) {
			e.printStackTrace();
			Util.addErrorMessage("Não foi possível encontrar a produto no banco de dados.");
			return;
		}
		
		Flash flash =  FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("produtoFlash", editarProduto);
		novoProduto();
	}

	public Integer getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(Integer tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Racao> getListaProduto() {
		if (listaProduto == null)
			listaProduto = new ArrayList<Racao>();
		return listaProduto;
	}

	public void setListaProduto(List<Racao> listaProduto) {
		this.listaProduto = listaProduto;
	}
}
