package fr.univ.tln.projet.planning.modele;

import fr.univ.tln.projet.planning.exception.dao.DaoException;

import java.util.Date;

public interface IAdmin {

     boolean addEtudiant(String nom, String prenom, String email, String password, String username, Date birthday, String genre, String adresse, String mobile) throws DaoException;
     boolean deleteEtudiant(String email);
    // boolean modifierEtudiant(String nom,String prenom,String email,String password,String username,String birthday,String genre,String adresse,String mobile);

     boolean  addEnseignant(String nom,String prenom,String email,String password,String username,Date birthday,String genre,String adresse,String mobile);
     boolean deleteEnseignant(String email);
  //   boolean modifierEnseignant(String nom,String prenom,String email,String password,String username,String birthday,String genre,String adresse,String mobile);

     boolean addResponsable(String nom,String prenom,String email,String password,String username,Date birthday,String genre,String adresse,String mobile);

     boolean listEtudiant();
     boolean listEnseignant();

    boolean addAdmin(String nom, String prenom, String email, String password, String username, Date birthday, String genre, String adresse, String mobile);
    boolean UserLogin(String username,String password);
}
