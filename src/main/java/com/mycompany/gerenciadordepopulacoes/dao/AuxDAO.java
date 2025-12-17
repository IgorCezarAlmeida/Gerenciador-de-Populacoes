/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadordepopulacoes.dao;

//IGOR: Essa clase serve de auxiliar para os comboBox pq fica dando erro no java por algum motivo se deixa os objetos 

public class AuxDAO {
    private String nome;
    private int id;

    public AuxDAO(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return nome;
    }
}
