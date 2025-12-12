/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

/**
 *
 * @author Rafael
 */

import pss.LogConfiguracao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import repository.ILogRepository;
import view.ConfigurarLogView;

public class ConfigurarLogPresenter {

    private ConfigurarLogView view;
    private final ILogRepository repository;

    public ConfigurarLogPresenter(ILogRepository repository) {
        
        this.view = new ConfigurarLogView(); 
        this.repository = repository;
        this.carregarConfiguracaoAtual();
        configurarView();
    }
    
    public ConfigurarLogPresenter(ILogRepository repository,String x) {
        this.repository = repository;
        this.carregarConfiguracaoIni();
    }
    

    private void configurarView(){
        view.setVisible(false);
        
        view.getBtnVoltar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    voltar();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        view.getBtnSalvar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    salvarConfiguracao();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: "+ex.getMessage());
                }
            }
        });
        
        view.setVisible(true);
    }
 
    private void carregarConfiguracaoIni() {
        
        int tipoAtual = repository.getTipoLog();
        if (tipoAtual == 1){
            LogConfiguracao.setTipoLog(repository.getTipoLog());
        } else {
            LogConfiguracao.setTipoLog(repository.getTipoLog());
        }      
        System.out.println("Configuração de log carregada: " + tipoAtual);
    }
    
    private void carregarConfiguracaoAtual() {
        int tipoAtual = repository.getTipoLog();
        
        if (tipoAtual == 1){
            LogConfiguracao.setTipoLog(repository.getTipoLog());
            view.getRadJSON().setSelected(true);
        } else {
            LogConfiguracao.setTipoLog(repository.getTipoLog());
            view.getRadCSV().setSelected(true);
        }      
        System.out.println("Configuração de log carregada: " + tipoAtual);
    }

    private void salvarConfiguracao() {
        int novoTipo;
        
        if (view.getRadCSV().isSelected()) {
            novoTipo = 0;
            LogConfiguracao.setTipoLog(novoTipo);
        } else if (view.getRadJSON().isSelected()) {
            novoTipo = 1;
            LogConfiguracao.setTipoLog(novoTipo);
        } else {
            JOptionPane.showMessageDialog(view, "Selecione o formato de log (CSV ou JSON).","Erro de Seleção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        repository.alterarTipoLog(novoTipo);

        String formato = (novoTipo == 0) ? "CSV" : "JSON";
        JOptionPane.showMessageDialog(view, "Configuração de Log salva com sucesso! Formato: " + formato,"Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void voltar(){
        view.dispose();
    }
}