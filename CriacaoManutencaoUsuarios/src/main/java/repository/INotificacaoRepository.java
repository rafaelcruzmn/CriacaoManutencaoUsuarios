/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repository;

import model.Notificacao;

/**
 *
 * @author Luis1
 */
public interface INotificacaoRepository {
    public void inserirNotificacao(Notificacao notificacao);
    public void limparSistema();
    
}
