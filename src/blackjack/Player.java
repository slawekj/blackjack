package blackjack;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * This is an abstract class which defines the common set of functions of
 * different players, e.g., human-player and casino-player. All the specific
 * functions to human-player and casino-player are defined in the corresponding
 * classes.
 * 
 * @author Janusz Slawek
 * 
 */
public abstract class Player {
	/**
	 * Field primaryHand.
	 */
	protected List<Hand> hands;

	/**
	 * Field account.
	 */
	private int account;

	/**
	 * Field minimalBet
	 */
	private int minimalBet;

	/**
	 * Constructor for Player.
	 * 
	 */
	public Player(int initialAccount, int minimalBet) {
		setAccount(initialAccount);
		setMinimalBet(minimalBet);
		hands = new LinkedList<Hand>();
	}

	/**
	 * Method setMinimalBet.
	 * 
	 */
	public void setMinimalBet(int minimalBet) {
		this.minimalBet = minimalBet;
	}

	/**
	 * Method getMinimalBet.
	 * 
	 */
	public int getMinimalBet() {
		return this.minimalBet;
	}

	/**
	 * Method giveOneCard.
	 * 
	 */
	public void giveOneCard(ICard card) {
		getHand().addElement(card);
	}

	/**
	 * Method minValueInHand.
	 * 
	 */
	public int minValueInHand() {
		return getHand().getValue();
	}

	/**
	 * Method getAccountBalance.
	 * 
	 */
	public int getAccountBalance() {
		return getAccount();
	}

	/**
	 * Method revealHand.
	 */
	public void revealHand() {
		getHand().reveal();
	}

	/**
	 * Method getOptimalValueInHand.
	 * 
	 */
	public int getOptimalValueInHand() {
		return getHand().getOptimalValue();
	}

	/**
	 * Method awardMoney.
	 * 
	 */
	public void awardMoney(int amount) {
		setAccount(getAccount() + amount);
	}

	/**
	 * Take money from player.
	 * 
	 */
	public int forfeitMoney(int amountToTake) {
		int amountTaken = 0;
		if (getAccount() >= amountToTake) {
			amountTaken = amountToTake;
			setAccount(getAccount() - amountToTake);
		} else {
			amountTaken = getAccount();
			setAccount(0);
		}
		return amountTaken;
	}

	/**
	 * Method getHand gets Player's primary hand. Every player should have at
	 * least one hand.
	 * 
	 */
	public Hand getHand() {
		return hands.get(0);
	}

	/**
	 * Method setAccount.
	 * 
	 */
	public void setAccount(int account) {
		this.account = account;
	}

	/**
	 * Method getAccount.
	 * 
	 */
	public int getAccount() {
		return account;
	}

	/**
	 * This method runs the simulation of a player game. Player starts with the
	 * initial hand. Player can finish with multiple hands if SPLIT occurred.
	 * None or all of player's hands can win the bet. They are evaluated
	 * independently.
	 * 
	 */
	public abstract List<Hand> play(IDealer dealer, IBanker banker);

	/**
	 * Method decideIfQuit.
	 * 
	 */
	public abstract boolean decideIfQuit();

	/**
	 * Method nextFreeSplitHand.
	 * 
	 */
	public abstract Hand nextHand();

	/**
	 * Method returnHand.
	 * 
	 */
	public abstract Collection<ICard> returnAllCards();
}
