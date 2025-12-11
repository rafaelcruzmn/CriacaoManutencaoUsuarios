/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.criacaomanutencaousuarios;

import presenter.AutenticacaoUsuarioPresenter;
import presenter.ConfigurarLogPresenter;
import repository.UsuarioRepository;
import presenter.PrimeiroCadastroPresenter;
import repository.LogRepositorySQLite;
/**
 *
 * @author Rafael
 */
public class CriacaoManutencaoUsuarios {

    public static void main(String[] args) {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        LogRepositorySQLite logRepository = new LogRepositorySQLite();
        ConfigurarLogPresenter x = new ConfigurarLogPresenter(logRepository,"");
        
        if (usuarioRepository.getTamanho() == 0){
            new PrimeiroCadastroPresenter(usuarioRepository,logRepository);
        } else {
            new AutenticacaoUsuarioPresenter(usuarioRepository,logRepository);
        }         
    }
}