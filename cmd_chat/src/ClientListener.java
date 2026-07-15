import java.io.BufferedReader;

public class ClientListener implements Runnable {

    private BufferedReader reader;

    public ClientListener (BufferedReader reader){
        this.reader = reader;
    }
    
    @Override
    public void run(){
        try {
            boolean exit= false;
            while (!exit){
                String message = reader.readLine();
                System.out.println(message);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}