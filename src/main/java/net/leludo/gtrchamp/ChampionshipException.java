package net.leludo.gtrchamp;

import javax.ws.rs.core.Response.Status;

/**
 * General exception for championship problems.
 *
 * @author Ludovic THOMAS
 *
 */
@SuppressWarnings("serial")
public class ChampionshipException extends Exception {

    private Status status ;

    public ChampionshipException() {
        super();
    }

    public ChampionshipException(final String message) {
        super(message);
    }

    public ChampionshipException(final Status status, final String message) {
        super(message);
        this.status = status;
    }

    /**
     * @return the status
     */
    public Status status() {
        return status;
    }

}
