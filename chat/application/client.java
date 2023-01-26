package chat.application;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

    public class client implements ActionListener {
        JTextField text;
        static JPanel a1;
        static Box vertical = Box.createVerticalBox();

        static JFrame  f = new JFrame();
        static DataOutputStream dout;
        client(){
            f.setLayout(null);
            JPanel p1 = new JPanel();// panel mtlb agar apko frame k upar kuch krna h to
            p1.setBackground(new Color(7,94,84));
            p1.setBounds(0, 0,450,70);//iski help se cordinates  pass kr skte ho
            p1.setLayout(null);
            f.add(p1);

            ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png")); //agar apko img file directory se uthana h to iska use krte h
            Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
            ImageIcon i3 = new ImageIcon(i2);
            JLabel back = new JLabel(i3);
            back.setBounds(5,20,25,25);
            p1.add(back);

            back.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.exit(0);
                }
            });

            ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png")); //agar apko img file directory se uthana h to iska use krte h
            Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
            ImageIcon i6 = new ImageIcon(i5);
            JLabel profile = new JLabel(i6);
            profile.setBounds(40,10,50,50);
            p1.add(profile);

            ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png")); //agar apko img file directory se uthana h to iska use krte h
            Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
            ImageIcon i9 = new ImageIcon(i8);
            JLabel video = new JLabel(i9);
            video.setBounds(300,20,30,30);
            p1.add(video);

            ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png")); //agar apko img file directory se uthana h to iska use krte h
            Image i11 = i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
            ImageIcon i12 = new ImageIcon(i11);
            JLabel phone = new JLabel(i12);
            phone.setBounds(360,20,30,30);
            p1.add(phone);

            ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png")); //agar apko img file directory se uthana h to iska use krte h
            Image i14 = i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
            ImageIcon i15 = new ImageIcon(i14);
            JLabel morevert = new JLabel(i15); //agar frame k upar apko kuch likhna h to aap Jlabel ki help se likh skte ho.
            morevert.setBounds(420,20,10,25);
            p1.add(morevert);

            JLabel name = new JLabel("Himanshu");
            name.setBounds(110,15,100,18);
            name.setForeground(Color.white);
            name.setFont(new Font("SAN_SERIF",Font.BOLD, 18));
            p1.add(name);

            JLabel status = new JLabel("Active Now");
            status.setBounds(110,35,100,18);
            status.setForeground(Color.white);
            status.setFont(new Font("SAN_SERIF",Font.BOLD, 14));
            p1.add(status);

            a1 = new JPanel();
            a1.setBounds(5 , 75 , 440 , 570);
            f.add(a1);

            text = new JTextField();
            text.setBounds(5 , 655 , 310 , 40 );
            text.setFont(new Font("SAN_SERIF",Font.PLAIN, 16));
            f.add(text);

            JButton send = new JButton();
            send.setBounds(320,655,123,40);
            send.setBackground(new Color(7,94,84));
            send.setForeground(Color.white);
            send.addActionListener(this);
            send.setFont(new Font("SAN_SERIF",Font.PLAIN, 16));
            f.add(send);

            f.setSize(450,700);
            f.setLocation(800,0);
            f.setUndecorated(true);//isse upar jo h vo hat jata hai.
            f.getContentPane().setBackground(Color.white);
            f.setVisible(true);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                String out = text.getText();

                JPanel p2 = formatLabel(out);

                a1.setLayout(new BorderLayout());

                JPanel right = new JPanel(new BorderLayout());//isse msg right side me aaega
                right.add(p2, BorderLayout.LINE_END);//upar bala hi
                vertical.add(right); //isse msg ek ke neeche ek aaega
                vertical.add(Box.createVerticalStrut(15));//msg k beech k space k liye.

                a1.add(vertical, BorderLayout.PAGE_START);//meko vertical page start pe chiye.

                dout.writeUTF(out);
                text.setText("");
                f.repaint();
                f.invalidate();
                f.validate();

            }catch (Exception ae){
                ae.printStackTrace();

            }
        }

        public static JPanel formatLabel(String out){
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel , BoxLayout.Y_AXIS));

            JLabel output = new JLabel("<html><p style=\"width : 150px\">" + out +"</p></html>");
            output.setFont(new Font("Tahoma" , Font.PLAIN , 16));
            output.setBackground(new Color(37 , 211 , 102));
            output.setOpaque(true);
            output.setBorder(new EmptyBorder(15 , 15 , 15 , 50));

            panel.add(output);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            JLabel time = new JLabel();
            time.setText(sdf.format(cal.getTime()));
            panel.add(time);

            return panel;
        }

        public static void main(String[] args) {
            new client();
            try{
                Socket s = new Socket("127.0.0.1",6001);
                DataInputStream din = new DataInputStream(s.getInputStream());//msg ko accept krane k liye.
                dout = new DataOutputStream(s.getOutputStream());//msg ko output krane k liye.

                while (true){
                    a1.setLayout(new BorderLayout());
                    String msg =  din.readUTF();  //jo msg read kiya h usko read krne k liye
                    JPanel panel= formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel , BorderLayout.LINE_START);
                    vertical.add(left);

                    vertical.add(Box.createVerticalStrut(15));
                    a1.add(vertical , BorderLayout.PAGE_START);
                    f.validate();

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

//hamne yaha socket programming ka use kiya hamne yaha socket programming ka protocol use kiya h readUTF or writeUTF
//hamne data ko read kiya h datainputstream ki help se
//hamne msg ko send kiya h dataoutputstream ki help se

