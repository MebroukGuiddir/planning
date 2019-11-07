package fr.univ.tln.projet.planning.ihm.components;

import javax.swing.*;
import java.awt.*;

public class JLabelAdapter extends JLabel {
    public JLabelAdapter(String text){
        super();
        this.setText(text);
        this.setFont(new Font("Arial", Font.BOLD, 12));
        this.setSize(100, 20);
        this.setPreferredSize(new Dimension(100, 20));
    }

}
