package blackjack;

import java.util.Collection;

/**
 * 
 * This class defines the behavior of a an abstract dealer. It's not specific to
 * a BlackJack game.
 * 
 * @author Janusz Slawek
 */
public interface IDealer {
	/**
	 * Shuffles the deck
	 * 
	 */
	public void shuffle();

	/**
	 * Collect cards
	 * 
	 */
	public void collectCards(Collection<ICard> cards);

	/**
	 * Deals one card to Player's primary hand.
	 * 
	 */
	public void dealOneCard(Player player, Face f);

	/**
	 * Deals a given card to Player's primary hand.
	 * 
	 */
	public void dealOneCard(Player player, ICard card, Face f);

	/**
	 * Deals one card to a specific hand.
	 * 
	 */
	public void dealOneCard(IHand<ICard> hand, Face f);

	/**
	 * Deals a given card to a specific hand.
	 * 
	 */
	public void dealOneCard(IHand<ICard> hand, ICard card, Face f);
}
