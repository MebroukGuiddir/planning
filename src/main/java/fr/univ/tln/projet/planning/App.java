package fr.univ.tln.projet.planning;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.controler.Controler;
import fr.univ.tln.projet.planning.ihm.vue.*;
import fr.univ.tln.projet.planning.ihm.components.*;
import fr.univ.tln.projet.planning.ihm.vue.event.JPanelAdapter;
import fr.univ.tln.projet.planning.ihm.vue.event.JFrameComponentListener;
import fr.univ.tln.projet.planning.ihm.vue.JMenu;
import fr.univ.tln.projet.planning.modele.Admin;
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
        //TODO delete this line when data base will be attached
        adminModele.getAdmins().add(Admin.builder().nom("GUIDDIR").prenom("MEBROUK").username("rootUser").password("Azerty1&").build());
        LoginVue  loginVue=new LoginVue(controler);
        adminModele.addObserver(loginVue);

    });
    }
}
