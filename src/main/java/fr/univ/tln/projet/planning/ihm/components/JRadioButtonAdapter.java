package fr.univ.tln.projet.planning.ihm.components;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;
import java.util.List;

    public class JRadioButtonAdapter extends JPanel {
       private ButtonGroup gengp ;
       private JLabel label;
    public  JRadioButtonAdapter(String title,String[] labels){
         super();
        gengp = new ButtonGroup();
        this.label = new JLabel(title);
        this.label.setFont(new Font("Arial", Font.BOLD, 12));
        this.label.setSize(100, 20);
        this.label.setPreferredSize(new Dimension(100, 20));
        this.add(label);
         for(String label :labels){
             addRadioButton(label);
         }
         this.setOpaque(false);


    }
    public JRadioButtonAdapter addRadioButton(String label){
        JRadioButton jRadioButton =new JRadioButton(label);
        jRadioButton.setFont(new Font("Arial", Font.BOLD, 12));
        jRadioButton.setSelected(true);
        jRadioButton.setSize(75, 20);
        jRadioButton.setPreferredSize(new Dimension(75,20));
        jRadioButton.setOpaque(false);
        this.add(jRadioButton);
        this.gengp.add(jRadioButton);
        return this;
    }
    public String getSelected(){
        for (Enumeration<AbstractButton> buttons = gengp.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }
        return "";
    }
}
