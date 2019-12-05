package fr.univ.tln.projet.planning.dao.utilisateursDao;

import fr.univ.tln.projet.planning.dao.DB;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.InsertDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuDaoException;
import fr.univ.tln.projet.planning.modele.utilisateurs.Etudiant;
import fr.univ.tln.projet.planning.modele.utilisateurs.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EtudiantDao extends UtilisateurDao <Etudiant > {

    /**
     * Le constructeur crée la table si nécessair
     */
    public EtudiantDao(DB bd) throws DaoException{
        super(bd);
        if (! isTableExiste("etudiant")){
            try (Connection connection = this.getConnection();
                 Statement statement = connection.createStatement( )) {
                statement.executeUpdate("CREATE TABLE etudiant " +
                        "(id_etudiant  SERIAL  PRIMARY KEY," +
                        "id_user INTEGER , FOREIGN KEY (id_user) REFERENCES  utilisateur (id_user) ON DELETE CASCADE)");

            }
            catch (SQLException exp) {throw new DaoException(exp);}
        }
    }

    /**
     * Création d'un Etudiant en base de données
     * @param email

     * @return
     * @throws DaoException
     */
    public Etudiant creer(String email, String username, String password, String nom, String prenom, String adresse, String mobile, Date dateNaissance, String genre)throws DaoException{
        UtilisateurDao dao = new UtilisateurDao(this.getDb());

       Utilisateur utilisateur= dao.creer(email,username,password,nom,prenom,adresse,mobile,dateNaissance,genre);

            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO etudiant (id_user) VALUES (?)",Statement.RETURN_GENERATED_KEYS )){
                statement.setInt(1, utilisateur.getIdUser());
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    return trouver(rs.getInt(1));
                }else throw new InsertDaoException("insert exception");
            }
            catch (SQLException exp) {throw new DaoException(exp);}

    }
    public List<Utilisateur> selectionner(String motif) throws DaoException
    { List<Utilisateur> listEtudiant=new ArrayList ();
        motif=motif.replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");
         try (Connection connection = this.getConnection();

              PreparedStatement statement =
                      connection.prepareStatement("SELECT * FROM etudiant e  JOIN utilisateur u ON e.id_user=u.id_user WHERE ( nom LIKE ?   OR  prenom LIKE ?  ) ")){
        statement.setString(1, "%" + motif + "%");
        statement.setString(2, "%" + motif + "%");
        ResultSet rs= statement.executeQuery( );

            while (rs.next()) {
                listEtudiant.add(
                        Etudiant.builder()
                                .nom(rs.getString("nom"))
                                .prenom(rs.getString("prenom"))
                                .email(rs.getString("email"))
                                .username(rs.getString("username"))
                                .dateNaissance(rs.getDate("dateNaissance"))
                                .adresse(rs.getString("adresse"))
                        .build()
                );

            }
         return listEtudiant;

    }
    catch (SQLException exp) {throw new DaoException(exp);}

    }
    public   Etudiant trouver(String username,String password ) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM etudiant e, utilisateur u WHERE u.username=? AND u.password=? AND e.id_user=u.id_user")){
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs= statement.executeQuery( );

            if (!rs.next( ))
                throw new ObjetInconnuDaoException("Etudiant inexistante id_user: "+username);

            else return Etudiant.builder()
                    .idEtudiant(rs.getInt("id_etudiant"))
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

    public   Etudiant trouver(int id_user ) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM etudiant e, utilisateur u WHERE e.id_user=u.id_user")){

            ResultSet rs= statement.executeQuery( );

            if (!rs.next( ))
             throw new ObjetInconnuDaoException("Etudiant inexistante id_user: "+id_user);

            else return Etudiant.builder()
                    .idEtudiant(rs.getInt("id_etudiant"))
                    .idUser(rs.getInt("id_user"))
                    .build();

        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }
    /**
     * Mettre à jour un Etudiant dans la base
     * @param etudiant
     * @throws DaoException
     */
    /*public void mettreAJour(Etudiant etudiant) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE etudiant SET =? ,=? WHERE id_etudiant=?")){
            statement.setString(1,etudiant.getNom());
            statement.setString(2, etudiant.getPrenom());
            statement.setString(3, etudiant.getUsername());
            statement.executeUpdate();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }*/

    /**
     * suppression d'un Etudiant en base
     * @param id_user
     * @throws DaoException
     */
    public void supprimer(int id_user) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM etudiant WHERE id_user=?")) {
            statement.setInt(1, id_user);
            statement.executeUpdate();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }

}
