/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conecta3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Mike
 */
public class ServidorConcurrenteUDP extends Thread {
    private static DatagramSocket socket;
    private static final int port = 8989;
    private static Queue<DatagramPacket> cola;
    private int tipo;
    
    public ServidorConcurrenteUDP(int tipo) {
        this.tipo = tipo;
    }
    
    public void run() {
        if (tipo == 1) {
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);  
                try {
                    socket.receive(packet);
                    System.out.println("Se ha recibido un paquete");
                    String respuesta = new String(packet.getData(), 0, "1000".getBytes().length);
                    if (respuesta.equals("1000")) {
                        DatagramPacket enviarSaludo = new DatagramPacket("1001".getBytes(),
                                "1001".getBytes().length, packet.getAddress(), packet.getPort());
                        socket.send(enviarSaludo);
                        System.out.println("Se ha saludado al usuario de puerto: " + packet.getPort());
                        synchronized (cola) {
                            cola.add(packet);
                        }
                    } else
                    {
                        System.out.println("Error: no se mandó petición de saludo.");
                    }
                } catch (IOException e) {
                    System.out.println("Error al recibir");
                }                          
            }
        }
        else {
            while (true) {
                int n;
                synchronized (cola) {
                    n = cola.size();
                }
                if (n > 1) {
                    DatagramPacket p1, p2;
                    synchronized (cola) {
                        p1 = cola.remove();
                        p2 = cola.remove();
                    }
                    
                    System.out.println("Hay dos en cola");
                    InetAddress dir1 = p1.getAddress();
                    InetAddress dir2 = p2.getAddress();
                    int port1 = p1.getPort();
                    int port2 = p2.getPort();
                   
                    byte[] buffer1 = Integer.toString(port1).getBytes();
                    byte[] buffer2 = Integer.toString(port2).getBytes();
                    
                    DatagramPacket newP1Dir = new DatagramPacket(dir2.getHostName().getBytes(), 
                            dir2.getHostName().getBytes().length, dir1, port1);
                    DatagramPacket newP1Port = new DatagramPacket(buffer2, 
                            buffer2.length, dir1, port1);
                    DatagramPacket newP2Dir = new DatagramPacket(dir1.getHostName().getBytes(), 
                            dir1.getHostName().getBytes().length, dir2, port2);
                    DatagramPacket newP2Port = new DatagramPacket(buffer1, 
                            buffer1.length, dir2, port2);
                    DatagramPacket empieza1 = new DatagramPacket("1002".getBytes(),
                            "1002".getBytes().length, dir1, port1);
                    DatagramPacket empieza2 = new DatagramPacket("1003".getBytes(),
                            "1003".getBytes().length, dir2, port2);
                    try {
                        socket.send(newP1Dir);
                        socket.send(newP1Port);
                        socket.send(empieza1);
                        socket.send(newP2Dir);
                        socket.send(newP2Port);
                        socket.send(empieza2); 
                    } catch (IOException e) {
                        System.err.println("Error: mandando paquetes");
                    }
                    System.out.println("Adios, jugadores en la cola");
                }
            }
        }
    }
    
    public static void main(String[] args) {
        cola = new LinkedList();
        try {
            socket = new DatagramSocket(port);
            ServidorConcurrenteUDP receptor = new ServidorConcurrenteUDP(1);
            ServidorConcurrenteUDP enviador = new ServidorConcurrenteUDP(0);
            receptor.start();
            enviador.start();
        } catch (IOException e) {
            System.err.println("Error con el socket server");
        }
    }
}
