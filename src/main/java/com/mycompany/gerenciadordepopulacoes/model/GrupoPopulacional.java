/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadordepopulacoes.model;

/**
 *
 * @author Jonat
 */
public class GrupoPopulacional {
    private int id;
    private int tamanho;
    private String tipo;
    private String nacionalidade;
    private String religiao;
    private int idEstado; 

    public GrupoPopulacional() {
    }

    

    public GrupoPopulacional(int id, int tamanho, String tipo, String nacionalidade, String religiao, int idEstado) {
        this.id = id;
        this.tamanho = tamanho;
        this.tipo = tipo;
        this.nacionalidade = nacionalidade;
        this.religiao = religiao;
        this.idEstado = idEstado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getReligiao() {
        return religiao;
    }

    public void setReligiao(String religiao) {
        this.religiao = religiao;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public String toString() {
        return "GrupoPopulacional{" + "id=" + id + ", tamanho=" + tamanho + ", tipo=" + tipo + ", nacionalidade=" + nacionalidade + ", religiao=" + religiao + ", idEstado=" + idEstado + '}';
    }

    
}
