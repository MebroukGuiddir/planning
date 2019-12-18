package fr.univ.tln.projet.planning.ihm.vue.etudesVue;


import fr.univ.tln.projet.planning.controler.AbstractControler;

import fr.univ.tln.projet.planning.ihm.components.JButtonAdapter;

import fr.univ.tln.projet.planning.ihm.components.JTextFieldAdapter;
import lombok.Getter;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
public class EtudesDefaultPanel extends JPanel {
    private AbstractControler controler;
    private  JLabel title;
    private  JComboBox comboBox ;
    private JComboBox niveau;
    private JTextFieldAdapter identifiant;
    private JTextFieldAdapter module;
    private JButtonAdapter button;
    public EtudesDefaultPanel(AbstractControler controler){
        super();
        this.controler =controler;

        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        Border blackline = BorderFactory.createTitledBorder("Ajouter");
        this.setBorder(blackline);
        title=new JLabel("");
        title.setFont(new Font("Arial", Font.BOLD, 12));
        title.setForeground(new Color(157, 43, 64));
        title.setSize(100, 20);
        title.setPreferredSize(new Dimension(100, 20));
        title.setAlignmentX( Component.RIGHT_ALIGNMENT );
        title.setVisible(false);
        comboBox =new JComboBox(new String[]{"Domaine","Formation","Promotion","Section","Groupe","Module"});
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

        identifiant =new JTextFieldAdapter("identifiant","");
        module=new JTextFieldAdapter("module","");
        module.setVisible(false);
        button=new JButtonAdapter("créer");

        comboBox.addActionListener((new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch (comboBox.getSelectedItem().toString()){
                    case "Domaine":niveau.setVisible(false);module.setVisible(false);
                        identifiant.setVisible(true);title.setText("");break;
                    case "Formation":niveau.setVisible(true);module.setVisible(false);
                        identifiant.setVisible(true);title.setText("*Sélectionner un domaine d'etudes dans la liste des domaines");break;
                    case "Promotion":niveau.setVisible(false);module.setVisible(false);
                        identifiant.setVisible(false);title.setText("*Sélectionner une formation dans la liste des formation");break;
                    case "Section":niveau.setVisible(false);module.setVisible(false);
                        identifiant.setVisible(false);title.setText("*Sélectionner une promotion");break;
                    case "Groupe":niveau.setVisible(false);module.setVisible(false);
                        identifiant.setVisible(false);title.setText("*Sélectionner une formation");break;
                    case "Module":niveau.setVisible(false);
                        identifiant.setVisible(true);
                        module.setVisible(true);
                        title.setText("*Sélectionner une section");break;
                }
            }}));

        JPanel panel=new JPanel();
        panel.add(comboBox);
        panel.add(identifiant);
        panel.add(module);
        panel.add(niveau);
        panel.add(button);
        this.add(panel);
        this.add(title);
        title.setHorizontalAlignment(0);

    }


}
