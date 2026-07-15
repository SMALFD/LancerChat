import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        try {
            boolean exit = false;
            while (!exit) {
                System.out.println("Are you running as GM or as Lancer?");
                BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
                String status = r.readLine();
                if (status != null && status.equals("Lancer")) {
                    Client client = new Client();
                    client.createClient();
                    exit = true;
                } else if (status != null && status.equals("GM")) {
                    ServerSide server = new ServerSide();
                    server.startServer();
                    exit = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("You have correctly exited the program.");
        return;
    }

}
