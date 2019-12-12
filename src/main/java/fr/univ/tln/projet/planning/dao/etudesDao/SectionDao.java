package fr.univ.tln.projet.planning.dao.etudesDao;

import fr.univ.tln.projet.planning.dao.DB;
import fr.univ.tln.projet.planning.dao.Dao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.InsertDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjectExistDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuDaoException;
import fr.univ.tln.projet.planning.modele.etudes.Formation;
import fr.univ.tln.projet.planning.modele.etudes.Promotion;
import fr.univ.tln.projet.planning.modele.etudes.Section;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionDao extends Dao<Section> {
    /**
     * Le constructeur crée la table si nécessair
     */
    public  SectionDao(DB bd) throws DaoException {
        super(bd);
        if (!isTableExiste("section")) {
            try (Connection connection = this.getConnection();
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE section " +
                        "(id_section SERIAL  PRIMARY KEY," +
                        "identifiant VARCHAR (50) , " +
                        "id_promotion INTEGER , FOREIGN KEY (id_promotion) REFERENCES  promotion (id_promotion))");

            } catch (SQLException exp) {
                throw new DaoException(exp);
            }
        }
    }

    public Section trouver(int id) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM section WHERE id_section=?")) {
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                throw new ObjetInconnuDaoException("inexistant : " + id);

            else
            {   PromotionDao dao = new PromotionDao(this.getDb());
                Promotion.setDao(dao);
                return Section.builder()
                        .idSection(rs.getInt("id_section"))
                        .identifiant(rs.getString("identifiant"))
                        .promotion(dao.trouver(rs.getInt("id_promotion")))
                        .build();
            }
        } catch (SQLException exp) {
            throw new DaoException(exp);
        }
    }

    @Override
    public Section trouver(String identifiant) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM module WHERE identifiant=?")) {
            statement.setString(1, identifiant);

            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                throw new ObjetInconnuDaoException("inexistant : " + identifiant);
            else
            {   PromotionDao dao = new PromotionDao(this.getDb());
                Promotion.setDao(dao);
                return Section.builder()
                        .idSection(rs.getInt("id_section"))
                        .identifiant(rs.getString("identifiant"))
                        .promotion(dao.trouver(rs.getInt("id_promotion")))
                        .build();
            }
        } catch (SQLException exp) {
            throw new DaoException(exp);
        }
    }


    public Section creer(String identifiant, String idPromotion) throws DaoException {
        if (isExisteDansLaBase(identifiant))
            throw new ObjectExistDaoException("exist déja: " + identifiant);
        else {
            PromotionDao dao = new PromotionDao(this.getDb());
            Promotion.setDao(dao);
            Promotion promotion = dao.trouver(idPromotion);
            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO promotion (indentifiant,id_promotion) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, identifiant);
                statement.setInt(3, promotion.getIdPromotion());
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

    public List<Section> selectionner(String idPromotion) throws DaoException {
        List<Section> sections = new ArrayList();
        PromotionDao dao = new PromotionDao(this.getDb());
        Promotion.setDao(dao);
        Promotion promotion = dao.trouver(idPromotion);
        try (Connection connection = this.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM  section  WHERE id_promotion=?")) {
            statement.setInt(1, promotion.getIdPromotion());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                sections.add(
                        Section.builder()
                                .idSection(rs.getInt("id_section"))
                                .identifiant(rs.getString("identifiant"))
                                .build()
                );

            }
            return sections;

        } catch (SQLException exp) {
            throw new DaoException(exp);
        }

    }

    /**
     * suppression d'une Section en base
     *
     * @param identifiant
     * @throws DaoException
     */
    public void supprimer(String identifiant) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM module WHERE identifiant=?")) {
            statement.setString(1, identifiant);
            statement.executeUpdate();
        } catch (SQLException exp) {
            throw new DaoException(exp);
        }
    }
}

