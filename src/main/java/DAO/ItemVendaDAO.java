package DAO;
import Conexão.ConnectionFactory;
import Model.ItemVenda;
import Model.Produto;
import Model.Venda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemVendaDAO {
    private Connection connection;
    
public ItemVendaDAO() {
    this.connection = new ConnectionFactory().getConnection();
    }
    
public void adiciona(ItemVenda item) {
    PreparedStatement ps = null;
    String sql = "insert into ItemVenda"
            + "(idVenda, produto, quantVendida, precoVenda) "
            + "values(?,?,?,?)"; 
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, item.getIdVenda().getId());
            ps.setInt(2, item.getProduto().getId());
            ps.setInt(3, item.getQuantVendida());
            ps.setFloat(4, item.getPrecoVenda());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar item de venda: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, null);
        }
    }
        
public List<ItemVenda> getLista() {
    List<ItemVenda> itens = new ArrayList<>();
    String sql = "select * from ItemVenda";
    PreparedStatement ps = null;
    ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ItemVenda item = new ItemVenda();
                item.setId(rs.getInt("id"));
                item.setQuantVendida(rs.getInt("quantVendida"));
                item.setPrecoVenda(rs.getFloat("precoVenda"));

                Produto p = new Produto();
                p.setId(rs.getInt("produto"));
                item.setProduto(p);

                Venda v = new Venda();
                v.setId(rs.getInt("idVenda"));
                item.setIdVenda(v);

                itens.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar item de venda: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, rs);
        }
        return itens;
    }
        
public ItemVenda getItemId(int idItem) {
    String sql = "select * from ItemVenda where id=?";
    ItemVenda item = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idItem);
            rs = ps.executeQuery();
            if (rs.next()) {
                item = new ItemVenda();
                item.setId(rs.getInt("id"));

                Produto p = new Produto();
                p.setId(rs.getInt("produto"));
                item.setProduto(p);

                Venda v = new Venda();
                v.setId(rs.getInt("idVenda"));
                item.setIdVenda(v);
                item.setQuantVendida(rs.getInt("quantVendida"));
                item.setPrecoVenda(rs.getFloat("precoVenda"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar itens de venda " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, rs);
        }
        return item;
    }
         
public void altera(ItemVenda item) {
    String sql = "update ItemVenda set idVenda=?,"
            + " produto=?, quantVendida=?, precoVenda=? where id=?";
    PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, item.getIdVenda().getId());
            ps.setInt(2, item.getProduto().getId());
            ps.setInt(3, item.getQuantVendida());
            ps.setFloat(4, item.getPrecoVenda());
            ps.setInt(5, item.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar itens de venda: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, null);
        }
    }
         
public void remove(ItemVenda item) {
    String sql = "delete from ItemVenda where id=?";
    PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, item.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover item de venda: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, null);
        }
    }
}