package fr.univ.tln.projet.planning.modele.etudes;

import fr.univ.tln.projet.planning.dao.etudesDao.SeanceDao;
import fr.univ.tln.projet.planning.modele.infrastructure.Salle;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.Date;

@Builder
@Getter
@Setter
@ToString
public class Seance {

    private int idSeance;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Date date;
    private Cours cours;
    private Salle salle;
    private int status;
    private static SeanceDao dao;
    public static void setDao(SeanceDao dao) {
        Seance.dao=dao;
    }
}
