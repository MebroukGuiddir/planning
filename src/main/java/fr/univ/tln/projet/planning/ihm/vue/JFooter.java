package fr.univ.tln.projet.planning.ihm.vue;

import fr.univ.tln.projet.planning.ihm.vue.event.JPanelAdapter;

import javax.swing.*;
import java.awt.*;

public class JFooter extends JPanelAdapter {
    JLabel footerLabel ;
    JLabel footerDate ;
    public JFooter(){
        super(TypePanel.FOOTER);
         footerLabel = new JLabel("Univeristé Toulon - UFR Sciences et Techniques  ");

        this.setLayout(new BorderLayout(2,0));

        footerLabel.setForeground(new Color(150,150,250));
        footerLabel.setFont(new Font("Courier New", Font.BOLD, 10));
        footerLabel.setPreferredSize(new Dimension(100,50));
        footerLabel.setVerticalAlignment(JLabel.CENTER);
        footerLabel.setHorizontalAlignment(JLabel.CENTER);




        this.add(footerLabel,BorderLayout.CENTER);

        this.setAlignmentY(FlowLayout.CENTER);
        this.setAlignmentX(FlowLayout.CENTER);

    }
    public JPanel setJFooterVue(int width,int height,Color color){
        this.setMinimumSize(new Dimension(width,height));
        this.setPreferredSize(new Dimension(width,height));
        this.setOpaque(true);
        this.setBackground(color);
        return this;
    }
}
