package net.leludo.connector;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Database connection informations holder.
 */
public class DbConnector {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(DbConnector.class);

    /** Connection URL. */
    private String url;

    /** Database user name. */
    private String username;

    /** Database password. */
    private String password;

    /**
     * Constructor. The connection URL is computed here
     *
     * @param uri
     *            The database connection URI
     * @throws URISyntaxException
     *             Exception thrown when the URI is malformed
     * @see DbConnector#getUrl()
     */
    public DbConnector(final String uri) throws URISyntaxException {

        URI dbUri = new URI(uri);
        username = dbUri.getUserInfo().split(":")[0];
        password = dbUri.getUserInfo().split(":")[1];

        StringBuilder sb = new StringBuilder();

        sb.append("jdbc:").append(dbUri.getScheme()).append("://").append(dbUri.getHost());
        if (dbUri.getPort() != -1) {
            sb.append(":").append(dbUri.getPort());
        }
        sb.append(dbUri.getPath()).append("?").append(dbUri.getQuery());
        this.url = sb.toString();

        if (LOG.isDebugEnabled()) {
            LOG.debug(dbUri.toString());
            LOG.debug(dbUri.getUserInfo());
            LOG.debug(url);
        }

    }

    /**
     * Return the connection URL.
     *
     * @return the connection URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Return the user name.
     *
     * @return the user name
     */
    public String getUsername() {
        return username;
    }

    /**
     * Return the pasword.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}
