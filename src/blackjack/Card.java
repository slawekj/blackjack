package blackjack;

/**
 * This class defines a BlackJack card.
 * 
 * @author Janusz Slawek
 * 
 */
public class Card implements ICard {
	/**
	 * This is a static variable, shared by all instances of Card. It defines
	 * the default mapping of a card rank to a value. By default Ace counts as
	 * 1.
	 */
	private final static int VALUES[] = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10,
			10, 1 };
	/**
	 * Field face.
	 */
	private Face face;

	/**
	 * Field rank.
	 */
	private final Rank rank;
	/**
	 * Field suit.
	 */
	private final Suit suit;

	/**
	 * Constructor for BJCard.
	 * 
	 */
	public Card(Rank r, Suit s) {
		this.rank = r;
		this.suit = s;
		face = Face.DOWN;
	}

	/**
	 * Method getValue.
	 * 
	 */
	@Override
	public int getValue() {
		return VALUES[rank.getIndex()];
	}

	/**
	 * Method toString.
	 * 
	 */
	@Override
	public String toString() {
		return face == Face.UP ? rank.getName() + suit.getName() : "X";
	}

	/**
	 * Method getRank.
	 * 
	 */
	@Override
	public Rank getRank() {
		return this.rank;
	}

	/**
	 * Method getSuit.
	 * 
	 */
	@Override
	public Suit getSuit() {
		return this.suit;
	}

	/**
	 * Method setFace.
	 * 
	 */
	@Override
	public void setFace(Face f) {
		this.face = f;
	}

	/**
	 * Method getFace.
	 * 
	 */
	@Override
	public Face getFace() {
		return this.face;
	}
}
