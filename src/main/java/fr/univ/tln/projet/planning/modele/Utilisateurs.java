package fr.univ.tln.projet.planning.modele;

import fr.univ.tln.projet.planning.exception.dao.DaoException;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@SuperBuilder
@Getter
@ToString
public abstract class Utilisateurs {

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
