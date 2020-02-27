package cat.paucasesnovescifp.sppsp.models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {

        Socket socket = null;
        DataInputStream entrada = null;
        DataOutputStream salida = null;
        Scanner sc = new Scanner(System.in);
        String mensajeCliente;
        String mensajeServidor;
        boolean activo = true;

        try {
            socket = new Socket("127.0.0.1",5555);
            System.out.println("Conexión iniciada");

            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());

            while(activo){
                System.out.println("Introduce un mensaje");
                mensajeCliente = sc.nextLine();
                // Escribimos para mandarlo al servidor
                salida.writeUTF(mensajeCliente);
                // Lo que recibimos del servidor
                mensajeServidor = entrada.readUTF();
                if("Por favor indica la hora".equals(mensajeServidor)){
                    System.out.println(mensajeServidor);
                    mensajeCliente = sc.nextLine();
                    salida.writeUTF(mensajeCliente);
                    mensajeServidor = entrada.readUTF();
                    System.out.println(mensajeServidor);
                    activo = false;
                }else{
                    System.out.println("El servidor dice: " + mensajeServidor);
                    activo = false;
                }

            }

            salida.close();
            entrada.close();
            socket.close();



        } catch (IOException e) {
            System.out.println("Error cliente: " + e.getMessage());
        }

    }

}
