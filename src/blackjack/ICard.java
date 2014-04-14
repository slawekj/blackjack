package blackjack;

/**
 * This class defines the behavior of a standard game card. It doesn't have
 * anything specific to a BlackJack game.
 * 
 * 
 * @author Janusz Slawek
 */
public interface ICard {
	/**
	 * Method getRank.
	 * 
	 */
	public Rank getRank();

	/**
	 * Method getSuit.
	 * 
	 */
	public Suit getSuit();

	/**
	 * Method getValue.
	 * 
	 */
	public int getValue();

	/**
	 * Method setFace.
	 * 
	 */
	public void setFace(Face f);

	/**
	 * Method getFace.
	 * 
	 */
	public Face getFace();
}
