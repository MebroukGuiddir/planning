package fr.univ.tln.projet.planning.dao.etudesDao;

import fr.univ.tln.projet.planning.dao.DB;
import fr.univ.tln.projet.planning.dao.Dao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.InsertDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjectExistDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuDaoException;
import fr.univ.tln.projet.planning.modele.etudes.Domaine;
import fr.univ.tln.projet.planning.modele.etudes.Formation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormationDao extends Dao<Formation> {
    /**
     * Le constructeur crée la table si nécessair
     */
    public FormationDao(DB bd) throws DaoException {
        super(bd);
        if (!isTableExiste("formation")) {
            try (Connection connection = this.getConnection();
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE formation " +
                        "(id_formation  SERIAL  PRIMARY KEY," +
                        "intitule VARCHAR (50) , " +
                        "niveau VARCHAR (5) , " +
                        "id_domaine INTEGER , FOREIGN KEY (id_domaine) REFERENCES  domaine (id_domaine))");

            } catch (SQLException exp) {
                throw new DaoException(exp);
            }
        }
    }

    public Formation trouver(int id) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM formation WHERE id_formation=?")) {
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                throw new ObjetInconnuDaoException("formation inexistant : " + id);

            else{   DomaineDao dao = new DomaineDao(this.getDb());
                    Domaine.setDao(dao);
                    return Formation.builder()
                    .idFormation(rs.getInt("id_formation"))
                    .intitule(rs.getString("intitule"))
                    .niveau(rs.getString("niveau"))
                    .domaine(dao.trouver(rs.getInt("id_domaine")))
                    .build();
            }
        } catch (SQLException exp) {
            throw new DaoException(exp);
        }
    }

    @Override
    public Formation trouver(String intitule) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM formation WHERE intitule=?")) {
            statement.setString(1, intitule);

            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                throw new ObjetInconnuDaoException("salle inexistante : " + intitule);

            else {   DomaineDao dao = new DomaineDao(this.getDb());
                    Domaine.setDao(dao);
                    return Formation.builder()
                        .idFormation(rs.getInt("id_formation"))
                        .intitule(rs.getString("intitule"))
                        .niveau(rs.getString("niveau"))
                        .domaine(dao.trouver(rs.getInt("id_domaine")))
                        .build();
            }
        } catch (SQLException exp) {
            throw new DaoException(exp);
        }
    }

    /**
     * Création d'une formation en base de données
     *
     * @param intitule
     * @return id_formation de la nouvelle salle ajouté
     * @throws DaoException
     */
    public Formation creer(String intitule, String niveau, int idDomaine) throws DaoException {
        if (isExisteDansLaBase(intitule))
            throw new ObjectExistDaoException("formation déjà existant: " + intitule);
        else {

            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO formation (intitule,niveau,id_domaine) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, intitule);
                statement.setString(2, niveau);
                statement.setInt(3, idDomaine);
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

    public List<Formation> selectionner(int idDomaine) throws DaoException {
        List<Formation> formations = new ArrayList();

        try (Connection connection = this.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM  formation  WHERE id_domaine=?")) {
            statement.setInt(1, idDomaine);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                formations.add(
                        Formation.builder()
                                .idFormation(rs.getInt("id_formation"))
                                .intitule(rs.getString("intitule"))
                                .niveau(rs.getString("niveau"))
                                .build()
                );

            }
            return formations;

        } catch (SQLException exp) {
            throw new DaoException(exp);
        }

    }

    /**
     * suppression d'une formation en base
     *
     * @param intitule
     * @throws DaoException
     */
    public void supprimer(String intitule) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM formation WHERE intitule=?")) {
            statement.setString(1, intitule);
            statement.executeUpdate();
        } catch (SQLException exp) {
            throw new DaoException(exp);
        }
    }
}
