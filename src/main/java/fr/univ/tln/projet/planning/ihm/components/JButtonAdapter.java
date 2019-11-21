package fr.univ.tln.projet.planning.ihm.components;

import javax.swing.*;
import java.awt.*;

public class JButtonAdapter  extends JButton {
    public JButtonAdapter(String text){

        super(text);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        this.setFont(new Font("Arial", Font.BOLD, 12));
        this.setSize(width/5, height/100);
        this.setBackground(new Color(65, 14, 15));
        this.setLocation(150, 450);
        this.setForeground(new Color(199, 201, 202));
    }
}
