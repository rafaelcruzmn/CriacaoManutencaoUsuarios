/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import enumerator.TipoUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Usuario;
import pss.LogService;
import repository.IUsuarioRepository;
import view.EdicaoUsuarioPorAdministradorView;

/**
 *
 * @author Luis1
 */
public class EdicaoUsuarioPorAdministradorPresenter {
    private Usuario usuarioLogado;
    private Usuario usuarioSelecionado;
    private IUsuarioRepository repository;
    private EdicaoUsuarioPorAdministradorView view;
    
    public EdicaoUsuarioPorAdministradorPresenter(Usuario usuarioLogado, Usuario usuarioSelecionado, IUsuarioRepository repository){
        if(usuarioLogado == null){
            throw new RuntimeException("usuarioLogado inválido.");
        }
        
        if(usuarioSelecionado == null){
            throw new RuntimeException("usuarioSelecionado inválido.");
        }
        
        if(repository == null){
            throw new RuntimeException("repository inválida.");
        }
        
        this.usuarioLogado = usuarioLogado;
        this.usuarioSelecionado = usuarioSelecionado;
        this.repository = repository;
        this.view = new EdicaoUsuarioPorAdministradorView();
        
        configurarView();
    }
    
    private void configurarView(){
        view.setVisible(false);
        String id = Integer.toString(usuarioSelecionado.getId());
        String nome = usuarioSelecionado.getNome();
        String nomeDeUsuario = usuarioSelecionado.getNomeDeUsuario();
        String tipo = usuarioSelecionado.getTipo().toString();
        String dataCadastro = usuarioSelecionado.getDataCadastro().toString();
        
        view.getTxtId().setText(id);
        view.getTxtId().setEditable(false);
        
        view.getTxtNome().setText(nome);
        view.getTxtNome().setEditable(false);
        
        view.getTxtNomeDeUsuario().setText(nomeDeUsuario);
        view.getTxtNomeDeUsuario().setEditable(false);
        
        if (tipo.equals("ADMINSEC")){
            view.getRdTipoAdmin().setSelected(true);
        } else{
            view.getRdTipoUsuario().setSelected(true);
        }
        
        view.getTxtDataCadastro().setText(dataCadastro);
        view.getTxtDataCadastro().setEditable(false);
        
        view.getBtnVoltar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    voltar();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.getBtnSalvar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    salvar();
                } catch (Exception ex){
                    LogService.logOperacaoFalha("EDICAO_TIPO_FEITO_POR_ADMINISTRADOR",usuarioSelecionado.getNome(),usuarioLogado.getNomeDeUsuario(),"Erro na edicao do usuario");
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.setVisible(true);
    }
    
    private void salvar(){
        TipoUsuario novoTipo;
        
        if("USUARIO".equals(view.getBgTipo().getSelection().getActionCommand())){
            novoTipo = TipoUsuario.USUARIOCOMUM;
            LogService.logOperacaoSucesso("EDICAO_TIPO_FEITO_POR_ADMINISTRADOR_ADM -> USUARIO_COMUM",usuarioSelecionado.getNome(),usuarioLogado.getNomeDeUsuario());
        } else{
            LogService.logOperacaoSucesso("EDICAO_TIPO_FEITO_POR_ADMINISTRADOR_USUARIO_COMUM -> ADM",usuarioSelecionado.getNome(),usuarioLogado.getNomeDeUsuario());
            novoTipo = TipoUsuario.ADMINSEC;
        }
        
        repository.alterarTipoUsuario(usuarioSelecionado, novoTipo);
        view.dispose();
    }
    
    private void voltar(){
        view.dispose();
    }
}
