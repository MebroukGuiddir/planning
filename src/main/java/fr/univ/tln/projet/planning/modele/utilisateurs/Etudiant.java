package fr.univ.tln.projet.planning.modele.utilisateurs;

import fr.univ.tln.projet.planning.dao.utilisateursDao.EtudiantDao;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.modele.etudes.Groupe;
import fr.univ.tln.projet.planning.modele.etudes.Promotion;
import fr.univ.tln.projet.planning.modele.etudes.Section;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@SuperBuilder
@Getter
public class Etudiant extends Utilisateur {
    private static EtudiantDao dao;
    private int idEtudiant;

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    private Promotion promotion;
    private Section section;
    private Groupe groupe;
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
        return "Etudiant [login=" + username + ", prenom=" + prenom + ", nom=" + nom + "]";
    }

}
