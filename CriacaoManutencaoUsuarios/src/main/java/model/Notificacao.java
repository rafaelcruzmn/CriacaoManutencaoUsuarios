/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Luis1
 */
public class Notificacao {
    private int id;
    private String titulo;
    private String mensagem; 
    private Usuario remetente;
    private List<Usuario> destinatarios;
    private LocalDate dataEnvio;
    
    public Notificacao(Optional<Integer> id, String titulo, String mensagem, boolean lida, Usuario remetente, List<Usuario> destinatarios, LocalDate dataEnvio){
        this.id = id.orElse(-1);
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.remetente = remetente;
        this.destinatarios = destinatarios;
        this.dataEnvio = dataEnvio;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Usuario getRemetente() {
        return remetente;
    }

    public List<Usuario> getDestinatarios() {
        return destinatarios;
    }

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }
}
