/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import enumerator.TipoUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import model.Usuario;
import repository.UsuarioRepository;
import view.VisualizacaoUsuarioPorAdministradorView;

/**
 *
 * @author Luis1
 */
public class VisualizacaoUsuarioPorAdministradorPresenter {
    private UsuarioRepository repository;
    private Usuario usuarioLogado;
    private Usuario usuarioSelecionado;
    private VisualizacaoUsuarioPorAdministradorView view;
    
    public VisualizacaoUsuarioPorAdministradorPresenter(Usuario usuarioLogado, Usuario usuarioSelecionado, UsuarioRepository repository){
        this.repository = repository;
        this.usuarioLogado = usuarioLogado;
        this.usuarioSelecionado = usuarioSelecionado;
        
        view = new VisualizacaoUsuarioPorAdministradorView();
        configurarView();
    }
    
    private void configurarView(){
        view.setVisible(false);
        String id = Integer.toString(usuarioSelecionado.getId());
        String nome = usuarioSelecionado.getNome();
        String nomeDeUsuario = usuarioSelecionado.getNomeDeUsuario();
        TipoUsuario tipo = usuarioSelecionado.getTipo();
        String dataCadastro = usuarioSelecionado.getDataCadastro().toString();
        
        view.getTxtId().setText(id);
        view.getTxtId().setEditable(false);
        
        view.getTxtNome().setText(nome);
        view.getTxtNome().setEditable(false);
        
        view.getTxtNomeDeUsuario().setText(nomeDeUsuario);
        view.getTxtNomeDeUsuario().setEditable(false);
        
        view.getTxtTipo().setText(tipo.toString());
        view.getTxtTipo().setEditable(false);
        
        view.getTxtDataCadastro().setText(dataCadastro);
        view.getTxtDataCadastro().setEditable(false);
        
        if (tipo.getValor() == 0){
            view.getBtnEditar().setEnabled(false);
            view.getBtnExcluir().setEnabled(false);
        }
        if (tipo.getValor() == 1 && usuarioLogado.getTipo().getValor() == 1){
            view.getBtnEditar().setEnabled(false);
            view.getBtnExcluir().setEnabled(false);
        }
        
        view.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    new EdicaoUsuarioPorAdministradorPresenter(usuarioLogado, usuarioSelecionado, repository);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.getBtnExcluir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    new ConfirmacaoExclusaoUsuarioPorAdministradorPresenter(usuarioSelecionado, repository);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
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
        
        view.setVisible(true);
    }
    
    private void voltar() {
        view.dispose();
    }
}