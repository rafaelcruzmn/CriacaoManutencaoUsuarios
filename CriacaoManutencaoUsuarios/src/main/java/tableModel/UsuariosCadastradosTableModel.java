/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableModel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Usuario;

/**
 *
 * @author Luis1
 */
public class UsuariosCadastradosTableModel extends AbstractTableModel {

    private List<Usuario> usuarios;
    private List<Integer> totalNotificacoes;
    private List<Integer> notificacoesLidas;
    private String[] colunas = {"ID", "Nome", "Usuário", "Tipo", "Data de Cadastro", "Notificações Lidas", "Total Notificações"};

    public UsuariosCadastradosTableModel(List<Usuario> usuarios, List<Integer> totalNotificacoes, List<Integer> notificacoesLidas) {
        this.usuarios = usuarios;
        this.totalNotificacoes = totalNotificacoes;
        this.notificacoesLidas = notificacoesLidas;
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
            case 5:
                return notificacoesLidas.get(rowIndex);
            case 6:
                return totalNotificacoes.get(rowIndex);
            default:
                return null;
        }
    }
}