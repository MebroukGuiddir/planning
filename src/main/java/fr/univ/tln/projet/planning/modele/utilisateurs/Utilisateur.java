package fr.univ.tln.projet.planning.modele.utilisateurs;

import fr.univ.tln.projet.planning.dao.utilisateursDao.UtilisateurDao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@SuperBuilder
@Getter
@ToString
public class Utilisateur {

    protected int idUser;
    protected String nom;
    protected String prenom;
    protected String username;
    protected String password;
    protected String email;
    protected Date dateNaissance;
    protected String adresse;
    protected String mobile;
    protected String genre;
    protected final Date dateCreation  ;
    private static UtilisateurDao dao;

    public static void setDao(UtilisateurDao dao) {
        Utilisateur.dao=dao;
    }
    public void setNom(String nom) throws DaoException {
        this.nom = nom;
    }

    public void setPrenom(String prenom) throws DaoException {
        this.prenom = prenom;
    }

    public void setUsername(String username) throws DaoException {
        this.username = username;
    }

    public void setPassword(String password) throws DaoException  {
        this.password = password;
    }

    public void setEmail(String email) throws DaoException {
        this.email = email;
    }

    public void setDateNaissance(Date dateNaissance) throws DaoException {
        this.dateNaissance = dateNaissance;
    }
    public void setAdresse(String adresse) throws DaoException {
        this.adresse = adresse;
    }

    public void setMobile(String mobile) throws DaoException{
        this.mobile = mobile;
    }

    public void setGenre(String genre) throws DaoException {
        this.genre = genre;
    }


}
