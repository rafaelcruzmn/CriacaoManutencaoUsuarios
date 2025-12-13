/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Usuario;
import repository.IUsuarioNotificacaoRepository;
import repository.IUsuarioRepository;
import view.ManterUsuariosView;

/**
 *
 * @author Luis1
 */
public class ManterUsuariosPresenter {
    private Usuario usuarioLogado;
    private ManterUsuariosView view;
    private IUsuarioRepository usuarioRepository;
    private IUsuarioNotificacaoRepository usuarioNotificacaoRepository;
    
    public ManterUsuariosPresenter(Usuario usuarioLogado, IUsuarioRepository usuarioRepository, IUsuarioNotificacaoRepository usuarioNotificacaoRepository){
        this.usuarioNotificacaoRepository = usuarioNotificacaoRepository;
        this.usuarioRepository = usuarioRepository;
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
                    new CadastroPorAdministradorPresenter(usuarioLogado, usuarioRepository);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.getBtnListarUsuarios().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    new ListagemUsuariosCadastradosPresenter(usuarioLogado, usuarioRepository.getTodosAutorizados(), usuarioRepository, usuarioNotificacaoRepository);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.setVisible(true);
    }
}
