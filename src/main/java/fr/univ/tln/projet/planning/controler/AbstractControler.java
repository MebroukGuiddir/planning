package fr.univ.tln.projet.planning.controler;

import fr.univ.tln.projet.planning.modele.AdminModele;

import fr.univ.tln.projet.planning.modele.infrastructure.Batiment;
import fr.univ.tln.projet.planning.modele.infrastructure.Salle;
import fr.univ.tln.projet.planning.modele.utilisateurs.Utilisateur;
import org.json.simple.JSONObject;

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
            //TODO set return object
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
}
