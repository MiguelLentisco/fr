/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conecta3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 *
 * @author Mike
 */
public class ReceptorMensajes {
    private final DatagramSocket socketUDP;
    private ServerSocket socketServerTCP;
    private Socket socketClienteTCP;
    
     private PrintWriter outPrinter;
    private BufferedReader inReader;
    
    // Informacion otro jugador
    private int portOtroJugador;
    private String dirOtroJugador;
    private boolean empiezoYo;
    
    // Modificar según donde se ponga el servidor
    private final int portServer = 8989;
    private final int portCliente;
    private final InetAddress direccion;
    
    // Tiempos de espera
    private final int ticksConexionCorrecta = 5000;
    private final int ticksOtroJugador = 20000;
    private final int ticksOtroJugador2 = 20000;
    
    public boolean getEmpiezoYo() {
        return empiezoYo;
    }
    
    public int esperarPosicion() throws IOException {
        String mensajeRespuesta = inReader.readLine();
        return Integer.parseInt(mensajeRespuesta);
    }
    
    public void mandarPosicion(int pos) {
        String mensajeRespuesta = String.valueOf(pos);
        outPrinter.println(mensajeRespuesta);
    }
    
    public ReceptorMensajes(int port) throws UnknownHostException, SocketException {
        this.direccion = InetAddress.getByName("localhost");
        this.portCliente = port;
        this.socketUDP = new DatagramSocket(portCliente);
        this.socketUDP.setSoTimeout(ticksConexionCorrecta);
    }
    
    public boolean conectarServidorUDP() throws IOException, SocketTimeoutException {
        DatagramPacket dP = new DatagramPacket("1000".getBytes(), "1000".getBytes().length, direccion, portServer);
        socketUDP.send(dP);
        System.out.println("Conectandose al servidor");
        byte[] buffer = new byte[1024];
        dP = new DatagramPacket(buffer, buffer.length);
        socketUDP.receive(dP);
        String respuesta = new String(dP.getData(), 0, "1001".getBytes().length);
        if (!respuesta.equals("1001")) {
            System.out.println("Error: el servidor no ha saludado bien");
            return false;
        }
        System.out.println("Esperando a otro jugador");
        this.socketUDP.setSoTimeout(ticksOtroJugador);
        buffer = new byte[1024];
        dP = new DatagramPacket(buffer, buffer.length);
        socketUDP.receive(dP);
        dirOtroJugador = new String(dP.getData(), 0, dP.getLength());
        System.out.println("Dirección: " + dirOtroJugador);
        this.socketUDP.setSoTimeout(ticksOtroJugador2);
        socketUDP.receive(dP);
        portOtroJugador = Integer.parseInt(new String(dP.getData(), 0, dP.getLength()));
        System.out.println("Puerto: " + portOtroJugador);
        socketUDP.receive(dP);
        respuesta = new String(dP.getData(), 0, "1002".getBytes().length);
        if (respuesta.equals("1002")) {
            System.out.println("Jugador encontrado, empiezas tú.");
            empiezoYo = true;
        }
        else if(respuesta.equals("1003")) {
            System.out.println("Jugador encontrado, vas segundo.");
            empiezoYo = false;
        }
        else {
            return false;
        }
        socketUDP.close();
        return true;
    }
    
    public void conectarJugadorTCP() throws IOException, InterruptedException {
        if (empiezoYo == true) {
            abroServidor();
        }
        else {
            abroCliente();
        }
        System.out.println("Conexión establecida");
    }
    
    private void abroServidor() throws IOException {
       socketServerTCP = new ServerSocket(portCliente);
       socketClienteTCP = socketServerTCP.accept();
       outPrinter = new PrintWriter(socketClienteTCP.getOutputStream(), true);
       inReader = new BufferedReader(new InputStreamReader(socketClienteTCP.getInputStream()));
    }
    
    private void abroCliente() throws IOException, InterruptedException {
        Thread.sleep(500);
        socketClienteTCP = new Socket(dirOtroJugador, portOtroJugador);
        outPrinter = new PrintWriter(socketClienteTCP.getOutputStream(), true);
        inReader = new BufferedReader(new InputStreamReader(socketClienteTCP.getInputStream()));
    }

}
