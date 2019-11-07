package fr.univ.tln.projet.planning.modele;

public class Enseignant extends Utilisateurs {
    public Enseignant() {
    }

    public Enseignant(String nom, String prenom, String username, String password, String email, int dateNaissance) {
        super(nom, prenom, username, password, email, dateNaissance);
    }
}
