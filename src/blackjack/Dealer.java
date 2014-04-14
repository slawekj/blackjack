package blackjack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class implements BlackJack dealer and banker, e.g. it implements a set
 * of BlackJack-specific functionality from IBanker interface, and abstract
 * "card dealing" functionality from IDealer interface.
 * 
 * @author Janusz Slawek
 */
public class Dealer implements IDealer, IBanker {
	/**
	 * Field notBusted.
	 */
	private final int notBusted;
	/**
	 * Field deck.
	 */
	private final Deck<ICard> deck;
	/**
	 * Field bets. Here dealer keeps mapping between hands and bets.
	 */
	private Map<IHand, Integer> bets;

	/**
	 * Constructor for Dealer.
	 * 
	 */
	public Dealer(int minimalBet, int notBusted) {
		this.notBusted = notBusted;
		this.bets = new HashMap<IHand, Integer>();

		this.deck = new Deck<ICard>();
		for (Suit s : Suit.values()) {
			for (Rank r : Rank.values()) {
				deck.returnOneToBottom(new Card(r, s));
			}
		}
	}

	/**
	 * Method shuffle.
	 * 
	 */
	@Override
	public void shuffle() {
		deck.shuffle();
	}

	/**
	 * Method collectCards.
	 * 
	 */
	@Override
	public void collectCards(Collection<ICard> cards) {
		for (Iterator<ICard> i = cards.iterator(); i.hasNext();) {
			ICard c = i.next();
			c.setFace(Face.DOWN);
			deck.returnOneToBottom(c);
		}
	}

	/**
	 * Method dealOneCard deals one card from the top of the deck to the primary
	 * hand of a Player.
	 * 
	 */
	@Override
	public void dealOneCard(Player player, Face f) {
		if (deck.countElements() > 0) {
			ICard topCard = deck.takeOneFromTop();
			topCard.setFace(f);
			player.getPrimaryHand().add(topCard);
		}
	}

	/**
	 * Method dealOneCard deals a given card to the primary hand of a Player.
	 * 
	 */
	@Override
	public void dealOneCard(Player player, ICard card, Face f) {
		card.setFace(f);
		player.giveOneCard(card);
	}

	/**
	 * Method dealOneCard deals one card from the top of the deck to a given
	 * hand.
	 * 
	 */
	@Override
	public void dealOneCard(IHand hand, Face f) {
		if (deck.countElements() > 0) {
			ICard topCard = deck.takeOneFromTop();
			topCard.setFace(f);
			hand.add(topCard);
		}
	}

	/**
	 * Method dealOneCard deals a given card to a given hand.
	 * 
	 */
	@Override
	public void dealOneCard(IHand hand, ICard card, Face f) {
		card.setFace(f);
		hand.add(card);
	}

	/**
	 * Method placeBet places a bet on a hand. It takes money from Player's
	 * account.
	 * 
	 */
	@Override
	public void placeBet(Player player, Hand hand, int bet) {
		int moneyTaken = player.forfeitMoney(bet);
		hand.setBet(hand.getBet() + moneyTaken);
		bets.put(hand, hand.getBet());
	}

	/**
	 * Method compareHands.
	 * 
	 */
	@Override
	public Outcome compareHands(Hand humanHand, Hand casinoHand) {
		/**
		 * As Player always busts a hand before the Dealer can, Player's busted
		 * hand will always lose to Dealer's busted hand.
		 * 
		 */
		int casinoScore = casinoHand.getOptimalValue() <= notBusted ? casinoHand
				.getOptimalValue() : -1;
		int humanScore = humanHand.getOptimalValue() <= notBusted ? humanHand
				.getOptimalValue() : -2;
		if (humanHand.isSurrendered()) {
			return Outcome.SURRENDER;
		}
		if (humanScore > casinoScore) {
			return Outcome.WIN;
		} else if (humanScore == casinoScore) {
			return Outcome.TIE;
		}
		return Outcome.LOSE;
	}

	/**
	 * Method resolveBet makes the payment to Player/Casino depending on the
	 * outcome of the comparison of their hands.
	 * 
	 */
	@Override
	public void resolveBet(Player player, Player casino, Hand hand,
			Outcome outcome) {
		if (bets.containsKey(hand)) {
			int money = bets.get(hand);
			bets.remove(hand);
			hand.resetBet();

			money += casino.forfeitMoney(money);
			if (outcome.getMultiplier() > 1.0) {
				money += casino
						.forfeitMoney((int) ((outcome.getMultiplier() - 1.0) * money));
			}

			int toPlayer = (int) (outcome.getMultiplier() * money);
			int toCasino = money - toPlayer;

			player.awardMoney(toPlayer);
			casino.awardMoney(toCasino);
		}
	}
}
