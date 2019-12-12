package fr.univ.tln.projet.planning.ihm.vue.etudesVue;


import fr.univ.tln.projet.planning.controler.AbstractControler;

import fr.univ.tln.projet.planning.ihm.components.JButtonAdapter;

import lombok.Getter;
import org.json.simple.JSONObject;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
@Getter
public class EtudesDefaultPanel extends JPanel {
    private AbstractControler controler;
    private  JLabel title;
    private  JComboBox comboBox ;
    private JComboBox niveau;
    private JTextField textField;
    private JButtonAdapter button;
    public EtudesDefaultPanel(AbstractControler controler){
        super();
        this.controler =controler;

        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        Border blackline = BorderFactory.createTitledBorder("Ajouter");
        this.setBorder(blackline);
        title=new JLabel("");
        title.setFont(new Font("Arial", Font.ITALIC, 12));
        title.setSize(100, 20);
        title.setPreferredSize(new Dimension(100, 20));
        title.setAlignmentX( Component.RIGHT_ALIGNMENT );

        comboBox =new JComboBox(new String[]{"Domaine","Formation","Promotion","Section","Groupe"});
        comboBox.setFont(new Font("Arial", Font.BOLD, 13));
        comboBox.setSize(150, 25);
        comboBox.setPreferredSize(new Dimension(150, 25));
        comboBox.setOpaque(false);

        niveau =new JComboBox(new String[]{"L1","L2","L3","M1","M2"});
        niveau.setFont(new Font("Arial", Font.BOLD, 13));
        niveau.setSize(100, 25);
        niveau.setPreferredSize(new Dimension(100, 25));
        niveau.setOpaque(false);
        niveau.setVisible(false);

        textField=new JTextField();
        textField.setFont(new Font("Arial",Font.PLAIN, 12));
        textField.setSize(200, 30);
        textField.setPreferredSize(new Dimension(200,30));
        textField.setMaximumSize(new Dimension(200,30));
        textField.setMinimumSize(new Dimension(200,30));
        textField.setAlignmentX( Component.RIGHT_ALIGNMENT);
        button=new JButtonAdapter("créer");

        comboBox.addActionListener((new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch (comboBox.getSelectedItem().toString()){
                    case "Domaine":niveau.setVisible(false);textField.setVisible(true);title.setText("");break;
                    case "Formation":niveau.setVisible(true);textField.setVisible(true);title.setText("*Sélectionner un domaine d'etudes dans la liste des domaines");break;
                    case "Promotion":niveau.setVisible(false);textField.setVisible(false);title.setText("*Sélectionner une formation dans la liste des formation");break;
                    case "Section":niveau.setVisible(false);textField.setVisible(false);title.setText("*Sélectionner une promotion");break;
                    case "Groupe":niveau.setVisible(false);textField.setVisible(false);title.setText("*Sélectionner une section");break;
                }
            }}));

        JPanel panel=new JPanel();
        panel.add(comboBox);
        panel.add(textField);
        panel.add(niveau);
        panel.add(button);
        this.add(panel);
        this.add(title);
        title.setHorizontalAlignment(0);

    }


}
