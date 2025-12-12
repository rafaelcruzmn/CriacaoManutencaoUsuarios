/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import pss.LogService;
import enumerator.TipoUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Usuario;
import repository.ILogRepository;
import repository.IUsuarioNotificacaoRepository;
import repository.IUsuarioRepository;
import view.AutenticacaoUsuarioView;

/**
 *
 * @author Luis1
 */
public class AutenticacaoUsuarioPresenter {
    private AutenticacaoUsuarioView view;
    private String nomeDeUsuario;
    private IUsuarioRepository repository;
    private ILogRepository logRepository;
    private IUsuarioNotificacaoRepository notificaRepository;
    
    public AutenticacaoUsuarioPresenter(IUsuarioRepository repository, ILogRepository logRepository, IUsuarioNotificacaoRepository notificaRepository) {
        if (repository == null){
            throw new RuntimeException("Repository inválida!\n");
        }

        this.repository = repository;
        this.logRepository = logRepository;
        this.notificaRepository = notificaRepository;
        this.view = new AutenticacaoUsuarioView();
        configuraView();   
    }
    
    private void configuraView(){
        view.setVisible(false);
        
        view.getBtnCadastrar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    new AutoCadastroPresenter(repository);
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
    
        view.getBtnAutenticar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    autenticarUsuario();
                } catch(Exception ex){
                    LogService.logOperacaoFalha("LOGIN_USUARIO",nomeDeUsuario = view.getTxtNomeDeUsuario().getText(),nomeDeUsuario = view.getTxtNomeDeUsuario().getText(),"Falha no Login");
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        view.setVisible(true);
    }
    
    private void autenticarUsuario(){
        String nomeDeUsuario = view.getTxtNomeDeUsuario().getText().toLowerCase();
        String senha = view.getTxtSenha().getText();
        
        Usuario usuario = repository.autenticarUsuario(nomeDeUsuario, senha);
        TipoUsuario tipo = null;
        

        if (usuario != null){
            view.dispose();
            tipo = usuario.getTipo();
        }
        else{
            LogService.logOperacaoFalha("LOGIN_USUARIO",view.getTxtNomeDeUsuario().getText(),"","Falha no Login");
        }
        
        if (tipo != null){
            if (tipo.getValor() == 2){
                new PainelUsuarioComumPresenter(usuario, repository, notificaRepository);
                LogService.logOperacaoSucesso("LOGIN_USUARIO",usuario.getNome(),usuario.getNomeDeUsuario());
            }
            if (tipo.getValor() == 0 || tipo.getValor() == 1){
                new PainelAdministradorPresenter(usuario, repository, logRepository, notificaRepository);
                LogService.logOperacaoSucesso("LOGIN_ADM",usuario.getNome(),usuario.getNomeDeUsuario());
            }
        }
    }
    
    private void limparTela(){
        view.getTxtNomeDeUsuario().setText("");
        view.getTxtSenha().setText("");
    }
}
