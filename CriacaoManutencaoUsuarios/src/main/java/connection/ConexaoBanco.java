/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;

import enumerator.TipoUsuario;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;

/**
 *
 * @author Luis1
 */
public class ConexaoBanco {
    private static String urlBD = "jdbc:sqlite:usuarios.db";
    
    
    public static void inserirUsuario(String sql, String nome, String nomeDeUsuario, 
            String senha, int tipo, boolean autorizado, LocalDate dataCadastro){
        try (var conn = DriverManager.getConnection(urlBD)){
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
    
    public static void criarTabelaUsuario(String sql){
        try (var conn = DriverManager.getConnection(urlBD)) {
            
            if (conn != null) {           
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
                conn.close();
            }
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }  
    }
    
    public static boolean autenticarUsuario(String sql, String nomeDeUsuario, String senha){
        int encontrados = 0;

        try (var conn = DriverManager.getConnection(urlBD)){
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
    
    public static TipoUsuario getTipo(String sql, int id){
        int tipo = -1;
        
        try (var conn = DriverManager.getConnection(urlBD)){
           PreparedStatement pstmt = conn.prepareStatement(sql);
           
           pstmt.setInt(1, id);
           tipo = pstmt.executeQuery().getInt(1);
           
           conn.close();
        } catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        return TipoUsuario.values()[tipo];
    }
    
    public static int getId(String sql, String nomeDeUsuario){
        int id = -1;
        
        try (var conn = DriverManager.getConnection(urlBD)){
           PreparedStatement pstmt = conn.prepareStatement(sql);
           
           pstmt.setString(1, nomeDeUsuario);
           id = pstmt.executeQuery().getInt(1);
           
           conn.close();
        } catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        return id;
    }
    
    public static LocalDate getDataCadastro(String sql, int id){
        LocalDate dataCadastro = null;
        
        try (var conn = DriverManager.getConnection(urlBD)){
           PreparedStatement pstmt = conn.prepareStatement(sql);
           
           pstmt.setInt(1, id);
           dataCadastro = pstmt.executeQuery().getObject("dataCadastro", LocalDate.class);
           
           conn.close();
        } catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        return dataCadastro;
    }
    
    public static int getTamanho(String sql){
        int tamanho = 0;
        
        try (var conn = DriverManager.getConnection(urlBD)){
            Statement smtm = conn.createStatement();
            tamanho = smtm.executeQuery(sql).getInt(1);
            conn.close();          
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return tamanho;
    }
    
    public static List<Usuario> getTodosAutorizados(String sql){
        List<Usuario> usuarios = new ArrayList<>();
        
        try (var conn = DriverManager.getConnection(urlBD)){
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