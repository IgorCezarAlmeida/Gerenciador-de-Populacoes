/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadordepopulacoes.dao;

/**
 *
 * @author Jonat
 */

import com.mycompany.gerenciadordepopulacoes.database.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GrupoPopulacionalDAO {

    public List<Object[]> buscarGruposPorEstado(int idEstado) throws SQLException {
        List<Object[]> grupos = new ArrayList<>();
        String sql = "SELECT tamanho, tipo, nacionalidade, religiao FROM grupos_populacionais WHERE id_estado = ?";

        // Usamos try-with-resources para garantir o fechamento de Connection e PreparedStatement
        try (Connection conn = new Conexao().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstado);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] linha = {
                        rs.getInt("tamanho"),
                        rs.getString("tipo"),
                        rs.getString("nacionalidade"),
                        rs.getString("religiao")
                    };
                    grupos.add(linha);
                }
            }
        } 
        return grupos;
    }

    public void salvarGrupo(int idGrupo, int tamanho, String tipo, String nacionalidade, String religiao, int idEstado) throws SQLException {
        boolean modoEdicao = (idGrupo > 0);
        String sql;

        if (modoEdicao) {
            sql = "UPDATE grupos_populacionais SET tamanho = ?, tipo = ?, nacionalidade = ?, religiao = ?, id_estado = ? WHERE id = ?";
        } else {
            sql = "INSERT INTO grupos_populacionais (tamanho, tipo, nacionalidade, religiao, id_estado) VALUES (?, ?, ?, ?, ?)";
        }

        try (Connection conn = new Conexao().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tamanho);
            stmt.setString(2, tipo);
            stmt.setString(3, nacionalidade);
            stmt.setString(4, religiao);
            stmt.setInt(5, idEstado);

            if (modoEdicao) {
                stmt.setInt(6, idGrupo);
            }

            stmt.executeUpdate();
        }
    }

    public List<Object[]> listarGruposComEstados() throws SQLException {
        List<Object[]> grupos = new ArrayList<>();
        String sql = "SELECT id, tamanho, tipo, nacionalidade, religiao, estado FROM vw_grupos_com_estados ORDER BY id";

        try (Connection conn = new Conexao().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] linha = {
                    rs.getInt("id"),
                    rs.getInt("tamanho"),
                    rs.getString("tipo"),
                    rs.getString("nacionalidade"),
                    rs.getString("religiao"),
                    rs.getString("estado")
                };
                grupos.add(linha);
            }
        }
        return grupos;
    }
    
    public int excluirGrupo(int idGrupo) throws SQLException {
        String sql = "DELETE FROM grupos_populacionais WHERE id = ?";
        try (Connection conn = new Conexao().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idGrupo);
            return stmt.executeUpdate(); 
        }
    }
}
