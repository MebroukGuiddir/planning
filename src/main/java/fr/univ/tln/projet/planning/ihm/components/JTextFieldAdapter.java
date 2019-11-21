package fr.univ.tln.projet.planning.ihm.components;

import javax.swing.*;
import java.awt.*;

public class JTextFieldAdapter extends JPanel {
      private JTextField textField;
      private JLabel errorLabel;
      private  JLabel textLabel;
      public JTextFieldAdapter(String title, String error){
          super();
          this.setLayout(new BorderLayout());
          GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
          int width = gd.getDisplayMode().getWidth();
          int height = gd.getDisplayMode().getHeight();
          errorLabel =new JLabel(error);
          errorLabel.setFont(new Font("Arial", Font.BOLD, 12));
          errorLabel.setForeground(new Color(157, 43, 64));
          errorLabel.setVisible(false);

          textLabel = new JLabel(title);
          textLabel.setFont(new Font("Arial", Font.BOLD, 12));
          textLabel.setSize(100, 20);
          textLabel.setPreferredSize(new Dimension(100, 20));


          textField=new JTextField();
          textField.setFont(new Font("Arial",Font.PLAIN, 12));
          textField.setSize(width/5, height/18);
          textField.setPreferredSize(new Dimension(width/5, height/18));
          textField.setMaximumSize(new Dimension(width/5, height/18));
          textField.setMinimumSize(new Dimension(width/5, height/18));

          this.add(textLabel,BorderLayout.WEST);
          this.add(textField,BorderLayout.EAST);
          this.add(errorLabel,BorderLayout.SOUTH);
      }
      public JTextFieldAdapter setError(String error){
          this.errorLabel.setText(error);
          return this;
      }
      public boolean showError(){
          this.errorLabel.setVisible(true);
          return true;
      }
      public boolean hideError(){
          this.errorLabel.setVisible(false);
          return false;
      }
      public String getText(){
          return this.textField.getText();
      }
      public void setText(String text){
           this.textField.setText(text);
      }


}
