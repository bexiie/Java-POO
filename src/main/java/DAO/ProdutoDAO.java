package DAO;
import Conexão.ConnectionFactory;
import Model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private Connection connection;
    
public ProdutoDAO() {
    this.connection = new ConnectionFactory().getConnection();
    }
    
public void adiciona(Produto produto) {
    PreparedStatement ps = null;
    String sql = "insert into produtos"
            + "(descricao, codigoBarras, unidadeVenda, ultimaCompra, precoVenda, quantEstoque) "
            + "values(?,?,?,?,?,?)"; 
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, produto.getDescricao());
            ps.setString(2, produto.getCodigoBarras());
            ps.setString(3, produto.getUnidadeVenda());
            ps.setDate(4, new java.sql.Date(produto.getUltimaCompra().getTime()));
            ps.setFloat(5, produto.getPrecoVenda());
            ps.setFloat(6, produto.getQuantEstoque());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar produto: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, null);
        }
    }
        
public List<Produto> getLista() {
    List<Produto> produtos = new ArrayList<>();
    String sql = "select * from produtos";
    PreparedStatement ps = null;
    ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setCodigoBarras(rs.getString("codigoBarras"));
                produto.setUnidadeVenda(rs.getString("unidadeVenda"));
                produto.setUltimaCompra(rs.getDate("ultimaCompra"));
                produto.setPrecoVenda(rs.getFloat("precoVenda"));
                produto.setQuantEstoque(rs.getFloat("quantEstoque"));

                produtos.add(produto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, rs);
        }
        return produtos;
    }
        
public Produto getProdutoId(int idProd) {
    String sql = "select * from produtos where id=?";
    Produto produto = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idProd);
            rs = ps.executeQuery();
            if (rs.next()) {
                produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setCodigoBarras(rs.getString("codigoBarras"));
                produto.setUnidadeVenda(rs.getString("unidadeVenda"));
                produto.setUltimaCompra(rs.getDate("ultimaCompra"));
                produto.setPrecoVenda(rs.getFloat("precoVenda"));
                produto.setQuantEstoque(rs.getFloat("quantEstoque"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produto: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, rs);
        }
        return produto;
    }
         
public void altera(Produto produto) {
    String sql = "update produtos set descricao=?, codigoBarras=?, "
            + "unidadeVenda=?, ultimaCompra=?, precoVenda=?, quantEstoque=? where id=?";
    PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, produto.getDescricao());
            ps.setString(2, produto.getCodigoBarras());
            ps.setString(3, produto.getUnidadeVenda());
            ps.setDate(4, new java.sql.Date(produto.getUltimaCompra().getTime()));
            ps.setFloat(5, produto.getPrecoVenda());
            ps.setFloat(6, produto.getQuantEstoque());
            ps.setInt(7, produto.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar produto: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, null);
        }
    }
         
public void remove(Produto produto) {
    String sql = "delete from produtos where id=?";
    PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, produto.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover produto: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, null);
        }
    }
}