package DAO;
import Conexão.ConnectionFactory;
import Model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection connection;
    
public UsuarioDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public void adiciona(Usuario usuario) {
        PreparedStatement ps = null;
        String sql = "insert into usuarios"
                + "(nomeDeLogin, senha, tipoUsuario) "
                + "values(?,?,?)"; 
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, usuario.getNomeDeLogin());
            ps.setString(2, usuario.getSenha());
            ps.setString(3, usuario.getTipoDeUsuario());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar usuário: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, null);
        }
    }
        
    public List<Usuario> getLista() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "select * from usuarios";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNomeDeLogin(rs.getString("nomeDeLogin"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTipoDeUsuario(rs.getString("tipoUsuario"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, rs);
        }
        return usuarios;
    }
        
    public Usuario getUsuarioId(int idUsu) {
        String sql = "select * from usuarios where id=?";
        Usuario usuario = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idUsu);
            rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNomeDeLogin(rs.getString("nomeDeLogin"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTipoDeUsuario(rs.getString("tipoUsuario"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuário: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, rs);
        }
        return usuario;
    }
         
    public void altera(Usuario usuario) {
        String sql = "update usuarios set nomeDeLogin=?, senha=?, "
                + "tipoUsuario=? where id=?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, usuario.getNomeDeLogin());
            ps.setString(2, usuario.getSenha());
            ps.setString(3, usuario.getTipoDeUsuario());
            ps.setInt(4, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar usuário: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, null);
        }
    }
         
    public void remove(Usuario usuario) {
        String sql = "delete from usuarios where id=?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover usuário: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(null, ps, null);
        }
    }
}