package fr.univ.tln.projet.planning.modele.etudes;


import fr.univ.tln.projet.planning.dao.etudesDao.SectionDao;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class Section {
    private int idSection;
    private int identifiant;
    private Promotion promotion;
    private static SectionDao dao;

    public static void setDao(SectionDao dao) {
        Section.dao=dao;
    }
}
