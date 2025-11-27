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

/**
 *
 * @author Luis1
 */
public class UsuarioRepository {
    private List<Usuario> usuarios;
    private String urlBD = "jdbc:sqlite:usuarios.db";
    private Connection connection = null;
    
    public UsuarioRepository(){
        this.usuarios = new ArrayList<>();
        conectarAoBanco(this.urlBD);
    }
 
    public void incluir(Usuario usuario){
        if (usuario == null){
            throw new RuntimeException("Usuário Inválido.\n");
        }

        String nome = usuario.getNome();
        String nomeDeUsuario = usuario.getNomeDeUsuario();
        int tipo = usuario.getTipo().getValor();
        LocalDate dataCadastro = usuario.getDataCadastro();
        
        System.out.println("Nome: "+nome+"\n");
        
        String sql = "INSERT INTO usuarios(nome, nomeDeUsuario, tipo, dataCadastro) "
                + "VALUES(?, ?, ?, ?)";
        
        try (var conn = DriverManager.getConnection(urlBD)){
          PreparedStatement pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, nome);
          pstmt.setString(2, nomeDeUsuario);
          pstmt.setInt(3, tipo);
          pstmt.setDate(4, java.sql.Date.valueOf(dataCadastro));
          pstmt.executeUpdate();
          
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }
    
    public List<Usuario> getTodos(){
        if (usuarios.isEmpty()) {
            throw new RuntimeException("Não existem usuários cadastrados!");            
        }
        return this.usuarios;
    }
    
    public int getTamanho(){
        String sql = "SELECT COUNT(*) FROM usuarios";
        
        try (var conn = DriverManager.getConnection(urlBD)){
            Statement smtm = conn.createStatement();
            return smtm.executeQuery(sql).getInt(1);
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return 0;
    }
    
    // --- Conecta ao banco de dados --- //

   private void conectarAoBanco(String urlBD){
        try (var conn = DriverManager.getConnection(urlBD)) {
            
            if (conn != null) {
                
                String sql = "CREATE TABLE usuarios ("
                        + "id INTEGER PRIMARY KEY, "
                        + "nome text NOT NULL, "
                        + "nomeDeUsuario text NOT NULL, "
                        + "tipo INTEGER, "
                        + "dataCadastro DATE"
                        + ");";
                        
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
   }
     
    // --- Fim --- //
}
