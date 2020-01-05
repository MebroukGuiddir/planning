package fr.univ.tln.projet.planning.modele.etudes;

import fr.univ.tln.projet.planning.dao.etudesDao.DomaineDao;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

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

    public boolean equals(Object intitule) {

        return Objects.equals(getIntitule(), intitule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIntitule());
    }

    @Override
    public String toString() {
        return intitule;

    }
    public static Domaine getDomaine(List <Domaine> domaines,String intitule){
        for(Domaine  domaine :domaines ){
            if(domaine.intitule.equals(intitule))return domaine;
        }
        return null;
    }
}
