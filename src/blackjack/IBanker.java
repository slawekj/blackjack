package blackjack;

/**
 * 
 * This class defines the behavior of a BlackJack banker. It is specific to a
 * BlackJack game, it manages bets, calculates the outcome of the game.
 * 
 * @author Janusz Slawek
 */
public interface IBanker {
	/**
	 * Banker keeps track of the bets. Bets are associated with Player's hands.
	 * 
	 */
	public void placeBet(Player player, Hand hand, int bet);

	/**
	 * Method getBet returns what is a current bet on a hand. It doesn't change
	 * the bet or the hand in any way.
	 * 
	 */
	public int getBet(Hand hand);

	/**
	 * Method resolveBet needs Hand (BlackJack specific) and not IHand. IBanker
	 * only knows how to resolve bet of BlackJack hand, not any hand.
	 * 
	 */
	public void resolveBet(Player player, Player casino, Hand hand,
			Outcome outcome);

	/**
	 * Method compareHands needs Hand (BlackJack specific) and not IHand.
	 * IBanker only knows how to compare BlackJack hands, not any hands.
	 * 
	 */
	public Outcome compareHands(Hand humanHand, Hand casinoHand);
}
