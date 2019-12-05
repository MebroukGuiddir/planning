package fr.univ.tln.projet.planning.modele.utilisateurs;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@ToString
public class Responsable extends Utilisateur{
    private int idResponsable;
    public String toString() {
        return "Responsable [login=" + username + ", prenom=" + prenom + ", nom=" + nom + "]";
    }

}
