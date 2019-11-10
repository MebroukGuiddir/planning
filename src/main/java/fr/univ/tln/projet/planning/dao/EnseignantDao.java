package fr.univ.tln.projet.planning.dao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuException;
import fr.univ.tln.projet.planning.modele.Enseignant;
import java.sql.*;
import java.util.Date;

public class EnseignantDao extends Dao<Enseignant> {
    public EnseignantDao(DB bd) throws DaoException{
        super(bd);
        if (! isTableExiste("enseignant")){
            try (Connection connection = this.getConnection();
                 Statement statement = connection.createStatement( )) {
                statement.executeUpdate("CREATE TABLE enseignant " +
                        "(email VARCHAR(50) PRIMARY KEY," +
                        "username VARCHAR(50)," +
                        "password VARCHAR(50)," +
                        "nom VARCHAR(50)," +
                        "prenom VARCHAR(50)," +
                        "adresse VARCHAR(50)," +
                        "mobile VARCHAR(15)," +
                        "genre VARCHAR(6)," +
                        "dateNaissance DATE ," +
                        "dateCreation Date)");
            }
            catch (SQLException exp) {throw new DaoException(exp);}
        }
    }
    public Enseignant creer(String email, String username, String password, String nom, String prenom, String adresse, String mobile, Date dateNaissance, String genre)throws DaoException{

        if (isExisteDansLaBase(username))
            throw new DaoException("Enseignant déjà existant: "+username);
        else {

            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO enseignant (email,username,password,nom,prenom,adresse,mobile,dateNaissance,genre,dateCreation) VALUES (?,?,?,?,?,?,?,?,?,?)" )){
                statement.setString(1, email);
                statement.setString(2, username);
                statement.setString(3, password);
                statement.setString(4, nom);
                statement.setString(5, prenom);
                statement.setString(6, adresse);
                statement.setString(7, mobile);
                statement.setDate(8, new java.sql.Date(dateNaissance.getTime()) );
                statement.setString(9, genre);
                statement.setDate(10, new java.sql.Date( new Date().getTime()));
                System.out.println(statement);
                statement.executeUpdate();

            }
            catch (SQLException exp) {throw new DaoException(exp);}
            return trouver(username);
        }
    }
    @Override
    public Enseignant trouver(String username) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM enseignant WHERE username=?")){
            statement.setString(1,username);
            ResultSet rs= statement.executeQuery( );
            if (!rs.next( ))
                throw new ObjetInconnuException("Fiche inexistante : "+username);
            else return Enseignant.builder()
                    .email(rs.getString(1))
                    .username(rs.getString(2))
                    .password(rs.getString(3))
                    .nom(rs.getString(4))
                    .prenom(rs.getString(5))
                    .adresse(rs.getString(6))
                    .mobile(rs.getString(7))
                    .dateNaissance(rs.getDate(8))
                    .genre(rs.getString(9))
                    .dateCreation( rs.getDate(10))
                    .build();

        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }


    public void mettreAJour(Enseignant enseignant) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE enseignant SET nom=? ,prenom=? WHERE username=?")){
            statement.setString(1,enseignant.getNom());
            statement.setString(2, enseignant.getPrenom());
            statement.setString(3, enseignant.getUsername());
            statement.executeUpdate();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }


    public void supprimer(Enseignant enseignant) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM enseignant  WHERE username=?")) {
            statement.setString(1, enseignant.getUsername());
            statement.executeUpdate();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }

}
