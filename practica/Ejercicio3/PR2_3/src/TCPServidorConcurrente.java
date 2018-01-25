import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServidorConcurrente {
    public static void main(String[] args) {
        // Puerto de escucha
        int port = 8989;
        ServerSocket serverSocket;
        Socket clienteSocket;

        try {
            // Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
            serverSocket = new ServerSocket(port);
            // Mientras ... siempre!
            do {
                // Aceptamos una nueva conexión con accept()
                clienteSocket = serverSocket.accept();
                // Creamos un objeto de la clase ProcesadorYodafy, pasándole como 
                // argumento el nuevo socket, para que realice el procesamiento
                // Este esquema permite que se puedan usar hebras más fácilmente.
                ProcesadorTCP procesador = new ProcesadorTCP(clienteSocket);
                procesador.start();
            } while(true);

        } catch (IOException e) {
            System.err.println("Error al escuchar en el puerto " + port);
        }
    }
}
