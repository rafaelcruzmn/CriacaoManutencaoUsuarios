/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import pss.LogService;
import com.pss.senha.validacao.ValidadorSenha;
import enumerator.TipoUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;
import model.Usuario;
import repository.IUsuarioRepository;
import view.CadastroPorAdministradorView;

/**
 *
 * @author Luis1
 */
public class CadastroPorAdministradorPresenter {
    private Usuario usuario;
    private Usuario usuarioLogado;
    private IUsuarioRepository repository;
    private CadastroPorAdministradorView view;
    private ValidadorSenha validadorSenha;
    
    public CadastroPorAdministradorPresenter(Usuario usuarioLogado, IUsuarioRepository repository){
        if (usuarioLogado == null){
            throw new RuntimeException("usuario inválido!");
        }
        
        if (repository == null){
            throw new RuntimeException("Repository inválida!");
        }
        
        this.usuarioLogado = usuarioLogado;
        this.repository = repository;
        this.view = new CadastroPorAdministradorView();
        this.validadorSenha = new ValidadorSenha();
        configurarView();
    }
    
    private void configurarView(){
        view.setVisible(false);
        
        view.getBtnCadastrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    cadastrar();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
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
        
        view.setVisible(true);
    }
    
    private void cadastrar(){
        String nome = view.getTxtNome().getText();
        String nomeDeUsuario = view.getTxtNomeDeUsuario().getText().toLowerCase();
        String senha = view.getTxtSenha().getText();
        TipoUsuario tipo;
        //System.out.println(view.getBgTipo().getSelection().getActionCommand());
        
        if("USUARIO".equals(view.getBgTipo().getSelection().getActionCommand())){
            tipo = TipoUsuario.USUARIOCOMUM;
        } else{
            tipo = TipoUsuario.ADMINSEC;
        }
        
        boolean autorizado = true;
        LocalDate dataCadastro = LocalDate.now();
        
        this.usuario = new Usuario(Optional.empty(), nome, nomeDeUsuario, senha, tipo, autorizado, dataCadastro);
        
        List<String> violacoesSenha = validadorSenha.validar(senha);
        String mensagem = "";
        
        if (violacoesSenha.isEmpty()){
            LogService.logOperacaoSucesso("CADASTRO_USUARIO_POR_ADMINISTRADOR",usuario.getNome(),usuarioLogado.getNomeDeUsuario());
            repository.inserirUsuario(usuario);
            limparTela();
        } else{
            for (String violacao: violacoesSenha){
                mensagem += violacao+"\n";
            }
            LogService.logOperacaoFalha("CADASTRO_USUARIO_POR_ADMINISTRADOR",usuario.getNome(),usuarioLogado.getNomeDeUsuario(),"Violacoes na senha");
            JOptionPane.showMessageDialog(view, mensagem);
        }
    }
    
    private void limparTela(){
        view.getTxtNome().setText("");
        view.getTxtNomeDeUsuario().setText("");
        view.getTxtSenha().setText("");
        view.getBgTipo().clearSelection();
    }
    
    private void voltar() {
        view.dispose();
    }
}
