/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import com.pss.senha.validacao.ValidadorSenha;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import enumerator.TipoUsuario;
import java.util.List;
import java.util.Optional;
import model.Usuario;
import repository.UsuarioRepository;
import view.PrimeiroCadastroView;

/**
 *
 * @author Luis1
 */
public class PrimeiroCadastroPresenter {
    private PrimeiroCadastroView view;
    private Usuario usuario;
    private UsuarioRepository repository;
    private ValidadorSenha validadorSenha;
    
    public PrimeiroCadastroPresenter(UsuarioRepository repository){
        if (repository == null){
            throw new RuntimeException("Repository inválida!");
        }
        
        this.repository = repository;
        this.view = new PrimeiroCadastroView();
        this.validadorSenha = new ValidadorSenha();
        configuraView();
    }
    
    private void configuraView(){
        view.setVisible(false);
        view.getBtnCadastrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    cadastrar();
                    view.dispose();
                    new AutenticacaoUsuarioPresenter(repository);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: asasa "+ex.getMessage());
                }
            }
        });
        view.setVisible(true);
    }
    
    private void cadastrar(){
        String nome = view.getTxtNome().getText();
        String nomeDeUsuario = view.getTxtNomeDeUsuario().getText();
        String senha = view.getTxtSenha().getText();
        TipoUsuario tipo = TipoUsuario.ADMININI;
        boolean autorizado = true;
        LocalDate dataCadastro = LocalDate.now();
        
        // Chamar o validador de senha aqui?
        
        this.usuario = new Usuario(Optional.empty(), nome, nomeDeUsuario, senha, tipo, autorizado, dataCadastro);
        List<String> violacoesSenha = validadorSenha.validar(senha);
        String mensagem = "";
        
        if (violacoesSenha.isEmpty()){
            repository.inserirUsuario(usuario);
            limparTela();
        } else{
            for (String violacao: violacoesSenha){
                mensagem += violacao+"\n";
            }
            JOptionPane.showMessageDialog(view, mensagem);
        }
    }
    
    private void limparTela(){
        view.getTxtNome().setText("");
        view.getTxtNomeDeUsuario().setText("");
        view.getTxtSenha().setText("");
    }
}
