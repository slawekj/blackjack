package blackjack;

import java.util.Collection;

/**
 * @author Janusz Slawek
 */
public class ActionStand implements IAction {
	/**
	 * This method implements STAND.
	 * 
	 * STAND terminates the hand.
	 * 
	 */
	@Override
	public Collection<Hand> execute() {
		return null;
	}
}
