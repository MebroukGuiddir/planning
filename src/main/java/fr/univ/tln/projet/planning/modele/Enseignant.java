package fr.univ.tln.projet.planning.modele;

import fr.univ.tln.projet.planning.dao.EnseignantDao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Date;
@Setter
@SuperBuilder
@Getter
@ToString
public class Enseignant extends Utilisateurs {

private static EnseignantDao dao;
    public static void setDao(EnseignantDao dao) {
        Enseignant.dao=dao;
    }

    public void setPrenom(String prenom) throws DaoException {
        super.setPrenom(prenom);
        dao.mettreAJour(this);
    }
    public void setNom(String nom)throws  DaoException{
        super.setNom(nom);
        dao.mettreAJour(this);
    }
    public void setUsername(String username) throws DaoException {
        super.setUsername(username);
        dao.mettreAJour(this);
    }

    public void setPassword(String password) throws DaoException  {
        super.setPassword(password);
        dao.mettreAJour(this);
    }

    public void setEmail(String email) throws DaoException {
        super.setEmail(email);
        dao.mettreAJour(this);
    }

    public void setDateNaissance(Date dateNaissance) throws DaoException {
       super.setDateNaissance(dateNaissance);
        dao.mettreAJour(this);
    }
    public void setAdresse(String adresse) throws DaoException {
        this.adresse = adresse;
        dao.mettreAJour(this);
    }

    public void setMobile(String mobile) throws DaoException{
        this.mobile = mobile;
        dao.mettreAJour(this);
    }

    public void setGenre(String genre) throws DaoException {
        this.genre = genre;
        dao.mettreAJour(this);
    }


    public String toString() {
        return "Enseignant [login=" + username + ", prenom=" + prenom + ", nom=" + nom + "]";
    }

}
