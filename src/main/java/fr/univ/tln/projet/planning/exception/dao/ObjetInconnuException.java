package fr.univ.tln.projet.planning.exception.dao;


import fr.univ.tln.projet.planning.exception.dao.DaoException;

public class ObjetInconnuException extends DaoException {
    public ObjetInconnuException(String message){
        super(message);
    }
    public ObjetInconnuException(Throwable cause) {
        super(cause);
    }
}

