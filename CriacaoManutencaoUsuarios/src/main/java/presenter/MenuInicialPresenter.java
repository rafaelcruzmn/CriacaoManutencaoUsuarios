/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import model.TipoUsuario;
import model.Usuario;
import repository.UsuarioRepository;
import view.MenuInicialView;

/**
 *
 * @author Luis1
 */
public class MenuInicialPresenter {
    private MenuInicialView view;
    private UsuarioRepository repository;
    
    public MenuInicialPresenter(UsuarioRepository repository){
        this.view = new MenuInicialView();
        this.repository = repository;
        configuraView();
    }
    
    private void configuraView(){
        view.setVisible(false);
        
        view.getBtnCadastrarUsuario().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    new PrimeiroCadastroPresenter(repository);
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.setVisible(true);
    }
}
