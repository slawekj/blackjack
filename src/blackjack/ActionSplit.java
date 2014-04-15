package blackjack;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * This Action implements SPLIT. Player can split any cards that have the same
 * value, i.e. Player can split two 7s or a King and Jack.
 * 
 * @author Janusz Slawek
 */
public class ActionSplit implements IAction {
	/**
	 * Field dealer.
	 */
	private final IDealer dealer;

	/**
	 * Field banker.
	 */
	private final IBanker banker;

	/**
	 * Field originalHand.
	 */
	private final Hand originalHand;

	/**
	 * Field duplicate.
	 */
	private final ICard duplicate;

	/**
	 * Constructor for BJActionSplit.
	 * 
	 */
	public ActionSplit(IDealer dealer, IBanker banker, Hand originalHand,
			ICard duplicate) {
		this.dealer = dealer;
		this.banker = banker;
		this.originalHand = originalHand;
		this.duplicate = duplicate;
	}

	/**
	 * 
	 * This method implements SPLIT. Player can split any cards that have the
	 * same value, i.e. Player can split two a King and Jack.
	 * 
	 * After SPLIT, Player can HIT again. There's one exception, if Player
	 * Splits Aces, then Dealer will deal only one card to each deck. No more
	 * hits.
	 * 
	 * After SPLIT, Player can SPLIT again. However, Player is allowed up to
	 * four hands in a game, i.e. one primary and three split hands.
	 * 
	 * After SPLIT, Player can DOUBLE.
	 * 
	 * After SPLIT, Player cannot SURRENDER.
	 * 
	 * @return Collection<IHand> This method returns two hands, i.e. it forks
	 *         the subsequent execution.
	 */
	@Override
	public Collection<Hand> execute() {
		List<Hand> result = new LinkedList<Hand>();
		Hand newHand = null;
		if (originalHand.isAllowedSplit()
				&& originalHand.getOwner().getAccountBalance() >= banker
						.getBet(originalHand)) {
			if (originalHand.remove(duplicate)
					&& (newHand = originalHand.getOwner().nextHand()) != null) {
				dealer.dealOneCard(newHand, duplicate, Face.UP);
				dealer.dealOneCard(newHand, Face.UP);
				banker.placeBet(originalHand.getOwner(), newHand,
						banker.getBet(originalHand));
				dealer.dealOneCard(originalHand, Face.UP);
				originalHand.setAllowedHit(true);
				originalHand.setAllowedSplit(true);
				originalHand.setAllowedDouble(true);
				originalHand.setAllowedSurrender(false);
				newHand.setAllowedHit(true);
				newHand.setAllowedSplit(true);
				newHand.setAllowedDouble(true);
				newHand.setAllowedSurrender(false);
				if (duplicate.getRank() == Rank.ACE) {
					originalHand.setAllowedHit(false);
					originalHand.setAllowedSplit(false);
					originalHand.setAllowedDouble(false);
					originalHand.setAllowedSurrender(false);
					newHand.setAllowedHit(false);
					newHand.setAllowedSplit(false);
					newHand.setAllowedDouble(false);
					newHand.setAllowedSurrender(false);
					dealer.dealOneCard(newHand, Face.UP);
					dealer.dealOneCard(originalHand, Face.UP);
				}
				result.add(newHand);
				result.add(originalHand);
			}
		}
		return result;
	}
}
