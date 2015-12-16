package net.leludo.gtrchamp.ws;

public class WsReturn {

	private int code;
	private String message;

	public WsReturn() {
		this.code = 0;
		this.message = "";
	}

	public WsReturn(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

}
