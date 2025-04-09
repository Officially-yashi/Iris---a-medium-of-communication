import java.net.*;
import java.io.*;
public class Server {
    
    public static void main(String[] args) {
        try
        {
            System.out.println("Waiting for client...");
            ServerSocket ss=new ServerSocket(9806);
            Socket senderSocket= ss.accept();
            System.out.println("Server connected");
            PrintWriter senderOut = new PrintWriter(senderSocket.getOutputStream(), true);
            BufferedReader senderIn = new BufferedReader(new InputStreamReader(senderSocket.getInputStream()));
            Socket receiverSocket=ss.accept();
            System.out.println("Connection established");
            PrintWriter receiverOut=new PrintWriter(receiverSocket.getOutputStream(),true);
            BufferedReader receiverIn=new BufferedReader(new InputStreamReader(receiverSocket.getInputStream()));
           
            new Thread(() -> {
                try {
                    String message;
                    while ((message = senderIn.readLine()) != null) {
                        receiverOut.println(message); // Send message to receiver
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            new Thread(() -> {
                try {
                    String message;
                    while ((message = receiverIn.readLine()) != null) {
                        senderOut.println(message); // Send message back to sender
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
