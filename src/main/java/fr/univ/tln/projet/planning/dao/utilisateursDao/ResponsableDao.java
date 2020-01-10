package fr.univ.tln.projet.planning.dao.utilisateursDao;

import fr.univ.tln.projet.planning.dao.DB;
import fr.univ.tln.projet.planning.dao.etudesDao.FormationDao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.InsertDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuDaoException;
import fr.univ.tln.projet.planning.modele.utilisateurs.Responsable;
import fr.univ.tln.projet.planning.modele.utilisateurs.Utilisateur;
import java.sql.*;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResponsableDao extends UtilisateurDao <Responsable>{


        /**
         * Le constructeur crée la table si nécessair
         */
        public ResponsableDao(DB bd) throws DaoException {
            super(bd);
            if (! isTableExiste("responsable")){
                try (Connection connection = this.getConnection();
                     Statement statement = connection.createStatement( )) {
                    statement.executeUpdate("CREATE TABLE responsable " +
                            "(id_responsable   SERIAL  PRIMARY KEY," +
                            "id_user INTEGER , FOREIGN KEY (id_user) REFERENCES  utilisateur (id_user) ON DELETE CASCADE,"+
                            "id_formation INTEGER NULL , FOREIGN KEY (id_formation) REFERENCES  formation (id_formation) ON DELETE CASCADE)"

                    );

                }
                catch (SQLException exp) {throw new DaoException(exp);}
            }
        }

        /**
         * Création d'un Responsable en base de données
         * @param email
         * @param username
         * @param password
         * @param nom
         * @param prenom
         * @param adresse
         * @param dateNaissance
         * @param mobile
         * @param genre
         * @return une instance
         * @throws InsertDaoException si erreur d'insertion
         */
        public Responsable creer(String email, String username, String password, String nom, String prenom, String adresse, String mobile, Date dateNaissance, String genre)throws DaoException{
            UtilisateurDao dao = new UtilisateurDao(this.getDb());

            Utilisateur utilisateur= dao.creer(email,username,password,nom,prenom,adresse,mobile,dateNaissance,genre);

            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO responsable (id_user,id_formation) VALUES (?,null)",Statement.RETURN_GENERATED_KEYS )){
                statement.setInt(1, utilisateur.getIdUser());
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    return trouver(rs.getInt("id_user"));
                }else throw new InsertDaoException("insert exception");
            }
            catch (SQLException exp) {throw new DaoException(exp);}

        }
        public List<Utilisateur> selectionner(String motif) throws DaoException
        { List<Utilisateur> listResponsable=new ArrayList();
            motif=motif.replace("!", "!!")
                    .replace("%", "!%")
                    .replace("_", "!_")
                    .replace("[", "![");
            try (Connection connection = this.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement("SELECT * FROM responsable r JOIN utilisateur u ON r.id_user=u.id_user WHERE ( nom LIKE ?   OR  prenom LIKE ?  ) ")){
                statement.setString(1, "%" + motif + "%");
                statement.setString(2, "%" + motif + "%");
                ResultSet rs= statement.executeQuery( );

                while (rs.next()) {
                    listResponsable.add(
                            Responsable.builder()
                                    .idUser(rs.getInt("id_user"))
                                    .idResponsable(rs.getInt("id_responsable"))
                                    .nom(rs.getString("nom"))
                                    .prenom(rs.getString("prenom"))
                                    .email(rs.getString("email"))
                                    .username(rs.getString("username"))
                                    .dateNaissance(rs.getDate("dateNaissance"))
                                    .adresse(rs.getString("adresse"))
                                    .build()
                    );

                }
                return listResponsable;

            }
            catch (SQLException exp) {throw new DaoException(exp);}

        }


        public   Responsable trouver(int id_user ) throws DaoException{
            try (Connection connection = this.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement("SELECT * FROM responsable r, utilisateur u WHERE r.id_user=? and r.id_user=u.id_user")){
                statement.setInt(1, id_user);
                ResultSet rs= statement.executeQuery( );

                if (!rs.next( ))
                    throw new ObjetInconnuDaoException("Responsable inexistante id_user: "+id_user);

                else{ Responsable responsable= Responsable.builder()
                        .idUser(rs.getInt("id_user"))
                        .idResponsable(rs.getInt("id_responsable"))
                        .nom(rs.getString("nom"))
                        .prenom(rs.getString("prenom"))
                        .email(rs.getString("email"))
                        .username(rs.getString("username"))
                        .dateNaissance(rs.getDate("dateNaissance"))
                        .adresse(rs.getString("adresse"))
                        .build();
                    if(rs.getInt("id_formation")!= 0)
                    {
                        FormationDao formationDao = new FormationDao(this.getDb());
                        responsable.setFormation(formationDao.trouver(rs.getInt("id_formation")));
                    }
                return responsable;
                }
            }
            catch (SQLException exp) {throw new DaoException(exp);}
        }

    public void mettreAJour(Responsable responsable,int idFormation) throws DaoException {
        if (idFormation != -1) {
            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "UPDATE responsable SET id_formation=?  WHERE id_responsable=?")) {
                statement.setInt(1, idFormation);
                statement.setInt(2, responsable.getIdResponsable());

                statement.executeUpdate();
            } catch (SQLException exp) {
                throw new DaoException(exp);
            }
        }
    }
    public  Responsable trouver(String username,String password ) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM responsable r, utilisateur u WHERE u.username=? AND u.password=? AND r.id_user=u.id_user")){
            statement.setString(1, username);
            statement.setString(2,MD5( password));
            ResultSet rs= statement.executeQuery( );

            if (!rs.next( ))
                throw new ObjetInconnuDaoException("Responsable inexistante id_user: "+username);

            else return  Responsable.builder()
                    .idResponsable(rs.getInt("id_responsable"))
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

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    }


