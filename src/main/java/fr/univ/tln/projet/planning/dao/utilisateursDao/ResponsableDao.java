package fr.univ.tln.projet.planning.dao.utilisateursDao;

import fr.univ.tln.projet.planning.dao.DB;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.InsertDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuDaoException;
import fr.univ.tln.projet.planning.modele.utilisateurs.Responsable;
import fr.univ.tln.projet.planning.modele.utilisateurs.Utilisateur;
import java.sql.*;
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
                            "(id_responsable  SERIAL  PRIMARY KEY," +
                            "id_user INTEGER , FOREIGN KEY (id_user) REFERENCES  utilisateur (id_user) ON DELETE CASCADE)");

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
                         "INSERT INTO responsable (id_user) VALUES (?)",Statement.RETURN_GENERATED_KEYS )){
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
                         connection.prepareStatement("SELECT * FROM responsable r, utilisateur u WHERE r.id_user=u.id_user")){

                ResultSet rs= statement.executeQuery( );

                if (!rs.next( ))
                    throw new ObjetInconnuDaoException("Responsable inexistante id_user: "+id_user);

                else return Responsable.builder()
                        .idResponsable(rs.getInt("id_responsable"))
                        .idUser(rs.getInt("id_user"))
                        .build();

            }
            catch (SQLException exp) {throw new DaoException(exp);}
        }





    }


