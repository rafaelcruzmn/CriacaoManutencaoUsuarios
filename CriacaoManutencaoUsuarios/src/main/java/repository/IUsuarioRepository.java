/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repository;

import enumerator.TipoUsuario;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import model.Usuario;

/**
 *
 * @author Luis1
 */
public interface IUsuarioRepository {
     public void inserirUsuario(Usuario usuario);
     public void alterarTipoUsuario(Usuario usuario, TipoUsuario novoTipo);
     public void alterarSenha(Usuario usuario, String novaSenha);
     public void excluir(Usuario usuario);
     public String getSenha(int id);
     public Usuario getUsuario(int id);
     public TipoUsuario getTipo(int id);
     public LocalDate getDataCadastro(int id);
     public int getTamanho();
     public List<Usuario> getTodosAutorizados();
     public List<Usuario> getTodosNaoAutorizados();
     public Usuario autenticarUsuario(String nomeDeUsuario, String senha);
     public void autorizarUsuario(int usuarioId) throws SQLException;
     public void recusarUsuario(int usuarioId) throws SQLException;
     public void limparSistema();
}
