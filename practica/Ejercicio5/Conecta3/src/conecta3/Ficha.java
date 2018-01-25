/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conecta3;

/**
 *
 * @author Mike
 */
public enum Ficha {
    NADA(0, " "), X(1, "X"), O(2, "O");
    
    private final int tipo;
    private final String cadena;
    
    public Ficha fichaContraria() {
        switch(this) {
            case X:
                return O;
            case O:
                return X;
        }
        return null;
    }
    
    public static Ficha tipoFicha(int n) {
        switch(n) {
            case 0:
                return NADA;
            case 1:
                return X;
            case 2:
                return O;
        }
        return null;
    }
    
    public static Ficha tipoFicha(String c) {
        switch(c) {
            case " ":
                return NADA;
            case "X":
                return X;
            case "O":
                return O;
        }
        return null;
    }
    
    Ficha(int tipo, String cadena) {
        this.tipo = tipo;
        this.cadena = cadena;
    }
    
    public int getTipo() {
        return tipo;
    }
    
    public String getCadena() {
        return cadena;
    }
}
