/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import repository.UsuarioRepository;
import view.AutenticacaoUsuarioView;
import presenter.PainelUsuarioComumPresenter;

/**
 *
 * @author Luis1
 */
public class AutenticacaoUsuarioPresenter {
    AutenticacaoUsuarioView view;
    //String nomeDeUsuario;
    //String senha;
    UsuarioRepository repository;
    
    public AutenticacaoUsuarioPresenter(UsuarioRepository repository) {
        if (repository == null){
            throw new RuntimeException("Repository inválida!\n");
        }
        
        this.repository = repository;
        this.view = new AutenticacaoUsuarioView();
        configuraView();   
    }
    
    private void configuraView(){
        view.setVisible(false);
        
        view.getBtnCadastrar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    new AutoCadastroPresenter(repository);
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
    
        view.getBtnAutenticar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    autenticarUsuario();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        view.setVisible(true);
    }
    
    private void autenticarUsuario(){
        String nomeDeUsuario = view.getTxtNomeDeUsuario().getText();
        String senha = view.getTxtSenha().getText();
        
        if (repository.autenticarUsuario(nomeDeUsuario, senha)){
            view.dispose();
            new PainelUsuarioComumPresenter(nomeDeUsuario, repository);
        }
    }
    
    private void limparTela(){
        view.getTxtNomeDeUsuario().setText("");
        view.getTxtSenha().setText("");
    }
}
