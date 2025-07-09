package DAO;

import Conexão.ConnectionFactory;
import Model.ResumoVenda;

import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResumoVendaDAO {
    private Connection connection;

    public ResumoVendaDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public List<ResumoVenda> getLista(Date data) {
        List<ResumoVenda> resumos = new ArrayList<>();
        String sql = "select dataVenda, formaPagamento, SUM(valorVenda)" +
                     "from venda where dataVenda = ? group by formaPagamento";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(data.getTime()));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ResumoVenda resumo = new ResumoVenda();
                resumo.setDataVenda(rs.getDate("dataVenda"));
                resumo.setFormaPag(rs.getInt("formaPag"));
                resumo.setValorFormaPag(rs.getFloat("valorVenda"));
                resumos.add(resumo);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerar resumo de vendas: " + e.getMessage(), e);
        }

        return resumos;
    }
}
