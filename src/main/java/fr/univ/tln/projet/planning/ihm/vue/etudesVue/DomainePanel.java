package fr.univ.tln.projet.planning.ihm.vue.etudesVue;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.ihm.components.*;
import fr.univ.tln.projet.planning.modele.etudes.*;
import fr.univ.tln.projet.planning.modele.etudes.Module;

import javax.swing.*;
import java.awt.*;

public class DomainePanel extends JPanel {
    private AbstractControler controler;

    public DomainePanel(AbstractControler controler){
        super();
        this.controler =controler;
        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        this.add(new JLabelAdapter("Domaine"));
        this.add(new JLabelAdapter("domaine selectionné"));
        this.add(new SearchBox("créer"));
    }
}
