import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;

public class ProcesadorUDP extends Thread {
    private DatagramPacket datagramPacket;
    // Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
    private Random random;
    private DatagramSocket servicioSocket;

    public ProcesadorUDP(DatagramPacket datagramPacket) {
        this.datagramPacket = datagramPacket;
        random = new Random();
    }

    // Aquí es donde se realiza el procesamiento realmente:
    public void run(){
        byte[] datosRecibidos;
        int bytesRecibidos = 0;
        byte[] datosEnviar;

        try { 
            servicioSocket = new DatagramSocket();
            
            // Lee la frase a Yodaficar:
            datosRecibidos = datagramPacket.getData();
            bytesRecibidos = datosRecibidos.length;
            
            // Yoda hace su magia:
            String peticion = new String(datosRecibidos, 0, bytesRecibidos);
            // Yoda reinterpreta el mensaje:
            String respuesta = yodaDo(peticion);
    
            sleep(5000);
            
            datosEnviar = respuesta.getBytes();
            
            DatagramPacket envioDatagramPacket = new DatagramPacket(datosEnviar,
                    datosEnviar.length, datagramPacket.getAddress(), datagramPacket.getPort());
            servicioSocket.send(envioDatagramPacket);
            
            // Cerramos todo
            servicioSocket.close();

        } catch(IOException e) {
            System.err.println("Error al obtener los flujos de entrada/salida.");
        } catch(InterruptedException e) {
            System.err.println("Error al dormir.");
        }
    }

    // Yoda interpreta una frase y la devuelve en su "dialecto":
    private String yodaDo(String peticion) {
        // Desordenamos las palabras:
        String[] s = peticion.split(" ");
        String resultado = "";
        for (int i=0; i < s.length; ++i) {
            int j = random.nextInt(s.length);
            int k = random.nextInt(s.length);
            String tmp = s[j]; 
            s[j] = s[k];
            s[k] = tmp;
        }
        resultado = s[0];
        for (int i = 1; i < s.length; ++i) {
          resultado += " " + s[i];
        }
        return resultado;
    }
}
