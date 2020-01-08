package fr.univ.tln.projet.planning.modele.infrastructure;

import fr.univ.tln.projet.planning.dao.infrastractureDao.SalleDao;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@SuperBuilder
@Getter
public class Salle{
    private  int idSalle;
    private String identifiant;
    private Batiment batiment;
    private static SalleDao dao;

    public static void setDao(SalleDao dao) {
        Salle.dao=dao;
    }

    @Override
    public String toString() {
        return  identifiant ;
    }
}
