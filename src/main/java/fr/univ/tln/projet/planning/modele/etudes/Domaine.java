package fr.univ.tln.projet.planning.modele.etudes;

import fr.univ.tln.projet.planning.dao.etudesDao.DomaineDao;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class Domaine {
    private static DomaineDao dao;
    private int idDomaine;
    private String intitule;
    public static void setDao(DomaineDao dao) {
        Domaine.dao=dao;
    }

}
