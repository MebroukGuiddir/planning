package fr.univ.tln.projet.planning.ihm.vue.etudesVue;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.ihm.components.JListAdapter;
import fr.univ.tln.projet.planning.modele.etudes.*;
import fr.univ.tln.projet.planning.modele.etudes.Module;

import javax.swing.*;
import java.awt.*;

public class DomaineVue extends JPanel {
    private AbstractControler controler;
    private JListAdapter<Domaine>  domaines;
    private JListAdapter <Formation>formations;
    private JListAdapter <Module> modules;
    private JListAdapter <Promotion> promotions;
    private JListAdapter <Section> Sections;
    private JListAdapter <Section> Groupes;
    public DomaineVue(AbstractControler controler){
        super();
        this.controler =controler;
        this.setLayout(new GridLayout(3,3));
        domaines=new JListAdapter<>("Domaines");
        formations=new JListAdapter<>("Formations");
        modules=new JListAdapter<>("Modules");
        promotions=new JListAdapter<>("Promotions");
        Sections=new JListAdapter<>("Sections");
        Groupes=new JListAdapter<>("Groupes");
        this.add(domaines);
        this.add(formations);
        this.add(modules);
        this.add(promotions);
        this.add(Sections);
        this.add(Groupes);

    }
}
