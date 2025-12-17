package com.mycompany.gerenciadordepopulacoes.dao;
import com.mycompany.gerenciadordepopulacoes.database.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UnidadeGeograficaDAO {


    public List<AuxDAO> listarPaises() throws SQLException {
        List<AuxDAO> paises = new ArrayList<>();
        String sql = "SELECT id, nome FROM paises ORDER BY nome";
        
        try (Connection conn = new Conexao().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                paises.add(new AuxDAO(rs.getString("nome"), rs.getInt("id")));
            }
        }
        return paises;
    }

    public List<AuxDAO> listarEstados() throws SQLException {
        List<AuxDAO> estados = new ArrayList<>();
        String sql = "SELECT id, nome FROM estados ORDER BY nome";
        
        try (Connection conn = new Conexao().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                estados.add(new AuxDAO(rs.getString("nome"), rs.getInt("id")));
            }
        }
        return estados;
    }
    
    public List<Object[]> buscarPaisesNaTabela() throws SQLException {
        List<Object[]> paises = new ArrayList<>();
        String sql = "SELECT id, nome FROM vw_paises_simples";
        
        try (Connection conn = new Conexao().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                paises.add(new Object[]{rs.getInt("id"), rs.getString("nome")});
            }
        }
        return paises;
    }

    public List<Object[]> buscarEstadosNaTabela() throws SQLException {
        List<Object[]> estados = new ArrayList<>();
        String sql = "SELECT id_estado, estado_nome, id_pais, pais_nome FROM vw_estados_detalhe";
        
        try (Connection conn = new Conexao().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                Object[] linha = {
                    rs.getInt("id_estado"),
                    rs.getString("estado_nome"),
                    rs.getInt("id_pais"),
                    rs.getString("pais_nome")
                };
                estados.add(linha);
            }
        }
        return estados;
    }
    
    
    public void salvarPais(int idPaisEmEdicao, String nome) throws SQLException {
        String sql;
        boolean isUpdate = (idPaisEmEdicao > 0);
        
        if (isUpdate) {
            sql = "UPDATE paises SET nome = ? WHERE id = ?";
        } else {
            sql = "INSERT INTO paises (nome) VALUES (?)";
        }
        
        try (Connection conn = new Conexao().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            if (isUpdate) {
                stmt.setInt(2, idPaisEmEdicao);
            }
            
            stmt.executeUpdate();
        }
    }
    
    public void excluirPais(int idPais) throws SQLException {
        String sql = "DELETE FROM paises WHERE id = ?";

        try (Connection conn = new Conexao().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPais);
            stmt.executeUpdate();
        }
    }
    
    
    public void salvarEstado(int idEstadoEmEdicao, String nomeEstado, int idPais) throws SQLException {
        String sql;
        boolean isUpdate = (idEstadoEmEdicao > 0);
        
        if (isUpdate) {
            sql = "UPDATE estados SET nome = ?, id_pais = ? WHERE id = ?";
        } else {
            sql = "INSERT INTO estados (nome, id_pais) VALUES (?, ?)";
        }

        try (Connection conn = new Conexao().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeEstado);
            stmt.setInt(2, idPais);
            
            if (isUpdate) {
                stmt.setInt(3, idEstadoEmEdicao);
            }
            
            stmt.executeUpdate();
        }
    }
    
    public void excluirEstado(int idEstado) throws SQLException {
        String sql = "DELETE FROM estados WHERE id = ?";

        try (Connection conn = new Conexao().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEstado);
            stmt.executeUpdate();
        }
    }
    
    
    public List<Object[]> buscarUnidadesParaArvore() throws SQLException {
        List<Object[]> resultados = new ArrayList<>();
        String sql = "SELECT p.id AS pais_id, p.nome AS pais_nome, e.id AS estado_id, e.nome AS estado_nome " +
                     "FROM paises p LEFT JOIN estados e ON p.id = e.id_pais " +
                     "ORDER BY p.nome, e.nome";

        try (Connection conn = new Conexao().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] linha = {
                    rs.getInt("pais_id"),
                    rs.getString("pais_nome"),
                    rs.getInt("estado_id"),
                    rs.getString("estado_nome")
                };
                resultados.add(linha);
            }
        }
        return resultados;
    }
}