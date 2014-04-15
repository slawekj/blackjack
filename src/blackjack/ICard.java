package blackjack;

/**
 * This class defines the behavior of a standard game card.
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
