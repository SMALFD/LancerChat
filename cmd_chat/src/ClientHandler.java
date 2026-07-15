import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ClientHandler implements Runnable {

    private int id;
    private boolean connected;
    private ServerSide server;
    Socket s;
    String nickname;
    

    public ClientHandler(int id, boolean connected, Socket s, ServerSide server) {
        this.id = id;
        this.connected = connected;
        this.s = s;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            PrintWriter userOutput = new PrintWriter(s.getOutputStream(), true);//userOutput prints in user through Socket
            userOutput.println("Welcome Lancer\nInsert your callsign: ");//Sends text to socket
            InputStreamReader userInput = new InputStreamReader(s.getInputStream());//userInput reads the text from socket
            BufferedReader in = new BufferedReader(userInput);//Actually reads from userInput
            this.nickname = in.readLine();
            server.broadcast(nickname + " has joined the chat", this);
            boolean exit = false;
            while (!exit) {//User must write exit to leave the chat
                String message = in.readLine();
                if (message.equals("exit")) {//clean up and exit
                    s.close();
                    exit = true;
                    server.removeClient(this);
                    server.broadcast(nickname + " has left the server", this);
                } else {//send the message
                    String sendmessage = nickname + ": " + message;//Gives it format, the server never knows the nickname thanks to this
                    server.broadcast(sendmessage, this);
                }
            }
            System.out.println("Exit really");
            userOutput.close();//clean up
            userInput.close();
            in.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    //This method sends all messages to their respective client
    public String send (String broadcast){
        try {
            PrintWriter sendMessage = new PrintWriter(s.getOutputStream(), true);
            sendMessage.println(broadcast);
            return broadcast;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


    //Getters and setters all around minus sockets
    public int getId() {
        return id;
    }

    public boolean getConnected() {
        return connected;
    }

    public Socket getSocket() {
        return s;
    }

    public String getnickname() {
        return nickname;
    }

    public int setId(int num) {
        this.id = num;
        return this.id;
    }

    public boolean setConnected(boolean connection) {
        this.connected = connection;
        return this.connected;
    }

    public String setnickname(String newname) {
        this.nickname = newname;
        return this.nickname;
    }
}