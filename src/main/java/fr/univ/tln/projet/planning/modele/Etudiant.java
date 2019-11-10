package fr.univ.tln.projet.planning.modele;

import fr.univ.tln.projet.planning.dao.EtudiantDao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import lombok.Getter;
import lombok.experimental.SuperBuilder;




@SuperBuilder
@Getter
public class Etudiant extends Utilisateurs {
    private static EtudiantDao dao;
    public static void setDao(EtudiantDao dao) {
        Etudiant.dao=dao;
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

    public void setDateNaissance(String dateNaissance) throws DaoException {
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
        return "Etudiant [login=" + username + ", prenom=" + prenom + ", nom=" + nom + "]";
    }

}
