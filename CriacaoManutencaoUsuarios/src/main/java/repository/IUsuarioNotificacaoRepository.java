/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repository;

import java.util.List;
import model.Notificacao;

/**
 *
 * @author Luis1
 */
public interface IUsuarioNotificacaoRepository {
    public List<Notificacao> getNotificacoes(int id);
    public boolean getLida(int idUsuario, int idNotificacao);
    public void lerNotificacao(int idUsuario, int idNotificacao);
    public void limparSistema();
}
