package fr.univ.tln.projet.planning.ihm.components;

import javax.swing.*;
import java.awt.*;

public class JTextFieldAdapter extends JTextField {
       public JTextFieldAdapter(){
         super();
           this.setFont(new Font("Arial",Font.PLAIN, 15));
           this.setSize(100, 20);
           this.setPreferredSize(new Dimension(100, 20));
           this.setMaximumSize(new Dimension(100, 20));
       }


}
