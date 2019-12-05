package fr.univ.tln.projet.planning.dao.utilisateursDao;

import fr.univ.tln.projet.planning.dao.DB;
import fr.univ.tln.projet.planning.dao.Dao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.InsertDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjectExistDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuDaoException;
import fr.univ.tln.projet.planning.modele.utilisateurs.Utilisateur;

import java.sql.*;
import java.util.Date;

public class UtilisateurDao<F> extends Dao<Utilisateur> {


    /**
     * Le constructeur crée la table si nécessair
     */
    public UtilisateurDao(DB bd) throws DaoException {
        super(bd);
        if (!isTableExiste("utilisateur")){
            try (Connection connection = this.getConnection();
                 Statement statement = connection.createStatement( )) {
                statement.executeUpdate("CREATE TABLE utilisateur " +
                        "(id_user SERIAL PRIMARY KEY," +
                        "email VARCHAR(50) ," +
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
    /**
     * trouver un utilisateur par username
     * @param username
     * @param password
     * @return
     * @throws DaoException
     */
    public   Utilisateur trouver(String username,String password) throws DaoException{

        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM Utilisateur WHERE username=? AND password=?")){
            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet rs= statement.executeQuery( );
            if (!rs.next( ))
                throw new ObjetInconnuDaoException("Utlisateur inexistant : "+username);

            else return  Utilisateur.builder()
                    .idUser(rs.getInt("id_user"))
                    .email(rs.getString("email"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .nom(rs.getString("nom"))
                    .prenom(rs.getString("prenom"))
                    .adresse(rs.getString("adresse"))
                    .mobile(rs.getString("mobile"))
                    .dateNaissance(rs.getDate("dateNaissance"))
                    .genre(rs.getString("genre"))
                    .dateCreation( rs.getDate("dateCreation"))
                    .build();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }
    /**
     * trouver un utilisateur par username
     * @param username
     * @return
     * @throws DaoException
     */
    @Override
    public   Utilisateur trouver(String username) throws DaoException{

        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM Utilisateur WHERE username=?")){
            statement.setString(1,username);
            ResultSet rs= statement.executeQuery( );
            if (!rs.next( ))
                throw new ObjetInconnuDaoException("Utlisateur inexistant : "+username);
            else return  Utilisateur.builder()
                     .idUser(rs.getInt("id_user"))
                    .email(rs.getString("email"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .nom(rs.getString("nom"))
                    .prenom(rs.getString("prenom"))
                    .adresse(rs.getString("adresse"))
                    .mobile(rs.getString("mobile"))
                    .dateNaissance(rs.getDate("dateNaissance"))
                    .genre(rs.getString("genre"))
                    .dateCreation( rs.getDate("dateCreation"))
                    .build();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }
    /**
     * trouver un utilisateur par username
     * @param id_user
     * @return
     * @throws DaoException
     */

    public   Utilisateur trouver(int id_user) throws DaoException{

        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM Utilisateur WHERE id_user=?")){
            statement.setInt(1,id_user);
            ResultSet rs= statement.executeQuery( );
            if (!rs.next( ))
                throw new ObjetInconnuDaoException("Utilisateur inexistante : "+id_user);
            else
                    return Utilisateur.builder()
                    .idUser(rs.getInt("id_user"))
                    .email(rs.getString("email"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .nom(rs.getString("nom"))
                    .prenom(rs.getString("prenom"))
                    .adresse(rs.getString("adresse"))
                    .mobile(rs.getString("mobile"))
                    .dateNaissance(rs.getDate("dateNaissance"))
                    .genre(rs.getString("genre"))
                    .dateCreation( rs.getDate("dateCreation"))
                    .build();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }

    /**
     * Création d'un Utilisateur en base de données
     * @param username
     * @param nom
     * @param prenom
     * @return id_user du nouveau utilisateur ajouté
     * @throws DaoException
     */
    public Utilisateur creer(String email, String username, String password, String nom, String prenom, String adresse, String mobile, Date dateNaissance, String genre)throws DaoException{

        if (isExisteDansLaBase(username))
            throw new ObjectExistDaoException("Utilisateur déjà existant: "+username);
        else {

            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO utilisateur (email,username,password,nom,prenom,adresse,mobile,dateNaissance,genre,dateCreation) VALUES (?,?,?,?,?,?,?,?,?,?)" , Statement.RETURN_GENERATED_KEYS)){
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
                ResultSet rs = statement.getGeneratedKeys();

                if (rs.next()) {
                    return trouver(rs.getInt(1));
                }else throw new InsertDaoException("insert exception");
            }
            catch (SQLException exp) {throw new DaoException(exp);}

        }
    }



    /**
     * Mettre à jour un  Utilisateur dans la base
     * @param utilisateur
     * @throws DaoException
     */
    public void mettreAJour(Utilisateur utilisateur) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE utilisateur SET nom=? ,prenom=? WHERE username=?")){
            statement.setString(1,utilisateur.getNom());
            statement.setString(2, utilisateur.getPrenom());
            statement.setString(3, utilisateur.getUsername());
            statement.executeUpdate();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }

    /**
     * suppression d'un Utilisateur en base
     * @param  username
     * @throws DaoException
     */
    public void supprimer(String username) throws DaoException{
        Utilisateur utilisateur=trouver(username);
       // EtudiantDao dao = new EtudiantDao(this.getDb());
      //  dao.supprimer(utilisateur.getId_user());
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM utilisateur WHERE username=?")) {
            statement.setString(1, username);
            statement.executeUpdate();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }
    /**
     * suppression d'un Utilisateur en base
     * @param utilisateur
     * @throws DaoException
     */
    public void supprimer(Utilisateur utilisateur) throws DaoException {

        supprimer(utilisateur.getUsername());
    }
}
