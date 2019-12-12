package fr.univ.tln.projet.planning.dao.etudesDao;

import fr.univ.tln.projet.planning.dao.DB;
import fr.univ.tln.projet.planning.dao.Dao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.InsertDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjectExistDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuDaoException;
import fr.univ.tln.projet.planning.modele.etudes.Domaine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DomaineDao  extends Dao<Domaine> {
    /**
     * Le constructeur crée la table si nécessair
     *
     *
     *
     */
    public DomaineDao(DB bd) throws DaoException {
        super(bd);
        if (! isTableExiste("domaine")){
            try (Connection connection = this.getConnection();
                 Statement statement = connection.createStatement( )) {
                statement.executeUpdate("CREATE TABLE domaine " +
                        "(id_domaine  SERIAL  PRIMARY KEY," +
                        "intitule VARCHAR (100)  )");

            }
            catch (SQLException exp) {throw new DaoException(exp);}
        }
    }

    public Domaine trouver(int id) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM domaine WHERE id_domaine=?")){
            statement.setInt(1,id);

            ResultSet rs= statement.executeQuery( );
            if (!rs.next( ))
                throw new ObjetInconnuDaoException("domaine inexistant : "+id);

            else return Domaine.builder()
                    .idDomaine(rs.getInt("id_domaine"))
                    .intitule(rs.getString("intitule"))
                    .build();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }
    @Override
    public Domaine trouver(String intitule) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM domaine WHERE intitule=?")){
            statement.setString(1,intitule);

            ResultSet rs= statement.executeQuery( );
            if (!rs.next( ))
                throw new ObjetInconnuDaoException("intitule inexistant : "+intitule);

            else  return Domaine.builder()
                    .idDomaine(rs.getInt("id_domaine"))
                    .intitule(rs.getString("intitule"))
                    .build();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }

    /**
     * Création d'un domaine en base de données
     * @param intitule

     * @return id_domaine du nouveau domaine ajouté
     * @throws DaoException
     */
    public Domaine creer(String intitule) throws DaoException {
        if (isExisteDansLaBase(intitule))
            throw new ObjectExistDaoException("domaine déjà existant: "+intitule);
        else {
            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO domaine (intitule) VALUES (?)" , Statement.RETURN_GENERATED_KEYS)){
                statement.setString(1, intitule);
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();

                if (rs.next()) {
                    return trouver(rs.getInt(1));
                }else throw new InsertDaoException("insert exception");
            }
            catch (SQLException exp) {throw new DaoException(exp);}

        }
    }

    public List<Domaine> selectionner() throws DaoException
    {
        List<Domaine> domaines = new ArrayList();

        try (Connection connection = this.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM  domaine  WHERE true ")){
            ResultSet rs= statement.executeQuery( );

            while (rs.next()) {
                domaines.add(
                        Domaine.builder()
                                .idDomaine(rs.getInt("id_domaine"))
                                .intitule(rs.getString("intitule"))
                                .build()
                );

            }
            return domaines;

        }
        catch (SQLException exp) {throw new DaoException(exp);}

    }

    /**
     * suppression d'uu domaines en base
     * @param intitule
     * @throws DaoException
     */
    public void supprimer(String intitule) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM domain WHERE intitule=?")) {
            statement.setString(1, intitule);
            statement.executeUpdate();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }}
