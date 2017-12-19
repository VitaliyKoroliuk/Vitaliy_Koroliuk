package main.java.ua.nure.koroliuk.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public abstract class DAOFactory {
    protected static final String USER_DAO = "main.java.ua.nure.chub.db.UserDAO";
    protected static Properties properties;
    private static String DAO_FACTORY = "dao.factory";
    private static DAOFactory instance;

    static {
        InputStream inputStream;
        properties = new Properties();
        try {
            inputStream = new FileInputStream("src/main/resources/settings.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    protected DAOFactory() {

    }

    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            Class factoryClass = null;
            try {
                factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
                instance = (DAOFactory) factoryClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public static void init(Properties prop) {
        properties = prop;
        instance = null;
    }

    protected ConnectionFactory getConnectionFactory() {
        return new ConnectionFactoryImpl(properties);
    }

    public abstract UserDAO getUserDAO();
}
