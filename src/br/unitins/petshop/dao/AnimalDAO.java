package br.unitins.petshop.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.unitins.petshop.application.Util;
import br.unitins.petshop.model.Animal;
import br.unitins.petshop.model.Categoria;
import br.unitins.petshop.model.Porte;
import br.unitins.petshop.dao.DAO;
import br.unitins.petshop.model.Animal;

public class AnimalDAO implements DAO<Animal>{

	@Override
	public void inserir(Animal obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append("animal ");
		sql.append("  (categoria, porte) ");
		sql.append("VALUES ");
		sql.append("  ( ?, ?) ");
		PreparedStatement stat = null;
		
		try {
			stat = conn.prepareStatement(sql.toString());
			// ternario java
			stat.setObject(1, (obj.getCategoria() == null ? null : obj.getCategoria().getId()));
			stat.setObject(2, (obj.getPorte() == null ? null : obj.getPorte().getId()));
			stat.execute();
			// efetivando a transacao
			conn.commit();

		} catch (SQLException e) {

			System.out.println("Erro ao realizar um comando sql de insert.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
			exception = new Exception("Erro ao inserir");

		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;
		
	}

	@Override
	public void alterar(Animal obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(Animal obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Animal> obterTodos() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Animal obterUm(Animal obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Animal obterId(Animal animal) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		
		Animal a = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  a.id, ");
		sql.append("  a.categoria, ");
		sql.append("  a.porte ");
		sql.append("FROM  ");
		sql.append("  animal a ");
		sql.append("WHERE a.categoria = ? and a.porte = ?");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, animal.getCategoria().getId());
			stat.setInt(2, animal.getPorte().getId());
			

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				a = new Animal();
				a.setId(rs.getInt("id"));
				a.setCategoria(Categoria.valueOf(rs.getInt("categoria")));
				a.setPorte(Porte.valueOf(rs.getInt("porte")));

			}

		} catch (SQLException e) {
			Util.addErrorMessage("N�o foi possivel buscar os dados do telefone.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em AnimalDAO.");
		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		return a;
	}


}
