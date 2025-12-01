/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import model.Usuario;
import java.util.List;
import enumerator.TipoUsuario;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import service.ConexaoBancoService;
/**
 *
 * @author Luis1
 */
public class UsuarioRepository {
    private ConexaoBancoService conexao;
    Connection conn;
    
    public UsuarioRepository(ConexaoBancoService conexao){
        this.conexao = conexao;
    }
    
    public void inserirUsuario(Usuario usuario){
        if (usuario == null){
            throw new RuntimeException("Usuário inválido.\n");
        }
        
        String nome = usuario.getNome();
        String nomeDeUsuario = usuario.getNomeDeUsuario();
        String senha = usuario.getSenha();
        int tipo = usuario.getTipo().getValor();
        boolean autorizado = usuario.getAutorizado();
        LocalDate dataCadastro = usuario.getDataCadastro();
             
        String sql = "INSERT INTO usuarios(nome, nomeDeUsuario, senha, tipo, autorizado, dataCadastro) "
                + "VALUES(?, ?, ?, ?, ?, ?)";
        
        conn = conexao.getConexao();
        try {
          PreparedStatement pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, nome);
          pstmt.setString(2, nomeDeUsuario);
          pstmt.setString(3, senha);
          pstmt.setInt(4, tipo);
          pstmt.setBoolean(5, autorizado);
          pstmt.setString(6, dataCadastro.toString());
          pstmt.executeUpdate();
          conn.close();
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    } 
    
    public boolean autenticarUsuario(String nomeDeUsuario, String senha){
        String sql = "SELECT COUNT(*) FROM usuarios WHERE nomeDeUsuario=? AND senha=? AND autorizado=?";
        
        int encontrados = 0;
        conn = conexao.getConexao();
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, nomeDeUsuario);
            pstmt.setString(2, senha);
            pstmt.setInt(3, 1);
            encontrados = pstmt.executeQuery().getInt(1);
            
            conn.close();
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        return encontrados > 0;
    }
    
    public int getId(String nomeDeUsuario){
        String sql = "SELECT id FROM usuarios WHERE nomeDeUsuario=?";
        
        int id = -1;
        conn = conexao.getConexao();
        
        try {
           PreparedStatement pstmt = conn.prepareStatement(sql);
           
           pstmt.setString(1, nomeDeUsuario);
           id = pstmt.executeQuery().getInt(1);
           
           conn.close();
        } catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        return id;               
    }
    
    public TipoUsuario getTipo(int id){
        String sql = "SELECT tipo FROM usuarios WHERE id=?";
        
        int tipo = -1;
        conn = conexao.getConexao();
        try {
           PreparedStatement pstmt = conn.prepareStatement(sql);
           
           pstmt.setInt(1, id);
           tipo = pstmt.executeQuery().getInt(1);
           
           conn.close();
        } catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        return TipoUsuario.values()[tipo];
    }

    public LocalDate getDataCadastro(int id){
        String sql = "SELECT dataCadastro FROM usuarios WHERE id=?";
        
        LocalDate dataCadastro = null;
        conn = conexao.getConexao();
        
        try {
           PreparedStatement pstmt = conn.prepareStatement(sql);
           
           pstmt.setInt(1, id);
           dataCadastro = pstmt.executeQuery().getObject("dataCadastro", LocalDate.class);
           
           conn.close();
        } catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        return dataCadastro;
    }

   public int getTamanho(){
        String sql = "SELECT COUNT(*) FROM usuarios";
        
        int tamanho = 0;
        conn = conexao.getConexao();
        
        try {
            Statement smtm = conn.createStatement();
            tamanho = smtm.executeQuery(sql).getInt(1);
            conn.close();     
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        
        return tamanho;
    }
   
   public List<Usuario> getTodosAutorizados(){
       String sql = "SELECT * FROM usuarios WHERE autorizado=?";
       
       List<Usuario> usuarios = new ArrayList<>();
        conn = conexao.getConexao();
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, 1);
            
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    String nome = rs.getString("nome");
                    String nomeDeUsuario = rs.getString("nomeDeUsuario");
                    String senha = "...";
                    TipoUsuario tipo = TipoUsuario.values()[rs.getInt("tipo")];
                    boolean autorizado = true;
                    LocalDate dataCadastro = rs.getObject("dataCadastro", LocalDate.class);
                    
                    Usuario usuario = new Usuario(nome, nomeDeUsuario, senha, tipo, autorizado, dataCadastro);
                    
                    usuarios.add(usuario); 
                }
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        return usuarios;
   }
}