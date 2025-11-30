/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import enumerator.TipoUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import model.Usuario;
import repository.UsuarioRepository;
import view.CadastroPorAdministradorView;

/**
 *
 * @author Luis1
 */
public class CadastroPorAdministradorPresenter {
    Usuario usuario;
    UsuarioRepository repository;
    CadastroPorAdministradorView view;
    
    public CadastroPorAdministradorPresenter(UsuarioRepository repository){
        if (repository == null){
            throw new RuntimeException("Repository inválida!");
        }
        
        this.repository = repository;
        this.view = new CadastroPorAdministradorView();
        configurarView();
    }
    
    private void configurarView(){
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
        TipoUsuario tipo;
        //System.out.println(view.getBgTipo().getSelection().getActionCommand());
        
        if("USUARIO".equals(view.getBgTipo().getSelection().getActionCommand())){
            tipo = TipoUsuario.USUARIOCOMUM;
        } else{
            tipo = TipoUsuario.ADMINSEC;
        }
        
        boolean autorizado = true;
        LocalDate dataCadastro = LocalDate.now();
        
        // Chamar o validador de senha aqui?    
        
        this.usuario = new Usuario(nome, nomeDeUsuario, senha, tipo, autorizado, dataCadastro);
        
        repository.inserirUsuario(usuario);
        limparTela();
    }
    
    private void limparTela(){
        view.getTxtNome().setText("");
        view.getTxtNomeDeUsuario().setText("");
        view.getTxtSenha().setText("");
        view.getBgTipo().clearSelection();
    }
    
    private void voltar() {
        view.dispose();
    }
}
