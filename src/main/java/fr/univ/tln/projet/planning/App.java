package fr.univ.tln.projet.planning;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.controler.Controler;
import fr.univ.tln.projet.planning.ihm.vue.*;
import fr.univ.tln.projet.planning.modele.ModeleClass;

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
            ModeleClass modeleClass = null;
            try {
                modeleClass = new ModeleClass();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            //Création du contrôleur
        AbstractControler controler = new Controler(modeleClass);


        LoginVue  loginVue=new LoginVue(controler, modeleClass);


    });
    }

}
