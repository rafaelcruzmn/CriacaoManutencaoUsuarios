package service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Luis1
 */
public class ConexaoBancoService {
    private final String urlBD = "jdbc:sqlite:usuarios.db";
    
    
    public ConexaoBancoService(){
        criarTabelaUsuario();
        criarTabelaNotificacao();
        criarTabelaUsuarioNotificacao();
        criarConfiguracaoLog();
    }

    public Connection getConexao(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(urlBD);
        } catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        return conn;
    }  
    
    private void criarConfiguracaoLog(){
        String sql = "CREATE TABLE IF NOT EXISTS ConfigLog ("
                        + "id INTEGER PRIMARY KEY, "
                        + "tipo INTEGER NOT NULL, "
                        + ");";
        
        try (Connection conn = DriverManager.getConnection(urlBD)){
            
            if (conn != null) {           
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }  
   }
    
    private void criarTabelaUsuario(){
        String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                        + "id INTEGER PRIMARY KEY, "
                        + "nome TEXT NOT NULL, "
                        + "nomeDeUsuario TEXT NOT NULL, "
                        + "senha TEXT NOT NULL, "
                        + "tipo INTEGER, "
                        + "autorizado BOOLEAN, "
                        + "dataCadastro TEXT"
                        + ");";
        
        try (Connection conn = DriverManager.getConnection(urlBD)){
            
            if (conn != null) {           
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }  
   }
    
    private void criarTabelaNotificacao(){
        String sql = "CREATE TABLE IF NOT EXISTS notificacoes ("
                + "id INTEGER PRIMARY KEY, "
                + "titulo TEXT NOT NULL, "
                + "mensagem TEXT NOT NULL, "
                + "dataEnvio TEXT, "
                + "idRemetente INTEGER NOT NULL, "
                + "FOREIGN KEY (idRemetente) REFERENCES usuarios(id)"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(urlBD)){
            
            if (conn != null) {           
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } 
    }
    
    private void criarTabelaUsuarioNotificacao(){
        String sql = "CREATE TABLE IF NOT EXISTS usuariosNotificacoes ("
                + "id INTEGER PRIMARY KEY, "
                + "idUsuario INTEGER NOT NULL, "
                + "idNotificacao INTEGER NOT NULL, "
                + "lida BOOLEAN, "
                + "FOREIGN KEY(idUsuario) REFERENCES usuarios(id),"
                + "FOREIGN KEY(idNotificacao) REFERENCES notificacoes(id)"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(urlBD)){
            
            if (conn != null) {           
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } 
    }
    
    
}