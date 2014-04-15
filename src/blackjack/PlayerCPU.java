package blackjack;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * This class defines casino-player, i.e. a player controlled by CPU. It
 * implements a simple "stand on 17" rule.
 * 
 * @author Janusz Slawek
 * 
 */
public class PlayerCPU extends Player {
	/**
	 * Field MAX_HIT. (value is 17)
	 */
	private static final int MAX_HIT = 17;

	/**
	 * Constructor for BJCpuPlayer.
	 * 
	 */
	public PlayerCPU(int initialAccount, int minBet) {
		super(initialAccount, minBet);
		hands.add(new Hand(this, 1, true));
	}

	/**
	 * Method play.
	 * 
	 */
	@Override
	public List<Hand> play(IDealer dealer, IBanker banker) {
		List<Hand> hand = new LinkedList<Hand>();
		while (getOptimalValueInHand() < MAX_HIT) {
			dealer.dealOneCard(this, Face.UP);
			System.out.println("Dealer hits. Dealer's hand is now: "
					+ getHand());
		}
		hand.add(getHand());
		return hand;
	}

	/**
	 * Method decideIfQuit.
	 * 
	 */
	@Override
	public boolean decideIfQuit() {
		return getAccountBalance() < getMinimalBet();
	}

	/**
	 * Method nextHand returns null, CPU-player will not have "next hand", it
	 * only has the first hand.
	 * 
	 * @return always null
	 * 
	 */
	@Override
	public Hand nextHand() {
		return null;
	}

	/**
	 * Method returnHand.
	 * 
	 */
	@Override
	public Collection<ICard> returnAllCards() {
		return getHand().clear();
	}
}
