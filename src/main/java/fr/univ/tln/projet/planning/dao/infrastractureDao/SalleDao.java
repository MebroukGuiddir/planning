package fr.univ.tln.projet.planning.dao.infrastractureDao;

import fr.univ.tln.projet.planning.dao.DB;
import fr.univ.tln.projet.planning.dao.Dao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.InsertDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjectExistDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuDaoException;
import fr.univ.tln.projet.planning.modele.infrastructure.Batiment;
import fr.univ.tln.projet.planning.modele.infrastructure.Salle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleDao extends Dao<Salle> {
    /**
     * Le constructeur crée la table si nécessair
     *
     *
     *
     */
    public SalleDao(DB bd) throws DaoException {
        super(bd);
        if (! isTableExiste("salle")){
            try (Connection connection = this.getConnection();
                 Statement statement = connection.createStatement( )) {
                statement.executeUpdate("CREATE TABLE salle " +
                        "(id_salle  SERIAL  PRIMARY KEY," +
                        "identifiant VARCHAR (10) , " +
                        "id_batiment INTEGER , FOREIGN KEY (id_batiment) REFERENCES  batiment (id_batiment))");

            }
            catch (SQLException exp) {throw new DaoException(exp);}
        }
    }

    public Salle trouver(int id) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM salle WHERE id_salle=?")){
            statement.setInt(1,id);

            ResultSet rs= statement.executeQuery( );
            if (!rs.next( ))
                throw new ObjetInconnuDaoException("batiment inexistant : "+id);

            else { BatimentDao batimentDao = new BatimentDao(this.getDb());
                Batiment.setDao(batimentDao);
                return Salle.builder()
                    .idSalle(rs.getInt("id_salle"))
                    .identifiant(rs.getString("identifiant"))
                    .batiment(batimentDao.trouver(rs.getInt("id_batiment")))
                    .build();}
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }
    @Override
    public Salle trouver(String identifiant) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM salle WHERE identifiant=?")){
            statement.setString(1,identifiant);

            ResultSet rs= statement.executeQuery( );
            if (!rs.next( ))
                throw new ObjetInconnuDaoException("salle inexistante : "+identifiant);

            else { BatimentDao batimentDao = new BatimentDao(this.getDb());
                Batiment.setDao(batimentDao);
                return Salle.builder()
                        .idSalle(rs.getInt("id_salle"))
                        .identifiant(rs.getString("identifiant"))
                        .batiment(batimentDao.trouver(rs.getInt("id_batiment")))
                        .build();}
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }

    /**
     * Création d'une salle en base de données
     * @param identifiant

     * @return id_salle de la nouvelle salle ajouté
     * @throws DaoException
     */
    public Salle creer(String identifiant,String idBatiment) throws DaoException {
        if (isExisteDansLaBase(identifiant))
            throw new ObjectExistDaoException("salle déjà existant: "+identifiant);
        else {
            BatimentDao dao = new BatimentDao(this.getDb());
            Batiment.setDao(dao);
            Batiment batiment=dao.trouver(idBatiment);
            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO salle (identifiant,id_batiment) VALUES (?,?)" , Statement.RETURN_GENERATED_KEYS)){
                statement.setString(1, identifiant);
                statement.setInt(2, batiment.getIdBatiment());
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();

                if (rs.next()) {
                    return trouver(rs.getInt(1));
                }else throw new InsertDaoException("insert exception");
            }
            catch (SQLException exp) {throw new DaoException(exp);}

        }
    }

    public List<Salle> selectionner(String idBatiment) throws DaoException
    {
        List<Salle> salles = new ArrayList();
        BatimentDao dao = new BatimentDao(this.getDb());
        Batiment.setDao(dao);
        Batiment batiment=dao.trouver(idBatiment);
        try (Connection connection = this.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM  salle  WHERE id_batiment=?")){
            statement.setInt(1, batiment.getIdBatiment());
            ResultSet rs= statement.executeQuery( );

            while (rs.next()) {
                salles.add(
                        Salle.builder()
                                .idSalle(rs.getInt("id_salle"))
                                .identifiant(rs.getString("identifiant"))
                                .build()
                );

            }
            return salles;

        }
        catch (SQLException exp) {throw new DaoException(exp);}

    }

    /**
     * suppression d'une salle en base
     * @param identifiant
     * @throws DaoException
     */
    public void supprimer(String identifiant) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM salle WHERE identifiant=?")) {
            statement.setString(1, identifiant);
            statement.executeUpdate();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }
}
