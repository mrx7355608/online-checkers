import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final Socket[] onlineClients = new Socket[2];

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            // Create server
            ServerSocket server = new ServerSocket(5555);
            System.out.println("Server is listening on port: 5555");

            // Accept first client connection
            Socket socket1 = server.accept();
            System.out.println(socket1.getInetAddress() + " connected");
            onlineClients[0] = socket1;
            executor.submit(new ClientHandler(socket1));

            // Accept second client connection
            Socket socket2 = server.accept();
            System.out.println(socket2.getInetAddress() + " connected");
            onlineClients[1] = socket2;
            executor.submit(new ClientHandler(socket2));
            broadcastMessage("START_GAME: both clients connected");
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void broadcastMessage(String message) {
        for (Socket s : onlineClients) {
            if (s == null)
                continue;

            try {
                PrintWriter out = new PrintWriter(s.getOutputStream());
                out.println(message);
                out.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
