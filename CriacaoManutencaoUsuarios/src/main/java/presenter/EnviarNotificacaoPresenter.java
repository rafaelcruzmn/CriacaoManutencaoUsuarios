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
import pss.LogService;
import repository.INotificacaoRepository;
import repository.IUsuarioNotificacaoRepository;
import repository.NotificacaoRepositorySQLite;
import repository.UsuarioNotificacaoRepositorySQLite;
import view.EnviarNotificacaoView;

/**
 *
 * @author Luis1
 */
public class EnviarNotificacaoPresenter {
    private Usuario usuarioLogado;
    private List<Usuario> usuariosNotificados;
    private Notificacao notificacao;
    private INotificacaoRepository notificacaoRepository;
    private IUsuarioNotificacaoRepository usuarioNotificacaoRepository;
    private EnviarNotificacaoView view;
    
    public EnviarNotificacaoPresenter(Usuario usuarioLogado, List<Usuario> usuariosNotificados){
        if(usuarioLogado == null){
            throw new RuntimeException("usuarioLogado inválido.");
        }
        
        if(usuariosNotificados == null){
            throw new RuntimeException("usuariosNotificados inválidos.");
        }
        
        this.usuarioLogado = usuarioLogado;
        this.usuariosNotificados = usuariosNotificados;
        this.view = new EnviarNotificacaoView();
        this.notificacaoRepository = new NotificacaoRepositorySQLite();
        this.usuarioNotificacaoRepository = new UsuarioNotificacaoRepositorySQLite();
        
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
        
        try{
        this.notificacao = new Notificacao(Optional.empty(), titulo, mensagem, usuarioLogado, usuariosNotificados, LocalDate.now());
        for (Usuario usuarioDestino : usuariosNotificados){
            LogService.logOperacaoSucesso("ENVIO_NOTIFICACAO",usuarioDestino.getNome(),usuarioLogado.getNomeDeUsuario());
        }     
        
        notificacaoRepository.inserirNotificacao(notificacao);
        view.dispose();
    }
     catch(Exception ex){
         for (Usuario usuarioDestino : usuariosNotificados) {
            LogService.logOperacaoFalha("ENVIO_NOTIFICACAO",usuarioDestino.getNome(),usuarioLogado.getNomeDeUsuario(),"Falha ao enviar notificação: ");
        }
        throw new RuntimeException("Erro ao enviar notificação");
    }
  }
}
