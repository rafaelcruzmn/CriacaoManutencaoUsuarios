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