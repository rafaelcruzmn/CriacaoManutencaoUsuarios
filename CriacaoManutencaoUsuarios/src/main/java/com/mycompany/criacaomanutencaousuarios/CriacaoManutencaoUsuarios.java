/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.criacaomanutencaousuarios;

import presenter.AutenticacaoUsuarioPresenter;
import repository.UsuarioRepository;
import presenter.PrimeiroCadastroPresenter;

/**
 *
 * @author Rafael
 */
public class CriacaoManutencaoUsuarios {

    public static void main(String[] args) {
        //IConexaoService conexao = new ConexaoService();
        //UsuarioRepository repository = new UsuarioRepository(conexao);
        UsuarioRepository repository = new UsuarioRepository();
        
        if (repository.getTamanho() == 0){
            new PrimeiroCadastroPresenter(repository);
        } else {
            new AutenticacaoUsuarioPresenter(repository);
        }         
    }
}