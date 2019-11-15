package fr.univ.tln.projet.planning.exception.dao;

public class ObjectExistDaoException extends DaoException {
    public ObjectExistDaoException(String message) {
        super(message);
    }

    public ObjectExistDaoException(Throwable cause) {
        super(cause);
    }
}
