package fr.univ.tln.projet.planning.controler;

import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.modele.AdminModele;

import fr.univ.tln.projet.planning.modele.Utilisateur;
import org.json.simple.JSONObject;

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
    public List<Utilisateur> selectEtudiants(String motif){
        List<Utilisateur> list = adminModele.selectEtudiants(motif);
        logger.info("list etudiants :"+list.toString());
        return list;
    }

    public boolean controlerLogin(String username ,String Password){

         return adminModele.UserLogin(username ,Password);

    }

    /**
     * Supprimer un utilisateur
     * @param username
     */
    public JSONObject deleteUser(String username){
        logger.info("Controler/delete User:"+username);
        return adminModele.deleteUser(username);
    }
}
