/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Notificacao;
import model.Usuario;
import pss.LogService;
import repository.IUsuarioNotificacaoRepository;
import tableModel.NotificacoesDoUsuarioTableModel;
import view.ListagemNotificacoesView;

/**
 *
 * @author Luis1
 */
public class ListagemNotificacoesPresenter {
    private Usuario usuarioLogado;
    private IUsuarioNotificacaoRepository usuarioNotificacaoRepository;
    private List<Notificacao> notificacoes;
    private ListagemNotificacoesView view;
    ListSelectionModel selectionModel;
    private JTable tabelaNotificacoes;
    
    public ListagemNotificacoesPresenter(Usuario usuarioLogado, List<Notificacao> notificacoes, IUsuarioNotificacaoRepository usuarioNotificacaoRepository){
        this.usuarioLogado = usuarioLogado;
        this.notificacoes = notificacoes;
        this.usuarioNotificacaoRepository = usuarioNotificacaoRepository;
        this.view = new ListagemNotificacoesView();
        this.tabelaNotificacoes = view.getTblNotificacoesDoUsuario();
        this.selectionModel = tabelaNotificacoes.getSelectionModel();
        
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
                int selectedRow = tabelaNotificacoes.getSelectedRow();
                if (selectedRow != -1) {
                    int modelRow = tabelaNotificacoes.convertRowIndexToModel(selectedRow);
                    NotificacoesDoUsuarioTableModel model = (NotificacoesDoUsuarioTableModel) tabelaNotificacoes.getModel();
                    Notificacao notificacao = model.getNotificacaoAt(modelRow);
                    lerNotificacao(usuarioLogado.getId(), notificacao.getId());
                    new VisualizacaoNotificacaoPresenter(notificacao);
                }
            }
        });
        
        view.setVisible(true);
    }
    
    private void popularTabela(){
        NotificacoesDoUsuarioTableModel model = new NotificacoesDoUsuarioTableModel(usuarioLogado.getId(), notificacoes, usuarioNotificacaoRepository);
        
        tabelaNotificacoes.setModel(model);
    }
    
    private void lerNotificacao(int idUsuario, int idNotificacao){
        LogService.logOperacaoSucesso("LEITURA_NOTIFICAO",usuarioLogado.getNome(),usuarioLogado.getNomeDeUsuario());
        usuarioNotificacaoRepository.lerNotificacao(idUsuario, idNotificacao);
    }
}
