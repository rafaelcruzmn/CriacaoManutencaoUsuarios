/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package enumerator;

/**
 *
 * @author Luis1
 */
public enum TipoUsuario {
    ADMININI(0),
    ADMINSEC(1),
    USUARIOCOMUM(2);

    private int valor;

    TipoUsuario (int valor){
        this.valor = valor;
    }

     public int getValor() {
        return valor;
    }
}
