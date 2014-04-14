package blackjack;

import java.util.Collection;

/**
 * @author Janusz Slawek
 */
public class ActionSurrender implements IAction {
	/**
	 * Field hand.
	 */
	private final Hand hand;

	/**
	 * Constructor for ActionSurrender.
	 * 
	 * @param hand
	 *            BJHand
	 */
	public ActionSurrender(Hand hand) {
		this.hand = hand;
	}

	/**
	 * This method implements SURRENDER.
	 * 
	 * SURRENDER terminates the hand.
	 * 
	 */
	@Override
	public Collection<Hand> execute() {
		hand.setSurrendered(true);
		return null;
	}
}
