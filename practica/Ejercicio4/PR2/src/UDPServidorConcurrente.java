import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServidorConcurrente {
    public static void main(String[] args) {
        // Puerto de escucha
        int port = 8989;
        DatagramSocket serverSocket;
        DatagramPacket datagramPacket;

        try {
            // Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
            serverSocket = new DatagramSocket(port);
            // Mientras ... siempre!
            do {
                byte[] buffer = new byte[1024];
                datagramPacket = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(datagramPacket);
                ProcesadorUDP procesador = new ProcesadorUDP(datagramPacket);
                procesador.start();
            } while(true);
            
        } catch (IOException e) {
            System.err.println("Error al escuchar en el puerto " + port);
        }
    }
}
