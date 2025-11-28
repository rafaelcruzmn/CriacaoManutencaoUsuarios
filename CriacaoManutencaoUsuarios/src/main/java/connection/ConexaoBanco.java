/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

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
    
    public static boolean autenticarUsuario(String sql, String nomeDeUsuario, String senha){
        int encontrados = 0;

        try (var conn = DriverManager.getConnection(urlBD)){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, nomeDeUsuario);
            pstmt.setString(2, senha);
            encontrados = pstmt.executeQuery().getInt(1);
            
            conn.close();
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        return encontrados > 0;
    }
}
