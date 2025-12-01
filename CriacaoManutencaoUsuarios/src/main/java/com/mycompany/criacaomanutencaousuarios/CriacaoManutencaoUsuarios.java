/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.criacaomanutencaousuarios;

import presenter.AutenticacaoUsuarioPresenter;
import repository.UsuarioRepository;
import presenter.PrimeiroCadastroPresenter;
import service.ConexaoBancoService;
/**
 *
 * @author Rafael
 */
public class CriacaoManutencaoUsuarios {

    public static void main(String[] args) {
        ConexaoBancoService conexao = new ConexaoBancoService();
        UsuarioRepository repository = new UsuarioRepository(conexao);
        
        if (repository.getTamanho() == 0){
            new PrimeiroCadastroPresenter(repository);
        } else {
            new AutenticacaoUsuarioPresenter(repository);
        }         
    }
}