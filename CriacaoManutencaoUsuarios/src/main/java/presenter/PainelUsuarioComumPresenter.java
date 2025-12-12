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
import repository.UsuarioNotificacaoRepositorySQLite;
import view.PainelUsuarioComumView;

/**
 *
 * @author Luis1
 */
public class PainelUsuarioComumPresenter {
    private Usuario usuarioLogado;
    private IUsuarioRepository repository;
    private PainelUsuarioComumView view;
    private IUsuarioNotificacaoRepository notificaRepository;
    
    public PainelUsuarioComumPresenter(Usuario usuarioLogado, IUsuarioRepository repository, IUsuarioNotificacaoRepository notificaRepository){
        if (repository == null){
            throw new RuntimeException("Repository inválida!\n");
        }
        
        if (usuarioLogado == null){
            throw new RuntimeException("Usuário inválido!\n");
        }
        
        this.usuarioLogado = usuarioLogado;
        this.repository = repository;
        this.notificaRepository = notificaRepository;
        view = new PainelUsuarioComumView();
        rodape();
        configuraView();
    }
    
    private void rodape(){
        view.setNome(usuarioLogado.getNome());
        String qtd = notificaRepository.getQuantidadeNaoLidas(usuarioLogado.getId());
        view.setNotificacoes(qtd);
        //view.setTipo(usuarioLogado.getTipo());
    }
    
    private void configuraView(){
        view.setVisible(false);
        
        view.getBtnAlterarSenha().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    new AlterarSenhaPorUsuarioPresenter(usuarioLogado, repository);
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
        
            view.getBtnNotificacoes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    String qtd = notificaRepository.getQuantidadeNaoLidas(usuarioLogado.getId());
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
