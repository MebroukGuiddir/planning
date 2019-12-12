package fr.univ.tln.projet.planning.modele.etudes;

import fr.univ.tln.projet.planning.dao.etudesDao.PromotionDao;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

@Builder
@Getter
@Setter
@ToString
public class Promotion {
    private int idPromotion;
    private String annee;
    private Formation formation;
    private static PromotionDao dao;

    public static void setDao(PromotionDao dao) {
        Promotion.dao=dao;
    }
}
