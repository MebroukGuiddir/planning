package fr.univ.tln.projet.planning.modele;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@ToString
public class Responsable extends Enseignant {
    private int idResponsable;
    public String toString() {
        return "Responsable [login=" + username + ", prenom=" + prenom + ", nom=" + nom + "]";
    }

}
