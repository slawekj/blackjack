package blackjack;

import java.util.Collection;
import java.util.Iterator;

/**
 * This class defines the behavior of an abstract hand of elements, it doesn't
 * define anything specific to a BlackJack game. Therefore, it can be possibly
 * reused to implement other games. Hand is regarded here as a container that
 * can possibly hide elements from other Players.
 * 
 * @author Janusz Slawek
 */
public interface IHand<T> {
	/**
	 * Method getOwner returns an owner of this hand.
	 * 
	 */
	public Player getOwner();

	/**
	 * Method getIndex returns an index of this hand. If a Player have multiple
	 * hands index can help distinguishing between them.
	 * 
	 */
	public int getIndex();

	/**
	 * Method addElement adds a single element to a hand.
	 * 
	 */
	public void addElement(T card);

	/**
	 * Method remove removes a single element from a hand.
	 * 
	 * @return If the element was in the hand and got removed it returns true.
	 *         It returns false otherwise.
	 * 
	 */
	public boolean remove(T element);

	/**
	 * Method iterator returns an iterator over elements in hand.
	 * 
	 */
	public Iterator<T> iterator();

	/**
	 * Method clear removes all elements from this hand and returns them.
	 * 
	 */
	public Collection<T> clear();

	/**
	 * Method getValue returns a value of a hand.
	 * 
	 */
	public int getValue();

	/**
	 * Method count elements in a hand.
	 * 
	 */
	public int countElements();

	/**
	 * Method isRevealed returns true if the elements in a hand are visible to
	 * Players. It returns false otherwise.
	 * 
	 */
	public boolean isRevealed();

	/**
	 * Method reveal shows all of the elements in hand.
	 * 
	 */
	public void reveal();
}
