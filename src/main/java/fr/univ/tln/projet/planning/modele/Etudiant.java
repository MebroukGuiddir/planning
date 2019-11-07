package fr.univ.tln.projet.planning.modele;

public class Etudiant extends Utilisateurs {
    public Etudiant() {
    }

    public Etudiant(String nom, String prenom, String username, String password, String email, int dateNaissance) {
        super(nom, prenom, username, password, email, dateNaissance);
    }


}
