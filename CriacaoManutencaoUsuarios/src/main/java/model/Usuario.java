/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author Luis1
 */
public class Usuario {
    private int id;
    private String nome;
    private String nomeDeUsuario;
    private String senha;
    private TipoUsuario tipo;
    private LocalDate dataCadastro;
    
    public Usuario(String nome, String nomeDeUsuario, String senha, TipoUsuario tipo, LocalDate dataCadastro){
        this.nome = nome;
        this.nomeDeUsuario = nomeDeUsuario;
        this.senha = senha;
        this.tipo = tipo;
        this.dataCadastro = dataCadastro;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeDeUsuario() {
        return nomeDeUsuario;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }
    
    
}
