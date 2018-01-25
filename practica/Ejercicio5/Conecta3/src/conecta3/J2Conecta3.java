/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conecta3;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 *
 * @author Mike
 */
public class J2Conecta3 {
     private static ReceptorMensajes rM;
    private static ControladorTablero juego;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {      
        try {
            rM = new ReceptorMensajes(8889);
            boolean OK = rM.conectarServidorUDP();
            int pos = -1;
            if (OK == true) {
                rM.conectarJugadorTCP();
                juego = new ControladorTablero();
                if (rM.getEmpiezoYo() ==  true) {
                   juego.mostrarTablero();
                   pos = juego.pedirFicha();
                   juego.mostrarTablero();
                   rM.mandarPosicion(pos);
                }
                
                while (juego.continuarPartida() == true) {
                    pos = rM.esperarPosicion();
                    juego.colocarFicha(pos);
                    juego.mostrarTablero();
                    if (juego.continuarPartida() == true) {
                        pos = juego.pedirFicha();
                        juego.mostrarTablero();
                        rM.mandarPosicion(pos);
                    }
                   }
                
                switch (juego.resultadoFinal(rM.getEmpiezoYo())) {
                    case 0:
                        System.out.println("Has ganado, campeón");
                        break;
                    case 1:
                        System.out.println("Has perdido, lo siento");
                        break;
                    case 2:
                        System.out.print("Mira que empatar en el 3 en raya.");
                        break;
                    default:
                        System.out.print("Esto no debería salir");
                        break;
                }
            }
            else
            {
                System.out.println("Mensaje recibido no válido");
            }
        }  catch (SocketTimeoutException es) {
            System.out.println("Pasado de tiempo");
        } catch (IOException ex) {
            System.out.println("Error: en algo");
        } catch (InterruptedException ed) {
            System.out.println("Error en algo");
        }
    }
}
