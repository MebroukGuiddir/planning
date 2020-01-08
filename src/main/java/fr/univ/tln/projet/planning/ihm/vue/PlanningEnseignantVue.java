package fr.univ.tln.projet.planning.ihm.vue;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.ihm.vue.etudesVue.AddSeanceVue;
import fr.univ.tln.projet.planning.modele.utilisateurs.Enseignant;

import javax.swing.*;
import java.awt.*;

public class PlanningEnseignantVue extends JPanel {

    private AbstractControler controler;

    PlanningEnseignantVue(AbstractControler controler, Enseignant enseignant) {
        super();
        this.controler = controler;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JPanel jHeader =new JPanel();
        JButton creer=new JButton("créer");
        creer.addActionListener((actionEvent) -> {
            JPanel addSeancePanel=new AddSeanceVue(controler,enseignant);
            JFrame addSeanceFrame=new JFrame();
            addSeanceFrame.setSize(new Dimension(1000,500));
            addSeanceFrame.setContentPane(addSeancePanel);
            addSeanceFrame.setVisible(true);
        });
        jHeader.add(creer);
        this.add(jHeader);
    }
}
