package fr.univ.tln.projet.planning;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.controler.Controler;
import fr.univ.tln.projet.planning.ihm.vue.*;
import fr.univ.tln.projet.planning.modele.Admin;
import fr.univ.tln.projet.planning.modele.AdminModele;

/**
 * Application
 *
 */
public class App 
{
    public static void main( String[] args )
    {   javax.swing.SwingUtilities.invokeLater(() -> {
        //creer le modéle
        AdminModele adminModele = new AdminModele();

        //Création du contrôleur
        AbstractControler controler = new Controler(adminModele);
        //TODO delete this line when data base will be attached
        adminModele.getAdmins().add(Admin.builder().nom("GUIDDIR").prenom("MEBROUK").username("r").password("a").build());
        LoginVue  loginVue=new LoginVue(controler);
        adminModele.addObserver(loginVue);

    });
    }
}
