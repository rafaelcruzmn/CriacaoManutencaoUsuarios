/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

/**
 *
 * @author Rafael
 */


import javax.swing.JOptionPane;
import pss.LogConfiguracao;
import view.ConfigurarLogView;


public class ConfigurarLogPresenter {
    
    private final ConfigurarLogView view;

    public ConfigurarLogPresenter() {
        this.view = new ConfigurarLogView();

        configurarListeners();
        carregarConfiguracao();

        this.view.setVisible(true);
    }

    private void configurarListeners() {
        view.getBtnSalvar().addActionListener(e -> salvar());
    }

    private void carregarConfiguracao() {
        String tipo = LogConfiguracao.getTipoLog();

        if (tipo == null || tipo.isBlank()) {
            LogConfiguracao.setTipoLog("csv");
            tipo = "csv";
        }

        if (tipo.equalsIgnoreCase("csv")) {
            view.getRadCSV().setSelected(true);
        } else {
            view.getRadJSON().setSelected(true);
        }
    }

    private void salvar() {
        if (view.getRadCSV().isSelected()) {
            LogConfiguracao.setTipoLog("csv");
        } else if (view.getRadJSON().isSelected()) {
            LogConfiguracao.setTipoLog("json");
        } else {
            JOptionPane.showMessageDialog(view, "Selecione um formato.");
            return;
        }

        JOptionPane.showMessageDialog(view, "Configuração salva com sucesso!");
        view.dispose();
    }

    public ConfigurarLogView getView() {
        return view;
    }
}