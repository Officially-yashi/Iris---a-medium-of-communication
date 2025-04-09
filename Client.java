import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.io.*;

public class Client {

    public static void main(String[] args) {

        try
        {
            System.out.println("Client started");
            Socket s= new Socket("localhost",9806);
            BufferedReader userInput=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("enter the string:");
            String str=userInput.readLine();
            PrintWriter out=new PrintWriter(s.getOutputStream(),true);
            out.println(str);
            BufferedReader in=new BufferedReader(new InputStreamReader(s.getInputStream()));
            System.out.println(in.readLine());
        }
        catch(Exception ee)
        {
          ee.printStackTrace();
        }
        
    }
    
}
