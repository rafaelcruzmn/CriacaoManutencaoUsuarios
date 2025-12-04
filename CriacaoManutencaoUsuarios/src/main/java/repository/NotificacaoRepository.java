/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import model.Notificacao;
import model.Usuario;
import service.ConexaoBancoService;

/**
 *
 * @author Luis1
 */
public class NotificacaoRepository {
    private ConexaoBancoService conexao;
    private Connection conn;
    
    public NotificacaoRepository(ConexaoBancoService conexao){
        this.conexao = conexao;
    }
    
    public void inserirNotificacao(Notificacao notificacao){
        if (notificacao == null){
            throw new RuntimeException("Notificação inválida.\n");
        }
        
        String titulo = notificacao.getTitulo();
        String mensagem = notificacao.getMensagem();
        LocalDate dataEnvio = notificacao.getDataEnvio();
        int idRemetente = notificacao.getRemetente().getId();
        
             
        String sqlNotificacao = "INSERT INTO notificacoes(titulo, mensagem, dataEnvio, idRemetente) "
                + "VALUES(?, ?, ?, ?)";
        
        String sqlUsuarioNotificacao = "INSERT INTO usuariosNotificacoes(idUsuario, idNotificacao, lida) "
                + "VALUES(?, ?, ?)";
        conn = conexao.getConexao();
        
        try {
          PreparedStatement pstmt = conn.prepareStatement(sqlNotificacao, Statement.RETURN_GENERATED_KEYS);
          pstmt.setString(1, titulo);
          pstmt.setString(2, mensagem);
          pstmt.setString(3, dataEnvio.toString());
          pstmt.setInt(4, idRemetente);
          pstmt.executeUpdate();
          
          var rs = pstmt.getGeneratedKeys();
          int idNotificacaoGerado = -1;
          
          if (rs.next()) {
                idNotificacaoGerado = rs.getInt(1);
            } else {
                throw new SQLException("Falha ao obter o ID da notificação.");
            }
         
          try {
                PreparedStatement pstmt2 = conn.prepareStatement(sqlUsuarioNotificacao);
                for (Usuario destinatario : notificacao.getDestinatarios()) {
                    
                    pstmt2.setInt(1, destinatario.getId());
                    pstmt2.setInt(2, idNotificacaoGerado);
                    pstmt2.setBoolean(3, false); 
                    pstmt2.executeUpdate();
                }
            } catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        
          conn.close();
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        } finally {
            if (conn != null) {
                try {                   
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println("Erro ao fechar conexão: " + ex.getMessage());
                }
            }
        }
    } 
}
