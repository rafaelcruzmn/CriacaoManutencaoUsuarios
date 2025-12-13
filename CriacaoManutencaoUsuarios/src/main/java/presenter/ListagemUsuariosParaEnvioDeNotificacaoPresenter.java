/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Usuario;
import repository.IUsuarioRepository;
import tableModel.EnvioNotificacaoTableModel;
import view.ListagemUsuariosParaEnvioDeNotificacaoView;

/**
 *
 * @author Luis1
 */
public class ListagemUsuariosParaEnvioDeNotificacaoPresenter {
    private ListagemUsuariosParaEnvioDeNotificacaoView view;
    private IUsuarioRepository repository;
    private Usuario usuarioLogado;
    private List<Usuario> usuarios;
    private JTable tabelaUsuarios;
    
    public ListagemUsuariosParaEnvioDeNotificacaoPresenter(Usuario usuarioLogado, List<Usuario> usuarios, 
            IUsuarioRepository repository){
        if(usuarioLogado == null){
            throw new RuntimeException("usuarioLogado inválido.");
        }
        
        if(usuarios == null){
            throw new RuntimeException("usuarios inválidos.");
        }
        
        if(repository == null){
            throw new RuntimeException("repository inválida");
        }
        
        this.view = new ListagemUsuariosParaEnvioDeNotificacaoView();
        this.repository = repository;
        this.usuarioLogado = usuarioLogado;
        this.usuarios = usuarios;
        this.tabelaUsuarios = view.getTblSelecaoUsuariosNotificacao();
        
        configurarView();
    }
    
    private void configurarView(){
        view.setVisible(false);
        popularTabela();
        
        view.getBtnCriarNotificacao().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    criarNotificacao();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
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
        
        view.setVisible(true);
    }
    
    private void popularTabela(){
        EnvioNotificacaoTableModel model = new EnvioNotificacaoTableModel(usuarios);
        
        
        tabelaUsuarios.setModel(model);
    }
    
    private void criarNotificacao(){
        
        EnvioNotificacaoTableModel model = (EnvioNotificacaoTableModel) tabelaUsuarios.getModel();
        List<Usuario> usuariosNotificados = model.getUsuariosSelecionados();
        
        if(usuariosNotificados.size() > 0){
            new EnviarNotificacaoPresenter(usuarioLogado, usuariosNotificados);
        }
        
    }
}
