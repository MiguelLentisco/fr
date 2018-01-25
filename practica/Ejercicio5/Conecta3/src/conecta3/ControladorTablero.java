/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conecta3;

import java.util.Scanner;

/**
 *
 * @author Mike
 */
public class ControladorTablero {
    private Tablero tablero;
    private Scanner in = new Scanner(System.in);
    private final Direccion[] direcciones = {new Direccion(0, 1), 
        new Direccion(1, 1), new Direccion(1, 0), new Direccion(-1, 1), 
        new Direccion(1, -1), new Direccion(-1, 0), new Direccion(0, -1), 
        new Direccion(-1, -1)};
   
    private boolean lleno() {
        boolean noVacio = true;
        for (int i = 0; i < 3 && noVacio; ++i) {
            for (int j = 0; j < 3 && noVacio; ++j) {
                noVacio = !tablero.getFicha(i, j).equals(Ficha.NADA);
            }
        }
        return noVacio;
    }
    
    private boolean seguirIndicacion(int f, int c, Direccion d, int alineadas) {
        if (alineadas == 3) {
            return true;
        }
        else if (!posValida(f, c)) {
            return false;
        }
        else if (tablero.getFicha(f,c) == tablero.fichaUltima()) {
            return seguirIndicacion(f+d.f, c+d.c, d, alineadas + 1);
        }
        else {
            return false;
        }
    }
    
    public int resultadoFinal(boolean yoPrimero) {
        Ficha ganador = hayGanador();
        if (lleno() && ganador.equals(Ficha.NADA)) {
            return 2;
        }
        else if(ganador.equals(tablero.getPrimeraFicha())) {
            if (yoPrimero) {
                return 0;
            }
            else {
                return 1;
            }
        }
        else {
            if (yoPrimero) {
                return 1;
            } 
            else {
                return 0;
            }
        }
    }
    
    private boolean posValida(int f, int c) {
        return -1 < f && 3 > f && -1 < c && 3 > c;
    }
    
    private Ficha buscar3Raya(int f, int c) {
        boolean coincide = tablero.getFicha(f, c).equals(tablero.fichaUltima());
        if (coincide == true) {
            boolean noHay = false;
            for (int i = 0; i < 8 && !noHay; ++i) {
                Direccion d = direcciones[i];
                if (posValida(f+d.f, c+d.c)) {
                    noHay = seguirIndicacion(f+d.f, c+d.c, d, 1);
                }
            }
            if (noHay == true) {
                return tablero.getFicha(f,c);
            }
            else {
                return Ficha.NADA;
            }
        }
        else {
            return Ficha.NADA;
        }
    }
    
    private Ficha hayGanador() {
        Ficha f1 = buscar3Raya(0,0);
        Ficha f2 = buscar3Raya(1,1);
        Ficha f3 = buscar3Raya(2,2);
        if (!f1.equals(Ficha.NADA)) {
            return f1;
        }
        else if(!f2.equals(Ficha.NADA)) {
            return f2;
        } 
        else if(!f3.equals(Ficha.NADA)) {
            return f3;
        }
        return Ficha.NADA;
    }
    
    public void mostrarTablero() {
        tablero.imprimirTableroPantalla();
    }
    
    public boolean continuarPartida() {
        return hayGanador().equals(Ficha.NADA) && !lleno();
    }
    
    public ControladorTablero() {
        tablero = new Tablero();
    }
    
    void colocarFicha(int f) {
        tablero.setFicha(f);
    }
    
    public int pedirFicha() {
      int f, c;
      System.out.println("Introduce fila y columna: ");
      do {
          f = in.nextInt();
          c = in.nextInt();
      } while(!posValida(f, c) || !tablero.getFicha(f, c).equals(Ficha.NADA));
      tablero.setFicha(f, c);
      return f * tablero.getFilas() + c;
    }
}
