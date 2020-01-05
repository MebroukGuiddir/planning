package fr.univ.tln.projet.planning.modele.utilisateurs;

import fr.univ.tln.projet.planning.modele.etudes.Formation;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@ToString
public class Responsable extends Utilisateur{
    private int idResponsable;
    private Formation formation;

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public String toString() {
        return "Responsable [login=" + username + ", prenom=" + prenom + ", nom=" + nom + "]";
    }

}
