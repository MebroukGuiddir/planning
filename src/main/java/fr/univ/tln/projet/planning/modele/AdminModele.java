package fr.univ.tln.projet.planning.modele;

import fr.univ.tln.projet.planning.controler.Changement;
import fr.univ.tln.projet.planning.dao.DB;
import fr.univ.tln.projet.planning.dao.EtudiantDao;
import fr.univ.tln.projet.planning.dao.ResponsableDao;
import fr.univ.tln.projet.planning.dao.UtilisateurDao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjectExistDaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuDaoException;
import fr.univ.tln.projet.planning.modele.observer.Observer;
import fr.univ.tln.projet.planning.modele.observer.Observable;
import lombok.Getter;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Getter
public class AdminModele<A extends IAdmin>  implements IAdmin, Observable {
    private static Logger logger = Logger.getLogger(AdminModele.class.getName());
    private ArrayList<Observer> listObserver = new ArrayList();
    private List<Etudiant> etudiants = new ArrayList();
    private List<Enseignant> enseignants = new ArrayList();
    private List<Responsable> responsables = new ArrayList();
    private List<Admin> admins = new ArrayList();
    DB bd = new DB("Bd.properties");


    public AdminModele() throws IOException, ClassNotFoundException, DaoException {
    }


    /**
     * Ajouter un étudiant.
     *
     *
     * @return
     */
    @Override
    public JSONObject addEtudiant(String nom,String prenom,String email,String password,String username,Date birthday,String genre,String adresse,String mobile)   {
        JSONObject message = new JSONObject();
        try {
            EtudiantDao dao = new EtudiantDao(bd);
            Etudiant.setDao(dao);
            Etudiant etudiant = dao.creer(email, username, password, nom, prenom, adresse, mobile, birthday, genre);
            etudiants.add(etudiant);
            logger.info("new 'Etudiant' was added :" + etudiant);
            notifyObserver(etudiants, Changement.builder().type(Changement.Type.ADD).section(Changement.Section.ETUDIANT).build());

            message.put("status","created");
            message.put("message", "user created");
            message.put("code", 201);
            return message;
        } catch (ObjectExistDaoException exception){
            message.put("status","Conflict");
            message.put("message", "user aleardy  exist");
            message.put("code", 409);
            return message;
        }
        catch (DaoException exception){
            message.put("status","Internal Server Error ");
            message.put("message", "Internal Server Error ");
            message.put("code", 500 );
            return message;
        }
    }

