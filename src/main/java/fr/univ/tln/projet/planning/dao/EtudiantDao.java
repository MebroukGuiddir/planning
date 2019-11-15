package fr.univ.tln.projet.planning.dao;

import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.InsertDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuDaoException;
import fr.univ.tln.projet.planning.modele.Etudiant;
import fr.univ.tln.projet.planning.modele.Utilisateur;

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
                        "id_user INTEGER , FOREIGN KEY (id_user) REFERENCES  utilisateur (id_user))");

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
                statement.setInt(1, utilisateur.getId_user());
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
                      connection.prepareStatement("SELECT * FROM etudiant e JOIN utilisateur u ON e.id_user=u.id_user WHERE  ( nom LIKE ? ESCAPE '!' OR  prenom LIKE ? ESCAPE '!') ")){
        statement.setString(1, "%" + motif + "%");
        statement.setString(2, "%" + motif + "%");
        ResultSet rs= statement.executeQuery( );

        if (!rs.next( ))
             return listEtudiant;

            while (rs.next()) {
                listEtudiant.add(
                        Etudiant.builder()
                        .username(rs.getString("username"))
                        .email(rs.getString("email"))
                        .nom(rs.getString("nom"))
                        .prenom(rs.getString("prenom"))
                        .build()
                );

            }
         return listEtudiant;

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
                    .id_etudiant(rs.getInt("id_etudiant"))
                    .id_user(rs.getInt("id_user"))
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
