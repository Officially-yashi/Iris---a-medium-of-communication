import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


class Sender 
{  
   private JPanel chatpanel;
   private PrintWriter out;
   private BufferedReader in;
   public Sender() {
        try {
            Socket socket = new Socket("localhost", 9806); // Connect to server
             in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        
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
   
  public JPanel senderPanel(){

    JPanel mainPanel=new JPanel(new BorderLayout());
    mainPanel.setBackground(new Color(255, 224, 189));
    mainPanel.setPreferredSize(new Dimension(400, 450));

    mainPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

    //tab
    JPanel tab=new JPanel();
    tab.setPreferredSize(new Dimension(400,60));
    tab.setBackground(new Color(0,128,128));//darkgreen
    tab.setLayout(new FlowLayout(FlowLayout.LEFT,10,5));

    //imageicon
    ImageIcon image=new ImageIcon(getClass().getResource( "images/pexels-pixabay-220453.jpg"));
    Image img=image.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
    image=new ImageIcon(img);

    //profileLabel
    JLabel profileLabel=new JLabel(image);
    profileLabel.setPreferredSize(new Dimension(50,50));
   
    //nameLabel
    JLabel namelabel=new JLabel(" Sam");
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
    tab.add(Box.createHorizontalStrut(10));
    tab.add(voicelabel);
    tab.add(Box.createHorizontalStrut(5));
    tab.add(dotlabel);
    mainPanel.add(tab,BorderLayout.NORTH);

    //textarea
     chatpanel=new JPanel();
    chatpanel.setLayout(new BoxLayout(chatpanel, BoxLayout.Y_AXIS));
    JPanel center=new JPanel(new BorderLayout());
    JTextArea chatarea= new JTextArea();
    chatarea.setOpaque(false);
    chatarea.setBackground(new Color(255, 224, 189));
    chatarea.setFont(new Font("Arial",Font.PLAIN,14));
    JScrollPane scrollPane=new JScrollPane(chatarea);
    scrollPane.setOpaque(false);
    scrollPane.getViewport().setOpaque(false);
    scrollPane.getViewport().setBackground((new Color(255, 224, 189)));
   mainPanel.add(chatarea,BorderLayout.CENTER);
   mainPanel.add(chatpanel);


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
         if(!message.isEmpty())
         {
            addMessage(message,true);
            out.println(message);
            messagefield.setText("");
         }
      }  
    });


    inputPanel.add(messagefield,BorderLayout.CENTER);
    inputPanel.add(sendButton,BorderLayout.EAST);
    mainPanel.add(inputPanel,BorderLayout.SOUTH);
     return mainPanel;
  }

  public void addMessage(String message,boolean isSender)
  {
   JPanel messagepanel=new JPanel(new FlowLayout(isSender? FlowLayout.RIGHT:FlowLayout.LEFT));
   messagepanel.setBackground(new Color(255, 224, 189));
   JLabel messageLabel=new JLabel("<html><p style='width: 150px;'>"+message+"</p></html>");
   messageLabel.setOpaque(true);
   messageLabel.setBackground(isSender? new Color(173,216,230):new Color(230,230,250));
   messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
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

   
