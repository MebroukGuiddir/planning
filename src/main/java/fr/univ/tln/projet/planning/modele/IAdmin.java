package fr.univ.tln.projet.planning.modele;

import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.modele.etudes.*;
import fr.univ.tln.projet.planning.modele.etudes.Module;
import fr.univ.tln.projet.planning.modele.infrastructure.Batiment;
import fr.univ.tln.projet.planning.modele.infrastructure.Salle;
import fr.univ.tln.projet.planning.modele.utilisateurs.Etudiant;
import fr.univ.tln.projet.planning.modele.utilisateurs.Responsable;
import fr.univ.tln.projet.planning.modele.utilisateurs.Utilisateur;
import org.json.simple.JSONObject;

import java.text.Format;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface IAdmin {

    JSONObject addEtudiant(String nom, String prenom, String email, String password, String username, Date birthday, String genre, String adresse, String mobile) throws DaoException;
    JSONObject  addEnseignant(String nom,String prenom,String email,String password,String username,Date birthday,String genre,String adresse,String mobile);
    JSONObject addResponsable(String nom,String prenom,String email,String password,String username,Date birthday,String genre,String adresse,String mobile);
    JSONObject addAdmin(String nom, String prenom, String email, String password, String username, Date birthday, String genre, String adresse, String mobile);

    JSONObject   etudiantLogin(String username,String password);
    JSONObject   adminLogin(String username,String password);
    JSONObject   enseignantLogin(String username,String password);
    JSONObject   responsableLogin(String username,String password);

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

    JSONObject addDomaine(String intitule);
    List<Domaine>  selectDomaines();
    JSONObject addFormation(String intitule,String niveau,int domaine);
    List<Formation>  selectFormations(int domaine);
    JSONObject addModule(String identifiant,String libelle,int formation);
    List<Module>  selectModules(int formation);
    List<Module>  selectModules();
    List<Cours>  selectCours(int idEnseignant);
    JSONObject addCours(int idEnseignant,int idModule);
    JSONObject addPromotion(int formation);
    List<Promotion>  selectPromotions(int formation);
    JSONObject addSection(int promotion);
    List<Section>  selectSections(int promotion);
    JSONObject addGroupe(int section);
    List<Groupe>  selectGroupes(int section);

    Utilisateur getUser(int idUser);
    JSONObject setEtudiant(Etudiant etudiant,int idFormation,int idSection,int idGroupe);
    JSONObject setResponsable(Responsable responsable, int idFormation);
    JSONObject annulerSeance( int idSeance);
    JSONObject validerSeance(int idSeance,int status );
    JSONObject addSeance(LocalTime heureDebut, LocalTime heureFin, Date date, int idSalle, int idCours,int idEnseignant);
    List<Seance>  selectSeanceEnseignant(int idEnseignant,Date date ,int periode);
    List<Seance> selectionnerSeancesResponsable(int idUser,Date date ,int periode);
}
