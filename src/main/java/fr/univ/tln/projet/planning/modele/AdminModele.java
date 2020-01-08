package fr.univ.tln.projet.planning.modele;

import fr.univ.tln.projet.planning.controler.Changement;
import fr.univ.tln.projet.planning.dao.*;
import fr.univ.tln.projet.planning.dao.etudesDao.*;
import fr.univ.tln.projet.planning.dao.infrastractureDao.BatimentDao;
import fr.univ.tln.projet.planning.dao.infrastractureDao.SalleDao;
import fr.univ.tln.projet.planning.dao.utilisateursDao.*;
import fr.univ.tln.projet.planning.dao.utilisateursDao.EnseignantDao;
import fr.univ.tln.projet.planning.exception.dao.*;
import fr.univ.tln.projet.planning.modele.etudes.*;
import fr.univ.tln.projet.planning.modele.etudes.Module;
import fr.univ.tln.projet.planning.modele.infrastructure.Batiment;
import fr.univ.tln.projet.planning.modele.infrastructure.Salle;
import fr.univ.tln.projet.planning.modele.utilisateurs.*;
import fr.univ.tln.projet.planning.modele.observer.Observer;
import fr.univ.tln.projet.planning.modele.observer.Observable;
import lombok.Getter;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
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


    public AdminModele() throws IOException, ClassNotFoundException {
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
            exception.printStackTrace();
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
    public List<Utilisateur> selectAdmins(String motif) {
        try {
            AdminDao dao = new AdminDao(bd);
            Admin.setDao(dao);
            return dao.selectionner(motif);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Utilisateur> selectResponsables(String motif) {
        try {
            ResponsableDao dao = new ResponsableDao(bd);
            Responsable.setDao(dao);
            return dao.selectionner(motif);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Utilisateur> selectEnseignants(String motif) {
        try {
            EnseignantDao dao = new EnseignantDao(bd);
            Enseignant.setDao(dao);
            return dao.selectionner(motif);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public JSONObject addEnseignant(String nom, String prenom, String email, String password, String username, Date birthday, String genre, String adresse, String mobile) {

        JSONObject message = new JSONObject();
        try {
            EnseignantDao dao = new EnseignantDao(bd);
            Enseignant.setDao(dao);
            Enseignant enseignant = dao.creer(email, username, password, nom, prenom, adresse, mobile, birthday, genre);

            logger.info("new 'Responsable' was added :" + enseignant);


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
            exception.printStackTrace();
            return message;
        }


    }




    @Override
    public JSONObject addResponsable(String nom, String prenom, String email, String password, String username, Date birthday, String genre, String adresse, String mobile) {

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
    public JSONObject addAdmin(String nom, String prenom, String email, String password, String username, Date birthday, String genre, String adresse, String mobile) {
        JSONObject message = new JSONObject();
        try {
            AdminDao dao = new AdminDao(bd);
            Admin.setDao(dao);
            Admin admin = dao.creer(email, username, password, nom, prenom, adresse, mobile, birthday, genre);
            logger.info("new 'Admin' was added :" + admin);
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
            message.put("id",etudiant.getIdUser());
            message.put("user","Etudiant");
            message.put("code", 200 );
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
            AdminDao dao = new AdminDao(bd);
            Admin.setDao(dao);

            Admin admin= dao.trouver(username,password);
            message.put("status","Authentification");
            message.put("message", "Login success");
            message.put("nom",admin.getNom());
            message.put("prenom",admin.getPrenom());
            message.put("id",admin.getIdUser());
            message.put("user","Admin");
            message.put("code", 200 );

            return message;
        }catch ( ObjetInconnuDaoException exe){
            logger.info("user not found :username ="+username);
            message.put("status","Authentification");
            message.put("message", "Invalid token gives");
            message.put("code", 401 );
            return message;
        }catch (DaoException exe){
            logger.info("Login/Internal Server Error");
            message.put("status","Internal Server Error ");
            message.put("message", "Internal Server Error ");
            message.put("code", 500 );
            return message;
        }
    }
    @Override
    public JSONObject enseignantLogin(String username, String password) {
        JSONObject message = new JSONObject();
        try {
            EnseignantDao dao = new EnseignantDao(bd);
            Enseignant.setDao(dao);

            Enseignant enseignant= dao.trouver(username,password);
            message.put("status","Authentification");
            message.put("message", "Login success");
            message.put("nom",enseignant.getNom());
            message.put("prenom",enseignant.getPrenom());
            message.put("id",enseignant.getIdUser());
            message.put("user","Enseignant");
            message.put("code", 200 );

            return message;
        }catch ( ObjetInconnuDaoException exe){
            logger.info("user not found :username ="+username);
            message.put("status","Authentification");
            message.put("message", "Invalid token gives");
            message.put("code", 401 );
            return message;
        }catch (DaoException exe){
            logger.info("Login/Internal Server Error");
            message.put("status","Internal Server Error ");
            message.put("message", "Internal Server Error ");
            message.put("code", 500 );
            return message;
        }
    }
    @Override
    public JSONObject responsableLogin(String username, String password) {
        JSONObject message = new JSONObject();
        try {
           ResponsableDao dao = new ResponsableDao(bd);
           Responsable.setDao(dao);

            Responsable responsable= dao.trouver(username,password);
            message.put("status","Authentification");
            message.put("message", "Login success");
            message.put("nom",responsable.getNom());
            message.put("prenom",responsable.getPrenom());
            message.put("id",responsable.getIdUser());
            message.put("user","Responsable");
            message.put("code", 200 );

            return message;
        }catch ( ObjetInconnuDaoException exe){
            logger.info("user not found :username ="+username);
            message.put("status","Authentification");
            message.put("message", "Invalid token gives");
            message.put("code", 401 );
            return message;
        }catch (DaoException exe){
            logger.info("Login/Internal Server Error");
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
    @Override
    public JSONObject addBatiment(String identifiant){
        JSONObject message = new JSONObject();
     logger.info("Batiment add");
        try {
            BatimentDao dao = new BatimentDao(bd);
            Batiment.setDao(dao);
            dao.creer(identifiant);
            message.put("status", "Success");
            message.put("message", "batiment added");
            message.put("code", 200);
            return message;


        }catch (ObjectExistDaoException e){
            message.put("status", "insert error");
            message.put("message", "ce Batiment exist deja");
            message.put("code", 500);
            return message;
        } catch (DaoException e) {
            message.put("status", "Internal server error");
            message.put("message", "Internal server error");
            message.put("code", 500);
            return message;
        }
    }
    @Override
    public JSONObject deleteBatiment(String identifiant){
        JSONObject message = new JSONObject();
        logger.info("Batiment supprimer");
        try {
            BatimentDao dao = new BatimentDao(bd);
            Batiment.setDao(dao);
            dao.supprimer(identifiant);
            message.put("status", "Success");
            message.put("message", "batiment supprimer");
            message.put("code", 200);
            return message;



        } catch (DaoException e) {
            message.put("status", "Internal server error");
            message.put("message", "Internal server error");
            message.put("code", 500);
            return message;
        }
    }

    @Override
    public JSONObject addSalle(String identifiant, String batiment) {
        JSONObject message = new JSONObject();
        logger.info(" add / Salle");
        try {
            SalleDao dao = new SalleDao(bd);
            Salle.setDao(dao);
            dao.creer(identifiant,batiment);
            message.put("status", "Success");
            message.put("message", "salle  ajouté");
            message.put("code", 200);
            return message;


        }catch (ObjectExistDaoException e){
            message.put("status", "insert error");
            message.put("message", "cette salle exist deja");
            message.put("code", 500);
            return message;
        } catch (DaoException e) {
            message.put("status", "Internal server error");
            message.put("message", "Internal server error");
            message.put("code", 500);
            return message;
        }
    }

    @Override
    public List<Batiment> selectBatiments() {
        try {
            BatimentDao dao = new BatimentDao(bd);
            Batiment.setDao(dao);
            return dao.selectionner();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Salle> selectSalles(String batiment) {
        try {
            SalleDao dao = new  SalleDao(bd);
           Salle.setDao(dao);
            return dao.selectionner(batiment);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject addDomaine(String intitule) {
        JSONObject message = new JSONObject();
        logger.info("Model/add Domaine");
        try {
            DomaineDao dao = new DomaineDao(bd);
            Domaine.setDao(dao);
            dao.creer(intitule);
            message.put("status", "Success");
            message.put("message", "nouveau domaine ajouté");
            message.put("code", 200);
            notifyObserver(message,Changement.builder().section( Changement.Section.DOMAINE).type(Changement.Type.ADD).build());
            return message;


        }catch (ObjectExistDaoException e){
            message.put("status", "insert error");
            message.put("message", "ce Domaine existe deja");
            message.put("code", 500);
            return message;
        } catch (DaoException e) {
            message.put("status", "Internal server error");
            message.put("message", "Internal server error");
            message.put("code", 500);
            logger.info(e.toString());
            return message;
        }
    }

    @Override
    public List<Domaine> selectDomaines() {
        try {
            DomaineDao dao = new  DomaineDao(bd);
            Domaine.setDao(dao);
            return dao.selectionner();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject addFormation(String intitule, String niveau, int domaine) {
        JSONObject message = new JSONObject();
        logger.info(" add / formation");
        try {
            FormationDao dao = new FormationDao(bd);
            Formation.setDao(dao);
            dao.creer(intitule,niveau,domaine);
            message.put("status", "Success");
            message.put("message", "Formation ajouté");
            message.put("code", 200);
            notifyObserver(message,Changement.builder().section( Changement.Section.FORMATION).type(Changement.Type.ADD).build());
            return message;


        }catch (ObjectExistDaoException e){
            message.put("status", "insert error");
            message.put("message", "cette Formation existe deja");
            message.put("code", 500);
            return message;
        } catch (DaoException e) {
            message.put("status", "Internal server error");
            message.put("message", "Internal server error");
            message.put("code", 500);
            logger.info(e.toString());
            return message;
        }
    }

    @Override
    public List<Formation> selectFormations(int domaine) {
        try {
            FormationDao dao = new  FormationDao(bd);
            Formation.setDao(dao);
            return dao.selectionner(domaine);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public JSONObject addPromotion(int formation) {
        JSONObject message = new JSONObject();
        logger.info("Model/ add  Module");
        try {
            PromotionDao dao = new PromotionDao(bd);
            Promotion.setDao(dao);
            dao.creer(formation);
            message.put("status", "Success");
            message.put("message", "Module ajouté");
            message.put("code", 200);
            notifyObserver(message,Changement.builder().section( Changement.Section.PROMOTION).type(Changement.Type.ADD).build());
            return message;


        }catch (ObjectExistDaoException e){
            message.put("status", "insert error");
            message.put("message", "La promotion pour l'année universitaire en cours est dejà crée");
            message.put("code", 500);
            e.printStackTrace();
            return message;
        } catch (DaoException e) {
            message.put("status", "Internal server error");
            message.put("message", "Internal server error");
            message.put("code", 500);
            e.printStackTrace();
            return message;
        }
    }

    @Override
    public List<Promotion> selectPromotions(int formation) {
        try {
            PromotionDao dao = new  PromotionDao(bd);
            Promotion.setDao(dao);
            return dao.selectionner(formation);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject addSection(int promotion) {
        JSONObject message = new JSONObject();
        logger.info("Model/ add  Section");
        try {
            SectionDao dao = new SectionDao(bd);
            Section.setDao(dao);
            dao.creer(promotion);
            message.put("status", "Success");
            message.put("message", "Section added");
            message.put("code", 200);
            notifyObserver(message,Changement.builder().section( Changement.Section.SECTION).type(Changement.Type.ADD).build());
            return message;


        }catch (ObjectExistDaoException e){
            message.put("status", "insert error");
            message.put("message", "cette section existe deja");
            message.put("code", 500);
            return message;
        } catch (DaoException e) {
            message.put("status", "Internal server error");
            message.put("message", "Internal server error");
            message.put("code", 500);
            e.printStackTrace();
            return message;
        }


    }

    @Override
    public List<Section> selectSections(int promotion) {
        try {
            SectionDao dao = new SectionDao(bd);
            Section.setDao(dao);
            logger.info("Model/selected");

            return dao.selectionner(promotion);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject addGroupe(int promotion) {
        JSONObject message = new JSONObject();
        logger.info("Model/ add  Groupe");
        try {
            GroupeDao dao = new GroupeDao(bd);
            Groupe.setDao(dao);
            dao.creer(promotion);
            message.put("status", "Success");
            message.put("message", "Groupe ajouté");
            message.put("code", 200);
            notifyObserver(message,Changement.builder().section( Changement.Section.GROUPE).type(Changement.Type.ADD).build());

            return message;


        }catch (ObjectExistDaoException e){
            message.put("status", "insert error");
            message.put("message", "ce Groupe existe deja");
            message.put("code", 500);
            return message;
        } catch (DaoException e) {
            message.put("status", "Internal server error");
            message.put("message", "Internal server error");
            message.put("code", 500);
            e.printStackTrace();
            return message;
        }
    }

    @Override
    public List<Groupe> selectGroupes(int section) {

            try {
                GroupeDao dao = new  GroupeDao(bd);
                Groupe.setDao(dao);
                return dao.selectionner(section);
            } catch (DaoException e) {
                e.printStackTrace();
            }
            return null;
    }
    @Override
    public JSONObject addModule(String identifiant,String libelle,int formation) {
        JSONObject message = new JSONObject();
        logger.info("Model/ add  Module");
        try {
            ModuleDao dao = new ModuleDao(bd);
            Module.setDao(dao);
            dao.creer(identifiant,libelle,formation);
            message.put("status", "Success");
            message.put("message", "Module ajouté");
            message.put("code", 200);
            notifyObserver(message,Changement.builder().section( Changement.Section.MODULE).type(Changement.Type.ADD).build());
            return message;


        }catch (ObjectExistDaoException e){
            message.put("status", "insert error");
            message.put("message", "ce module existe  dejà");
            message.put("code", 500);
            e.printStackTrace();
            return message;
        } catch (DaoException e) {
            message.put("status", "Internal server error");
            message.put("message", "Internal server error");
            message.put("code", 500);
            e.printStackTrace();
            return message;
        }
    }

    @Override
    public List<Module> selectModules(int formation) {
        try {
            ModuleDao dao = new  ModuleDao(bd);
            Module.setDao(dao);
            return dao.selectionner(formation);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Module> selectModules() {
        try {
            ModuleDao dao = new  ModuleDao(bd);
            Module.setDao(dao);
            return dao.selectionner();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Cours>  selectCours(int idEnseignant){
        try {
            CoursDao dao = new  CoursDao(bd);
            Cours.setDao(dao);
            return dao.selectionner(idEnseignant);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject addCours(int idEnseignant, int idModule) {
        JSONObject message = new JSONObject();
        logger.info("Model/ add  Module");
        try {
            CoursDao dao = new CoursDao(bd);
            Cours.setDao(dao);
            dao.creer(idEnseignant,idModule);
            message.put("status", "Success");
            message.put("message", "Affecté");
            message.put("code", 200);
            return message;


        }catch (ObjectExistDaoException e){
            message.put("status", "insert error");
            message.put("message", "dejà Affecté");
            message.put("code", 500);
            e.printStackTrace();
            return message;
        } catch (DaoException e) {
            message.put("status", "Internal server error");
            message.put("message", "Internal server error");
            message.put("code", 500);
            e.printStackTrace();
            return message;
        }
    }

    @Override
    public Utilisateur getUser(int idUser){
        try {
            EtudiantDao dao = new EtudiantDao(bd);
            Etudiant.setDao(dao);
            return dao.trouver(idUser);
        } catch (DaoException e) {

        }
        try {
            AdminDao dao = new AdminDao(bd);
            Admin.setDao(dao);
            return dao.trouver(idUser);
        } catch (DaoException e) {

        }
        try {
            EnseignantDao dao = new EnseignantDao(bd);
            Enseignant.setDao(dao);
            return dao.trouver(idUser);
        } catch (DaoException e) {

        }
        try {
            ResponsableDao dao = new ResponsableDao(bd);
            Responsable.setDao(dao);
            return dao.trouver(idUser);
        } catch (DaoException e) {

        }
        return null;
    }

    @Override
    public JSONObject setEtudiant(Etudiant etudiant, int idFormation, int idSection, int idGroupe) {
        JSONObject message = new JSONObject();
        logger.info("Model/ set Etudiant");
        try {
            EtudiantDao dao = new EtudiantDao(bd);
            Etudiant.setDao(dao);
            dao.mettreAJour(etudiant,idFormation,idSection,idGroupe);
            message.put("status", "Success");
            message.put("message", "Affectation effectué");
            message.put("code", 200);
            return message;

        } catch (DaoException e) {
            message.put("status", "Internal server error");
            message.put("message", "Internal server error");
            message.put("code", 500);
            e.printStackTrace();
            return message;
        }
    }

    @Override
    public JSONObject setResponsable(Responsable responsable, int idFormation) {
        JSONObject message = new JSONObject();
        logger.info("Model/ set Responsable");
        try {
            ResponsableDao dao = new ResponsableDao(bd);
            Responsable.setDao(dao);
            dao.mettreAJour(responsable,idFormation);
            message.put("status", "Success");
            message.put("message", "Affectation effectué");
            message.put("code", 200);
            return message;

        } catch (DaoException e) {
            message.put("status", "Internal server error");
            message.put("message", "Internal server error");
            message.put("code", 500);
            e.printStackTrace();
            return message;
        }
    }


    @Override
    public JSONObject addSeance(LocalTime heureDebut, LocalTime heureFin, Date date, int idSalle, int idCours,int idEnseignant){
        JSONObject message = new JSONObject();
        logger.info("Model/ add seance");
        try {
            SeanceDao dao = new SeanceDao(bd);
            Seance.setDao(dao);
            dao.creer(heureDebut, heureFin, date, idSalle, idCours,idEnseignant);
            message.put("status", "Success");
            message.put("message", "Seance Ajouté,En attente de validation");
            message.put("code", 200);
            return message;

        } catch (EnseignantNonLibreException e) {
            message.put("status", "error");
            message.put("message", "Enseignant non libre sur ce creneau");
            message.put("code", 500);
            e.printStackTrace();
            return message;
        } catch (PromotionNonLibreException e) {
            message.put("status", "error");
            message.put("message", "Promotion non libre sur ce creneau");
            message.put("code", 500);
            e.printStackTrace();
            return message;
        }catch (SalleNonLibreException e) {
            message.put("status", "error");
            message.put("message", "Salle non libre sur ce creneau");
            message.put("code", 500);
            e.printStackTrace();
            return message;
        }
        catch (DaoException e) {
            message.put("status", "Internal server error");
            message.put("message", "Internal server error");
            message.put("code", 500);
            e.printStackTrace();
            return message;
        }
    }
    @Override
    public List<Seance>  selectSeanceEnseignant(int idEnseignant,Date date ,int periode){
        try {
            SeanceDao dao = new  SeanceDao(bd);
            Seance.setDao(dao);
            return dao.selectionnerSeancesEnseignant(idEnseignant,periode,date);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }
    public  List<Seance> selectionnerSeancesResponsable(int idUser,Date date ,int periode){
        try {
            SeanceDao dao = new  SeanceDao(bd);
            Seance.setDao(dao);
            return dao.selectionnerSeancesResponsable(idUser,periode,date);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }
    public  List<Seance> selectionnerSeancesEtudiant(int idUser,Date date ,int periode){
        try {
            SeanceDao dao = new  SeanceDao(bd);
            Seance.setDao(dao);
            return dao.selectionnerSeancesEtudiant(idUser,periode,date);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }
    public JSONObject validerSeance(int idSeance,int status ){
        JSONObject message = new JSONObject();
        logger.info("Model/ valider seance");
        try {
            SeanceDao dao = new SeanceDao(bd);
            Seance.setDao(dao);
            dao.validerSeance(idSeance,status);
            message.put("status", "Success");
            message.put("message", "Changement valider");
            message.put("code", 200);
            return message;

        } catch (DaoException e) {
            message.put("status", "error");
            message.put("message", "Internal server error");
            message.put("code", 500);
            e.printStackTrace();
            return message;
        }
    }
    public JSONObject annulerSeance( int idSeance){
        JSONObject message = new JSONObject();
        logger.info("Model/ add seance");
        try {
            SeanceDao dao = new SeanceDao(bd);
            Seance.setDao(dao);
            dao.annulerSeance(idSeance);
            message.put("status", "Success");
            message.put("message", "Séance Annulé , En Attente de validation");
            message.put("code", 200);
            return message;

        } catch (DaoException e) {
            message.put("status", "error");
            message.put("message", "Internal server error");
            message.put("code", 500);
            e.printStackTrace();
            return message;
        }
    }

    //Implémentation du pattern observer
    public void addObserver(Observer obs) {
        this.listObserver.add(obs);
    }

    public void notifyObserver(Object object, Changement changement) {


        for(Observer obs : listObserver)
            obs.update(object,changement);
    }

    public void removeObserver() {
        listObserver = new ArrayList<Observer>();
    }

}
