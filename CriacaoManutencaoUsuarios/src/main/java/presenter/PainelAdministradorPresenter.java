/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Usuario;
import repository.NotificacaoRepository;
import repository.UsuarioNotificacaoRepository;
import repository.UsuarioRepository;
import service.ConexaoBancoService;
import view.PainelAdministradorView;
import repository.LogRepository;

/**
 *
 * @author Luis1
 */
public class PainelAdministradorPresenter {
    private Usuario usuarioLogado;
    private UsuarioRepository usuarioRepository;
    private LogRepository logRepository;
    private PainelAdministradorView view;
    
    public PainelAdministradorPresenter(Usuario usuarioLogado, UsuarioRepository usuarioRepository, LogRepository logRepository){
        if (usuarioRepository == null){
            throw new RuntimeException("Repository inválida!\n");
        }
        
        if (usuarioLogado == null){
            throw new RuntimeException("Usuário inválido!\n");
        }
        
        if (logRepository == null){
            throw new RuntimeException("Repository inválida!\n");
        }
        
        this.usuarioLogado = usuarioLogado;
        this.usuarioRepository = usuarioRepository;
        this.logRepository = logRepository;
        view = new PainelAdministradorView();
        configurarView();
    }
    
    private void configurarView(){
        view.setVisible(false);
        
        if (usuarioLogado.getTipo().getValor() != 0){
            view.getBtnLimparSistema().setEnabled(false);
        }
        
        view.getBtnManterUsuarios().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    new ManterUsuariosPresenter(usuarioLogado, usuarioRepository);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.getBtnAutorizarNovoUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    new AutorizarNovoUsuarioPresenter(usuarioLogado, usuarioRepository);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.getBtnAlterarSenha().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    new AlterarSenhaPorUsuarioPresenter(usuarioLogado, usuarioRepository);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.getBtnEnviarNotificacao().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    new ListagemUsuariosParaEnvioDeNotificacaoPresenter(usuarioLogado, usuarioRepository.getTodosAutorizados(), usuarioRepository);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.getBtnVisualizarNotificacoes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    UsuarioNotificacaoRepository usuarioNotificacaoRepository = new UsuarioNotificacaoRepository(new ConexaoBancoService());
                    
                    new ListagemNotificacoesPresenter(usuarioLogado, usuarioNotificacaoRepository.getNotificacoes(usuarioLogado.getId()), usuarioNotificacaoRepository);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.getBtnLimparSistema().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    new ConfirmacaoLimpezaSistemaPresenter(usuarioLogado, usuarioRepository, new NotificacaoRepository(new ConexaoBancoService()), 
                            new UsuarioNotificacaoRepository(new ConexaoBancoService()));
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        view.getBtnConfiguracao().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    new ConfigurarLogPresenter(logRepository);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.setVisible(true);
    }
}
