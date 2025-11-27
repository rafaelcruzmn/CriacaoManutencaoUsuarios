/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.criacaomanutencaousuarios;

import repository.UsuarioRepository;
import presenter.PrimeiroCadastroPresenter;
import presenter.MenuInicialPresenter;

/**
 *
 * @author Rafael
 */
public class CriacaoManutencaoUsuarios {

    public static void main(String[] args) {
        UsuarioRepository repository = new UsuarioRepository();
        
        if (repository.getTamanho() == 0){
            PrimeiroCadastroPresenter presenter = new PrimeiroCadastroPresenter(repository);
        } else {
            MenuInicialPresenter presenter = new MenuInicialPresenter(repository);
        }
                
    }
}