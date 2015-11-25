package net.leludo.connector;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connecteur SGBD
 */
public class BdConnector {

	/** Logger */
	private static final Logger LOG = LoggerFactory.getLogger(ServletListener.class);

	/** Driver */
	private String driverClass;

	/** URL de connexion au SGBD */
	private String url;

	/** Login de connexion au SGBD */
	private String username;

	/** Mot de passe de connexion au SGBD */
	private String password;

	/**
	 * Constructeur
	 * 
	 * @param dbUri
	 *            L'URI à partir de laquelle le connecteur va extraire les
	 *            données de connexion
	 */
	public BdConnector(URI dbUri) {

		username = dbUri.getUserInfo().split(":")[0];
		password = dbUri.getUserInfo().split(":")[1];

		url = "jdbc:" + dbUri.getScheme() + "://" + dbUri.getHost()
				+ (dbUri.getPort() == -1 ? "" : ":" + dbUri.getPort()) + dbUri.getPath() + "?" + dbUri.getQuery();

		LOG.debug(dbUri.toString());
		LOG.debug(dbUri.getUserInfo());
		LOG.debug(url);

	}

	/**
	 * Retourne le driver SGBD
	 * 
	 * @return Le driver SGBD
	 */
	public String getDriverClass() {
		return driverClass;
	}

	/**
	 * Retourne l'URL de connexion au SGBD
	 * 
	 * @return L'URL de connexion au SGBD
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Retourne le login de connexion au SGBD
	 * 
	 * @return Le login de connexion au SGBD
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Retourne le mot de passe de connexion au SGBD
	 * 
	 * @return Le mot de passe de connexion au SGBD
	 */
	public String getPassword() {
		return password;
	}
}
