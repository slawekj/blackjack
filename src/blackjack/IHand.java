package blackjack;

import java.util.Collection;
import java.util.Iterator;

/**
 * This class defines the behavior of an abstract hand of cards, it has nothing
 * specific to BlackJack game.
 * 
 * @author Janusz Slawek
 */
public interface IHand {
	/**
	 * Method getIndex.
	 * 
	 */
	public int getIndex();

	/**
	 * Method add.
	 * 
	 */
	public void add(ICard card);

	/**
	 * Method remove.
	 * 
	 */
	public boolean remove(ICard card);

	/**
	 * Method iterator.
	 * 
	 */
	public Iterator<ICard> iterator();

	/**
	 * Method clear.
	 * 
	 */
	public Collection<ICard> clear();

	/**
	 * Method getValue.
	 * 
	 */
	public int getValue();

	/**
	 * Method count.
	 * 
	 */
	public int countCards();

	/**
	 * Method isRevealed.
	 * 
	 */
	public boolean isRevealed();

	/**
	 * Method reveal.
	 * 
	 */
	public void reveal();
}
