package blackjack;

/**
 * This enum defines Suit of card.
 * 
 * @author Janusz Slawek
 * 
 */
public enum Suit {
	/**
	 * Field HEARTS.
	 */
	HEARTS("h"), /**
	 * Field DIAMONDS.
	 */
	DIAMONDS("d"), /**
	 * Field CLUBS.
	 */
	CLUBS("c"), /**
	 * Field SPADES.
	 */
	SPADES("s");

	/**
	 * Field name.
	 */
	private final String name;

	/**
	 * Constructor for Suit.
	 * 
	 */
	private Suit(String name) {
		this.name = name;
	}

	/**
	 * Method getName.
	 * 
	 * 
	 * @return String
	 */
	public String getName() {
		return this.name;
	}
}
