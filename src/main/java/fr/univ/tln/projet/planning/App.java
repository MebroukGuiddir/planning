package fr.univ.tln.projet.planning;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.controler.Controler;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.ihm.vue.*;
import fr.univ.tln.projet.planning.modele.AdminModele;

import java.io.IOException;

/**
 * Application
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        javax.swing.SwingUtilities.invokeLater(() -> {
        //creer le modéle
            AdminModele adminModele = null;
            try {
                adminModele = new AdminModele();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (DaoException e) {
                e.printStackTrace();
            }

            //Création du contrôleur
        AbstractControler controler = new Controler(adminModele);


        LoginVue  loginVue=new LoginVue(controler);


    });
    }

}
