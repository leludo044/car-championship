package net.leludo.connector;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class ServletListener implements ServletContextListener {

    /** Logger */
    private static final Logger LOG = LoggerFactory.getLogger(ServletListener.class);

    /** Environment variable name holding connection URL to SGBD */
    private static final String DATABASE_URL = "CLEARDB_DATABASE_URL";

    private EntityManager entityManager;

    @Override
    public void contextDestroyed(final ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void contextInitialized(final ServletContextEvent arg0) {
        try {
            String uri = System.getenv(DATABASE_URL);
            if (uri != null) {
                URI dbUri = new URI(uri);

                LOG.info(DATABASE_URL + " variable found.");

                BdConnector connector = new BdConnector(dbUri);

                Properties properties = new Properties();
                properties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
                properties.put("hibernate.connection.url", connector.getUrl());
                properties.put("hibernate.connection.username", connector.getUsername());
                properties.put("hibernate.connection.password", connector.getPassword());
                properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
                properties.put("hibernate.show_sql", "false");

                EntityManagerFactory emf = Persistence.createEntityManagerFactory("gtrchamp",
                        properties);
                entityManager = emf.createEntityManager();

                arg0.getServletContext().setAttribute(EntityManagerFactory.class.getName(), emf);
            } else {
                LOG.error(
                        DATABASE_URL + " variable not found ! SGBD connection impossible.");
            }

        } catch (URISyntaxException e) {
            LOG.error(DATABASE_URL + " variable malformed ! SGBD connection impossible.");
        }
    }

}
