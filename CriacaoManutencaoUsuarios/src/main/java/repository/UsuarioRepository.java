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
import java.util.Optional;
import service.ConexaoBancoService;
/**
 *
 * @author Luis1
 */
public class UsuarioRepository {
    private ConexaoBancoService conexao;
    private Connection conn;
    
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
    
    public void alterarTipoUsuario(Usuario usuario, TipoUsuario novoTipo){
        
        if (usuario == null){
            throw new RuntimeException("Usuário inválido.\n");
        }
        
        String sql = "UPDATE usuarios SET tipo=? WHERE id=?";
        
        conn = conexao.getConexao();
        
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, novoTipo.getValor());
            pstmt.setInt(2, usuario.getId());
            pstmt.executeUpdate();
            conn.close();
        } catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
    }
    
    public void excluir(Usuario usuario){
        String sql = "DELETE FROM usuarios WHERE id=?";
        
        conn = conexao.getConexao();
        
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, usuario.getId());
            pstmt.executeUpdate();
            conn.close();
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public String getSenha(int id){
        String sql = "SELECT senha FROM usuarios WHERE id=?";
        
        String senha = "";
        conn = conexao.getConexao();
        
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, id);
            senha = pstmt.executeQuery().getString(1);
            
            conn.close();
        } catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        return senha;
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
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String nomeDeUsuario = rs.getString("nomeDeUsuario");
                    String senha = "...";
                    TipoUsuario tipo = TipoUsuario.values()[rs.getInt("tipo")];
                    boolean autorizado = true;
                    LocalDate dataCadastro = rs.getObject("dataCadastro", LocalDate.class);
                    
                    Usuario usuario = new Usuario(Optional.of(id), nome, nomeDeUsuario, senha, tipo, autorizado, dataCadastro);
                    
                    usuarios.add(usuario); 
                }
            }
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        return usuarios;
   }
   
   public Usuario autenticarUsuario(String nomeDeUsuario, String senha){
        String sql = "SELECT * FROM usuarios WHERE nomeDeUsuario=? AND senha=? AND autorizado=?";
        
        Usuario usuario = null;
        conn = conexao.getConexao();
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, nomeDeUsuario);
            pstmt.setString(2, senha);
            pstmt.setInt(3, 1);
            
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    if (rs.getInt(1) > 0){
                        int id = rs.getInt("id");
                        String nome = rs.getString("nome");
                        TipoUsuario tipo = TipoUsuario.values()[rs.getInt("tipo")];
                        LocalDate dataCadastro = rs.getObject("dataCadastro", LocalDate.class);

                        usuario = new Usuario(Optional.of(id), nome, nomeDeUsuario, senha, tipo, true, dataCadastro);
                    }
                } 
            }
            conn.close();
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        return usuario;
    }
}