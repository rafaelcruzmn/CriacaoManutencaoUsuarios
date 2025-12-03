/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Optional;

/**
 *
 * @author Luis1
 */
public class UsuarioNotificacao {
    private int id;
    private int idUsuario;
    private int idNotificacao;
    private boolean lida;
    
    public UsuarioNotificacao(Optional<Integer> id, Usuario usuario, Notificacao notificacao){
        this.id = id.orElse(-1);
        this.idUsuario = usuario.getId();
        this.idNotificacao = notificacao.getId();
    }

    public int getId() {
        return id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdNotificacao() {
        return idNotificacao;
    }

    public boolean isLida() {
        return lida;
    }
 
}
