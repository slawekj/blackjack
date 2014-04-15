package blackjack;

import java.util.Collection;

/**
 * This Action implements DOUBLE. Player can double any hand which has not been
 * HIT by Player.
 * 
 * @author Janusz Slawek
 */
public class ActionDouble implements IAction {
	/**
	 * Field dealer.
	 */
	private final IDealer dealer;

	/**
	 * Field banker.
	 */
	private final IBanker banker;

	/**
	 * Field hand.
	 */
	private final Hand hand;

	/**
	 * Constructor for ActionDouble.
	 * 
	 */
	public ActionDouble(IDealer dealer, IBanker banker, Hand hand) {
		this.dealer = dealer;
		this.banker = banker;
		this.hand = hand;
	}

	/**
	 * This method implements DOUBLE. By doubling Player commits to stand after
	 * receiving one card.
	 * 
	 * After DOUBLE, Player cannot HIT.
	 * 
	 * After DOUBLE, Player cannot SPLIT.
	 * 
	 * After DOUBLE, Player cannot DOUBLE.
	 * 
	 * After DOUBLE, Player cannot SURRENDER.
	 * 
	 * 
	 * @return Collection<IHand> This method returns null, which means that it
	 *         terminates this hand.
	 */
	@Override
	public Collection<Hand> execute() {
		if (hand.isAllowedDouble()) {
			banker.placeBet(hand.getOwner(), hand, banker.getBet(hand));
			dealer.dealOneCard(hand, Face.UP);
		}
		hand.setAllowedHit(false);
		hand.setAllowedSplit(false);
		hand.setAllowedDouble(false);
		hand.setAllowedSurrender(false);
		return null;
	}

}
