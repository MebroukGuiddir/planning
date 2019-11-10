package fr.univ.tln.projet.planning.ihm.vue;
/**
 * @autor GUIDDIR MEBROUL
 * @since 1.0
 */
import javax.swing.*;
import java.awt.*;

public class Template extends JPanel {

    public Template(JPanel header,JPanel body,JPanel footer){
        this.setLayout(new BorderLayout( 0, 0) );
        this.add(header , BorderLayout.NORTH);
        this.add(body,BorderLayout.CENTER);
        this.add(footer,BorderLayout.SOUTH);
    }
    public JPanel setTemplateVue(Color color){

        this.setOpaque(true);
        this.setBackground(color);
        return this;
    }
}
