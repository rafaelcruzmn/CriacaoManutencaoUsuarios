/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

/**
 *
 * @author Rafael
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import service.ConexaoBancoServiceSingleton;

public class LogRepositorySQLite implements ILogRepository{
    
    private final ConexaoBancoServiceSingleton conexao;
    private Connection conn;
    
    public static final int TIPO_CSV = 0;
    public static final int TIPO_JSON = 1;

    public LogRepositorySQLite() {
        this.conexao = ConexaoBancoServiceSingleton.getInstancia();    
    }

    @Override
    public int getTipoLog() {
        try (Connection conn = conexao.getConexao(); 
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT tipo FROM ConfigLog LIMIT 1")) {

            int tipo = TIPO_CSV;

            if (rs.next()) {
                tipo = rs.getInt(1); 
            }
            return tipo;

          } catch(SQLException ex){
            System.err.println("Erro ao buscar o tipo de log no SQLite: " + ex.getMessage());
            return TIPO_CSV;
        }
    }
    
    @Override
    public void alterarTipoLog(int novoTipo) {
        String sql = "UPDATE ConfigLog SET tipo = ? WHERE id = 1";

        try (Connection conn = conexao.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, novoTipo);
            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                final int TIPO_CSV = 0;
                String formato = (novoTipo == TIPO_CSV) ? "CSV (0)" : "JSON (1)";
                System.out.println("Formato de log alterado com sucesso para: " + formato);
            } else {
                System.err.println("Falha ao alterar o tipo de log. Verifique se o ID=1 existe na tabela ConfigLog.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao alterar o tipo de log no SQLite: " + e.getMessage());
        }
    }
}
