package fr.univ.tln.projet.planning.dao.etudesDao;

import fr.univ.tln.projet.planning.dao.DB;
import fr.univ.tln.projet.planning.dao.Dao;
import fr.univ.tln.projet.planning.dao.infrastractureDao.SalleDao;
import fr.univ.tln.projet.planning.dao.utilisateursDao.EtudiantDao;
import fr.univ.tln.projet.planning.dao.utilisateursDao.ResponsableDao;
import fr.univ.tln.projet.planning.exception.dao.*;
import fr.univ.tln.projet.planning.ihm.vue.etudesVue.EtudiantAffecterVue;
import fr.univ.tln.projet.planning.modele.etudes.Cours;
import fr.univ.tln.projet.planning.modele.etudes.Promotion;
import fr.univ.tln.projet.planning.modele.etudes.Seance;
import fr.univ.tln.projet.planning.modele.etudes.Section;
import fr.univ.tln.projet.planning.modele.infrastructure.Salle;
import fr.univ.tln.projet.planning.modele.utilisateurs.Etudiant;
import fr.univ.tln.projet.planning.modele.utilisateurs.Responsable;


import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SeanceDao extends Dao {

    /**
     * Le constructeur crée la table si nécessair
     */
    public   SeanceDao(DB bd) throws DaoException {
        super(bd);
        if (!isTableExiste("seance")) {
            try (Connection connection = this.getConnection();
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE seance " +
                        "(id_seance SERIAL  PRIMARY KEY," +
                        "date  DATE NOT NULL,"+
                        "start_at TIME (0) NOT NULL, " +
                        "end_at TIME (0) NOT NULL,"+
                        "status INTEGER ,"+
                        "id_cours INTEGER , FOREIGN KEY (id_cours) REFERENCES  cours (id_cours),"+
                        "id_salle INTEGER , FOREIGN KEY (id_salle) REFERENCES  salle (id_salle))");

            } catch (SQLException exp) {
                throw new DaoException(exp);
            }
        }
    }
    /**
     * trouver un seance
     * @param id
     * @return
     * @throws DaoException
     */
    @Override
    public Seance trouver(String id) throws DaoException {
        return trouver(Integer.valueOf(id));
    }
    public Seance trouver(int id) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM seance WHERE id_seance=?")){
            statement.setInt(1,id);
            ResultSet rs= statement.executeQuery( );
            if (!rs.next( ))
                throw new ObjetInconnuDaoException("null");
            else{CoursDao coursDao = new CoursDao(this.getDb());
                Cours.setDao(coursDao);
                SalleDao salleDao = new SalleDao(this.getDb());
                Salle.setDao(salleDao);
                return Seance.builder()
                        .idSeance(rs.getInt("id_seance"))
                        .date(rs.getDate("date"))
                        .status(rs.getInt("status"))
                        .heureDebut(rs.getTime("start_at").toLocalTime())
                        .heureFin(rs.getTime("end_at").toLocalTime())
                        .cours(coursDao.trouver(rs.getInt("id_cours")))
                        .salle(salleDao.trouver(rs.getInt("id_salle")))
                        .build();

            }
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }

    /**
     * Création d'une seance en base de données
     * @throws DaoException
     */
    public Seance creer(LocalTime heureDebut,LocalTime heureFin, java.util.Date date,int idSalle,int idCours,int idEnseignant) throws DaoException {
        boolean libre=true;
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM seance WHERE date=? AND not((start_at<? and start_at<?)or(end_at>? and end_at>?) ) AND id_salle=? ")){
            statement.setDate(1,new java.sql.Date(date.getTime()));
            statement.setTime(2,Time.valueOf(heureDebut));
            statement.setTime(3, Time.valueOf(heureFin));
            statement.setTime(4,Time.valueOf(heureDebut));
            statement.setTime(5, Time.valueOf(heureFin));
            statement.setInt(6, idSalle);
            ResultSet rs= statement.executeQuery( );
            if (rs.next( ))
            {   libre =false;
                throw new SalleNonLibreException("salle non libre");
            }
        } catch (SQLException exp) {
            throw new DaoException(exp);
        }

        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM  seance WHERE date=? AND not((start_at<? and start_at<?)or(end_at>? and end_at>?) ) AND id_cours " +
                             "IN(SELECT id_cours FROM cours WHERE id_enseignant=?) ")){

            statement.setDate(1, new java.sql.Date(date.getTime()));
            statement.setTime(2, Time.valueOf(heureDebut));
            statement.setTime(3, Time.valueOf(heureFin));
            statement.setTime(4, Time.valueOf(heureDebut));
            statement.setTime(5, Time.valueOf(heureFin));
            statement.setInt(6,idEnseignant);
            ResultSet rs= statement.executeQuery( );
            if (rs.next( ))
            {   libre =false;
                throw new EnseignantNonLibreException("enseignant non libre");
            }
        } catch (SQLException exp) {
            throw new DaoException(exp);
        }
        PromotionDao promtionDao = new PromotionDao(this.getDb());
        Promotion.setDao(promtionDao);
        Promotion promotion=promtionDao.trouverParCours(idCours);
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM  seance WHERE date=? AND NOT((start_at<? and start_at<?)or(end_at>? and end_at>?)) AND id_cours " +
                             "IN( SELECT id_cours FROM cours c, module m WHERE m.id_module=c.id_module and m.id_formation=? ) ")){

            statement.setDate(1, new java.sql.Date(date.getTime()));
            statement.setTime(2, Time.valueOf(heureDebut));
            statement.setTime(3, Time.valueOf(heureFin));
            statement.setTime(4, Time.valueOf(heureDebut));
            statement.setTime(5, Time.valueOf(heureFin));
            statement.setInt(6 , promotion.getFormation().getIdFormation());
            ResultSet rs= statement.executeQuery( );
            if (rs.next( ))
            {   libre =false;
                throw new EnseignantNonLibreException("promotion non libre");
            }


        } catch (SQLException exp) {
            throw new DaoException(exp);
        }

            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO seance (date,start_at,end_at,status,id_cours,id_salle) VALUES (?,?,?,-1,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setDate(1,new java.sql.Date(date.getTime()));
                statement.setTime(2,Time.valueOf(heureDebut));
                statement.setTime(3, Time.valueOf(heureFin));
                statement.setInt(4, idCours);
                statement.setInt(5, idSalle);
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();

                if (rs.next()) {
                    return trouver(rs.getInt(1));
                } else throw new InsertDaoException("insert exception");
            } catch (SQLException exp) {
                throw new DaoException(exp);
            }

        }

        public boolean annulerSeance(int idSeance) throws DaoException {
            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "UPDATE seance SET status=0  WHERE id_seance=?")){
                statement.setInt(1,idSeance);


                statement.executeUpdate();
            }
            catch (SQLException exp) {
                throw new DaoException(exp);}
           return true;
        }
        public boolean validerSeance(int idSeance,int status) throws DaoException {
            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "UPDATE seance SET status=?  WHERE id_seance=?")){
                statement.setInt(1,status);
                statement.setInt(2,idSeance);

                statement.executeUpdate();
            }
            catch (SQLException exp) {
                throw new DaoException(exp);}
            return true;
        }


    public List<Seance> selectionnerSeancesEnseignant(int idEnseignant,int periode,java.util.Date date) throws DaoException {
        List<Seance> seances = new ArrayList();
        System.out.println("         "+date.getTime()+"             "+new Date(date.getTime() + (1000 * 60 * 60 * 24 *periode)));
        Calendar c = Calendar.getInstance();

        c.setTime(date);
         if(periode==0) c.add(Calendar.DATE, 1);
             else if(periode==1) c.add(Calendar.DATE, 5);
                 else if(periode==2) c.add(Calendar.MONTH, 1);
        // manipulate date


        try (Connection connection = this.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM  seance  WHERE date >= ? and date <= ? and id_cours IN(SELECT id_cours from cours WHERE id_enseignant=?) ORDER BY date ASC, start_at ASC")) {

            statement.setDate(1,new java.sql.Date(date.getTime()));
            statement.setDate(2,new java.sql.Date(c.getTime().getTime()));
            statement.setInt(3, idEnseignant);
            ResultSet rs = statement.executeQuery();
            logger.info("selected");
            CoursDao coursDao = new CoursDao(this.getDb());
            Cours.setDao(coursDao);
            SalleDao salleDao = new SalleDao(this.getDb());
            Salle.setDao(salleDao);
            while (rs.next()) {
                seances.add(
                        Seance.builder()
                                .idSeance(rs.getInt("id_seance"))
                                .date(rs.getDate("date"))
                                .status(rs.getInt("status"))
                                .heureDebut(rs.getTime("start_at").toLocalTime())
                                .heureFin(rs.getTime("end_at").toLocalTime())
                                .cours(coursDao.trouver(rs.getInt("id_cours")))
                                .salle(salleDao.trouver(rs.getInt("id_salle")))
                                .build()
                );


            }
            return seances;

        } catch (SQLException exp) {
            throw new DaoException(exp);
        }

    }

    public List<Seance> selectionnerSeancesResponsable(int idUser,int periode,java.util.Date date) throws DaoException {
        List<Seance> seances = new ArrayList();
        Calendar c = Calendar.getInstance();

        c.setTime(date);
        if (periode == 0) c.add(Calendar.DATE, 1);
        else if (periode == 1) c.add(Calendar.DATE, 5);
        else if (periode == 2) c.add(Calendar.MONTH, 1);
        else if (periode == 3) c.add(Calendar.YEAR, 1);
        // manipulate date

        ResponsableDao responsableDao = new ResponsableDao(this.getDb());
        Responsable.setDao(responsableDao);

        try (Connection connection = this.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM  seance  WHERE date >= ? and date <= ? and (status=-1 or status=0) and id_cours " +
                             "IN(SELECT id_cours from cours WHERE id_module " +
                             "IN(SELECT id_module from module WHERE id_formation=?)) " +
                             "ORDER BY date ASC, start_at ASC")) {

            statement.setDate(1, new java.sql.Date(date.getTime()));
            statement.setDate(2, new java.sql.Date(c.getTime().getTime()));
            statement.setInt(3, responsableDao.trouver(idUser).getFormation().getIdFormation());
            ResultSet rs = statement.executeQuery();
            logger.info("selected");
            CoursDao coursDao = new CoursDao(this.getDb());
            Cours.setDao(coursDao);
            SalleDao salleDao = new SalleDao(this.getDb());
            Salle.setDao(salleDao);
            while (rs.next()) {
                seances.add(
                        Seance.builder()
                                .idSeance(rs.getInt("id_seance"))
                                .date(rs.getDate("date"))
                                .status(rs.getInt("status"))
                                .heureDebut(rs.getTime("start_at").toLocalTime())
                                .heureFin(rs.getTime("end_at").toLocalTime())
                                .cours(coursDao.trouver(rs.getInt("id_cours")))
                                .salle(salleDao.trouver(rs.getInt("id_salle")))
                                .build()
                );


            }
            return seances;

        } catch (SQLException exp) {
            throw new DaoException(exp);
        }

    }
        public List<Seance> selectionnerSeancesEtudiant(int idUser,int periode,java.util.Date date) throws DaoException
        {
            List<Seance> seances = new ArrayList();
            Calendar c = Calendar.getInstance();

            c.setTime(date);
            if (periode == 0) c.add(Calendar.DATE, 1);
            else if (periode == 1) c.add(Calendar.DATE, 5);
            else if (periode == 2) c.add(Calendar.MONTH, 1);
            else if (periode == 3) c.add(Calendar.YEAR, 1);
            // manipulate date

            EtudiantDao etudiantDao = new EtudiantDao(this.getDb());
            Etudiant.setDao(etudiantDao);

            try (Connection connection = this.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement("SELECT * FROM  seance  WHERE date >= ? and date <= ? and (status=1 or status=0) and id_cours " +
                                 "IN(SELECT id_cours from cours WHERE id_module " +
                                 "IN(SELECT id_module from module WHERE id_formation=?)) " +
                                 "ORDER BY date ASC, start_at ASC")) {

                statement.setDate(1, new java.sql.Date(date.getTime()));
                statement.setDate(2, new java.sql.Date(c.getTime().getTime()));
                statement.setInt(3, etudiantDao.trouver(idUser).getPromotion().getFormation().getIdFormation());
                ResultSet rs = statement.executeQuery();
                logger.info("selected");
                CoursDao coursDao = new CoursDao(this.getDb());
                Cours.setDao(coursDao);
                SalleDao salleDao = new SalleDao(this.getDb());
                Salle.setDao(salleDao);
                while (rs.next()) {
                    seances.add(
                            Seance.builder()
                                    .idSeance(rs.getInt("id_seance"))
                                    .date(rs.getDate("date"))
                                    .status(rs.getInt("status"))
                                    .heureDebut(rs.getTime("start_at").toLocalTime())
                                    .heureFin(rs.getTime("end_at").toLocalTime())
                                    .cours(coursDao.trouver(rs.getInt("id_cours")))
                                    .salle(salleDao.trouver(rs.getInt("id_salle")))
                                    .build()
                    );


                }
                return seances;

            } catch (SQLException exp) {
                throw new DaoException(exp);
            }
        }



}

