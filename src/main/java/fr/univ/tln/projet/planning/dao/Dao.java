package fr.univ.tln.projet.planning.dao;

import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.exception.dao.ObjetInconnuDaoException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * class qui contient des méthodes générales
 * @param <E>
 * @since 1.0
 * @author MEBROUK GUIDDIR
 */
public abstract class Dao<E>{
    private DB bd;
    @Getter
    protected static Logger logger ;
    public Dao(DB bd) {
        this.bd=bd;
        logger= Logger.getLogger(Dao.class.getName());
    }
    protected DB getDb()   {
        return this.bd;
    }
    protected Connection getConnection() throws SQLException {
        return this.bd.getConnection();
    }

    /**
     * teste si une table existe dans la base de données
     * @param nomTable
     * @return vrai si la table existe, faux sinon
     * @throws DaoException si une erreur apparait au cours de ce test
     */
    public boolean isTableExiste(String nomTable) throws DaoException{
        try (Connection connection = bd.getConnection();
             Statement statement = connection.createStatement()) {
            try {
                statement.executeQuery("SELECT * from "+nomTable);
                return true;
            }
            catch (SQLException exp) {return false;}
        }
        catch (SQLException exp) {throw new DaoException(exp);}
    }
    public abstract E trouver(String id) throws DaoException;

    /** teste si l'objet d'identifiant donné existe en base
     * @param identifiant l'identifiant
     * @return true si l'objet existe en base, faux sinon
     * @throws DaoException
     */
    public boolean isExisteDansLaBase(String identifiant) throws DaoException{
        try {
            trouver(identifiant);
            return true;
        }
        catch (ObjetInconnuDaoException exp) {
            return false;
        }
    }
}