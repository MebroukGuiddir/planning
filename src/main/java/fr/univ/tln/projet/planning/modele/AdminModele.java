package fr.univ.tln.projet.planning.modele;

import fr.univ.tln.projet.planning.controler.Changement;
import fr.univ.tln.projet.planning.dao.DB;
import fr.univ.tln.projet.planning.dao.EtudiantDao;
import fr.univ.tln.projet.planning.dao.UtilisateurDao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjectExistDaoException;
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
    public JSONObject addEtudiant(String nom,String prenom,String email,String password,String username,Date birthday,String genre,String adresse,String mobile) throws DaoException {
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
    public boolean deleteEtudiant(String email) {
        return false;
    }


    @Override
    public JSONObject addEnseignant(String nom, String prenom, String email, String password, String username, Date birthday, String genre, String adresse, String mobile) {
     //   enseignants.add(Enseignant.builder().nom(nom).prenom(prenom).dateNaissance(birthday).email(email).password(password).username(username).dateCreation(LocalDateTime.now()).build());

        JSONObject message = new JSONObject();
        return  message;
    }

    @Override
    public boolean deleteEnseignant( String email) {


       return true;
    }



    @Override
    public JSONObject addResponsable(String nom, String prenom, String email, String password, String username, Date birthday, String genre, String adresse, String mobile) {
      //  responsables.add(Responsable.builder().nom(nom).prenom(prenom).dateNaissance(birthday).email(email).password(password).username(username).dateCreation(LocalDateTime.now()).build());



        JSONObject message = new JSONObject();
        return  message;
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
    public boolean UserLogin(String username, String password) {
       for(Etudiant etudiant:etudiants)
           if(etudiant.getUsername()==username && etudiant.getPassword()==password){
               logger.info("etudiant login");
               notifyObserver(etudiant, Changement.builder().type(Changement.Type.LOGIN).section(Changement.Section.ETUDIANT).build());
           }
        for(Enseignant enseignant:enseignants)
        if(enseignant.getUsername()==username && enseignant.getPassword()==password){
            logger.info("enseignant login");
            notifyObserver(enseignant,Changement.builder().type(Changement.Type.LOGIN).section(Changement.Section.ENSEIGANT).build());
        }
        for(Admin admin:admins)
        if(admin.getUsername().equals(username) && admin.getPassword().equals(password)){
            logger.info("Admin login");
            notifyObserver(admin,Changement.builder().type(Changement.Type.LOGIN).section(Changement.Section.ADMIN).build());
        }
        logger.info("user not found :username ="+username+" password = "+password);
        logger.info(admins.toString());
        notifyObserver(null,Changement.builder().type(Changement.Type.LOGIN).section(Changement.Section.ADMIN).build());
        return false;
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
