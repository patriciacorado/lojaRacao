package br.unitins.petshop.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.petshop.application.Util;
import br.unitins.petshop.model.Categoria;
import br.unitins.petshop.model.FaixaEtaria;
import br.unitins.petshop.model.Racao;

public class RacaoDAO implements DAO<Racao> {

	@Override
	public void inserir(Racao obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		AnimalDAO animal = new AnimalDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append("racao ");
		sql.append("  (marca, animal, faixaetaria, peso, preco, estoque, datavalidade, foto) ");
		sql.append("VALUES ");
		sql.append("  ( ?, ?, ?, ?, ?, ?, ?, ?) ");
		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			
			stat.setObject(1, obj.getMarca());
			if(animal.obterId(obj.getAnimal())==null) animal.inserir(obj.getAnimal());
			obj.getAnimal().setId(animal.obterId(obj.getAnimal()).getId());
			
			stat.setObject(2, obj.getAnimal().getId());
			stat.setObject(3, (obj.getFaixaetaria() == null ? null : obj.getFaixaetaria().getId()));
			stat.setDouble(4, obj.getPeso());
			stat.setDouble(5, obj.getPreco());
			stat.setInt(6, obj.getEstoque());
			stat.setDate(7, Date.valueOf(obj.getDataValidade()));
			stat.setString(8, obj.getFoto());
			

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
	public void alterar(Racao obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE produto SET ");
		sql.append("  idanimal = ?, ");
		sql.append("  marca = ?, ");
		sql.append("  faixaetaria = ?, ");
		sql.append("  kg = ?, ");
		sql.append("  preco = ?, ");
		sql.append("  estoque = ? ");
		sql.append("  datavalidade = ? ");
		sql.append("WHERE ");
		sql.append("  id = ? ");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setObject(1, obj.getAnimal().getId());
			stat.setString(2, obj.getMarca());
			stat.setObject(3, (obj.getFaixaetaria() == null ? null : obj.getFaixaetaria().getId()));
			stat.setDouble(4, obj.getPeso());
			stat.setDouble(5, obj.getPreco());
			stat.setInt(6, obj.getEstoque());
			stat.setDate(7, Date.valueOf(obj.getDataValidade()));
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
	public void excluir(Racao obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM produto WHERE id = ?");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());
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
	public List<Racao> obterTodos() throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Racao> listaProduto = new ArrayList<Racao>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.marca, ");
		sql.append("  p.faixaetaria, ");
		sql.append("  p.kg, ");
		sql.append("  p.preco, ");
		sql.append("  p.estoque, ");
		sql.append("  p.datavalidade");
		sql.append("FROM  ");
		sql.append("  produto p ");
		sql.append("ORDER BY p.marca ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Racao produto = new Racao();
				produto.setId(rs.getInt("id"));
				produto.setMarca(rs.getString("marca"));
				produto.setFaixaetaria(FaixaEtaria.valueOf(rs.getInt("faixaetaria")));
				produto.setPeso(rs.getDouble("kg"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setEstoque(rs.getInt("estoque"));
				Date data = rs.getDate("datavalidade");

				listaProduto.add(produto);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("N�o foi possivel buscar os dados do produto.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em ProdutoDAO.");
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

		return listaProduto;
	}

	@Override
	public Racao obterUm(Racao obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		Racao produto = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.nome, ");
		sql.append("  p.descricao, ");
		sql.append("  p.preco, ");
		sql.append("  p.estoque, ");
		sql.append("  p.validade, ");
		sql.append("  p.categoria ");
		sql.append("FROM  ");
		sql.append("  produto p ");
		sql.append("WHERE p.id = ? ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				produto = new Racao();
				produto.setId(rs.getInt("id"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setEstoque(rs.getInt("estoque"));
				Date data = rs.getDate("validade");
			}

		} catch (SQLException e) {
			Util.addErrorMessage("N�o foi possivel buscar os dados do produto.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em ProdutoDAO.");
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

		return produto;
	}

	public List<Racao> obterListaProduto(Integer tipo, String filtro) throws Exception {
		// tipo - 1 Nome; 2 Descricao
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Racao> listaProduto = new ArrayList<Racao>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.nome, ");
		sql.append("  p.descricao, ");
		sql.append("  p.preco, ");
		sql.append("  p.estoque, ");
		sql.append("  p.validade, ");
		sql.append("  p.categoria ");
		sql.append("FROM  ");
		sql.append("  produto p ");
		sql.append("WHERE ");
		sql.append("  upper(p.nome) LIKE upper( ? ) ");
		sql.append("  AND upper(p.descricao) LIKE upper( ? ) ");
		sql.append("ORDER BY p.nome ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, tipo == 1 ? "%" + filtro + "%" : "%");
			stat.setString(2, tipo == 2 ? "%" + filtro + "%" : "%");

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Racao produto = new Racao();
				produto.setId(rs.getInt("id"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setEstoque(rs.getInt("estoque"));
				Date data = rs.getDate("validade");
				listaProduto.add(produto);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("N�o foi possivel buscar os dados do produto.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em ProdutoDAO.");
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

		return listaProduto;
	}

	public List<Racao> obterListaProdutoComEstoque(Integer tipo, String filtro) throws Exception {
		// tipo - 1 Nome; 2 Descricao
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Racao> listaProduto = new ArrayList<Racao>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.nome, ");
		sql.append("  p.descricao, ");
		sql.append("  p.preco, ");
		sql.append("  p.estoque, ");
		sql.append("  p.validade, ");
		sql.append("  p.categoria ");
		sql.append("FROM  ");
		sql.append("  produto p ");
		sql.append("WHERE ");
		sql.append("  upper(p.nome) LIKE upper( ? ) ");
		sql.append("  AND upper(p.descricao) LIKE upper( ? ) ");
		sql.append("  AND p.estoque > 0 ");
		sql.append("ORDER BY p.nome ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, tipo == 1 ? "%" + filtro + "%" : "%");
			stat.setString(2, tipo == 2 ? "%" + filtro + "%" : "%");

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Racao produto = new Racao();
				produto.setId(rs.getInt("id"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setEstoque(rs.getInt("estoque"));
				Date data = rs.getDate("validade");

				listaProduto.add(produto);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("N�o foi possivel buscar os dados do produto.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em ProdutoDAO.");
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

		return listaProduto;
	}
}
