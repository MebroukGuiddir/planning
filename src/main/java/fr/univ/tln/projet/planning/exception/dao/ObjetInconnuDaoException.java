package fr.univ.tln.projet.planning.exception.dao;



public class ObjetInconnuDaoException extends DaoException {
    public ObjetInconnuDaoException(String message){
        super(message);
    }
    public ObjetInconnuDaoException(Throwable cause) {
        super(cause);
    }
}

