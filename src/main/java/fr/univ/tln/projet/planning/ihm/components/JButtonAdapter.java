package fr.univ.tln.projet.planning.ihm.components;

import javax.swing.*;
import java.awt.*;

public class JButtonAdapter  extends JButton {
    public JButtonAdapter(String text){
        super(text);
        this.setFont(new Font("Arial", Font.BOLD, 12));
        this.setSize(100, 20);
        this.setBackground(new Color(65, 14, 15));
        this.setLocation(150, 450);
        this.setForeground(new Color(199, 201, 202));
    }
}
