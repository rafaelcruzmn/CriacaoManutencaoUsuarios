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
import view.ConfirmacaoLimpezaSistemaView;

/**
 *
 * @author Luis1
 */
public class ConfirmacaoLimpezaSistemaPresenter {
    private ConfirmacaoLimpezaSistemaView view;
    private UsuarioRepository repository;
    private Usuario usuarioLogado;
    
    public ConfirmacaoLimpezaSistemaPresenter(Usuario usuarioLogado, UsuarioRepository repository){
        this.usuarioLogado = usuarioLogado;
        this.repository = repository;
        view = new ConfirmacaoLimpezaSistemaView();
        
        configurarView();
    }
    
    private void configurarView(){
        view.setVisible(false);
        
        view.getBtnVoltar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    view.dispose();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.getBtnConfirmar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    repository.limparSistema();
                    System.exit(0);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.setVisible(true);
    }
}
