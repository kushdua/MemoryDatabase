package src;

/**
 * Basic commands Interface
 * @author kushdua
 * Date September 19th 2015
 */

public interface Commands {
	
	/**
	 * Set the variable name to the value value
	 * @param name
	 * @param value
	 */
	public void set(String name, String value);
	
	/**
	 * Grab the value of the name to lookup
	 * @param name
	 * @return value if found else null
	 */
	public String get(String name);
	
	/**
	 * delete the value from the database
	 * @param name
	 * @return true if it exist, false if it does not
	 */
	public boolean unset(String name);
	
	/**
	 * Return the number of variables that are currently set to value. If no variables equal that value, return 0.
	 * @param value
	 * @return
	 */
	public int numEqualValue(String value);
	
	/**
	 * Just a command to end the program if running
	 * not used in this code base
	 */
	public void end();
	
}
