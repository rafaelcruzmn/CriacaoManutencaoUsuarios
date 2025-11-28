/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import enumerator.TipoUsuario;
import model.Usuario;
import repository.UsuarioRepository;
import view.AutoCadastroView;
import view.PrimeiroCadastroView;

/**
 *
 * @author Luis1
 */
public class AutoCadastroPresenter {
    private AutoCadastroView view;
    private Usuario usuario;
    private UsuarioRepository repository;
    
    public AutoCadastroPresenter(UsuarioRepository repository){
        if (repository == null){
            throw new RuntimeException("Repository inválida!");
        }
        
        this.repository = repository;
        this.view = new AutoCadastroView();
        configuraView();
    }
    
    private void configuraView(){
        view.setVisible(false);
        
        view.getBtnCadastrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    cadastrar();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.getBtnVoltar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    voltar();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.setVisible(true);
    }
    
    private void cadastrar(){
        String nome = view.getTxtNome().getText();
        String nomeDeUsuario = view.getTxtNomeDeUsuario().getText();
        String senha = view.getTxtSenha().getText();
        TipoUsuario tipo = TipoUsuario.USUARIOCOMUM;
        boolean autorizado = false;
        LocalDate dataCadastro = LocalDate.now();
        
        // Chamar o validador de senha aqui?
        
        this.usuario = new Usuario(nome, nomeDeUsuario, senha, tipo, autorizado, dataCadastro);
        
        repository.inserirUsuario(usuario);
        limparTela();
    }
    
    private void voltar() {
        view.dispose();
    }
    
    private void limparTela(){
        view.getTxtNome().setText("");
        view.getTxtNomeDeUsuario().setText("");
        view.getTxtSenha().setText("");
    }
}
