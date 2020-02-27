package cat.paucasesnovescifp.sppsp.models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

public class Servidor {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        Socket socketCliente = null;
        DataInputStream entrada = null;
        DataOutputStream salida = null;
        String mensaje;
        Calendar calendario;
        int hora, minutos, segundos;
        boolean noCiudad = false;
        boolean activo = true;

        try {

            serverSocket = new ServerSocket(5555);
            System.out.println("Servidor iniciado");

            socketCliente = serverSocket.accept();

            entrada = new DataInputStream(socketCliente.getInputStream());
            salida = new DataOutputStream(socketCliente.getOutputStream());


            while(activo){

                mensaje = entrada.readUTF();
                calendario = Calendar.getInstance();


                switch (mensaje){
                    case "España":
                        System.out.println("El cliente nos dice: " + mensaje);

                        hora = calendario.get(Calendar.HOUR_OF_DAY);
                        minutos = calendario.get(Calendar.MINUTE);
                        segundos = calendario.get(Calendar.SECOND);

                        salida.writeUTF("La hora es: " + hora + ":" + minutos + ":" + segundos);
                        activo = false;
                        break;
                    default:
                        if(noCiudad){
                            noCiudad = false;
                            activo = false;
                            System.out.println("El cliente nos ofrece la hora: " + mensaje);
                            salida.writeUTF("Gracias cliente.");
                        }
                        else{
                            noCiudad = true;
                            System.out.println("El cliente nos dice: " + mensaje);
                            salida.writeUTF("Por favor indica la hora");
                        }
                        break;
                }

            }

            salida.close();
            entrada.close();
            serverSocket.close();

        } catch (IOException e) {
            System.out.println("Error servidor: " + e.getMessage());
        }

    }

}
