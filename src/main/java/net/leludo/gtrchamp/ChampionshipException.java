package net.leludo.gtrchamp;

import org.springframework.http.HttpStatus;

/**
 * General exception for championship problems.
 */
@SuppressWarnings("serial")
public class ChampionshipException extends Exception {

    /** The status (HTTP return code) of the exception. */
    private HttpStatus status;

    /**
     * Default constructor. No message, no status code
     *
     * @deprecated Use {@link #ChampionshipException(String)} or
     *             {@link #ChampionshipException(HttpStatus, String)}
     */
    @Deprecated
    public ChampionshipException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message
     *            Exception message
     */
    public ChampionshipException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param status
     *            Exception status (HTTP return code)
     * @param message
     *            Exception message
     */
    public ChampionshipException(final HttpStatus status, final String message) {
        super(message);
        this.status = status;
    }

    /**
     * Return the status (HTTP return code) of the exception.
     *
     * @return the status (HTTP return code) of the exception
     */
    public HttpStatus status() {
        return status;
    }

}
