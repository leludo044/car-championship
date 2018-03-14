package net.leludo.gtrchamp.jersey;

import java.net.URISyntaxException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import net.leludo.connector.DbConnector;
import net.leludo.gtrchamp.dao.DataManager;

/**
 * Listener pour prendre la main au moment ou l'application est prête à rendre
 * le service
 */
@Component
public class AppListener implements ApplicationListener<ApplicationReadyEvent> {

	private static Logger LOGGER = LoggerFactory.getLogger(AppListener.class);

    /** Environment variable name holding connection URL to SGBD. */
    private static final String DATABASE_URL = "CLEARDB_DATABASE_URL";

    /** The unique instance of entity manager factory. */
    private EntityManagerFactory emf;

	/**
	 * Environnement préchargé avec les données du fichier application.properties
	 */
	@Autowired
	Environment env;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		LOGGER.debug("ApplicationReadyEvent...");

        try {
            String uri = System.getenv(DATABASE_URL);
            if (uri != null) {
                LOGGER.info(DATABASE_URL + " variable found.");

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

            } else {
                LOGGER.error(DATABASE_URL + " variable not found ! SGBD connection impossible.");
            }

        } catch (URISyntaxException e) {
            LOGGER.error(DATABASE_URL + " variable malformed ! SGBD connection impossible.");
        }

	}
}
