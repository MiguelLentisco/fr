import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class ProcesadorTCP {
    // Referencia a un socket para enviar/recibir las peticiones/respuestas
    private Socket servicioSocket;
    // stream de lectura (por aquí se recibe lo que envía el cliente)
    private InputStream inputStream;
    // stream de escritura (por aquí se envía los datos al cliente)
    private OutputStream outputStream;
    // Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
    private Random random;

    // Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
    public ProcesadorTCP(Socket socketServicio) {
        this.servicioSocket = socketServicio;
        random = new Random();
    }

    // Aquí es donde se realiza el procesamiento realmente:
    void procesa(){
        // Como máximo leeremos un bloque de 1024 bytes. Esto se puede modificar.
        byte[] datosRecibidos = new byte[1024];
        int bytesRecibidos = 0;
        // Array de bytes para enviar la respuesta. Podemos reservar memoria cuando vayamos a enviarka:
        byte[] datosEnviar;

        try {
            // Obtiene los flujos de escritura/lectura
            inputStream = servicioSocket.getInputStream();
            outputStream = servicioSocket.getOutputStream();
            
            // Lee la frase a Yodaficar:
            bytesRecibidos = inputStream.read(datosRecibidos);
            
            // Yoda hace su magia:
            // Creamos un String a partir de un array de bytes de tamaño "bytesRecibidos":
            String peticion= new String(datosRecibidos, 0, bytesRecibidos);
            // Yoda reinterpreta el mensaje:
            String respuesta = yodaDo(peticion);
            // Convertimos el String de respuesta en una array de bytes:
            datosEnviar = respuesta.getBytes();

            // Enviamos la traducción de Yoda:
            outputStream.write(datosEnviar, 0, datosEnviar.length);
            outputStream.flush();
            
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
