package blackjack;

/**
 * 
 * @author Janusz Slawek
 * 
 */
public enum Rank {
	/**
	 * Field TWO.
	 */
	TWO(0, "2"), /**
	 * Field THREE.
	 */
	THREE(1, "3"), /**
	 * Field FOUR.
	 */
	FOUR(2, "4"), /**
	 * Field FIVE.
	 */
	FIVE(3, "5"), /**
	 * Field SIX.
	 */
	SIX(4, "6"), /**
	 * Field SEVEN.
	 */
	SEVEN(5, "7"), /**
	 * Field EIGHT.
	 */
	EIGHT(6, "8"), /**
	 * Field NINE.
	 */
	NINE(7, "9"), /**
	 * Field TEN.
	 */
	TEN(8, "10"), /**
	 * Field JACK.
	 */
	JACK(9, "J"), /**
	 * Field QUEEN.
	 */
	QUEEN(10, "Q"), /**
	 * Field KING.
	 */
	KING(11, "K"), /**
	 * Field ACE.
	 */
	ACE(12, "A");

	/**
	 * Field index.
	 */
	private final int index;
	/**
	 * Field name.
	 */
	private final String name;

	/**
	 * Constructor for Rank.
	 * 
	 */
	private Rank(int index, String name) {
		this.index = index;
		this.name = name;
	}

	/**
	 * Method getName.
	 * 
	 */
	String getName() {
		return this.name;
	}

	/**
	 * Method getIndex.
	 * 
	 */
	int getIndex() {
		return this.index;
	}
}
