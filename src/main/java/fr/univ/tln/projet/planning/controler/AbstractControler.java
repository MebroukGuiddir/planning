package fr.univ.tln.projet.planning.controler;

import fr.univ.tln.projet.planning.modele.AdminModele;

import java.util.logging.Logger;

public abstract class AbstractControler {
    protected AdminModele adminModele;
    private static Logger logger = Logger.getLogger(AdminModele.class.getName());
    public AbstractControler(AdminModele adminModele){
    this.adminModele=adminModele;

    }
    abstract void control();
    public boolean controlerAddUser(String nom,String prenom,String email,String password,String username,String birthday,String genre,String adresse,String mobile,String status){
      logger.info(status);
      switch (status){
          case "Etudiant":adminModele.addEtudiant(nom,prenom,email, password,username, birthday,genre,adresse,mobile);break;
          case "Enseignant":adminModele.addEnseignant(nom,prenom,email, password,username, birthday,genre,adresse,mobile);break;
          case "Responsable":adminModele.addResponsable(nom,prenom,email, password,username, birthday,genre,adresse,mobile);break;
          case "Admin":adminModele.addAdmin(nom,prenom,email, password,username, birthday,genre,adresse,mobile);break;
          default:return false;
      }
      return true;
    }

    public boolean controlerLogin(String username ,String Password){

         return adminModele.UserLogin(username ,Password);

    }
}