    @Override
    public List <Utilisateur>selectEtudiants(String motif) {
        try {
            EtudiantDao dao = new EtudiantDao(bd);
            Etudiant.setDao(dao);
           return dao.selectionner(motif);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public JSONObject addEnseignant(String nom, String prenom, String email, String password, String username, Date birthday, String genre, String adresse, String mobile) {
     //   enseignants.add(Enseignant.builder().nom(nom).prenom(prenom).dateNaissance(birthday).email(email).password(password).username(username).dateCreation(LocalDateTime.now()).build());

        JSONObject message = new JSONObject();
        return  message;
    }




    @Override
    public JSONObject addResponsable(String nom, String prenom, String email, String password, String username, Date birthday, String genre, String adresse, String mobile) {
      //  responsables.add(Responsable.builder().nom(nom).prenom(prenom).dateNaissance(birthday).email(email).password(password).username(username).dateCreation(LocalDateTime.now()).build());

        JSONObject message = new JSONObject();
        try {
            ResponsableDao dao = new ResponsableDao(bd);
            Responsable.setDao(dao);
            Responsable responsable = dao.creer(email, username, password, nom, prenom, adresse, mobile, birthday, genre);

            logger.info("new 'Responsable' was added :" + responsable);
            //notifyObserver(responsables, Changement.builder().type(Changement.Type.ADD).section(Changement.Section.Responsable).build());

            message.put("status","created");
            message.put("message", "user created");
            message.put("code", 201);
            return message;
        } catch (ObjectExistDaoException exception){
            message.put("status","Conflict");
            message.put("message", "user aleardy  exist");
            message.put("code", 409);
            return message;
        }
        catch (DaoException exception){
            message.put("status","Internal Server Error ");
            message.put("message", "Internal Server Error ");
            message.put("code", 500 );
            return message;
        }



    }

    @Override
    public boolean listEtudiant() {
        return false;
    }

    @Override
    public boolean listEnseignant() {
        return false;
    }

    @Override
    public JSONObject addAdmin(String nom, String prenom, String email, String password, String username, Date birthday, String genre, String adresse, String mobile) {
        //admins.add(Admin.builder().nom(nom).prenom(prenom).dateNaissance(birthday).email(email).password(password).username(username).dateCreation(LocalDateTime.now()).build());



        JSONObject message = new JSONObject();
        return  message;

    }

    @Override
    public JSONObject etudiantLogin(String username, String password) {
        JSONObject message = new JSONObject();
        try {
            EtudiantDao dao = new EtudiantDao(bd);
            Etudiant.setDao(dao);
            Etudiant etudiant= dao.trouver(username,password);
            message.put("status","Authentification");
            message.put("message", "Login success");
            message.put("nom",etudiant.getNom());
            message.put("prenom",etudiant.getPrenom());
            message.put("code", 200 );
            notifyObserver(etudiant,Changement.builder().type(Changement.Type.LOGIN).section(Changement.Section.ADMIN).build());
            return message;
        }catch ( ObjetInconnuDaoException exe){
            logger.info("user not found :username ="+username+" password = "+password);
            message.put("status","Authentification");
            message.put("message", "Invalid token gives");
            message.put("code", 401 );
            return message;
        }catch (DaoException exe){
            message.put("status","Internal Server Error ");
            message.put("message", "Internal Server Error ");
            message.put("code", 500 );
            return message;
        }
    }
    @Override
    public JSONObject adminLogin(String username, String password) {
        JSONObject message = new JSONObject();
        try {
            EtudiantDao dao = new EtudiantDao(bd);
            Etudiant.setDao(dao);
            Etudiant etudiant= dao.trouver(username,password);
            message.put("status","Authentification");
            message.put("message", "Login success");
            message.put("nom",etudiant.getNom());
            message.put("prenom",etudiant.getPrenom());
            message.put("code", 200 );
            notifyObserver(etudiant,Changement.builder().type(Changement.Type.LOGIN).section(Changement.Section.ADMIN).build());
            return message;
        }catch ( ObjetInconnuDaoException exe){
            logger.info("user not found :username ="+username+" password = "+password);
            message.put("status","Authentification");
            message.put("message", "Invalid token gives");
            message.put("code", 401 );
            return message;
        }catch (DaoException exe){
            message.put("status","Internal Server Error ");
            message.put("message", "Internal Server Error ");
            message.put("code", 500 );
            return message;
        }
    }

    @Override
    public JSONObject deleteUser(String username) {
        JSONObject message = new JSONObject();
        logger.info("Modele/delete User:"+username);
        try {
            UtilisateurDao dao = new UtilisateurDao(bd);
            Utilisateur.setDao(dao);
            dao.supprimer(username);
            message.put("status", "Success");
            message.put("message", "user deleted");
            message.put("code", 200);
            return message;
        } catch (DaoException e) {
            message.put("status", "Internal server error");
            message.put("message", "Internal server error");
            message.put("code", 500);
            return message;
        }
    }

        //Implémentation du pattern observer
    public void addObserver(Observer obs) {
        this.listObserver.add(obs);
    }

    public void notifyObserver(Object object,Changement changement) {


        for(Observer obs : listObserver)
            obs.update(object,changement);
    }

    public void removeObserver() {
        listObserver = new ArrayList<Observer>();
    }
}
