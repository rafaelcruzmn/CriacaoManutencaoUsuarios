/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Notificacao;
import model.Usuario;
import service.ConexaoBancoServiceSingleton;

/**
 *
 * @author Luis1
 */
public class UsuarioNotificacaoRepository implements IUsuarioNotificacaoRepository{
    private ConexaoBancoServiceSingleton conexao;
    private Connection conn;
    
    public UsuarioNotificacaoRepository(){
        this.conexao = ConexaoBancoServiceSingleton.getInstancia();
    }
    
    @Override
    public List<Notificacao> getNotificacoes(int id){
        String sql = "SELECT * FROM usuariosNotificacoes WHERE idUsuario=?";
        String sql2 = "SELECT * FROM notificacoes WHERE id=?";
        List<Notificacao> notificacoes = new ArrayList<>();
        List<Integer> idsNotificacoes = new ArrayList<>();
        
        conn = conexao.getConexao();
        
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, id);
            
            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    int idNotificacao = rs.getInt("idNotificacao");
                    idsNotificacoes.add(idNotificacao);
                }
            }
        } catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        for (int i = 0; i < idsNotificacoes.size(); i++){
            try{
                PreparedStatement pstmt2 = conn.prepareStatement(sql2);
                
                pstmt2.setInt(1, idsNotificacoes.get(i));
                
                try(ResultSet rs2 = pstmt2.executeQuery()){
                    while(rs2.next()){
                        int idNotificacao = rs2.getInt("id");
                        String titulo = rs2.getString("titulo");
                        String mensagem = rs2.getString("mensagem");
                        LocalDate dataEnvio = rs2.getObject("dataEnvio", LocalDate.class);
                        int idRemetente = rs2.getInt("idRemetente");
                        Usuario remetente = new UsuarioRepository().getUsuario(idRemetente);
                        
                        Notificacao notificacao = new Notificacao(Optional.of(idNotificacao), titulo, mensagem, remetente, new ArrayList<>(), dataEnvio);
                        notificacoes.add(notificacao);
                    }
                }
            } catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
        
        
        return notificacoes;
    }
    
    @Override
    public boolean getLida(int idUsuario, int idNotificacao){
        String sql = "SELECT * FROM usuariosNotificacoes WHERE idUsuario=? AND idNotificacao=?";
        boolean lida = false;
        
        conn = conexao.getConexao();
        
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, idNotificacao);
            
            lida = pstmt.executeQuery().getBoolean("lida");
            conn.close();
        } catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        return lida;
    }
    
    @Override
    public void lerNotificacao(int idUsuario, int idNotificacao){
        String sql = "UPDATE usuariosNotificacoes SET lida=? "
                + "WHERE idUsuario=? AND idNotificacao=?";
        
        conn = conexao.getConexao();
        
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1, true);
            pstmt.setInt(2, idUsuario);
            pstmt.setInt(3, idNotificacao);
            pstmt.executeUpdate();

            conn.close();
        } catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
    }
    
    @Override
    public void limparSistema(){
        String sql = "DELETE FROM usuariosNotificacoes";
       
        conn = conexao.getConexao();
       
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }
}
