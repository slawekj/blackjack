package blackjack;

/**
 * 
 * This class defines a generic card. Calculating the value of a specific card
 * is not defined in this class. It's because in some games the value of a
 * specific card cannot be determined without having a knowledge of the whole
 * hand, an example is ACE in Black Jack.
 * 
 * @author Janusz Slawek
 * 
 */
public class Card implements ICard {
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
