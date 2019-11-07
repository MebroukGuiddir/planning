package fr.univ.tln.projet.planning.ihm.vue;

import fr.univ.tln.projet.planning.ihm.vue.event.JPanelAdapter;

import javax.swing.*;
import java.awt.*;

public class JHeaderPanel extends JPanelAdapter {

    public JHeaderPanel(JLabel date,JLabel user){
        super(TypePanel.HEADER);
        this.setLayout(new BorderLayout( 20, 20));
        this.setAlignmentY(FlowLayout.CENTER);
        this.setAlignmentX(FlowLayout.CENTER);
        user.setForeground(new Color(100,100,150));
        user.setFont(new Font("Courier New", Font.BOLD, 15));
        user.setVerticalAlignment(JLabel.CENTER);
        user.setHorizontalAlignment(JLabel.CENTER);
        user.setSize(100,20);
        user.setPreferredSize(new Dimension( 200, 20));
        date.setHorizontalAlignment(JLabel.CENTER);
        date.setForeground(new Color(100,100,150));
        this.add(user,BorderLayout.WEST);
        this.add(date,BorderLayout.CENTER);




    }
    public JPanel setJHeaderVue(int width,int height,Color color){
        this.setMinimumSize(new Dimension(width,height));
        this.setPreferredSize(new Dimension(width,height));
        this.setOpaque(true);
        this.setBackground(color);
        return this;
    }

}
