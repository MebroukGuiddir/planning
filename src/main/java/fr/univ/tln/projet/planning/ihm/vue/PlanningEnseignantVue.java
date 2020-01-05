package fr.univ.tln.projet.planning.ihm.vue;

import fr.univ.tln.projet.planning.controler.AbstractControler;

import javax.swing.*;
import java.awt.*;

public class PlanningEnseignantVue extends JPanel {

    private AbstractControler controler;

    PlanningEnseignantVue(AbstractControler controler ) {
        super();
        this.controler = controler;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JPanel jHeader =new JPanel();
        JButton creer=new JButton("créer");
        creer.addActionListener((actionEvent) -> {
            JPanel addUserPanel=new AddUserVue(controler);
            JFrame addUserFrame=new JFrame();
            addUserFrame.setSize(new Dimension(1000,500));
            addUserFrame.setContentPane(addUserPanel);
            addUserFrame.setVisible(true);
        });
        jHeader.add(creer);
        this.add(jHeader);
    }
}
