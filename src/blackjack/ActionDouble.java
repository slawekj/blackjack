package blackjack;

import java.util.Collection;

/**
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
	 * Field player.
	 */
	private final PlayerHuman player;
	/**
	 * Field hand.
	 */
	private final Hand hand;

	/**
	 * Constructor for ActionDouble.
	 * 
	 */
	public ActionDouble(IDealer dealer, IBanker banker, PlayerHuman player,
			Hand hand) {
		this.dealer = dealer;
		this.banker = banker;
		this.player = player;
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
		if (hand.canDouble()) {
			banker.placeBet(player, hand, hand.getBet());
			dealer.dealOneCard(hand, Face.UP);
		}
		hand.revokeHit();
		hand.revokeSplit();
		hand.revokeDouble();
		hand.revokeSurrender();
		return null;
	}

}
