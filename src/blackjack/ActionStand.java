package blackjack;

import java.util.Collection;

/**
 * This class implements action STAND. By standing, Player terminates current
 * hand, i.e. no more action will be available for that hand.
 * 
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
