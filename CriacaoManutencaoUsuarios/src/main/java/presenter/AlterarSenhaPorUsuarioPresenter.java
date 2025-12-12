/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import pss.LogService;
import com.pss.senha.validacao.ValidadorSenha;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import model.Usuario;
import repository.IUsuarioRepository;
import view.AlterarSenhaUsuarioView;

/**
 *
 * @author Rafael
 */
public class AlterarSenhaPorUsuarioPresenter{
    
    private Usuario usuarioLogado;
    private AlterarSenhaUsuarioView view;
    private IUsuarioRepository repository;
    
    public AlterarSenhaPorUsuarioPresenter(Usuario usuarioLogado, IUsuarioRepository repository){
    this.usuarioLogado = usuarioLogado;
    this.repository = repository;
    this.view = new AlterarSenhaUsuarioView();
    
    configurarView();
    }
    
    private void configurarView(){
    view.setVisible(false);
    
    view.getbtnVoltar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    voltar();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
    
    view.getbtnAlterarSenha().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    alterarSenha();
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
    
    private void alterarSenha(){
    String senhaAtual = view.getTxtSenhaAtual().getText();
    String senhaNova = view.getTxtNovaSenha().getText();
    String confirmarNovaSenha = view.getTxtConfirmarNovaSenha().getText();
    String senhaDB = repository.getSenha(usuarioLogado.getId());
               
    ValidadorSenha validador = new ValidadorSenha();
    List<String> erros = validador.validar(senhaNova);

    if (!erros.isEmpty()) {
        
        StringBuilder sb = new StringBuilder("A senha não atende aos requisitos:\n\n");
        for (String erro : erros) {
            sb.append("• ").append(erro).append("\n");
        }
        LogService.logOperacaoFalha("ALTERACAO_SENHA",usuarioLogado.getNome(),usuarioLogado.getNomeDeUsuario(),"Senha invalida");
        JOptionPane.showMessageDialog(view, sb.toString());
        return;
    }

    if(senhaAtual.equals(senhaDB) && confirmarNovaSenha.equals(senhaNova)){
        LogService.logOperacaoSucesso("ALTERACAO_SENHA",usuarioLogado.getNome(),usuarioLogado.getNomeDeUsuario());
        repository.alterarSenha(usuarioLogado, senhaNova);
        JOptionPane.showMessageDialog(view, "Senha alterada com sucesso!"); 
        voltar();
    }else{
        LogService.logOperacaoFalha("ALTERACAO_SENHA",usuarioLogado.getNome(),usuarioLogado.getNomeDeUsuario(),"Senha invalida");
        JOptionPane.showMessageDialog(view, "Problema com as senhas!");
    }      
  }
}