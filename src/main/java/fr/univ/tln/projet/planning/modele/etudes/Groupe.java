package fr.univ.tln.projet.planning.modele.etudes;


import fr.univ.tln.projet.planning.dao.etudesDao.GroupeDao;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class Groupe {
    private int idGroupe;
    private String identifiant;
    private Section section;
    private static GroupeDao dao;

    public static void setDao(GroupeDao dao) {
        Groupe.dao=dao;
    }
}
