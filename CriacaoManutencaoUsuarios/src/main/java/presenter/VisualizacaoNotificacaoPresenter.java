/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Notificacao;
import view.VisualizacaoNotificacaoView;

/**
 *
 * @author Luis1
 */
public class VisualizacaoNotificacaoPresenter {
    private VisualizacaoNotificacaoView view;
    private Notificacao notificacao;
    
    public VisualizacaoNotificacaoPresenter(Notificacao notificacao){
        if(notificacao == null){
            throw new RuntimeException("notificacao inválida.");
        }
        
        this.notificacao = notificacao;
        this.view = new VisualizacaoNotificacaoView();
        
        configurarView();
    }
    
    private void configurarView(){
        view.setVisible(false);
        
        String titulo = notificacao.getTitulo();
        String mensagem = notificacao.getMensagem();
        
        view.getTxtTitulo().setText(titulo);
        view.getTxtTitulo().setEditable(false);
        
        view.getTxtAreaMensagem().setText(mensagem);
        view.getTxtAreaMensagem().setEditable(false);
        
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
        
        view.setVisible(true);
    }
}
