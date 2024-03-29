package fr.univ.tln.projet.planning.dao.etudesDao;

import fr.univ.tln.projet.planning.dao.DB;
import fr.univ.tln.projet.planning.dao.Dao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.InsertDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjectExistDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuDaoException;
import fr.univ.tln.projet.planning.modele.etudes.Formation;
import fr.univ.tln.projet.planning.modele.etudes.Module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleDao extends Dao<Module> {
    /**
     * Le constructeur crée la table si nécessair
     */
    public ModuleDao(DB bd) throws DaoException {
        super(bd);
        if (!isTableExiste("module")) {
            try (Connection connection = this.getConnection();
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE module " +
                        "(id_module  SERIAL  PRIMARY KEY," +
                        "identifiant VARCHAR (50) , " +
                        "libelle VARCHAR (50) , " +
                        "id_formation INTEGER , FOREIGN KEY (id_formation) REFERENCES  formation (id_formation))");

            } catch (SQLException exp) {
                throw new DaoException(exp);
            }
        }
    }

    public Module trouver(int id) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM module WHERE id_module=?")) {
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                throw new ObjetInconnuDaoException("inexistant : " + id);

            else
            {   FormationDao dao = new FormationDao(this.getDb());
                Formation.setDao(dao);
                return Module.builder()
                        .idModule(rs.getInt("id_module"))
                        .libelle(rs.getString("libelle"))
                        .identifiant(rs.getString("identifiant"))
                        .formation(dao.trouver(rs.getInt("id_formation")))
                        .build();
            }
        } catch (SQLException exp) {
            throw new DaoException(exp);
        }
    }

    @Override
    public Module trouver(String identifiant) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM module WHERE identifiant=?")) {
            statement.setString(1, identifiant);

            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                throw new ObjetInconnuDaoException("module inexistant : " + identifiant);
            else
                {   FormationDao dao = new FormationDao(this.getDb());
                    Formation.setDao(dao);
                    return Module.builder()
                            .idModule(rs.getInt("id_module"))
                            .libelle(rs.getString("libelle"))
                            .identifiant(rs.getString("identifiant"))
                            .formation(dao.trouver(rs.getInt("id_formation")))
                            .build();
                }
        } catch (SQLException exp) {
            throw new DaoException(exp);
        }
    }


    public Module creer(String identifiant,String libelle,int idFormation) throws DaoException {
        if (isExisteDansLaBase(identifiant))
            throw new ObjectExistDaoException("exist déja: " + libelle);
        else {

            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                       "INSERT INTO module (identifiant,libelle,id_formation) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1,identifiant);
                statement.setString(2, libelle );
                statement.setInt(3, idFormation);
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

    public List<Module> selectionner(int idFormation) throws DaoException {
        List<Module> modules = new ArrayList();

        try (Connection connection = this.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM  module  WHERE id_formation=?")) {
            statement.setInt(1, idFormation);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                modules.add(
                        Module.builder()
                                .idModule(rs.getInt("id_module"))
                                .identifiant(rs.getString("identifiant"))
                                .libelle(rs.getString("libelle"))
                                .build()
                );

            }
            return modules;

        } catch (SQLException exp) {
            throw new DaoException(exp);
        }

    }
    public List<Module> selectionner() throws DaoException {
        List<Module> modules = new ArrayList();

        try (Connection connection = this.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM  module  WHERE true")) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                modules.add(
                        Module.builder()
                                .idModule(rs.getInt("id_module"))
                                .identifiant(rs.getString("identifiant"))
                                .libelle(rs.getString("libelle"))
                                .build()
                );

            }
            return modules;

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

