import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ServerSide {
    private List<ClientHandler> clients = new ArrayList<>();
    private int num_conexiones = 0;


    public void startServer() {
        try {
            int port = 0;
            System.out.println("Starting server in port: ");
            BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
            port = Integer.parseInt(r.readLine());// Select a port
            r.close();
            ServerSocket socket_server = new ServerSocket(port);// Create the server socket
            while (num_conexiones <= 15) {
                Socket s = socket_server.accept();
                fullBroadcast("A user is attempting to join the chat\nAuthorizing request");
                num_conexiones++;
                ClientHandler client = new ClientHandler(num_conexiones, true, s, this);
                clients.add(client);
                Thread clientThread = new Thread(client);
                clientThread.start();
            }
            socket_server.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return;
    }

    public String broadcast(String message, ClientHandler sender){
        for (ClientHandler clientHandler : clients) {
            if (sender!=clientHandler) {
                clientHandler.send(message);
            }
        }
        System.out.println(message);
        return message;
    }

    public String fullBroadcast(String message){
        for (ClientHandler clientHandler : clients) {
                clientHandler.send(message);
        }
        System.out.println(message);
        return message;
    }

    public ClientHandler removeClient(ClientHandler client){
        clients.remove(client);
        num_conexiones--;
        return client;
    }
}