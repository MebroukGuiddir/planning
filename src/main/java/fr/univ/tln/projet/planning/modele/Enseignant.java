package fr.univ.tln.projet.planning.modele;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import java.util.Date;


@SuperBuilder
@Getter
public class Enseignant extends Utilisateur {


    public void setPrenom(String prenom) throws DaoException {
        super.setPrenom(prenom);

    }
    public void setNom(String nom)throws  DaoException{
        super.setNom(nom);

    }
    public void setUsername(String username) throws DaoException {
        super.setUsername(username);

    }

    public void setPassword(String password) throws DaoException  {
        super.setPassword(password);

    }

    public void setEmail(String email) throws DaoException {
        super.setEmail(email);

    }

    public void setDateNaissance(Date dateNaissance) throws DaoException {
        super.setDateNaissance(dateNaissance);

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


    public String toString() {
        return "Enseignant [login=" + username + ", prenom=" + prenom + ", nom=" + nom + "]";
    }

}
