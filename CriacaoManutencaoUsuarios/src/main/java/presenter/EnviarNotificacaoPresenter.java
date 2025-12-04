/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;
import model.Notificacao;
import model.Usuario;
import repository.NotificacaoRepository;
import repository.UsuarioNotificacaoRepository;
import service.ConexaoBancoService;
import view.EnviarNotificacaoView;

/**
 *
 * @author Luis1
 */
public class EnviarNotificacaoPresenter {
    private Usuario usuarioLogado;
    private List<Usuario> usuariosNotificados;
    private Notificacao notificacao;
    private NotificacaoRepository notificacaoRepository;
    private UsuarioNotificacaoRepository usuarioNotificacaoRepository;
    private EnviarNotificacaoView view;
    
    public EnviarNotificacaoPresenter(Usuario usuarioLogado, List<Usuario> usuariosNotificados){
        this.usuarioLogado = usuarioLogado;
        this.usuariosNotificados = usuariosNotificados;
        this.view = new EnviarNotificacaoView();
        this.notificacaoRepository = new NotificacaoRepository(new ConexaoBancoService("jdbc:sqlite:usuarios.db"));
        this.usuarioNotificacaoRepository = new UsuarioNotificacaoRepository(new ConexaoBancoService("jdbc:sqlite:usuarios.db"));
        
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
        
        view.getBtnEnviar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    enviar();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.setVisible(true);
    }
    
    private void enviar(){
        String titulo = view.getTxtTitulo().getText();
        String mensagem = view.getTxtAreaMensagem().getText();
        
        this.notificacao = new Notificacao(Optional.empty(), titulo, mensagem, usuarioLogado, usuariosNotificados, LocalDate.now());
        
        notificacaoRepository.inserirNotificacao(notificacao);
        view.dispose();
    }
}
