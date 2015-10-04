package src;
/**
 * Interface to support database transactions
 * @author kushdua
 * date September 19, 2015
 */
public interface Transaction {
	
	/**
	 * Open a new transaction block. Transaction blocks can be nested.
	 * BEGIN can be issued inside of an existing block.
	 */
	public void begin();
	
	/**
	 * Undo all of the commands issued in the most recent transaction block, and close the block.
	 * @return true if successful, or return false if no transaction is in progress.
	 */
	public boolean rollBack();
	
	/**
	 * Close all open transaction blocks, permanently applying the changes made in them. 
	 * Print nothing if successful, or print NO TRANSACTION if no transaction is in progress
	 */
	public void commit();
	
}
