/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Usuario;
import repository.IUsuarioNotificacaoRepository;
import repository.IUsuarioRepository;
import tableModel.UsuariosCadastradosTableModel;
import view.ListagemUsuariosCadastradosView;

/**
 *
 * @author Luis1
 */
public class ListagemUsuariosCadastradosPresenter {
    private ListagemUsuariosCadastradosView view;
    private IUsuarioRepository usuarioRepository;
    private IUsuarioNotificacaoRepository usuarioNotificacaoRepository;
    private Usuario usuarioLogado;
    private List<Usuario> usuarios;
    private ListSelectionModel selectionModel;
    private JTable tabelaUsuarios;
    
    ListagemUsuariosCadastradosPresenter(Usuario usuarioLogado, List<Usuario> usuarios, IUsuarioRepository usuarioRepository, 
            IUsuarioNotificacaoRepository usuarioNotificacaoRepository){
        
        if(usuarioLogado == null){
            throw new RuntimeException("usuarioLogado inválido.");
        }
        
        if(usuarios == null){
            throw new RuntimeException("usuarios inválidos.");
        }
        
        if(usuarioRepository == null){
            throw new RuntimeException("usuarioRepository inválida.");
        }
        
        if(usuarioNotificacaoRepository == null){
            throw new RuntimeException("usuarioNotificacaoRepository inválida.");
        }
        
        this.usuarioRepository = usuarioRepository;
        this.usuarioLogado = usuarioLogado;
        this.usuarios = usuarios;
        this.usuarioNotificacaoRepository = usuarioNotificacaoRepository;
        view = new ListagemUsuariosCadastradosView();
        tabelaUsuarios = view.getTblListagemUsuarios();
        this.selectionModel = tabelaUsuarios.getSelectionModel();
        configurarView();
    }
    
    private void configurarView(){
        view.setVisible(false);
        popularTabela();
        
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                return;
            }
                int selectedRow = tabelaUsuarios.getSelectedRow();
                if (selectedRow != -1) {
                    int modelRow = tabelaUsuarios.convertRowIndexToModel(selectedRow);
                    UsuariosCadastradosTableModel model = (UsuariosCadastradosTableModel) tabelaUsuarios.getModel();
                    Usuario usuarioSelecionado = model.getUsuarioAt(modelRow);
                    new VisualizacaoUsuarioPorAdministradorPresenter(usuarioLogado, usuarioSelecionado, usuarioRepository);
                }
            }
        });
        
        view.setVisible(true);
    }
   
        
    
    private void popularTabela(){
        List<Integer> totalNotificacoes = new ArrayList<>();
        List<Integer> notificacoesLidas = new ArrayList<>();
        
        for(Usuario usuario: usuarios){
            totalNotificacoes.add(usuarioNotificacaoRepository.getQuantidadeNotificacoes(usuario.getId()));
            notificacoesLidas.add(usuarioNotificacaoRepository.getQuantidadeLidas(usuario.getId()));
        }
        
        UsuariosCadastradosTableModel model = new UsuariosCadastradosTableModel(usuarios, totalNotificacoes, notificacoesLidas);
        
        tabelaUsuarios.setModel(model);
    }
}
