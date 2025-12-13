/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Usuario;
import repository.ILogRepository;
import repository.IUsuarioNotificacaoRepository;
import repository.IUsuarioRepository;
import repository.NotificacaoRepositorySQLite;
import repository.UsuarioNotificacaoRepositorySQLite;
import view.PainelAdministradorView;

/**
 *
 * @author Luis1
 */
public class PainelAdministradorPresenter {
    private Usuario usuarioLogado;
    private IUsuarioRepository usuarioRepository;
    private ILogRepository logRepository;
    private IUsuarioNotificacaoRepository usuarioNotificacaoRepository;
    private PainelAdministradorView view;
    
    public PainelAdministradorPresenter(Usuario usuarioLogado, IUsuarioRepository usuarioRepository, ILogRepository logRepository, IUsuarioNotificacaoRepository usuarioNotificacaoRepository){
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
        this.usuarioNotificacaoRepository = usuarioNotificacaoRepository;
        view = new PainelAdministradorView();
        rodape();
        configurarView();
    }
    private void rodape(){
        view.setNome(usuarioLogado.getNome());
        String qtd = String.valueOf(usuarioNotificacaoRepository.getQuantidadeNaoLidas(usuarioLogado.getId()));
        view.setNotificacoes(qtd);
        //view.setTipo(usuarioLogado.getTipo());
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
                    new ManterUsuariosPresenter(usuarioLogado, usuarioRepository, usuarioNotificacaoRepository);
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
                    IUsuarioNotificacaoRepository usuarioNotificacaoRepository = new UsuarioNotificacaoRepositorySQLite();
                    
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
                    new ConfirmacaoLimpezaSistemaPresenter(usuarioLogado, usuarioRepository, new NotificacaoRepositorySQLite(), 
                            new UsuarioNotificacaoRepositorySQLite());
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
        
        view.getBtnNotificacoes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    String qtd = String.valueOf(usuarioNotificacaoRepository.getQuantidadeNaoLidas(usuarioLogado.getId()));
                    if(qtd.equals("0")){
                        JOptionPane.showMessageDialog(view, "Não há notificações pendentes!");
                        rodape();
                    }
                    else{
                        IUsuarioNotificacaoRepository usuarioNotificacaoRepository = new UsuarioNotificacaoRepositorySQLite();
                        new ListagemNotificacoesPresenter(usuarioLogado, usuarioNotificacaoRepository.getNotificacoesNaoLidas(usuarioLogado.getId()), usuarioNotificacaoRepository);
                        rodape();
                    }
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.setVisible(true);
    }
}
