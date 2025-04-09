import java.awt.Color;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main 
{
    public static void main(String[] args) {
     JFrame frame=new JFrame();
    frame.setSize(800,450);
    frame.getContentPane().setBackground(new Color(255, 224, 189)); //skin
    frame.setLocationRelativeTo(null);
    JPanel mainContainer = new JPanel(new GridLayout(1, 2));

    Sender s=new Sender();
    Receiver r=new Receiver();
 

    mainContainer.add(s.senderPanel());
    mainContainer.add(r.receiverPanel());
   
    frame.add(mainContainer);
    frame.setVisible(true);
    }
}
