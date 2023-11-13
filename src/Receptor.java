import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Receptor {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 5050;

        try {
            // Crear el socket del cliente
            Socket clienteSocket = new Socket(host, puerto);

            // Obtener el stream de entrada después de la conexión
            BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));

            // Leer datos del servidor
            String patenteRecibida;
            while ((patenteRecibida = entrada.readLine()) != null) {
                System.out.println("Patente recibida de una cámara: " + patenteRecibida);
            }

            // Cerrar conexión
            clienteSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
