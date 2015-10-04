package src;
/**
 * Handler to control old state of variables and perform transaction functions begin , rollback and commit
 * @author kushdua
 * date September 19, 2015
 */
import java.util.Stack;

public class TransactionHandler extends CommandHandler implements Transaction {
	//Temporary stack to hold the old values of variable
	private Stack<TempVariableState> commandStack = null;
	private boolean isTransactionInProgress;

	@Override
	public void begin() {
		//inilialize the command stack
		if(commandStack == null) {
			commandStack = new Stack<TempVariableState>();
		}
		isTransactionInProgress = true;
		commandStack.add(null);
	}

	@Override
	public boolean rollBack() {
		if(commandStack!=null) {
			//keep on popping the old values unless we hit null (i.e BEGIN)
			while(commandStack.peek() != null) {
				TempVariableState temp = commandStack.pop();
				if(temp.oldvalue != null) {
					super.set(temp.name, temp.oldvalue);
				} else {
					super.unset(temp.name);
				}
			}
			commandStack.pop();
			//if stack gets empty transaction is done
			if(commandStack.isEmpty()) {
				commandStack = null;
				isTransactionInProgress = false;
			}
			return true;
		}
		System.out.println("NO TRANSACTION");
		return false;
	}

	@Override
	public void commit() {
		if(commandStack != null) {
			//clear the stack
			commandStack.removeAllElements();
		} else {
			System.out.println("NO TRANSACTION");
		}
		commandStack = null;
		isTransactionInProgress = false;	
	}
	
	@Override
	public void set(String name, String value) {
		//if transaction is in progress grab the old value
		if(isTransactionInProgress) {
			TempVariableState temp = grabOldValue(name);
			commandStack.add(temp);
		}
		super.set(name, value);
	}
	
	
	@Override
	public String get(String name) {
		return super.get(name);
	}
	
	@Override
	public boolean unset(String name) {
		if(isTransactionInProgress) {
			TempVariableState temp = grabOldValue(name);
			if(temp.oldvalue != null) {
				commandStack.add(temp);
			}
		}
		return super.unset(name);
	}
	
	@Override
	public int numEqualValue(String value) {
		return super.numEqualValue(value);
	}
	
	@Override
	public void end() {
		super.end();
	}
	
	/**
	 * Grab the value from the database before replacing it if transaction is in progress
	 * @param name
	 * @return
	 */
	private TempVariableState grabOldValue(String name) {
		TempVariableState temp;
		if(super.get(name) != null) {
			String oldval = super.get(name);
			temp = new TempVariableState(name,oldval);
		} else {
			temp = new TempVariableState(name,null);
		}
		return temp;
	}
	
	/**
	 * Stack element object to save old name value pair during transaction
	 * @author kushdua
	 *
	 */
	private class TempVariableState {
		private String name;
		private String oldvalue;
		
		public TempVariableState(String name, String oldvalue) {
			this.name = name;
			this.oldvalue = oldvalue;
		}
	}

}
