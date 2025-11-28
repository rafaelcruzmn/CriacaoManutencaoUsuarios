/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import repository.UsuarioRepository;
import view.PainelAdministradorView;

/**
 *
 * @author Luis1
 */
public class PainelAdministradorPresenter {
    String nomeDeUsuario;
    UsuarioRepository repository;
    PainelAdministradorView view;
    
    public PainelAdministradorPresenter(String nomeDeUsuario, UsuarioRepository repository){
        if (repository == null){
            throw new RuntimeException("Repository inválida!\n");
        }
        
        if (nomeDeUsuario == null){
            throw new RuntimeException("Usuário inválido!\n");
        }
        
        this.nomeDeUsuario = nomeDeUsuario;
        this.repository = repository;
        view = new PainelAdministradorView();
        configuraView();
    }
    
    private void configuraView(){
        view.setVisible(false);
        view.getBtnCadastrarUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    new CadastroPorAdministradorPresenter(nomeDeUsuario, repository);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        
        view.setVisible(true);
    }
}
