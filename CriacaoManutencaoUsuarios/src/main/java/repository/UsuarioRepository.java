/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import model.Usuario;
import java.util.List;
import java.util.ArrayList;
import connection.ConexaoBanco;
/**
 *
 * @author Luis1
 */
public class UsuarioRepository {
    public UsuarioRepository(){
        criarTabelaUsuario();
    }
 
    public void inserirUsuario(Usuario usuario){
        if (usuario == null){
            throw new RuntimeException("Usuário Inválido.\n");
        }
        
        String nome = usuario.getNome();
        String nomeDeUsuario = usuario.getNomeDeUsuario();
        String senha = usuario.getSenha();
        int tipo = usuario.getTipo().getValor();
        boolean autorizado = usuario.getAutorizado();
        LocalDate dataCadastro = usuario.getDataCadastro();
             
        String sql = "INSERT INTO usuarios(nome, nomeDeUsuario, senha, tipo, autorizado, dataCadastro) "
                + "VALUES(?, ?, ?, ?, ?, ?)";
        
        ConexaoBanco.inserirUsuario(sql, nome, nomeDeUsuario, senha, tipo, autorizado, dataCadastro);
    }
    
    public boolean autenticarUsuario(String nomeDeUsuario, String senha){
        String sql = "SELECT COUNT(*) FROM usuarios WHERE nomeDeUsuario=? AND senha=? AND autorizado=?";
        
        return ConexaoBanco.autenticarUsuario(sql, nomeDeUsuario, senha); 
    }
    
    public int getTipo(String nomeDeUsuario){
        String sql = "SELECT tipo FROM usuarios WHERE nomeDeUsuario=?";
        
        return ConexaoBanco.getTipo(sql, nomeDeUsuario);
    }
        
    public int getTamanho(){
        String sql = "SELECT COUNT(*) FROM usuarios";
        
        return ConexaoBanco.getTamanho(sql);
    }

   private void criarTabelaUsuario(){
        String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                        + "id INTEGER PRIMARY KEY, "
                        + "nome text NOT NULL, "
                        + "nomeDeUsuario TEXT NOT NULL, "
                        + "senha TEXT NOT NULL, "
                        + "tipo INTEGER, "
                        + "autorizado BOOLEAN, "
                        + "dataCadastro TEXT"
                        + ");";
        
        ConexaoBanco.criarTabelaUsuario(sql);
   }
}
