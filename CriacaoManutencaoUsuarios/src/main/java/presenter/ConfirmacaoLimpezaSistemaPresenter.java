/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Usuario;
import repository.NotificacaoRepositorySQLite;
import repository.UsuarioNotificacaoRepository;
import repository.UsuarioRepository;
import view.ConfirmacaoLimpezaSistemaView;
import pss.LogService;

/**
 *
 * @author Luis1
 */
public class ConfirmacaoLimpezaSistemaPresenter {
    private ConfirmacaoLimpezaSistemaView view;
    private UsuarioRepository usuarioRepository;
    private NotificacaoRepositorySQLite notificacaoRepository;
    private UsuarioNotificacaoRepository usuarioNotificacaoRepository;
    private Usuario usuarioLogado;
    
    public ConfirmacaoLimpezaSistemaPresenter(Usuario usuarioLogado, UsuarioRepository usuarioRepository, 
            NotificacaoRepositorySQLite notificacaoRepository, UsuarioNotificacaoRepository usuarioNotificacaoRepository){
        this.usuarioLogado = usuarioLogado;
        this.usuarioRepository = usuarioRepository;
        this.notificacaoRepository = notificacaoRepository;
        this.usuarioNotificacaoRepository = usuarioNotificacaoRepository;
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
                    LogService.logOperacaoSucesso("EXCLUSAO_DO_SISTEMA_ADMINISTRADOR",usuarioLogado.getNome(),usuarioLogado.getNomeDeUsuario());
                    usuarioRepository.limparSistema();
                    notificacaoRepository.limparSistema();
                    usuarioNotificacaoRepository.limparSistema();
                    System.exit(0);
                } catch (Exception ex){
                    LogService.logOperacaoFalha("EXCLUSAO_DO_SISTEMA_ADMINISTRADOR",usuarioLogado.getNome(),usuarioLogado.getNomeDeUsuario(),"Erro na exclusao do sistema");
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.setVisible(true);
    }
}
