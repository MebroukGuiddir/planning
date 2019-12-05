package fr.univ.tln.projet.planning.modele;

import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.modele.infrastructure.Batiment;
import fr.univ.tln.projet.planning.modele.infrastructure.Salle;
import fr.univ.tln.projet.planning.modele.utilisateurs.Utilisateur;
import org.json.simple.JSONObject;

import java.util.Date;
import java.util.List;

public interface IAdmin {

    JSONObject addEtudiant(String nom, String prenom, String email, String password, String username, Date birthday, String genre, String adresse, String mobile) throws DaoException;
    JSONObject  addEnseignant(String nom,String prenom,String email,String password,String username,Date birthday,String genre,String adresse,String mobile);
    JSONObject addResponsable(String nom,String prenom,String email,String password,String username,Date birthday,String genre,String adresse,String mobile);
    JSONObject addAdmin(String nom, String prenom, String email, String password, String username, Date birthday, String genre, String adresse, String mobile);

    JSONObject   etudiantLogin(String username,String password);
    JSONObject   adminLogin(String username,String password);

    //   boolean modifierEnseignant(String nom,String prenom,String email,String password,String username,String birthday,String genre,String adresse,String mobile);
    // boolean modifierEtudiant(String nom,String prenom,String email,String password,String username,String birthday,String genre,String adresse,String mobile);

    List<Utilisateur>  selectEtudiants(String motif);
    List<Utilisateur>  selectAdmins(String motif);
    List<Utilisateur>  selectResponsables(String motif);
    List<Utilisateur>  selectEnseignants(String motif);

    JSONObject deleteUser(String username);
    JSONObject addBatiment(String identifiant);
    JSONObject deleteBatiment(String identifiant);
    JSONObject addSalle(String identifiant,String batiment);
    List<Batiment>  selectBatiments();
    List<Salle> selectSalles(String batiment);
}
