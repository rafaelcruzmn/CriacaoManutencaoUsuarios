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
import view.ManterUsuariosView;

/**
 *
 * @author Luis1
 */
public class ManterUsuariosPresenter {
    private Usuario usuarioLogado;
    private ManterUsuariosView view;
    private UsuarioRepository repository;
    
    public ManterUsuariosPresenter(Usuario usuarioLogado, UsuarioRepository repository){
        this.repository = repository;
        this.usuarioLogado = usuarioLogado;
        this.view = new ManterUsuariosView();
        
        configurarView();
    }
    
    
    private void configurarView(){
        view.setVisible(false);
        view.getBtnCadastrarUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    new CadastroPorAdministradorPresenter(repository);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.getBtnListarUsuarios().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    new ListagemUsuariosCadastradosPresenter(usuarioLogado, repository.getTodosAutorizados(), repository);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.setVisible(true);
    }
}
