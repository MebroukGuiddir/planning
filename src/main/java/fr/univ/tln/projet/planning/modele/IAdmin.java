package fr.univ.tln.projet.planning.modele;

public interface IAdmin {

     boolean addEtudiant(String nom,String prenom,String email,String password,String username,String birthday,String genre,String adresse,String mobile);
     boolean deleteEtudiant(String nom,String prenom,String email,String password,String username,String birthday,String genre,String adresse,String mobile);
     boolean modifierEtudiant(String nom,String prenom,String email,String password,String username,String birthday,String genre,String adresse,String mobile);

     boolean  addEnseignant(String nom,String prenom,String email,String password,String username,String birthday,String genre,String adresse,String mobile);
     boolean deleteEnseignant(String nom,String prenom,String email,String password,String username,String birthday,String genre,String adresse,String mobile);
     boolean modifierEnseignant(String nom,String prenom,String email,String password,String username,String birthday,String genre,String adresse,String mobile);

     boolean addResponsable(String nom,String prenom,String email,String password,String username,String birthday,String genre,String adresse,String mobile);

     boolean listEtudiant();
     boolean listEnseignant();

    boolean addAdmin(String nom, String prenom, String email, String password, String username, String birthday, String genre, String adresse, String mobile);
}
