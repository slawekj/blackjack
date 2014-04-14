package blackjack;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Janusz Slawek
 */
public class ActionSplit implements IAction {
	/**
	 * Field MAX_SPLIT_HANDS. (value is 3)
	 */
	private final static int MAX_SPLIT_HANDS = 3;
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
	public ActionSplit(IDealer dealer, IBanker banker, PlayerHuman player,
			Hand originalHand, ICard duplicate) {
		this.dealer = dealer;
		this.banker = banker;
		this.player = player;
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
		if (originalHand.canSplit()
				&& player.getAccountBalance() >= originalHand.getBet()) {
			Hand newHand = null;
			if (originalHand.remove(duplicate)
					&& player.countSplitHands() < MAX_SPLIT_HANDS) {
				newHand = player.getSplitHand();
				dealer.dealOneCard(newHand, duplicate, Face.UP);
				dealer.dealOneCard(newHand, Face.UP);
				banker.placeBet(player, newHand, originalHand.getBet());
				dealer.dealOneCard(originalHand, Face.UP);
				originalHand.allowHit();
				originalHand.allowSplit();
				originalHand.allowDouble();
				originalHand.revokeSurrender();
				newHand.allowHit();
				newHand.allowSplit();
				newHand.allowDouble();
				newHand.revokeSurrender();
				if (duplicate.getRank() == Rank.ACE) {
					originalHand.revokeHit();
					originalHand.revokeSplit();
					originalHand.revokeDouble();
					originalHand.revokeSurrender();
					newHand.revokeHit();
					newHand.revokeSplit();
					newHand.revokeDouble();
					newHand.revokeSurrender();
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
