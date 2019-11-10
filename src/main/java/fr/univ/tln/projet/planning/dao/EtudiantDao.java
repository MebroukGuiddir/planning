package fr.univ.tln.projet.planning.dao;

import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuException;
import fr.univ.tln.projet.planning.modele.Etudiant;

import java.sql.*;
import java.util.Date;

public class EtudiantDao extends Dao<Etudiant> {

    /**
     * Le constructeur crée la table si nécessair
     */
    public EtudiantDao(DB bd) throws DaoException{
        super(bd);
        if (! isTableExiste("etudiant")){
            try (Connection connection = this.getConnection();
                 Statement statement = connection.createStatement( )) {
                statement.executeUpdate("CREATE TABLE etudiant " +
                        "(email VARCHAR(50) PRIMARY KEY," +
                        "username VARCHAR(25)," +
                        "password VARCHAR(25)," +
                        "nom VARCHAR(25)," +
                        "prenom VARCHAR(25)," +
                        "adresse VARCHAR(50)," +
                        "mobile VARCHAR(15)," +
                        "genre VARCHAR(5)," +
                        "dateNaissance DATE ," +
                        "dateCreation Date)");
            }
            catch (SQLException exp) {throw new DaoException(exp);}
        }
    }

    /**
     * Création d'un Etudiant en base de données
     * @param username
     * @param nom
     * @param prenom
     * @return
     * @throws DaoException
     */
    public Etudiant creer(String email, String username, String password, String nom, String prenom, String adresse, String mobile, Date dateNaissance, String genre)throws DaoException{
        if (isExisteDansLaBase(username))
            throw new DaoException("Etudiant déjà existant: "+username);
        else {
            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO fiche (login,nom,prenom) VALUES (?,?,?)" )){
                statement.setString(1, username);
                statement.setString(2, nom);
                statement.setString(3, prenom);
                statement.executeUpdate();
            }
            catch (SQLException exp) {throw new DaoException(exp);}
            return trouver(username);
        }
    }
    @Override
    public Etudiant trouver(String username) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT nom,prenom FROM etudiant WHERE login=?")){
            statement.setString(1,username);
            ResultSet rs= statement.executeQuery( );
            if (!rs.next( ))
                throw new ObjetInconnuException("Fiche inexistante : "+username);
            else return Etudiant.builder().username(username).nom(rs.getString(1)).prenom(rs.getString(2)).build();

        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }

    /**
     * Mettre à jour un Etudiant dans la base
     * @param etudiant
     * @throws DaoException
     */
    public void mettreAJour(Etudiant etudiant) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE etudiant SET nom=? ,prenom=? WHERE login=?")){
            statement.setString(1,etudiant.getNom());
            statement.setString(2, etudiant.getPrenom());
            statement.setString(3, etudiant.getUsername());
            statement.executeUpdate();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }

    /**
     * suppression d'un Etudiant en base
     * @param etudiant
     * @throws DaoException
     */
    public void supprimer(Etudiant etudiant) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM etudiant WHERE login=?")) {
            statement.setString(1, etudiant.getUsername());
            statement.executeUpdate();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }

}
