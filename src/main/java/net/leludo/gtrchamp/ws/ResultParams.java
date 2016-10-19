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
     * @return the raceId
     */
    public Integer getRaceId() {
        return raceId;
    }
    /**
     * @param raceId the raceId to set
     */
    public void setRaceId(final Integer raceId) {
        this.raceId = raceId;
    }
    /**
     * @return the driverId
     */
    public Integer getDriverId() {
        return driverId;
    }
    /**
     * @param driverId the driverId to set
     */
    public void setDriverId(final Integer driverId) {
        this.driverId = driverId;
    }
    /**
     * @return the raceNumber
     */
    protected int getRaceNumber() {
        return raceNumber;
    }
    /**
     * @param raceNumber the raceNumber to set
     */
    protected void setRaceNumber(int raceNumber) {
        this.raceNumber = raceNumber;
    }
    /**
     * @return the startingPosition
     */
    public int getStartingPosition() {
        return startingPosition;
    }
    /**
     * @param startingPosition the startingPosition to set
     */
    public void setStartingPosition(final int startingPosition) {
        this.startingPosition = startingPosition;
    }
    /**
     * @return the arrivalPosition
     */
    public int getArrivalPosition() {
        return arrivalPosition;
    }
    /**
     * @param arrivalPosition the arrivalPosition to set
     */
    public void setArrivalPosition(final int arrivalPosition) {
        this.arrivalPosition = arrivalPosition;
    }
    /**
     * @return the scoringSystem
     */
    protected String getScoringSystem() {
        return scoringSystem;
    }
    /**
     * @param scoringSystem the scoringSystem to set
     */
    protected void setScoringSystem(String scoringSystem) {
        this.scoringSystem = scoringSystem;
    }

}
