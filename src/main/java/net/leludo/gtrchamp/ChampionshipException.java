package net.leludo.gtrchamp;

import javax.ws.rs.core.Response.Status;

/**
 * General exception for championship problems.
 */
@SuppressWarnings("serial")
public class ChampionshipException extends Exception {

    /** The status (HTTP return code) of the exception. */
    private Status status;

    /**
     * Default constructor. No message, no status code
     *
     * @deprecated Use {@link #ChampionshipException(String)} or
     *             {@link #ChampionshipException(Status, String)}
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
    public ChampionshipException(final Status status, final String message) {
        super(message);
        this.status = status;
    }

    /**
     * Return the status (HTTP return code) of the exception.
     *
     * @return the status (HTTP return code) of the exception
     */
    public Status status() {
        return status;
    }

}
