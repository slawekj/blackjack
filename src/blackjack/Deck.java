package blackjack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * This class implements deck of type T.
 * 
 * @author Janusz Slawek
 * 
 */
public class Deck<T> implements IDeck<T> {
	/**
	 * Field deck.
	 */
	private List<T> deck;

	/**
	 * Constructs a standard deck.
	 * 
	 */
	public Deck() {
		deck = new LinkedList<T>();
	}

	/**
	 * Method takeOneFromTop. It returns null if there're no more cards in the
	 * deck.
	 * 
	 */
	@Override
	public T takeOneFromTop() {
		if (!deck.isEmpty()) {
			return deck.remove(0);
		} else {
			return null;
		}
	}

	/**
	 * Method returnOneToBottom.
	 * 
	 */
	@Override
	public void returnOneToBottom(T t) {
		deck.add(t);
	}

	/**
	 * Method shuffle.
	 * 
	 */
	@Override
	public void shuffle() {
		Collections.shuffle(deck);
	}

	/**
	 * Method countElements.
	 * 
	 * 
	 */
	@Override
	public int countElements() {
		return deck.size();
	}
}
