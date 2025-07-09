package DAO;
import Model.Cliente;
import Conexão.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ClienteDAO {
    private Connection connection;
    
public ClienteDAO () {
    this.connection = new ConnectionFactory().getConnection();
    }
    
public void adiciona(Cliente cliente) {
    PreparedStatement ps = null;
    String sql = "insert into clientes"
    + "(nome, cpf, endereco, cidade, cep, UF) "
    + "values(?,?,?,?,?,?)"; 
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getEndereco());
            ps.setString(4, cliente.getCidade());
            ps.setString(5, cliente.getCep());
            ps.setString(6, cliente.getUF());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar cliente: " + e.getMessage(), e);
        }
    }
        
public List<Cliente> getLista() {
    List<Cliente> clientes = new ArrayList<>();
    String sql = "select * from clientes";
    PreparedStatement ps = null;
    ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setCidade(rs.getString("cidade"));
                cliente.setCep(rs.getString("cep"));
                cliente.setUF(rs.getString("UF"));
                clientes.add(cliente);
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes: " + e.getMessage(), e);
        }
        return clientes;
    }
        
public Cliente getClienteId(int idCli) {
    String sql = "select * from cliente where id=?";
    Cliente cliente = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idCli);
            rs = ps.executeQuery();
            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setCidade(rs.getString("cidade"));
                cliente.setCep(rs.getString("cep"));
                cliente.setUF(rs.getString("UF"));
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar cliente: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection, ps, rs);//fecha conexão
        }
        return cliente;
    }
         
public void altera(Cliente cliente) {
    String sql = "update clientes set nome=?,cpf=?,"
            + "endereco=?, cidade=?, cep=?, UF=? where id=?";
    PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getEndereco());
            ps.setString(4, cliente.getCidade());
            ps.setString(5, cliente.getCep());
            ps.setString(6, cliente.getUF());
            ps.setInt(7, cliente.getId());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar cliente: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection, ps);//fecha conexão
        }
    }
public void remove(Cliente cliente) {
    String sql = "delete from clientes where id=?";
    PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir cliente: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection, ps);//fecha conexão
        }
    }
         
}