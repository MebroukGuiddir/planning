package fr.univ.tln.projet.planning.ihm.components;

import javax.swing.*;
import java.awt.*;

public class JComboBoxAdapter extends  JPanel {
    private JComboBox comboBox;

    private  JLabel textLabel;
    public JComboBoxAdapter(String title,String[] status){
        super();
        this.setLayout(new BorderLayout());
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        textLabel = new JLabel(title);
        textLabel.setFont(new Font("Arial", Font.BOLD, 12));
        textLabel.setSize(100, 20);
        textLabel.setPreferredSize(new Dimension(100, 20));


        comboBox =new JComboBox(status);
        comboBox.setFont(new Font("Arial", Font.BOLD, 13));
        comboBox.setSize(width/5, height/18);
        comboBox.setPreferredSize(new Dimension(width/5, height/18));
        comboBox.setMaximumSize(new Dimension(width/5, height/18));
        comboBox.setMinimumSize(new Dimension(width/5, height/18));
        comboBox.setOpaque(false);


        this.add(textLabel,BorderLayout.WEST);
        this.add(comboBox,BorderLayout.EAST);

    }
    public String getSelectedItem(){
      return   comboBox.getSelectedItem().toString();
    }

    public JComboBox getComboBox() {
        return comboBox;
    }
}
