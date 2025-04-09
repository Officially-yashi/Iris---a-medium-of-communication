import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.ScrollPane;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.Font;


public class Receiver 
{
   private JPanel chatpanel;
   private BufferedReader in;
   private PrintWriter out;
   public Receiver()
   { 
        try{
         Socket s=new Socket("localhost",9806);
         in=new BufferedReader(new InputStreamReader(s.getInputStream()));
         out = new PrintWriter(s.getOutputStream(), true);
          
         new Thread(()->
         {
            while(true)
            {
               try{
                  String message =in.readLine();
                  if(message!=null)
                  {
                     addMessage(message,false);
                  }
               }
               catch(Exception ee)
               {
                     ee.printStackTrace();
               }
            }
         }).start();
        }
        catch(Exception ee)
        {
           ee.printStackTrace();
        }
   }
   public JPanel receiverPanel()
   {
    JPanel mainPanel2 = new JPanel(new BorderLayout());
    mainPanel2.setBackground(new Color(255, 224, 189));
    mainPanel2.setPreferredSize(new Dimension(400, 450));
   mainPanel2.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

    //tab
    JPanel tab=new JPanel();
    tab.setPreferredSize(new Dimension(400,60));
    tab.setBackground(new Color(0,128,128));//darkgreen
    tab.setLayout(new FlowLayout(FlowLayout.LEFT,10,5));

    //imageicon
    ImageIcon image=new ImageIcon("images/OIP.jpeg");
    Image img=image.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
    image=new ImageIcon(img);

    //profileLabel
    JLabel profileLabel=new JLabel(image);
    profileLabel.setPreferredSize(new Dimension(50,50));
   
    //nameLabel
    JLabel namelabel=new JLabel(" Allena  ");
    namelabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
    namelabel.setForeground(Color.black);
    namelabel.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
    
    //videoicon
    ImageIcon videoicon=new ImageIcon(getClass().getResource("images/video-camera-alt.png"));
    JLabel videolabel=new JLabel(resizeIcon(videoicon, 30, 30));
    
    //voicecallicon
    ImageIcon voiceicon=new ImageIcon(getClass().getResource("images/phone-call.png"));
    JLabel voicelabel=new JLabel(resizeIcon(voiceicon, 25,25));

    //doticon
    ImageIcon doticon=new ImageIcon(getClass().getResource("images/menu-dots-vertical.png"));
    JLabel dotlabel=new JLabel(resizeIcon(doticon, 25, 25));

    tab.add(profileLabel);
    tab.add(namelabel);
    tab.add(Box.createHorizontalStrut(50)); //it shifted the video icon to right
    tab.add(videolabel);
    tab.add(Box.createHorizontalStrut(20));
    tab.add(voicelabel);
    tab.add(Box.createHorizontalStrut(15));
    tab.add(dotlabel);
    mainPanel2.add(tab,BorderLayout.NORTH);

    //textarea
    chatpanel=new JPanel();
    chatpanel.setLayout(new BoxLayout(chatpanel, BoxLayout.Y_AXIS));

    JScrollPane scrollPane=new JScrollPane(chatpanel);
    scrollPane.setOpaque(false);
    scrollPane.getViewport().setOpaque(false);
    scrollPane.getViewport().setBackground((new Color(255, 224, 189)));
    mainPanel2.add(scrollPane, BorderLayout.CENTER);


    //inputpanel
    JPanel bottompanel=new JPanel(new BorderLayout());
    JPanel inputPanel=new JPanel(new BorderLayout());
    inputPanel.setPreferredSize(new Dimension(400,50));

    //msgfield
    JTextField messagefield=new JTextField();
    messagefield.setPreferredSize(new Dimension(300, 40));

    //button
    JButton sendButton=new JButton("Send");
    sendButton.setPreferredSize(new Dimension(80,40));
    
   
    sendButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
         String message=messagefield.getText().trim();
         if(!message.isEmpty()&& out!=null)
         {
            addMessage(message,true);
            out.println(message);
            messagefield.setText("");
         }
      }  
    });


    inputPanel.add(messagefield,BorderLayout.CENTER);
    inputPanel.add(sendButton,BorderLayout.EAST);
    mainPanel2.add(inputPanel,BorderLayout.SOUTH);

    return mainPanel2;
  }
  public void addMessage(String message,boolean isSender)
  {
   JPanel messagepanel=new JPanel(new FlowLayout(isSender? FlowLayout.RIGHT:FlowLayout.LEFT));
   messagepanel.setBackground(new Color(255, 224, 189));

   JLabel messageLabel=new JLabel("<html><p style='width: 150px;'>"+message+"</p></html>");
   messageLabel.setOpaque(true);
   messageLabel.setBackground(isSender? new Color(173,216,230):new Color(230,230,250));
   messageLabel.setForeground(Color.black);
   
   messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
  
   messageLabel.setSize(messageLabel.getPreferredSize());
   messagepanel.add(messageLabel);
   chatpanel.add(messagepanel);
   

   chatpanel.revalidate();
   chatpanel.repaint();
  }

  public static ImageIcon resizeIcon(ImageIcon icon, int width, int height)
  {
     Image img=icon.getImage();
     Image resizedimg=img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
     return new ImageIcon(resizedimg);
  }

}

