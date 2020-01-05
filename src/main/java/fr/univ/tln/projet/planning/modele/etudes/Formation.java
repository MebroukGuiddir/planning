package fr.univ.tln.projet.planning.modele.etudes;

import fr.univ.tln.projet.planning.dao.etudesDao.FormationDao;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
public class Formation {
    private int idFormation;
    private String intitule;
    private String niveau;
    private Domaine domaine;
    private static FormationDao dao;

    public static void setDao(FormationDao dao) {
        Formation.dao=dao;
    }



    public String toString(){
        return niveau+"  "+intitule;
    }


}

