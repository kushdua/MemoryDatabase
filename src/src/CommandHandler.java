package src;

/**
 * Does the basic commands handling for set, get , unset etc.
 * @author kushdua
 * Date September 19th 2015
 */
import java.util.HashMap;

public class CommandHandler implements Commands{
	//HashMap to keep the name value pair
	private HashMap<String, String> stateMap = new HashMap<>();
	
	//HashMap to keep the count of each value entered
	private HashMap<String, Integer> frequencyMap = new HashMap<>();
	
	@Override
	public void set(String name, String value) {
		//check if name exists in the stateMap
		if(stateMap.containsKey(name)) {
			String stateVal = stateMap.get(name); //grab the old value
			//adjust the frequency map to decrease the counter for old value
			if(frequencyMap.containsKey(stateVal)) {
				int tempDecrease = frequencyMap.get(stateVal);
				tempDecrease--;
				frequencyMap.put(stateVal, tempDecrease);
			}
			stateMap.put(name, value);
		//if name doesn't exist in the stateMap
		} else {
			stateMap.put(name, value);
		}
		
		//adjust the frequency map
		if(frequencyMap.containsKey(value)) {
			//increase the counter for new value
			int tempIncrease = frequencyMap.get(value);
			tempIncrease++;
			frequencyMap.put(value, tempIncrease);
		} else {
			frequencyMap.put(value, 1);
		}
	}

	@Override
	public String get(String name) {
		if(stateMap.containsKey(name)) {
			return stateMap.get(name);
		} else {
			return null;
		}
	}

	@Override
	public boolean unset(String name) {
		if(stateMap.containsKey(name)) {
			//adjust the frequency map as well
			String val = stateMap.get(name);
			stateMap.remove(name);
			int x = frequencyMap.get(val);
			x--;
			frequencyMap.put(val, x);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int numEqualValue(String value) {
		if(frequencyMap.containsKey(value)) {
			return frequencyMap.get(value);
		} else {
			return 0;
		}
	}

	@Override
	public void end() {
		System.out.println("End of the program");
	}

}
