import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {
    public void createClient() {
        try {
            System.out.println("Connecting to server in port: ");
            BufferedReader clientReader = new BufferedReader(new InputStreamReader(System.in));
            int port = Integer.parseInt(clientReader.readLine());// Select a port
            System.out.println("Provide the server's ip adress: ");
            String ip = clientReader.readLine();
            Socket socket = new Socket(ip, port);
            // Read the input from the Socket
            InputStreamReader handlerListen = new InputStreamReader(socket.getInputStream());// userInput reads the text
                                                                                             // from socket
            BufferedReader socketReader = new BufferedReader(handlerListen);// Actually reads from handlerListen
            ClientListener listener = new ClientListener(socketReader);
            Thread listenerThread = new Thread(listener);
            listenerThread.start();
            //System.out.println(socketReader.readLine());// Welcome Lancer
            //System.out.println(socketReader.readLine());
            PrintWriter clientOutput = new PrintWriter(socket.getOutputStream(), true);
            String nickname = clientReader.readLine();
            clientOutput.println(nickname);
            boolean exit = false;
            while (!exit) {
                String message = clientReader.readLine();
                if (message != null) {
                    clientOutput.println(message);
                    if (message.equals("exit")) {
                        exit = true;
                    }
                }
            }

            System.out.println("You have reached the end of this execution");
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return;
    }
}