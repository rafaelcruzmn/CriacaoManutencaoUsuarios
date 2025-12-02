/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Usuario;
import repository.UsuarioRepository;
import view.ConfirmacaoExclusaoUsuarioPorAdministradorView;

/**
 *
 * @author Luis1
 */
public class ConfirmacaoExclusaoUsuarioPorAdministradorPresenter {
    private Usuario usuarioSelecionado;
    private UsuarioRepository repository;
    private ConfirmacaoExclusaoUsuarioPorAdministradorView view;
    
    public ConfirmacaoExclusaoUsuarioPorAdministradorPresenter(Usuario usuarioSelecionado, UsuarioRepository repository){
        this.usuarioSelecionado = usuarioSelecionado;
        this.repository = repository;
        view = new ConfirmacaoExclusaoUsuarioPorAdministradorView();
        
        configurarView();
    }
    
    
    private void configurarView(){
        view.setVisible(false);
        
        view.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    voltar();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.getBtnConfirmar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    repository.excluir(usuarioSelecionado);
                    voltar();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        view.setVisible(true);
    }
    
    private void voltar(){
        view.dispose();
    }
}