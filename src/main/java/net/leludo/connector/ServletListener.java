package net.leludo.connector;

import java.net.URISyntaxException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.leludo.gtrchamp.dao.DataManager;

/**
 * Servlet listener providing JPA initialization.
 */
@WebListener
public class ServletListener implements ServletContextListener {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(ServletListener.class);

    /** Environment variable name holding connection URL to SGBD. */
    private static final String DATABASE_URL = "CLEARDB_DATABASE_URL";

    /** The unique instance of entity manager factory. */
    private EntityManagerFactory emf;

    @Override
    public void contextDestroyed(final ServletContextEvent arg0) {
        emf.close();
    }

    @Override
    public void contextInitialized(final ServletContextEvent arg0) {
        try {
            String uri = System.getenv(DATABASE_URL);
            if (uri != null) {
                LOG.info(DATABASE_URL + " variable found.");

                DbConnector connector = new DbConnector(uri);

                Properties properties = new Properties();
                properties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
                properties.put("hibernate.connection.url", connector.getUrl());
                properties.put("hibernate.connection.username", connector.getUsername());
                properties.put("hibernate.connection.password", connector.getPassword());
                properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
                properties.put("hibernate.show_sql", "false");
                properties.put("hibernate.format_sql", "false");
                properties.put("hibernate.cache.provider_class",
                        "org.hibernate.cache.NoCacheProvider");

                emf = Persistence.createEntityManagerFactory("gtrchamp", properties);

                DataManager.getInstance().setEntityManagerfactory(emf);

                arg0.getServletContext().setAttribute(EntityManagerFactory.class.getName(), emf);
            } else {
                LOG.error(DATABASE_URL + " variable not found ! SGBD connection impossible.");
            }

        } catch (URISyntaxException e) {
            LOG.error(DATABASE_URL + " variable malformed ! SGBD connection impossible.");
        }
    }

}
