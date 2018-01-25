import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ProcesadorTCP {
    // Referencia a un socket para enviar/recibir las peticiones/respuestas
    private Socket servicioSocket;
    private PrintWriter outPrinter;
    private BufferedReader inReader;
    // Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
    private Random random;

    // Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
    public ProcesadorTCP(Socket socketServicio) {
        this.servicioSocket = socketServicio;
        random = new Random();
    }

    // Aquí es donde se realiza el procesamiento realmente:
    void procesa(){
        String mensajeRecibido;
        String mensajeEnviar;

        try {
            outPrinter = new PrintWriter(servicioSocket.getOutputStream(), true);
            inReader = new BufferedReader(new InputStreamReader(servicioSocket.getInputStream()));
            
            // Lee la frase a Yodaficar:
            mensajeRecibido = inReader.readLine();
            
            // Yoda hace su magia:
            // Yoda reinterpreta el mensaje:
            mensajeEnviar = yodaDo(mensajeRecibido);
    
            outPrinter.println(mensajeEnviar);
            
            // Cerramos todo
            servicioSocket.close();

        } catch(IOException e) {
            System.err.println("Error al obtener los flujos de entrada/salida.");
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
