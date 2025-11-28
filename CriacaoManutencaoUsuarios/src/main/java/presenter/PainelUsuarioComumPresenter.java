/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import model.Usuario;
import repository.UsuarioRepository;
import view.PainelUsuarioComumView;

/**
 *
 * @author Luis1
 */
public class PainelUsuarioComumPresenter {
    String nomeDeUsuario;
    UsuarioRepository repository;
    PainelUsuarioComumView view;
    
    public PainelUsuarioComumPresenter(String nomeDeUsuario, UsuarioRepository repository){
        if (repository == null){
            throw new RuntimeException("Repository inválida!\n");
        }
        
        if (nomeDeUsuario == null){
            throw new RuntimeException("Usuário inválido!\n");
        }
        
        this.nomeDeUsuario = nomeDeUsuario;
        this.repository = repository;
        view = new PainelUsuarioComumView();
        configuraView();
    }
    
    private void configuraView(){
        view.setVisible(false);
        
        
        
        view.setVisible(true);
    }
}
