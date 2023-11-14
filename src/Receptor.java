import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Receptor {
    public static void main(String[] args) {
        int puerto = 5050;

        try {
            // Crear el socket del servidor
            ServerSocket receptorSocket = new ServerSocket(puerto);
            System.out.println("Escuchando en el puerto " + puerto);

            while (true) {
                // Esperar a que una cámara se conecte
                Socket camaraSocket = receptorSocket.accept();

                // Obtener la dirección IP y puerto de la cámara
                String direccionCamara = camaraSocket.getInetAddress().getHostAddress();
                int puertoCamara = camaraSocket.getPort();
                System.out.println("Conexión establecida con la cámara en la dirección: " + direccionCamara +
                        ", puerto: " + puertoCamara);

                // Manejar la conexión en un nuevo hilo
                Thread camaraThread = new Thread(new CamaraHandler(camaraSocket, direccionCamara, puertoCamara));
                camaraThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class CamaraHandler implements Runnable {
    private Socket camaraSocket;
    private String direccionCamara;
    private int puertoCamara;

    public CamaraHandler(Socket camaraSocket, String direccionCamara, int puertoCamara) {
        this.camaraSocket = camaraSocket;
        this.direccionCamara = direccionCamara;
        this.puertoCamara = puertoCamara;
    }

    @Override
    public void run() {
        try {
            // Obtener el stream de entrada después de la conexión
            BufferedReader entrada = new BufferedReader(new InputStreamReader(camaraSocket.getInputStream()));

            // Leer datos de la cámara
            String patenteRecibida;
            while ((patenteRecibida = entrada.readLine()) != null) {
                System.out.println("Patente recibida de la cámara en " + direccionCamara +
                        ", puerto " + puertoCamara + ": " + patenteRecibida);
            }

            // Cerrar conexión
            camaraSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
