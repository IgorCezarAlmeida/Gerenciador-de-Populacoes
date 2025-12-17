/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadordepopulacoes.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Jonat
 */
public class Conexao {
    
    private static final String USUARIO = "postgres";
    private static final String SENHA = "1234";
    private static final String URL = "jdbc:postgresql://localhost:4321/db_controleDePopulacoes";
    
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(URL,USUARIO, SENHA);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao PostgreSQL: " + e.getMessage(), e);
        }
    }
}


