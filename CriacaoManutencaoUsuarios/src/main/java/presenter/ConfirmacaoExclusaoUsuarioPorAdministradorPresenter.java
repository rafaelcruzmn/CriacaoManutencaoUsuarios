/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import pss.LogService;
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
    private Usuario usuarioLogado;
    
    public ConfirmacaoExclusaoUsuarioPorAdministradorPresenter(Usuario usuarioLogado, Usuario usuarioSelecionado, UsuarioRepository repository){
        this.usuarioSelecionado = usuarioSelecionado;
        this.repository = repository;
        this.usuarioLogado = usuarioLogado;
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
                    LogService.logOperacaoSucesso("EXCLUSAO_USUARIO_POR_ADMINISTRADOR",usuarioSelecionado.getNome(),usuarioLogado.getNomeDeUsuario());
                    repository.excluir(usuarioSelecionado);
                    voltar();
                } catch (Exception ex){
                    LogService.logOperacaoFalha("EXCLUSAO_USUARIO_POR_ADMINISTRADOR",usuarioSelecionado.getNome(),usuarioLogado.getNomeDeUsuario(),"Erro na exclusao do usuario");
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