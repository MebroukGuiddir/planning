package fr.univ.tln.projet.planning.modele.etudes;

import fr.univ.tln.projet.planning.dao.etudesDao.ModuleDao;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class Module {
    private int idModule;
    private String libelle;
    private Formation formation;
    private static ModuleDao dao;

    public static void setDao(ModuleDao dao) {
        Module.dao=dao;
    }
}
