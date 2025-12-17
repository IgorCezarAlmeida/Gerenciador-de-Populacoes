/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadordepopulacoes.model;

/**
 *
 * @author Jonat
 */
public class Estado {
    private int id;
    private String nome;
    private int idPais;

    public Estado(int id, String nome, int idPais) {
        this.id = id;
        this.nome = nome;
        this.idPais = idPais;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    @Override
    public String toString() {
        return "Estado{" + "id=" + id + ", nome=" + nome + ", idPais=" + idPais + '}';
    }
    
    
}
