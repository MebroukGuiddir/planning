package fr.univ.tln.projet.planning.modele.infrastructure;

import fr.univ.tln.projet.planning.dao.infrastractureDao.BatimentDao;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Setter
@Getter
public class Batiment {
    private  int idBatiment;
    private String identifiant;
    private static BatimentDao dao;

    public static void setDao(BatimentDao dao) {
        Batiment.dao=dao;
    }
    @Override
    public String toString() {
        return  identifiant ;
    }
}
