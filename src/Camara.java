import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Camara {
    public static void main(String[] args) {
        int puerto = 5050;

        try {
            // Crear el socket del servidor
            ServerSocket servidorSocket = new ServerSocket(puerto);
            System.out.println("Escuchando en el puerto " + puerto);
            Socket clienteSocket = servidorSocket.accept();
            OutputStream salida = clienteSocket.getOutputStream();

            int i = 0;

            while(i<100) {
                String enviarPatente = generarPatenteChilena() + "\n";
                salida.write(enviarPatente.getBytes());
                i++;
            }

            clienteSocket.close();
            servidorSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generarPatenteChilena() {
        char[] letras = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
                        'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                        'W', 'X', 'Y', 'Z'};
        int[] numeros = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        StringBuilder patente = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            char letra = letras[new Random().nextInt(letras.length)];
            patente.append(letra);
        }

        for (int i = 0; i < 3; i++) {
            int numero = numeros[new Random().nextInt(numeros.length)];
            patente.append(numero);
        }

        return patente.toString();
    }
}
