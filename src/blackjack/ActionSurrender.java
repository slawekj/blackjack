package blackjack;

import java.util.Collection;

/**
 * This class implements action SURRENDER. In this action Player decides to
 * withdraw half of the bet. The other half automatically goes to Casino.
 * 
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
