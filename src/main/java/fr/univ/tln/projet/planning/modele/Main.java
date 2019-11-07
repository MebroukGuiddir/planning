package fr.univ.tln.projet.planning.modele;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Etudiant et1 = new Etudiant("nom", "prenom", "username", "password", "email",20191106);
        Etudiant et2 = new Etudiant("nom2", "prenom2", "username", "password", "email",20191106);
        Etudiant et3 = new Etudiant("nom3", "prenom3", "username", "password", "email",20191106);

        Admin ad = new Admin();
        ad.addEtudiant(et1);
        ad.addEtudiant(et2);
        ad.addEtudiant(et3);

        ad.listEtudiant();

        System.out.println("--------");

        ad.modifierEtudiant(et1);

        ad.listEtudiant();

    }
}