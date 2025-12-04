/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Usuario;
import repository.UsuarioRepository;
import tableModel.UsuariosCadastradosTableModel;
import view.ListagemUsuariosCadastradosView;

/**
 *
 * @author Luis1
 */
public class ListagemUsuariosCadastradosPresenter {
    ListagemUsuariosCadastradosView view;
    UsuarioRepository repository;
    Usuario usuarioLogado;
    List<Usuario> usuarios;
    ListSelectionModel selectionModel;
    JTable tabelaUsuarios;
    
    ListagemUsuariosCadastradosPresenter(Usuario usuarioLogado, List<Usuario> usuarios, UsuarioRepository repository){
        this.repository = repository;
        this.usuarioLogado = usuarioLogado;
        this.usuarios = usuarios;
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
                    new VisualizacaoUsuarioPorAdministradorPresenter(usuarioLogado, usuarioSelecionado, repository);
                }
            }
        });
        
        view.setVisible(true);
    }
   
        
    
    private void popularTabela(){
        UsuariosCadastradosTableModel model = new UsuariosCadastradosTableModel(usuarios);
        
        tabelaUsuarios.setModel(model);
    }
}
