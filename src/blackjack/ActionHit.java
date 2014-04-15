package blackjack;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * This class implements action HIT. Player can hit a hand, i.e. ask Dealer to
 * deal one more card to a hand.
 * 
 * @author Janusz Slawek
 */
public class ActionHit implements IAction {
	/**
	 * Field notBusted.
	 */
	private final int notBusted;
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
	public ActionHit(IDealer dealer, Hand hand, int notBusted) {
		this.dealer = dealer;
		this.hand = hand;
		this.notBusted = notBusted;
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
		if (hand.isAllowedHit()) {
			dealer.dealOneCard(hand, Face.UP);
		}
		if (hand.getValue() < notBusted) {
			result.add(hand);
		}
		hand.setAllowedHit(true);
		hand.setAllowedSplit(false);
		hand.setAllowedDouble(false);
		hand.setAllowedSurrender(false);
		return result;
	}
}
