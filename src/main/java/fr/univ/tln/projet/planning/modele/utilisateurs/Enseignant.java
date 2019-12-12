package fr.univ.tln.projet.planning.modele.utilisateurs;
import fr.univ.tln.projet.planning.dao.utilisateursDao.EnseignantDao;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@SuperBuilder
@Getter
public class Enseignant extends Utilisateur {
    private static EnseignantDao dao;
    private int idEnseignant;
    public static void setDao(EnseignantDao dao) {
        Enseignant.dao=dao;
    }


    public String toString() {
        return "Enseignant [login=" + username + ", prenom=" + prenom + ", nom=" + nom + "]";
    }

}
