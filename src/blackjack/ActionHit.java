package blackjack;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Janusz Slawek
 */
public class ActionHit implements IAction {
	/**
	 * Field NOTBUSTED. (value is 21)
	 */
	private final static int NOTBUSTED = 21;
	/**
	 * Field dealer.
	 */
	private final IDealer dealer;
	/**
	 * Field hand.
	 */
	private final Hand hand;

	/**
	 * Constructor for BJActionHit.
	 * 
	 */
	public ActionHit(IDealer dealer, Hand hand) {
		this.dealer = dealer;
		this.hand = hand;
	}

	/**
	 * This method implements HIT.
	 * 
	 * After HIT, Player can HIT again.
	 * 
	 * After HIT, Player cannot SPLIT.
	 * 
	 * After HIT, Player cannot DOUBLE.
	 * 
	 * After HIT, Player cannot SURRENDER.
	 * 
	 * @return Collection<IHand> This method returns a hand if it's not busted.
	 */
	@Override
	public Collection<Hand> execute() {
		List<Hand> result = new LinkedList<Hand>();
		if (hand.canHit()) {
			dealer.dealOneCard(hand, Face.UP);
		}
		if (hand.getValue() < NOTBUSTED) {
			result.add(hand);
		}
		hand.allowHit();
		hand.revokeSplit();
		hand.revokeDouble();
		hand.revokeSurrender();
		return result;
	}
}
