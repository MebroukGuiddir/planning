package fr.univ.tln.projet.planning;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.controler.Controler;
import fr.univ.tln.projet.planning.ihm.vue.*;
import fr.univ.tln.projet.planning.ihm.components.*;
import fr.univ.tln.projet.planning.ihm.vue.event.JPanelAdapter;
import fr.univ.tln.projet.planning.ihm.vue.event.JFrameComponentListener;
import fr.univ.tln.projet.planning.ihm.vue.JMenu;
import fr.univ.tln.projet.planning.modele.AdminModele;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {   javax.swing.SwingUtilities.invokeLater(() -> {

        AdminModele adminModele = new AdminModele();

        //Création du contrôleur
        AbstractControler controler = new Controler(adminModele);

        //Création de notre fenêtre avec le contrôleur en paramètre
        final JFrameComponentListener frame = new JFrameComponentListener("Hyper-Planning");

        frame.setResizable(false);

       //header
       JHeaderPanel jHeaderPanel=new JHeaderPanel(new JLabel(java.time.LocalDate.now().toString()),new JLabel("GUIDDIR MEBROUK"));
       jHeaderPanel.setJHeaderVue(1000,50,new Color(7, 21, 23));
       //footer
        JFooter jFooter= new JFooter();
        jFooter.setJFooterVue(1000,50,new Color(7, 21, 23));
        //body
        JPanelAdapter p1=new AddUserVue(JPanelAdapter.TypePanel.SECTION,controler);
        JPanelAdapter p2=new JPanelAdapter(JPanelAdapter.TypePanel.SECTION);
        p2.add(new JLabel("2"));
        JPanelAdapter p3=new JPanelAdapter(JPanelAdapter.TypePanel.SECTION);
        p3.add(new JLabel("3"));
        JMenu jMenu= new JMenu().addItem("Ajouter compte",p1).addItem("Liste Compte",p2).addItem("Gérer les ressources",p3);
        jMenu.setJMenuVue(1000,400,new Color(10, 24, 65));
        frame.addObserver(jHeaderPanel);
        frame.addObserver(jFooter);
        frame.addObserver(jMenu);
        frame.addObserver(p1);
        frame.addObserver(p2);
        frame.addObserver(p3);
        frame.addObserver(jMenu.getPanelMenu());
        Template template=new Template(jHeaderPanel,jMenu, jFooter );
         template.setTemplateVue(new Color(45,45,45));

        frame.setContentPane(template);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    });
    }
}
