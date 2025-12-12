/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Usuario;
import repository.UsuarioRepositorySQLite;

/**
 *
 * @author Luis1
 */
public class EnvioNotificacaoTableModel extends AbstractTableModel{
    private List<Usuario> usuarios;
    private String[] colunas = {"Id", "Usuário", "Tipo", "Selecionar"};
    private final Boolean[] selecionarChecks;

    public EnvioNotificacaoTableModel(List<Usuario> usuarios) {
        this.usuarios = usuarios;
        
        int size = usuarios.size();
        
        this.selecionarChecks = new Boolean[size];
        java.util.Arrays.fill(this.selecionarChecks, Boolean.FALSE);
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
    
    @Override
    public Class getColumnClass(int column) {
        if (column == 3) {
            return Boolean.class;
        }
        return String.class;
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
                return usuario.getNomeDeUsuario();
            case 2:
                return usuario.getTipo().toString(); 
            case 3:
                return selecionarChecks[rowIndex];
            default:
                return null;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 3;
    }
    
    @Override
    public void setValueAt(Object valor, int rowIndex, int columnIndex) {
        if (columnIndex == 3) {
            selecionarChecks[rowIndex] = (Boolean) valor;
        }
        
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public List<Usuario> getUsuariosSelecionados() {
    List<Usuario> listaFiltrada = new ArrayList<>();

    for (int i = 0; i < usuarios.size(); i++) {
        if (Boolean.TRUE.equals(selecionarChecks[i])) {
            listaFiltrada.add(usuarios.get(i));
        }
    }
    return listaFiltrada;
    }
}
