/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import enumerator.TipoUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Usuario;
import repository.UsuarioRepository;
import view.PainelUsuarioComumView;

/**
 *
 * @author Luis1
 */
public class PainelUsuarioComumPresenter {
    Usuario usuarioLogado;
    UsuarioRepository repository;
    PainelUsuarioComumView view;
    
    public PainelUsuarioComumPresenter(Usuario usuarioLogado, UsuarioRepository repository){
        if (repository == null){
            throw new RuntimeException("Repository inválida!\n");
        }
        
        if (usuarioLogado == null){
            throw new RuntimeException("Usuário inválido!\n");
        }
        
        this.usuarioLogado = usuarioLogado;
        this.repository = repository;
        view = new PainelUsuarioComumView();
        configuraView();
    }
    
    private void configuraView(){
        view.setVisible(false);
        
        view.getBtAlterarSenha().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    new AlterarSenhaPorUsuarioPresenter(usuarioLogado, repository);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        
        view.setVisible(true);
    }
}
