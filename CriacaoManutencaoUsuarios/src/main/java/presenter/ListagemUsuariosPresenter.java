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
import model.Usuario;
import repository.UsuarioRepository;
import tableModel.UsuariosCadastradosTableModel;
import view.ListagemUsuariosView;

/**
 *
 * @author Luis1
 */
public class ListagemUsuariosPresenter {
    ListagemUsuariosView view;
    UsuarioRepository repository;
    List<Usuario> usuarios;
    JTable tabelaUsuarios;
    
    ListagemUsuariosPresenter(UsuarioRepository repository, List<Usuario> usuarios){
        this.repository = repository;
        this.usuarios = usuarios;
        view = new ListagemUsuariosView();
        tabelaUsuarios = view.getTblListagemUsuarios();
        configurarView();
    }
    
    private void configurarView(){
        view.setVisible(false);
        
        
        popularTabela();
        
        view.setVisible(true);
    }
    
    private void popularTabela(){
        UsuariosCadastradosTableModel model = new UsuariosCadastradosTableModel(usuarios, repository);
        
        tabelaUsuarios.setModel(model);
    }
}
