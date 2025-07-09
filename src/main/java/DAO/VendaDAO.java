package DAO;
import Conexão.ConnectionFactory;
import Model.Venda;
import Model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {
    private Connection connection;
    
    public VendaDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public void adiciona(Venda venda) {
        PreparedStatement ps = null;
        String sql = "insert into vendas"
                + "(dataVenda, idCli, formaPag, valorVenda) "
                + "values(?,?,?,?)"; 
        try {
            ps = connection.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(venda.getDataVenda().getTime()));
            ps.setInt(2, venda.getIdCli().getId());
            ps.setInt(3, venda.getFormaPag());
            ps.setFloat(4, venda.getValorVenda());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar venda: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, null);
        }
    }
        
    public List<Venda> getLista() {
        List<Venda> vendas = new ArrayList<>();
        String sql = "select * from vendas";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setDataVenda(rs.getDate("dataVenda"));
                
                Cliente c = new Cliente();
                c.setId(rs.getInt("idCli"));
                venda.setIdCli(c);
                
                venda.setFormaPag(rs.getInt("formaPag"));
                venda.setValorVenda(rs.getFloat("valorVenda"));
                vendas.add(venda);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vendas: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, rs);
        }
        return vendas;
    }
        
    public Venda getVendaId(int idVen) {
        String sql = "select * from vendas where id=?";
        Venda venda = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idVen);
            rs = ps.executeQuery();
            if (rs.next()) {
                venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setDataVenda(rs.getDate("dataVenda"));
                
                Cliente c = new Cliente();
                c.setId(rs.getInt("idCli"));
                venda.setIdCli(c);
                
                venda.setFormaPag(rs.getInt("formaPag"));
                venda.setValorVenda(rs.getFloat("valorVenda"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar venda: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, rs);
        }
        return venda;
    }
         
    public void altera(Venda venda) {
        String sql = "update vendas set dataVenda=?, idCli=?, "
                + "formaPag=?, valorVenda=? where id=?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(venda.getDataVenda().getTime()));
            ps.setInt(2, venda.getIdCli().getId());
            ps.setInt(3, venda.getFormaPag());
            ps.setFloat(4, venda.getValorVenda());
            ps.setInt(5, venda.getId());  // Changed from setFloat to setInt for ID
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar venda: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, null);
        }
    }
         
    public void remove(Venda venda) {
        String sql = "delete from vendas where id=?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, venda.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover venda: " + e.getMessage(), e);  // Fixed error message
        } finally {
            ConnectionFactory.closeConnection(null, ps, null);
        }
    }
}