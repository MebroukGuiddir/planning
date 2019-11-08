package fr.univ.tln.projet.planning.ihm.vue;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.controler.Controler;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginVue extends JFrame implements ActionListener  {
    JLabel l1, l2, l3;
    JTextField tf1;
    JButton btn1;
    JPasswordField p1;
    AbstractControler controler;
    public LoginVue(AbstractControler controler) {
        super();
        this.controler=controler;
        this.setTitle("Connexion");
        l1 = new JLabel("Login Form");
        l1.setForeground(Color.blue);
        l1.setFont(new Font("Serif", Font.BOLD, 20));

        l2 = new JLabel("Username");
        l3 = new JLabel("Password");
        tf1 = new JTextField();
        p1 = new JPasswordField();
        btn1 = new JButton("Login");

        l1.setBounds((getScreenBounds(this).width-400)/2,30, 400, 30);
        l2.setBounds(80, 70, 200, 30);
        l3.setBounds(80, 110, 200, 30);
        tf1.setBounds(300, 70, 200, 30);
        p1.setBounds(300, 110, 200, 30);
        btn1.setBounds(150, 160, 100, 30);

        this.add(l1);
        this.add(l2);
        this.add(tf1);
        this.add(l3);
        this.add(p1);
        this.add(btn1);

        this.setSize(400, 400);
        this.setLayout(null);
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae)
    {
        String uname = tf1.getText();
        String pass = p1.getPassword().toString();
        if(uname.equals("sudhir@oodlesTech") && pass.equals("abc@123"))
        {

        }
        else
        {
            JOptionPane.showMessageDialog(this,"Incorrect login or password",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    public static  Rectangle getScreenBounds(Component component) {
        System.out.println(component.getGraphicsConfiguration().getBounds());
        return component.getGraphicsConfiguration().getBounds();

    }
}
