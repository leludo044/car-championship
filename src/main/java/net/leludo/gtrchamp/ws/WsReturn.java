package net.leludo.gtrchamp.ws;

/**
 * Wrapper for web service response.
 */
public class WsReturn {

    private int code;
    private String message;

    /**
     * Constructor with default response code to 0 and empty response message.
     */
    public WsReturn() {
        this.code = 0;
        this.message = "";
    }

    /**
     * Constructor.
     *
     * @param code
     *            Response code
     * @param message
     *            Response message
     */
    public WsReturn(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Return the response code.
     *
     * @return The response code
     */
    public int getCode() {
        return code;
    }

    /**
     * Return the response message.
     *
     * @return The response message
     */
    public String getMessage() {
        return message;
    }
}
