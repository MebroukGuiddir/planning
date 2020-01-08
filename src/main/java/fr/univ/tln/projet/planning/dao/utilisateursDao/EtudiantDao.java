package fr.univ.tln.projet.planning.dao.utilisateursDao;

import fr.univ.tln.projet.planning.dao.DB;
import fr.univ.tln.projet.planning.dao.etudesDao.GroupeDao;
import fr.univ.tln.projet.planning.dao.etudesDao.PromotionDao;
import fr.univ.tln.projet.planning.dao.etudesDao.SectionDao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.InsertDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuDaoException;
import fr.univ.tln.projet.planning.modele.etudes.Promotion;
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
                        "id_user INTEGER , FOREIGN KEY (id_user) REFERENCES  utilisateur (id_user) ON DELETE CASCADE," +
                        "id_promotion INTEGER NULL , FOREIGN KEY (id_promotion) REFERENCES  promotion (id_promotion) ON DELETE CASCADE,"+
                        "id_section INTEGER NULL, FOREIGN KEY (id_section) REFERENCES  section (id_section) ON DELETE CASCADE,"+
                        "id_groupe INTEGER NULL, FOREIGN KEY (id_groupe) REFERENCES  groupe (id_groupe) ON DELETE CASCADE)"

                );

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
                         "INSERT INTO etudiant (id_user,id_promotion,id_section,id_groupe) VALUES (?,NULL,NULL,NULL)",Statement.RETURN_GENERATED_KEYS )){
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
                                .idUser(rs.getInt("id_user"))
                                .idEtudiant(rs.getInt("id_etudiant"))
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

            else {
                Etudiant etudiant= Etudiant.builder()
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
                if(rs.getInt("id_promotion")!= 0)
                {PromotionDao promotionDao = new PromotionDao(this.getDb());
                    etudiant.setPromotion(promotionDao.trouver(rs.getInt("id_promotion")));
                }
                if(rs.getInt("id_section")!= 0)
                {SectionDao sectionDao=new SectionDao(this.getDb());
                    etudiant.setSection(sectionDao.trouver(rs.getInt("id_section")));
                }
                if(rs.getInt("id_groupe")!= 0)
                { GroupeDao groupeDao=new GroupeDao(this.getDb());
                    etudiant.setGroupe(groupeDao.trouver(rs.getInt("id_groupe")));
                }


                return etudiant;
            }
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }

    public   Etudiant trouver(int id_user ) throws DaoException{
        try (Connection connection = this.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM etudiant e, utilisateur u WHERE e.id_user=?")){
             statement.setInt(1, id_user);
            ResultSet rs= statement.executeQuery( );

            if (!rs.next( ))
             throw new ObjetInconnuDaoException("Etudiant inexistante id_user: "+id_user);

            else {
               Etudiant etudiant= Etudiant.builder()
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
                       if(rs.getInt("id_promotion")!= 0)
                       {PromotionDao promotionDao = new PromotionDao(this.getDb());
                       etudiant.setPromotion(promotionDao.trouver(rs.getInt("id_promotion")));
                       }
                    if(rs.getInt("id_section")!= 0)
                    {SectionDao sectionDao=new SectionDao(this.getDb());
                        etudiant.setSection(sectionDao.trouver(rs.getInt("id_section")));
                    }
                    if(rs.getInt("id_groupe")!= 0)
                    { GroupeDao groupeDao=new GroupeDao(this.getDb());
                        etudiant.setGroupe(groupeDao.trouver(rs.getInt("id_groupe")));
                    }


               return etudiant;
            }


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
     * Mettre à jour
     * @param etudiant
     * @param idFormation
     * @throws DaoException
     */
    public void mettreAJour(Etudiant etudiant,int idFormation,int idSection,int idGroupe) throws DaoException{
        if(idFormation!=-1){
            PromotionDao promotionDao = new PromotionDao(this.getDb());
            int idPromotion=promotionDao.selectionner(idFormation).get(0).getIdPromotion();
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE etudiant SET id_promotion=?  WHERE id_etudiant=?")){
            statement.setInt(1,idPromotion);
            statement.setInt(2, etudiant.getIdEtudiant());

            statement.executeUpdate();
        }
        catch (SQLException exp) {throw new DaoException(exp);}}
        if(idSection!=-1){
            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "UPDATE etudiant SET id_section=? WHERE id_etudiant=?")){
                statement.setInt(1, idSection);
                statement.setInt(2, etudiant.getIdEtudiant());

                statement.executeUpdate();
            }
            catch (SQLException exp) {throw new DaoException(exp);}}
        if(idGroupe!=-1){
            try (Connection connection = this.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "UPDATE etudiant SET id_groupe=?  WHERE id_etudiant=?")){
                statement.setInt(1, idGroupe);
                statement.setInt(2, etudiant.getIdEtudiant());

                statement.executeUpdate();
            }
            catch (SQLException exp) {throw new DaoException(exp);}
        }
    }


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
