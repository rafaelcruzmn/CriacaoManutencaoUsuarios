/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import enumerator.TipoUsuario;
import java.time.LocalDate;
import java.util.Optional;

/**
 *
 * @author Luis1
 */
public class Usuario {
    private Integer id;
    private String nome;
    private String nomeDeUsuario;
    private String senha;
    private TipoUsuario tipo;
    private LocalDate dataCadastro;
    private boolean autorizado;
    
    public Usuario(Optional<Integer> id, String nome, String nomeDeUsuario, String senha, TipoUsuario tipo, boolean autorizado, LocalDate dataCadastro){
        this.id = id.orElse(-1);
        this.nome = nome;
        this.nomeDeUsuario = nomeDeUsuario;
        this.senha = senha;
        this.tipo = tipo;
        this.autorizado = autorizado;
        this.dataCadastro = dataCadastro;
    }
    
    public int getId(){
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

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }



    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }
}
