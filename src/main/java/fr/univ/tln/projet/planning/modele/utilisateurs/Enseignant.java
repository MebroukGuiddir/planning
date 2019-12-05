package fr.univ.tln.projet.planning.modele.utilisateurs;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@SuperBuilder
@Getter
public class Enseignant extends Utilisateur {
     private  int idEnseignant;




    public String toString() {
        return "Enseignant [login=" + username + ", prenom=" + prenom + ", nom=" + nom + "]";
    }

}
