package fr.univ.tln.projet.planning.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * une classe pour obtenir facilement des connexions avec un fichier de configuration
 * @since 1.0
 * @author mebrouk guiddir
 */
public class DB {
    private Properties properties = new Properties();
    public DB(String fileName) throws IOException, ClassNotFoundException {
        try (InputStream input = DB.class.getResourceAsStream("/"+fileName))
        {
            properties.load(input);
        }
        Class.forName(properties.getProperty("driver"));
    }
    /** rend une connection à la base de données
     *
     */
    public Connection getConnection( ) throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("login"),
                properties.getProperty("password"));
    }
}