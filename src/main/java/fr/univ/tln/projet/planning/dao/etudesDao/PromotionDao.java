package fr.univ.tln.projet.planning.dao.etudesDao;

import fr.univ.tln.projet.planning.dao.DB;
import fr.univ.tln.projet.planning.dao.Dao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.InsertDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjectExistDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuDaoException;
import fr.univ.tln.projet.planning.modele.etudes.Formation;
import fr.univ.tln.projet.planning.modele.etudes.Promotion;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PromotionDao extends Dao<Promotion> {
    /**
     * Le constructeur crée la table si nécessair
     */
    public PromotionDao(DB bd) throws DaoException {
        super(bd);
        if (!isTableExiste("promotion")) {
            try (Connection connection = this.getConnection();
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE promotion " +
                        "(id_promotion  SERIAL  PRIMARY KEY," +
                        "annee varchar (20) , " +
                        "id_formation INTEGER , FOREIGN KEY (id_formation) REFERENCES  formation (id_formation))");

            } catch (SQLException exp) {
                throw new DaoException(exp);
            }
        }
    }
    /** teste si l'objet d'identifiant donné existe en base
     * @param identifiant l'identifiant
     * @return true si l'objet existe en base, faux sinon
     * @throws DaoException
     */
    public boolean isExisteDansLaBase(String identifiant,int formation) throws DaoException{
        try {
            trouver(identifiant,formation);
            return true;
        }
        catch (ObjetInconnuDaoException exp) {
            return false;
        }
    }
    public Promotion trouver(int id) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM promotion WHERE id_promotion=?")) {
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                throw new ObjetInconnuDaoException("inexistant : " + id);

            else return Promotion.builder()
                    .idPromotion(rs.getInt("id_promotion"))
                    .annee(rs.getString("annee"))
                    .build();
        } catch (SQLException exp) {
            throw new DaoException(exp);
        }
    }

    @Override
    public Promotion trouver(String annee) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM promotion WHERE annee=?")) {
                     statement.setString(1, annee);

            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                throw new ObjetInconnuDaoException("promotion inexistant : " + annee);

            else
            {   FormationDao dao = new FormationDao(this.getDb());
                Formation.setDao(dao);
                return Promotion.builder()
                        .idPromotion(rs.getInt("id_promotion"))
                        .annee(rs.getString("annee"))
                        .formation(dao.trouver(rs.getInt("id_formation")))
                        .build();
            }
        } catch (SQLException exp) {
            throw new DaoException(exp);
        }
    }

    public Promotion trouver(String annee,int formation) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM promotion  WHERE annee=? AND id_formation=?")) {
            statement.setString(1, annee);
            statement.setInt(2, formation);
            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                throw new ObjetInconnuDaoException("promotion inexistant : " + annee);
             else
            {   FormationDao dao = new FormationDao(this.getDb());
                Formation.setDao(dao);
                return Promotion.builder()
                        .idPromotion(rs.getInt("id_promotion"))
                        .annee(rs.getString("annee"))
                        .formation(dao.trouver(rs.getInt("id_formation")))
                        .build();
            }
        } catch (SQLException exp) {
            throw new DaoException(exp);
        }
    }



    public Promotion creer(int  idFormation) throws DaoException {

        Calendar calendar = Calendar.getInstance();
        int annee=calendar.get(Calendar.YEAR);
        if ( calendar.get( Calendar.MONTH )<=Calendar.AUGUST )
            annee--;


        if (isExisteDansLaBase(annee+"",idFormation))
            throw new ObjectExistDaoException("exist déja: " + annee);
        else {
            FormationDao dao = new FormationDao(this.getDb());
            Formation.setDao(dao);
            Formation formation = dao.trouver(idFormation);
            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO promotion (annee,id_formation) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, annee+"");
                statement.setInt(2, formation.getIdFormation());
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();

                if (rs.next()) {
                    return trouver(rs.getInt(1));
                } else throw new InsertDaoException("insert exception");
            } catch (SQLException exp) {
                throw new DaoException(exp);
            }

        }
    }

    public List<Promotion> selectionner(int idFormation) throws DaoException {
        List<Promotion> promotions = new ArrayList();

        try (Connection connection = this.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM  promotion  WHERE id_formation=?")) {
            statement.setInt(1, idFormation);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                promotions.add(
                        Promotion.builder()
                                .idPromotion(rs.getInt("id_promotion"))
                                .annee(rs.getString("annee"))
                                .build()
                );

            }
            return promotions;

        } catch (SQLException exp) {
            throw new DaoException(exp);
        }

    }

    /**
     * suppression d'un Module en base
     *
     * @param libelle
     * @throws DaoException
     */
    public void supprimer(String libelle) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM module WHERE libelle=?")) {
            statement.setString(1, libelle);
            statement.executeUpdate();
        } catch (SQLException exp) {
            throw new DaoException(exp);
        }
    }
}

