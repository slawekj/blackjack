package blackjack;

/**
 * This class defines the behavior of a deck of type T.
 * 
 * 
 * @author Janusz Slawek
 */
public interface IDeck<T> {
	/**
	 * This method removes one element from the deck.
	 * 
	 */
	public T takeOneFromTop();

	/**
	 * This method receives one element and pushes it to the bottom of the deck.
	 * 
	 */
	public void returnOneToBottom(T t);

	/**
	 * This method shuffles the deck
	 * 
	 */
	public void shuffle();

	/**
	 * This method counts the elements in the deck
	 * 
	 */
	public int countElements();
}
