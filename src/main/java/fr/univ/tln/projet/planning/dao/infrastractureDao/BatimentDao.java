package fr.univ.tln.projet.planning.dao.infrastractureDao;

import fr.univ.tln.projet.planning.dao.DB;
import fr.univ.tln.projet.planning.dao.Dao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.InsertDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjectExistDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuDaoException;
import fr.univ.tln.projet.planning.modele.infrastructure.Batiment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BatimentDao extends Dao <Batiment>{
    /**
     * Le constructeur crée la table si nécessair
     *
     *
     *
     */
    public BatimentDao(DB bd) throws DaoException {
        super(bd);
        if (! isTableExiste("batiment")){
            try (Connection connection = this.getConnection();
                 Statement statement = connection.createStatement( )) {
                statement.executeUpdate("CREATE TABLE batiment " +
                        "(id_batiment  SERIAL  PRIMARY KEY," +
                        "identifiant VARCHAR (10)  )");

            }
            catch (SQLException exp) {throw new DaoException(exp);}
        }
    }

    public Batiment trouver(int id) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM batiment WHERE id_batiment=?")){
            statement.setInt(1,id);

            ResultSet rs= statement.executeQuery( );
            if (!rs.next( ))
                throw new ObjetInconnuDaoException("batiment inexistant : "+id);

            else return Batiment.builder()
                    .idBatiment(rs.getInt("id_batiment"))
                    .identifiant(rs.getString("identifiant"))

                    .build();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }
    @Override
    public Batiment trouver(String identifiant) throws DaoException {
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM batiment WHERE identifiant=?")){
            statement.setString(1,identifiant);

            ResultSet rs= statement.executeQuery( );
            if (!rs.next( ))
                throw new ObjetInconnuDaoException("batiment inexistant : "+identifiant);

            else return Batiment.builder()
                    .idBatiment(rs.getInt("id_batiment"))
                    .identifiant(rs.getString("identifiant"))

                    .build();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }

    /**
     * Création d'un batiment en base de données
     * @param identifiant

     * @return id_batiment du nouveau batiment ajouté
     * @throws DaoException
     */
    public Batiment creer(String identifiant) throws DaoException {
        if (isExisteDansLaBase(identifiant))
            throw new ObjectExistDaoException("batiment déjà existant: "+identifiant);
        else {

            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO batiment (identifiant) VALUES (?)" , Statement.RETURN_GENERATED_KEYS)){
                statement.setString(1, identifiant);
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();

                if (rs.next()) {
                    return trouver(rs.getInt(1));
                }else throw new InsertDaoException("insert exception");
            }
            catch (SQLException exp) {throw new DaoException(exp);}

        }
    }

    public List<Batiment> selectionner() throws DaoException
    {
        List<Batiment> batiments = new ArrayList();

        try (Connection connection = this.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM  batiment  WHERE true ")){
            ResultSet rs= statement.executeQuery( );

            while (rs.next()) {
                batiments.add(
                        Batiment.builder()
                                .idBatiment(rs.getInt("id_batiment"))
                                .identifiant(rs.getString("identifiant"))
                                .build()
                );

            }
            return batiments;

        }
        catch (SQLException exp) {throw new DaoException(exp);}

    }

    /**
     * suppression d'uu batiment en base
     * @param identifiant
     * @throws DaoException
     */
    public void supprimer(String identifiant) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM batiment WHERE identifiant=?")) {
            statement.setString(1, identifiant);
            statement.executeUpdate();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }
}
