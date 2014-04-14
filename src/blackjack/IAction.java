package blackjack;

import java.util.Collection;

/**
 * This interface defines the sole execute functionality that each HIT, SPLIT,
 * DOUBLE, STAND, or SURRENDER actions should implement.
 * 
 * @author Janusz Slawek
 * 
 */
public interface IAction {
	/**
	 * Executes an action
	 * 
	 * 
	 * @return Returns all the spawned hands, which have to be consequently
	 *         processed. For example SPLIT will always return two hands, HIT
	 *         might return a hand if it is below 21 in value, or null
	 *         otherwise, STAND will always return null.
	 * 
	 * 
	 */
	public Collection<Hand> execute();
}
