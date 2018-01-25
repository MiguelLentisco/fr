import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPCliente {
   public static void main(String[] args) {
        // Nombre del host donde se ejecuta el servidor:
        String host = "localhost";
        // Puerto en el que espera el servidor:
        int port = 8989;
        // Socket para la conexión TCP
        Socket clienteSocket = null;
        String mensajeEnvio;
        String mensajeRespuesta;
        PrintWriter outPrinter;
        BufferedReader inReader;

        try {
            // Creamos un socket que se conecte a "host" y "port":			
            clienteSocket = new Socket(host, port);
  
            outPrinter = new PrintWriter(clienteSocket.getOutputStream(), true);
            inReader = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            
            mensajeEnvio = "Al monte del volcan debes ir sin demora";

            outPrinter.println(mensajeEnvio);

            mensajeRespuesta = inReader.readLine();

            // Mostremos la cadena de caracteres recibidos:
            System.out.println("Recibido: " + mensajeRespuesta);

            // Una vez terminado el servicio, cerramos el socket (automáticamente se cierran
            // el inpuStream  y el outputStream)
            clienteSocket.close();

        // Excepciones:
        } catch (UnknownHostException e) {
            System.err.println("Error: Nombre de host no encontrado.");
        } catch (IOException e) {
            System.err.println("Error de entrada/salida al abrir el socket.");
        }
    } 
}
