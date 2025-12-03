/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableModel;

/**
 *
 * @author Rafael
 */
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Usuario;

public class AutorizarUsuarioTableModel extends AbstractTableModel {
    
    private final List<Usuario> usuarios;
    private final String[] colunas = {"Autorizar", "Recusar", "Nome", "Nome Usuário"};
    
    private final Boolean[] autorizarChecks;
    private final Boolean[] recusarChecks;

    public AutorizarUsuarioTableModel(List<Usuario> usuarios) {
        this.usuarios = usuarios;
        int size = usuarios.size();
        
        this.autorizarChecks = new Boolean[size];
        this.recusarChecks = new Boolean[size];
    }

    @Override
    public int getRowCount() {
        return usuarios.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int coluna) {
        return colunas[coluna];
    }

    @Override
    public Class getColumnClass(int coluna) {
        if (coluna == 0 || coluna == 1) {
            return Boolean.class;
        }
        return String.class;
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return coluna == 0 || coluna == 1;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Usuario usuario = usuarios.get(linha);
        switch (coluna) {
            case 0: return autorizarChecks[linha];
            case 1: return recusarChecks[linha];
            case 2: return usuario.getNome(); 
            case 3: return usuario.getNomeDeUsuario();
            default: return null;
        }
    }

    @Override
    public void setValueAt(Object valor, int linha, int coluna) {
        if (coluna == 0) {
            autorizarChecks[linha] = (Boolean) valor;
            if (autorizarChecks[linha]) {
                recusarChecks[linha] = false; 
            }
        } else if (coluna == 1) {
            recusarChecks[linha] = (Boolean) valor;
            if (recusarChecks[linha]) {
                autorizarChecks[linha] = false; 
            }
        }       
        
        // Notifica a tabela que o valor mudou para atualizar a exibição.
        fireTableCellUpdated(linha, coluna); 
        
        if (coluna == 0) fireTableCellUpdated(linha, 1);
        if (coluna == 1) fireTableCellUpdated(linha, 0);
    }
    
    public Usuario getUsuarioAt(int linha) {
        return usuarios.get(linha);
    }
    
}
