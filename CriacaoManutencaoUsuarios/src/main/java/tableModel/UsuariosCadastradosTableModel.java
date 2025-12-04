/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableModel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Usuario;
import repository.UsuarioRepository;

/**
 *
 * @author Luis1
 */
public class UsuariosCadastradosTableModel extends AbstractTableModel {

    private List<Usuario> usuarios;
    private String[] colunas = {"ID", "Nome", "Usuário", "Tipo", "Data de Cadastro"};

    public UsuariosCadastradosTableModel(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int getRowCount() {
        if (usuarios == null) {
            return 0;
        }
        return usuarios.size(); 
    }

    @Override
    public int getColumnCount() {
        return colunas.length; 
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column]; 
    }
    
    public Usuario getUsuarioAt(int rowIndex) {
    return usuarios.get(rowIndex);
}

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Usuario usuario = usuarios.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return usuario.getId();
            case 1:
                return usuario.getNome();
            case 2:
                return usuario.getNomeDeUsuario();
            case 3:
                return usuario.getTipo().toString(); 
            case 4:
                return usuario.getDataCadastro();
            default:
                return null;
        }
    }
}