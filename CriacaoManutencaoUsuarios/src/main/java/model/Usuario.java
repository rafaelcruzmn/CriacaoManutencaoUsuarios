/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import enumerator.TipoUsuario;
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
    private boolean autorizado;
    
    public Usuario(String nome, String nomeDeUsuario, String senha, TipoUsuario tipo, boolean autorizado, LocalDate dataCadastro){
        this.nome = nome;
        this.nomeDeUsuario = nomeDeUsuario;
        this.senha = senha;
        this.tipo = tipo;
        this.autorizado = autorizado;
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

    public String getSenha(){
        return senha;
    }
    
    public TipoUsuario getTipo() {
        return tipo;
    }
    
    public boolean getAutorizado(){
        return autorizado;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }
}
