package fr.univ.tln.projet.planning.modele;

import fr.univ.tln.projet.planning.exception.dao.DaoException;
import org.json.simple.JSONObject;

import java.util.Date;
import java.util.List;

public interface IAdmin {

    JSONObject addEtudiant(String nom, String prenom, String email, String password, String username, Date birthday, String genre, String adresse, String mobile) throws DaoException;
    List<Utilisateur>  selectEtudiants(String motif);

    // boolean modifierEtudiant(String nom,String prenom,String email,String password,String username,String birthday,String genre,String adresse,String mobile);

    JSONObject  addEnseignant(String nom,String prenom,String email,String password,String username,Date birthday,String genre,String adresse,String mobile);

  //   boolean modifierEnseignant(String nom,String prenom,String email,String password,String username,String birthday,String genre,String adresse,String mobile);

    JSONObject addResponsable(String nom,String prenom,String email,String password,String username,Date birthday,String genre,String adresse,String mobile);

     boolean listEtudiant();
     boolean listEnseignant();

    JSONObject addAdmin(String nom, String prenom, String email, String password, String username, Date birthday, String genre, String adresse, String mobile);
    boolean UserLogin(String username,String password);
    JSONObject deleteUser(String username);
}
