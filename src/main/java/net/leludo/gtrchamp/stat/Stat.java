package net.leludo.gtrchamp.stat;

/**
 * Statistic for a driver.
 */
public class Stat {

	/** Name of the driver */
	private String name;

	/** Count of a particular statistic */
	private Integer count;

	/**
	 * Return the name of the driver
	 * 
	 * @return the name of the driver
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the driver
	 * 
	 * @param name
	 *            The new name of the driver
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Return the statistic count
	 * 
	 * @return the statistic count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * Set the statistic count
	 * 
	 * @param count
	 *            The new statistic count
	 */
	public void setCount(final Integer count) {
		this.count = count;
	}
}
