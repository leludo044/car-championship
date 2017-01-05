package net.leludo.gtrchamp.ws;

/**
 * Wrapper for competitor request parameters.
 */
public class ResultParams {
	private Integer raceId;
	private Integer driverId;
	private int raceNumber;
	private int startingPosition;
	private int arrivalPosition;
	private String scoringSystem;

	/**
	 * Return the race id parameter of the competitor
	 * 
	 * @return The race id parameter of the competitor
	 */
	public Integer getRaceId() {
		return raceId;
	}

	/**
	 * Set the race id parameter of the competitor
	 * 
	 * @param raceId
	 *            the raceId to set
	 */
	public void setRaceId(final Integer raceId) {
		this.raceId = raceId;
	}

	/**
	 * Return the driver id parameter of the competitor
	 * 
	 * @return The driver id parameter of the competitor
	 */
	public Integer getDriverId() {
		return driverId;
	}

	/**
	 * Set the driver id parameter of the competitor
	 * 
	 * @param driverId
	 *            the driverId to set
	 */
	public void setDriverId(final Integer driverId) {
		this.driverId = driverId;
	}

	/**
	 * Return the race number parameter of the competitor
	 * 
	 * @return The race number parameter of the competitor
	 */
	protected int getRaceNumber() {
		return raceNumber;
	}

	/**
	 * Set the race number parameter of the competitor
	 * 
	 * @param raceNumber
	 *            the raceNumber to set
	 */
	protected void setRaceNumber(int raceNumber) {
		this.raceNumber = raceNumber;
	}

	/**
	 * Return the starting position parameter of the competitor
	 * 
	 * @return The starting position parameter of the competitor
	 */
	public int getStartingPosition() {
		return startingPosition;
	}

	/**
	 * Set the starting position parameter of the competitor
	 * 
	 * @param startingPosition
	 *            the startingPosition to set
	 */
	public void setStartingPosition(final int startingPosition) {
		this.startingPosition = startingPosition;
	}

	/**
	 * Return the arrival position parameter of the competitor
	 * 
	 * @return The arrival position parameter of the competitor
	 */
	public int getArrivalPosition() {
		return arrivalPosition;
	}

	/**
	 * Set the arrival position parameter of the competitor
	 * 
	 * @param arrivalPosition
	 *            the arrivalPosition to set
	 */
	public void setArrivalPosition(final int arrivalPosition) {
		this.arrivalPosition = arrivalPosition;
	}

	/**
	 * Return the scoring system parameter of the competitor
	 * 
	 * @return The scoring system parameter of the competitor
	 */
	protected String getScoringSystem() {
		return scoringSystem;
	}

	/**
	 * Set the scoring system parameter of the competitor
	 * 
	 * @param scoringSystem
	 *            the scoringSystem to set
	 */
	protected void setScoringSystem(String scoringSystem) {
		this.scoringSystem = scoringSystem;
	}

}
