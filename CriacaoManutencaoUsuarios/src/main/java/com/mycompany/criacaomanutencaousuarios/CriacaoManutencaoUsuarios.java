/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.criacaomanutencaousuarios;

import presenter.AutenticacaoUsuarioPresenter;
import presenter.ConfigurarLogPresenter;
import repository.UsuarioRepositorySQLite;
import presenter.PrimeiroCadastroPresenter;
import repository.LogRepositorySQLite;
import repository.UsuarioNotificacaoRepositorySQLite;
/**
 *
 * @author Rafael
 */
public class CriacaoManutencaoUsuarios {

    public static void main(String[] args) {
        UsuarioRepositorySQLite usuarioRepository = new UsuarioRepositorySQLite();
        LogRepositorySQLite logRepository = new LogRepositorySQLite();
        new ConfigurarLogPresenter(logRepository,"");
        UsuarioNotificacaoRepositorySQLite notificaRepository = new UsuarioNotificacaoRepositorySQLite();
        
        if (usuarioRepository.getTamanho() == 0){
            new PrimeiroCadastroPresenter(usuarioRepository,logRepository, notificaRepository);
        } else {
            new AutenticacaoUsuarioPresenter(usuarioRepository,logRepository, notificaRepository);
        }         
    }
}