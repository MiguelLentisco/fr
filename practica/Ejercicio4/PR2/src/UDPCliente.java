import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPCliente {
   public static void main(String[] args) {
        InetAddress direccion;
        // Puerto en el que espera el servidor:
        int port = 8989;
        DatagramSocket clienteSocket;
        DatagramPacket datagramPacket;
        byte[] buffer = new byte[1024];
        byte[] bufferEnvio;
        byte[] bufferRecepcion = new byte[1024];
        int bytesLeidos = 0;

        try {
            // Creamos un socket que se conecte a "host" y "port":			
            clienteSocket = new DatagramSocket();
            
            bufferEnvio = "Al monte del volcan debes ir sin demora".getBytes();
            direccion = InetAddress.getByName("localhost");
            datagramPacket = new DatagramPacket(bufferEnvio, bufferEnvio.length, 
                    direccion, port);
            clienteSocket.send(datagramPacket);
            
            datagramPacket = new DatagramPacket(buffer, buffer.length);
            clienteSocket.receive(datagramPacket);
            
            bufferRecepcion = datagramPacket.getData();
            bytesLeidos = bufferRecepcion.length;

            // Mostremos la cadena de caracteres recibidos:
            System.out.println("Recibido: ");
            for (int i = 0; i < bytesLeidos; ++i) {
                System.out.print((char)bufferRecepcion[i]);
            }

            clienteSocket.close();

        // Excepciones:
        } catch (UnknownHostException e) {
            System.err.println("Error: Nombre de host no encontrado.");
        } catch (IOException e) {
            System.err.println("Error de entrada/salida al abrir el socket.");
        }
    } 
}
