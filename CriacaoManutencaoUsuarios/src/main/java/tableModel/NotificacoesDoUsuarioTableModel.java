/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableModel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Notificacao;
import model.Usuario;
import repository.NotificacaoRepository;
import repository.UsuarioNotificacaoRepository;

/**
 *
 * @author Luis1
 */
public class NotificacoesDoUsuarioTableModel extends AbstractTableModel{
    List<Notificacao> notificacoes;
    private String[] colunas = {"Título", "Remetente", "Data de Envio", "Lida?"};
    private int idUsuario;
    private NotificacaoRepository notificacaoRepository;
    private UsuarioNotificacaoRepository usuarioNotificacaoRepository;
    
 
    public NotificacoesDoUsuarioTableModel(int idUsuario, List<Notificacao> notificacoes, UsuarioNotificacaoRepository usuarioNotificacaoRepository){
        this.idUsuario = idUsuario;
        this.notificacoes = notificacoes;
        this.usuarioNotificacaoRepository = usuarioNotificacaoRepository;
    }
    
    @Override
    public int getRowCount() {
        if (notificacoes == null) {
            return 0;
        }
        return notificacoes.size(); 
    }

    @Override
    public int getColumnCount() {
        return colunas.length; 
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column]; 
    }
    
    public Notificacao getNotificacaoAt(int rowIndex) {
    return notificacoes.get(rowIndex);
}

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Notificacao notificacao = notificacoes.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return notificacao.getTitulo();
            case 1:
                return notificacao.getRemetente().getNome();
            case 2:
                return notificacao.getDataEnvio();
            case 3:
                if(usuarioNotificacaoRepository.getLida(idUsuario, notificacao.getId())){
                    return "Lida";
                }
                return "Não lida";
            default:
                return null;
        }
    }
}
