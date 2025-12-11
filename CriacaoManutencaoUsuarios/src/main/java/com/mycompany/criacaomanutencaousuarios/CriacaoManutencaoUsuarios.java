/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.criacaomanutencaousuarios;

import presenter.AutenticacaoUsuarioPresenter;
import presenter.ConfigurarLogPresenter;
import repository.UsuarioRepository;
import presenter.PrimeiroCadastroPresenter;
import service.ConexaoBancoService;
import repository.LogRepository;
/**
 *
 * @author Rafael
 */
public class CriacaoManutencaoUsuarios {

    public static void main(String[] args) {
        ConexaoBancoService conexao = new ConexaoBancoService();
        UsuarioRepository repository = new UsuarioRepository(conexao);
        LogRepository logRepository = new LogRepository(conexao);
        ConfigurarLogPresenter x = new ConfigurarLogPresenter(logRepository,"");
        
        if (repository.getTamanho() == 0){
            new PrimeiroCadastroPresenter(repository,logRepository);
        } else {
            new AutenticacaoUsuarioPresenter(repository,logRepository);
        }         
    }
}