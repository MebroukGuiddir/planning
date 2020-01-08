package fr.univ.tln.projet.planning.modele.etudes;

import fr.univ.tln.projet.planning.dao.etudesDao.CoursDao;
import fr.univ.tln.projet.planning.modele.utilisateurs.Enseignant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter

public class Cours {
    private int idCours;
    private Enseignant enseignant;
    private Module module;
    private static CoursDao dao;
    public static void setDao(CoursDao dao) {
        Cours.dao=dao;
    }

    @Override
    public String toString() {
        return  module.getIdentifiant() +"   "+module.getLibelle();
    }
}
