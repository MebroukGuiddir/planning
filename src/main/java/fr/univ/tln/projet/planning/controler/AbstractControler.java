package fr.univ.tln.projet.planning.controler;

import fr.univ.tln.projet.planning.modele.ModeleClass;

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
    protected ModeleClass modeleClass;
    private static Logger logger = Logger.getLogger(ModeleClass.class.getName());
    public AbstractControler(ModeleClass modeleClass){
    this.modeleClass = modeleClass;

    }
    abstract void control();
    public JSONObject controlerAddUser(String nom, String prenom, String email, String password, String username, Date birthday, String genre, String adresse, String mobile, String status)   {
      logger.info(status);
      switch (status){
          case "Etudiant": return modeleClass.addEtudiant(nom,prenom,email, password,username, birthday,genre,adresse,mobile);
          case "Enseignant":return modeleClass.addEnseignant(nom,prenom,email, password,username, birthday,genre,adresse,mobile);
          case "Responsable":return modeleClass.addResponsable(nom,prenom,email, password,username, birthday,genre,adresse,mobile);
          case "Admin":return modeleClass.addAdmin(nom,prenom,email, password,username, birthday,genre,adresse,mobile);
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
           case "Etudiant": list = modeleClass.selectEtudiants(motif);break;
           case "Admin": list = modeleClass.selectAdmins(motif);break;
           case "Enseignant": list = modeleClass.selectEnseignants(motif);break;
           case "Responsable": list = modeleClass.selectResponsables(motif);break;
           default:list=new ArrayList<>();
       }
        return list;
    }

    public JSONObject  controlerLogin(String username ,String Password,String status){
        switch (status){
            case "Etudiant":return modeleClass.etudiantLogin(username ,Password);
            case "Admin":return  modeleClass.adminLogin(username ,Password);
            case "Enseignant":return  modeleClass.enseignantLogin(username ,Password);
            case "Responsable":return  modeleClass.responsableLogin(username ,Password);
            default:return new JSONObject();
        }


    }

    /**
     * Supprimer un utilisateur
     * @param username
     */
    public JSONObject deleteUser(String username){
        logger.info("Controler/delete User:"+username);
        return modeleClass.deleteUser(username);
    }


    /**
     *
     */
    public JSONObject addBatiment(String identifiant){
        logger.info("Controler/add Batiment:"+identifiant);
        return modeleClass.addBatiment(identifiant);
    }
    /**
     *
     */
    public JSONObject deleteBatiment(String identifiant){
        logger.info("Controler/delete Batiment:"+identifiant);
        return modeleClass.deleteBatiment(identifiant);
    }
    public List<Batiment> selectBatiments(){
         return   modeleClass.selectBatiments();
    }

    public JSONObject  addSalle(String identifiant, String batiment){
        return modeleClass.addSalle(identifiant,batiment);
    }
    public List<Salle> selectSalles(String batiment){
        return   modeleClass.selectSalles(batiment);
    }

    public JSONObject  addDomaine(String intitule){
        return modeleClass.addDomaine(intitule);
    }
    public List<Domaine> selectDomaines(){
        return   modeleClass.selectDomaines();
    }
    public JSONObject  addFormation(String intitule,String niveau,int domaine){
        return modeleClass.addFormation(intitule,niveau,domaine);
    }
    public List<Formation> selectFormations(int domaine){
        return   modeleClass.selectFormations( domaine);
    }
    public JSONObject  addPromotion(int formation){
        return modeleClass.addPromotion(formation);
    }
    public List<Promotion> selectPromotions(int formation){
        return   modeleClass.selectPromotions( formation);
    }
    public JSONObject  addSection(int promotion){
        return modeleClass.addSection(promotion);
    }
    public List<Section> selectSections(int promotion){
        return   modeleClass.selectSections( promotion);
    }
    public JSONObject  addGroupe(int section){
        return modeleClass.addGroupe(section);
    }
    public List<Groupe> selectGroupes(int section){
        return   modeleClass.selectGroupes( section);
    }
    public JSONObject addModule(String identifiant,String libelle,int formation){
        return modeleClass.addModule(identifiant,libelle,formation);
    }
    public  List<Module>  selectModules(int formation){return modeleClass.selectModules(formation); }
    public  List<Module>  selectModules(){return modeleClass.selectModules(); }
    public  List<Cours>  selectCours(int idEnseignant){
        return  modeleClass.selectCours(idEnseignant);
    }
    public JSONObject addCours(int idEnseignant, int idModule){
        return modeleClass.addCours(idEnseignant,idModule);
    }
    public  Utilisateur getUser(int idUser){
        return modeleClass.getUser(idUser);
    }
    public JSONObject setEtudiant(Etudiant etudiant, int idFormation, int idSection, int idGroupe){
        return modeleClass.setEtudiant(etudiant,idFormation,idSection,idGroupe);
    }
    public JSONObject setResponsable(Responsable responsable, int idFormation){
        return modeleClass.setResponsable(responsable,idFormation);
    }

   public  JSONObject addSeance(LocalTime heureDebut, LocalTime heureFin, Date date, int idSalle, int idCours,int idEnseignant){
        return modeleClass.addSeance(heureDebut,heureFin,date,idSalle,idCours,idEnseignant);
   }
    public List<Seance>  selectSeanceEnseignant(int idEnseignant,Date date ,int periode){
       return  modeleClass.selectSeanceEnseignant(idEnseignant, date, periode);
    }

    public JSONObject annulerSeance( int idSeance){
        return modeleClass.annulerSeance(idSeance);
    }
    public JSONObject validerSeance(int idSeance,int status ){
        return modeleClass.validerSeance(idSeance, status);
    }
    public  List<Seance> selectSeancesResponsable(int idUser,Date date ,int periode){
        return modeleClass.selectionnerSeancesResponsable(idUser, date, periode);
    }
    public  List<Seance> selectSeancesEtudiant(int idUser,Date date ,int periode){
         return  modeleClass.selectionnerSeancesEtudiant(idUser, date, periode);
    }
}
