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
public class Tablero {
    private final int filas;
    private final int columnas;
    private final Ficha[] tablero;
    Ficha primeraFicha;
    int turnoActual;
    
    public Tablero() {
        filas = 3;
        columnas = 3;
        primeraFicha = Ficha.X;
        turnoActual = 0;
        tablero = new Ficha[filas * columnas];
        for (int i = 0; i < filas * columnas ; ++i)
            tablero[i] = Ficha.NADA;
    }
   
    public Ficha fichaUltima() {
        if (turnoActual % 2 == 0) {
            return primeraFicha.fichaContraria();
        }
        else {
            return primeraFicha;
        }
    }
    
    public int getFilas() {
        return filas;
    }
    
    public Tablero(Ficha[] tablero, int filas, int columnas, Ficha primeraFicha) {
        this.filas = filas;
        this.columnas = columnas;
        this.tablero = tablero;
        this.primeraFicha = primeraFicha;
        recalcularTurno();
    }
    
    private void recalcularTurno() {
        turnoActual = 0;
        for (Ficha f : tablero) {
            if (f != Ficha.NADA) {
                turnoActual++;
            }
        }
    }
    
    private String fichaTurno() {
        if (turnoActual % 2 == 0) {
            return primeraFicha.getCadena();
        }
        return primeraFicha.fichaContraria().getCadena();
    }
    
    public void imprimirTableroPantalla() {
       System.out.println("Turno número: " + turnoActual + "\nLe toca a las: " + fichaTurno());
       for (int i = 0; i < filas; ++i) {
           System.out.println("-------");
           for (int j = 0; j < columnas; ++j) {
               System.out.print("|" + tablero[i * filas + j].getCadena());
           }
           System.out.print("|\n");
       }
       System.out.println("-------\n\n------------------------------\n");
    }
    
    public int getTurnoActual() {
        return turnoActual;
    }
    
    public Ficha getPrimeraFicha() {
        return primeraFicha;
    }
    
    public Ficha getFicha(int f, int c) {
        return tablero[f * filas + c];
    }
    
    public void setFicha(Ficha v, int f, int c) {
        tablero[f * filas + c] = v;
        turnoActual++;
    }
    
    public void setFicha(int f) {
        if (turnoActual % 2 == 0) {
            tablero[f] = primeraFicha;
            turnoActual++;
        }
        else {
            tablero[f] = primeraFicha.fichaContraria();
            turnoActual++;
        }
    }
    
    public void setFicha(int f, int c) {
        setFicha(f * filas + c);
    }
}
