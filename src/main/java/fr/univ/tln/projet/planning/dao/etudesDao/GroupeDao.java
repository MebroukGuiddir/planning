package fr.univ.tln.projet.planning.dao.etudesDao;

import fr.univ.tln.projet.planning.dao.DB;
import fr.univ.tln.projet.planning.dao.Dao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.InsertDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuDaoException;
import fr.univ.tln.projet.planning.modele.etudes.Groupe;
import fr.univ.tln.projet.planning.modele.etudes.Section;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupeDao extends Dao<Groupe> {
    /**
     * Le constructeur crée la table si nécessair
     */
    public  GroupeDao(DB bd) throws DaoException {
        super(bd);
        if (!isTableExiste("groupe")) {
            try (Connection connection = this.getConnection();
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE groupe " +
                        "(id_groupe SERIAL  PRIMARY KEY," +
                        "identifiant INTEGER , " +
                        "id_section INTEGER , FOREIGN KEY (id_section) REFERENCES  section (id_section))");

            } catch (SQLException exp) {
                throw new DaoException(exp);
            }
        }
    }

    public Groupe trouver(int id) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM groupe WHERE id_groupe=?")) {
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                throw new ObjetInconnuDaoException("inexistant : " + id);

            else
            {   SectionDao dao = new SectionDao(this.getDb());
                Section.setDao(dao);
                return Groupe.builder()
                        .idGroupe(rs.getInt("id_groupe"))
                        .identifiant(rs.getInt("identifiant"))
                        .section(dao.trouver(rs.getInt("id_section")))
                        .build();
            }
        } catch (SQLException exp) {
            throw new DaoException(exp);
        }
    }
    public Groupe trouver(String identifiant) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM groupe WHERE id_groupe=?")) {
            statement.setInt(1, Integer.valueOf(identifiant));

            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                throw new ObjetInconnuDaoException("inexistant : " + identifiant);

            else
            {   SectionDao dao = new SectionDao(this.getDb());
                Section.setDao(dao);
                return Groupe.builder()
                        .idGroupe(rs.getInt("id_groupe"))
                        .identifiant(rs.getInt("identifiant"))
                        .section(dao.trouver(rs.getInt("id_section")))
                        .build();
            }
        } catch (SQLException exp) {
            throw new DaoException(exp);
        }
    }



    public Groupe creer(int idSection) throws DaoException {
            int identifiant=0;
            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "SELECT COUNT(*) AS rowcount FROM  groupe WHERE id_section=? ")) {
                statement.setInt(1, idSection);
                ResultSet rs = statement.executeQuery();;
                rs.next();
                identifiant=rs.getInt("rowcount")+1;
            }catch (SQLException exp){throw new DaoException(exp);}

            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO groupe (identifiant,id_section) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, identifiant);
                statement.setInt(2, idSection);
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();

                if (rs.next()) {
                    return trouver(rs.getInt(1));
                } else throw new InsertDaoException("insert exception");
            } catch (SQLException exp) {
                throw new DaoException(exp);
            }

        }


    public List<Groupe> selectionner(int idPromotion) throws DaoException {
        List<Groupe> groupes = new ArrayList();
        try (Connection connection = this.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM  groupe  WHERE id_section=?")) {
            statement.setInt(1,idPromotion);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                groupes.add(
                        Groupe.builder()
                                .idGroupe(rs.getInt("id_groupe"))
                                .identifiant(rs.getInt("identifiant"))
                                .build()
                );

            }
            return groupes;

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

