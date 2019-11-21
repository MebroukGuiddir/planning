package fr.univ.tln.projet.planning.dao;


import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.InsertDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuDaoException;
import fr.univ.tln.projet.planning.modele.Admin;
import fr.univ.tln.projet.planning.modele.Etudiant;
import fr.univ.tln.projet.planning.modele.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminDao extends UtilisateurDao <Admin> {

    /**
     * Le constructeur crée la table si nécessair
     *
     *
     *
     */
    public AdminDao(DB bd) throws DaoException {
        super(bd);
        if (! isTableExiste("etudiant")){
            try (Connection connection = this.getConnection();
                 Statement statement = connection.createStatement( )) {
                statement.executeUpdate("CREATE TABLE admin " +
                        "(id_admin  SERIAL  PRIMARY KEY," +
                        "id_user INTEGER , FOREIGN KEY (id_user) REFERENCES  utilisateur (id_user) ON DELETE CASCADE)");

            }
            catch (SQLException exp) {throw new DaoException(exp);}
        }
    }

    /**
     * Création d'un Admin en base de données
     * @param email

     * @return
     * @throws DaoException
     */
    public Admin creer(String email, String username, String password, String nom, String prenom, String adresse, String mobile, Date dateNaissance, String genre)throws DaoException{
        UtilisateurDao dao = new UtilisateurDao(this.getDb());

        Utilisateur utilisateur= dao.creer(email,username,password,nom,prenom,adresse,mobile,dateNaissance,genre);

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO admin (id_user) VALUES (?)",Statement.RETURN_GENERATED_KEYS )){
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
    { List<Utilisateur> listAdmin=new ArrayList();
        motif=motif.replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");
        try (Connection connection = this.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM admin a FULL JOIN utilisateur u ON a.id_user=u.id_user WHERE ( nom LIKE ?   OR  prenom LIKE ?  ) ")){
            statement.setString(1, "%" + motif + "%");
            statement.setString(2, "%" + motif + "%");
            ResultSet rs= statement.executeQuery( );

            while (rs.next()) {
                listAdmin.add(
                        Admin.builder()
                                .nom(rs.getString("nom"))
                                .prenom(rs.getString("prenom"))
                                .email(rs.getString("email"))
                                .username(rs.getString("username"))
                                .dateNaissance(rs.getDate("dateNaissance"))
                                .adresse(rs.getString("adresse"))
                                .build()
                );

            }
            return listAdmin;

        }
        catch (SQLException exp) {throw new DaoException(exp);}

    }
    public   Admin trouver(String username,String password ) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM admin a, utilisateur u WHERE u.username=? AND u.password=? AND a.id_user=u.id_user")){
            statement.setString(1, username);
            statement.setString(1, password);
            ResultSet rs= statement.executeQuery( );

            if (!rs.next( ))
                throw new ObjetInconnuDaoException("Etudiant inexistante id_user: "+username);

            else return Admin.builder()
                    .idAdmin(rs.getInt("id_admin"))
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

    public   Admin trouver(int id_user ) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM admin a, utilisateur u WHERE a.id_user=u.id_user")){

            ResultSet rs= statement.executeQuery( );

            if (!rs.next( ))
                throw new ObjetInconnuDaoException("Admin inexistante id_user: "+id_user);

            else return Admin.builder()
                    .idAdmin(rs.getInt("id_admin"))
                    .idUser(rs.getInt("id_user"))
                    .build();

        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }


    /**
     * suppression d'uu compte admin en base
     * @param id_user
     * @throws DaoException
     */
    public void supprimer(int id_user) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM admin WHERE id_user=?")) {
            statement.setInt(1, id_user);
            statement.executeUpdate();
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }

}
