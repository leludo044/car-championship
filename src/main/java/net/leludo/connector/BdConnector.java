package net.leludo.connector;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BdConnector {
    private static final Logger LOG = LoggerFactory.getLogger(ServletListener.class);

    private String driverClass;
    private String url;
    private String username;
    private String password;

    public BdConnector() {
    }

    public BdConnector(final URI dbUri) {

        username = dbUri.getUserInfo().split(":")[0];
        password = dbUri.getUserInfo().split(":")[1];

        url = "jdbc:" + dbUri.getScheme() + "://" + dbUri.getHost()
                + (dbUri.getPort() == -1 ? "" : ":" + dbUri.getPort()) + dbUri.getPath() + "?"
                + dbUri.getQuery();

        LOG.debug(dbUri.toString());
        LOG.debug(dbUri.getUserInfo());
        LOG.debug(url);

    }

    /**
     * @return the driverClass
     */
    public String getDriverClass() {
        return driverClass;
    }

    /**
     * @param driverClass
     *            the driverClass to set
     */
    public void setDriverClass(final String driverClass) {
        this.driverClass = driverClass;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }
}
