/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

/**
 *
 * @author Rafael
 */
import pss.LogService;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import model.Usuario;
import repository.UsuarioRepository;
import tableModel.AutorizarUsuarioTableModel;
import view.AutorizarNovoUsuarioView;


public class AutorizarNovoUsuarioPresenter{
    private AutorizarNovoUsuarioView view;
    private UsuarioRepository repository;
    private Usuario usuarioLogado;
    private List<Usuario> usuarios;
    private JTable tabelaUsuarios;
    
    AutorizarNovoUsuarioPresenter(Usuario usuarioLogado, UsuarioRepository repository){
        this.repository = repository;
        this.usuarioLogado = usuarioLogado;
        this.usuarios = repository.getTodosNaoAutorizados();
        view = new AutorizarNovoUsuarioView();
        tabelaUsuarios = view.getTblAutorizarUsuarios();
        configurarView();
    }
    private void configurarView(){
        view.setVisible(false);
        popularTabela();
        
        view.getBtnVoltar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    voltar();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.getBtnConfirmarAlteracao().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    confirmarAlteracoes();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });

        view.setVisible(true);      
    }

    private void voltar(){
        view.dispose();
    }
    
private void popularTabela(){

        AutorizarUsuarioTableModel model = new AutorizarUsuarioTableModel(usuarios);
        
        tabelaUsuarios.setModel(model);
    }

private void confirmarAlteracoes() throws Exception {
    AutorizarUsuarioTableModel model = (AutorizarUsuarioTableModel) tabelaUsuarios.getModel();
    
    boolean alteracaoRealizada = false; 

    for (int i = 0; i < model.getRowCount(); i++) {
        Usuario usuario = model.getUsuarioAt(i);
        int usuarioId = usuario.getId(); 

        boolean autorizar = (Boolean) model.getValueAt(i, 0); 
        boolean recusar = (Boolean) model.getValueAt(i, 1);
        
        if (autorizar) {
            repository.autorizarUsuario(usuarioId);
            LogService.logOperacaoSucesso("CADASTRO_USUARIO_AUTORIZADO",usuario.getNome(),usuarioLogado.getNomeDeUsuario());
            System.out.println("Usuário autorizado: " + usuario.getNome());
            alteracaoRealizada = true;
            
        } else if (recusar) {
            repository.recusarUsuario(usuarioId);
            LogService.logOperacaoFalha("CADASTRO_USUARIO_RECUSADO",usuario.getNome(),usuarioLogado.getNomeDeUsuario(),"Cadastro Recusado");
            System.out.println("Usuário recusado e excluído: " + usuario.getNome());
            alteracaoRealizada = true;
        }
    }
    
    if (alteracaoRealizada) {
        JOptionPane.showMessageDialog(view, "Alterações de autorização salvas com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        view.dispose();
    } else {
        JOptionPane.showMessageDialog(view, "Nenhuma alteração de autorização foi selecionada.", "Aviso", JOptionPane.WARNING_MESSAGE);
    }
    }
}


    

