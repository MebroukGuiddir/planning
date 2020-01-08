package fr.univ.tln.projet.planning.controler;

import fr.univ.tln.projet.planning.modele.AdminModele;

import fr.univ.tln.projet.planning.modele.etudes.*;
import fr.univ.tln.projet.planning.modele.etudes.Module;
import fr.univ.tln.projet.planning.modele.infrastructure.Batiment;
import fr.univ.tln.projet.planning.modele.infrastructure.Salle;
import fr.univ.tln.projet.planning.modele.utilisateurs.Etudiant;
import fr.univ.tln.projet.planning.modele.utilisateurs.Responsable;
import fr.univ.tln.projet.planning.modele.utilisateurs.Utilisateur;
import org.json.simple.JSONObject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractControler {
    protected AdminModele adminModele;
    private static Logger logger = Logger.getLogger(AdminModele.class.getName());
    public AbstractControler(AdminModele adminModele){
    this.adminModele=adminModele;

    }
    abstract void control();
    public JSONObject controlerAddUser(String nom, String prenom, String email, String password, String username, Date birthday, String genre, String adresse, String mobile, String status)   {
      logger.info(status);
      switch (status){
          case "Etudiant": return adminModele.addEtudiant(nom,prenom,email, password,username, birthday,genre,adresse,mobile);
          case "Enseignant":return adminModele.addEnseignant(nom,prenom,email, password,username, birthday,genre,adresse,mobile);
          case "Responsable":return adminModele.addResponsable(nom,prenom,email, password,username, birthday,genre,adresse,mobile);
          case "Admin":return adminModele.addAdmin(nom,prenom,email, password,username, birthday,genre,adresse,mobile);
          default: {JSONObject message=new JSONObject();
                    message.put("status","Internal Server Error ");
                    message.put("message", "Internal Server Error ");
                    message.put("code", 500 );
              return message;
          }

      }

    }
    public List<Utilisateur> selectUsers(String motif,String status){
        List<Utilisateur> list ;
       switch (status){
           case "Etudiant": list = adminModele.selectEtudiants(motif);break;
           case "Admin": list = adminModele.selectAdmins(motif);break;
           case "Enseignant": list = adminModele.selectEnseignants(motif);break;
           case "Responsable": list = adminModele.selectResponsables(motif);break;
           default:list=new ArrayList<>();
       }
        return list;
    }

    public JSONObject  controlerLogin(String username ,String Password,String status){
        switch (status){
            case "Etudiant":return adminModele.etudiantLogin(username ,Password);
            case "Admin":return  adminModele.adminLogin(username ,Password);
            case "Enseignant":return  adminModele.enseignantLogin(username ,Password);
            case "Responsable":return  adminModele.responsableLogin(username ,Password);
            default:return new JSONObject();
        }


    }

    /**
     * Supprimer un utilisateur
     * @param username
     */
    public JSONObject deleteUser(String username){
        logger.info("Controler/delete User:"+username);
        return adminModele.deleteUser(username);
    }


    /**
     *
     */
    public JSONObject addBatiment(String identifiant){
        logger.info("Controler/add Batiment:"+identifiant);
        return adminModele.addBatiment(identifiant);
    }
    /**
     *
     */
    public JSONObject deleteBatiment(String identifiant){
        logger.info("Controler/delete Batiment:"+identifiant);
        return adminModele.deleteBatiment(identifiant);
    }
    public List<Batiment> selectBatiments(){
         return   adminModele.selectBatiments();
    }

    public JSONObject  addSalle(String identifiant, String batiment){
        return adminModele.addSalle(identifiant,batiment);
    }
    public List<Salle> selectSalles(String batiment){
        return   adminModele.selectSalles(batiment);
    }

    public JSONObject  addDomaine(String intitule){
        return adminModele.addDomaine(intitule);
    }
    public List<Domaine> selectDomaines(){
        return   adminModele.selectDomaines();
    }
    public JSONObject  addFormation(String intitule,String niveau,int domaine){
        return adminModele.addFormation(intitule,niveau,domaine);
    }
    public List<Formation> selectFormations(int domaine){
        return   adminModele.selectFormations( domaine);
    }
    public JSONObject  addPromotion(int formation){
        return adminModele.addPromotion(formation);
    }
    public List<Promotion> selectPromotions(int formation){
        return   adminModele.selectPromotions( formation);
    }
    public JSONObject  addSection(int promotion){
        return adminModele.addSection(promotion);
    }
    public List<Section> selectSections(int promotion){
        return   adminModele.selectSections( promotion);
    }
    public JSONObject  addGroupe(int section){
        return adminModele.addGroupe(section);
    }
    public List<Groupe> selectGroupes(int section){
        return   adminModele.selectGroupes( section);
    }
    public JSONObject addModule(String identifiant,String libelle,int formation){
        return adminModele.addModule(identifiant,libelle,formation);
    }
    public  List<Module>  selectModules(int formation){return adminModele.selectModules(formation); }
    public  List<Module>  selectModules(){return adminModele.selectModules(); }
    public  List<Cours>  selectCours(int idEnseignant){
        return  adminModele.selectCours(idEnseignant);
    }
    public JSONObject addCours(int idEnseignant, int idModule){
        return adminModele.addCours(idEnseignant,idModule);
    }
    public  Utilisateur getUser(int idUser){
        return adminModele.getUser(idUser);
    }
    public JSONObject setEtudiant(Etudiant etudiant, int idFormation, int idSection, int idGroupe){
        return adminModele.setEtudiant(etudiant,idFormation,idSection,idGroupe);
    }
    public JSONObject setResponsable(Responsable responsable, int idFormation){
        return adminModele.setResponsable(responsable,idFormation);
    }

   public  JSONObject addSeance(LocalTime heureDebut, LocalTime heureFin, Date date, int idSalle, int idCours,int idEnseignant){
        return adminModele.addSeance(heureDebut,heureFin,date,idSalle,idCours,idEnseignant);
   }
    public List<Seance>  selectSeanceEnseignant(int idEnseignant,Date date ,int periode){
       return  adminModele.selectSeanceEnseignant(idEnseignant, date, periode);
    }

    public JSONObject annulerSeance( int idSeance){
        return adminModele.annulerSeance(idSeance);
    }
    public JSONObject validerSeance(int idSeance,int status ){
        return adminModele.validerSeance(idSeance, status);
    }
    public  List<Seance> selectSeancesResponsable(int idUser,Date date ,int periode){
        return adminModele.selectionnerSeancesResponsable(idUser, date, periode);
    }
}
