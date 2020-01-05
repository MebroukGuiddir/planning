package fr.univ.tln.projet.planning.dao.etudesDao;

import fr.univ.tln.projet.planning.dao.DB;
import fr.univ.tln.projet.planning.dao.Dao;
import fr.univ.tln.projet.planning.dao.utilisateursDao.EnseignantDao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.InsertDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjectExistDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuDaoException;
import fr.univ.tln.projet.planning.modele.etudes.Cours;
import fr.univ.tln.projet.planning.modele.etudes.Module;
import fr.univ.tln.projet.planning.modele.utilisateurs.Enseignant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursDao extends Dao<Cours> {
    /**
     * Le constructeur crée la table si nécessair
     */
    public CoursDao(DB bd) throws DaoException {
        super(bd);
        if (!isTableExiste("cours")) {
            try (Connection connection = this.getConnection();
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE cours " +
                        "(id_cours  SERIAL  PRIMARY KEY," +
                        "id_enseignant INTEGER , FOREIGN KEY (id_enseignant) REFERENCES enseignant (id_enseignant)," +
                        "id_module INTEGER , FOREIGN KEY (id_module) REFERENCES module (id_module) )");

            } catch (SQLException exp) {
                throw new DaoException(exp);
            }
        }
    }
    public boolean isExisteDansLaBase(int idEnseignant, int idModule) throws DaoException {


        try (Connection connection = this.getDb().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM cours WHERE id_enseignant=? and id_module=?")) {
            statement.setInt(1, idEnseignant);
            statement.setInt(2, idModule);
            ResultSet rs = statement.executeQuery();
            if (!rs.next())
               return false;
            else return true;

        }

        catch (SQLException exp) {throw new DaoException(exp);}
    }
    @Override
    public Cours trouver(String id) throws DaoException {
        return trouver(Integer.valueOf(id));
    }

    public Cours trouver(int id) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM cours WHERE id_cours=?")) {
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                throw new ObjetInconnuDaoException("inexistant : " + id);

            else
            {   EnseignantDao enseignantDao = new EnseignantDao(this.getDb());
                Enseignant.setDao(enseignantDao);
                ModuleDao moduleDao = new ModuleDao(this.getDb());
                Module.setDao(moduleDao);
                return Cours.builder()
                        .idCours(rs.getInt("id_cours"))
                        .module(moduleDao.trouver(rs.getInt("id_module")))
                        .build();
            }
        } catch (SQLException exp) {
            throw new DaoException(exp);
        }
    }

    public List<Cours> selectionner(int idEnseignant) throws DaoException {
        List<Cours> cours = new ArrayList();

        try (Connection connection = this.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM  cours  WHERE id_enseignant=?")) {
            statement.setInt(1, idEnseignant);
            ResultSet rs = statement.executeQuery();

            ModuleDao moduleDao = new ModuleDao(this.getDb());
            Module.setDao(moduleDao);
            EnseignantDao enseignantDao = new EnseignantDao(this.getDb());
            Enseignant.setDao(enseignantDao);
            while (rs.next()) {
                cours.add(
                        Cours.builder()
                                .idCours(rs.getInt("id_cours"))
                                .module(moduleDao.trouver(rs.getInt("id_module")))
                                .build()
                );

            }
            return cours;

        } catch (SQLException exp) {
            throw new DaoException(exp);
        }

    }
    public Cours creer(int idEnseignant,int idModule) throws DaoException {
        if (isExisteDansLaBase(idEnseignant,idModule))
            throw new ObjectExistDaoException("exist déja: ");
        else {

            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO cours (id_enseignant,id_module) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1,idEnseignant);
                statement.setInt(2, idModule );
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

}
