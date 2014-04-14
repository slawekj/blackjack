package blackjack;

import java.util.Collection;
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
	private Hand primaryHand;
	/**
	 * Field account.
	 */
	private int account;
	/**
	 * Field minimalBet
	 */
	private int minimalBet;

	/**
	 * Constructor for AbstractBJPlayer.
	 * 
	 */
	public Player(int initialAccount, int minimalBet) {
		setAccount(initialAccount);
		setMinimalBet(minimalBet);
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
		getPrimaryHand().add(card);
	}

	/**
	 * Method minValueInHand.
	 * 
	 */
	public int minValueInHand() {
		return getPrimaryHand().getValue();
	}

	/**
	 * Method getAccountBalance.
	 * 
	 */
	public int getAccountBalance() {
		return getAccount();
	}

	/**
	 * Method showPrimaryHand.
	 * 
	 */
	public String showPrimaryHand() {
		return getPrimaryHand().toString();
	}

	/**
	 * Method revealHand.
	 */
	public void revealHand() {
		getPrimaryHand().reveal();
	}

	/**
	 * Method getOptimalValueInHand.
	 * 
	 */
	public int getOptimalValueInHand() {
		return getPrimaryHand().getOptimalValue();
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
	 * Method setPrimaryHand.
	 * 
	 */
	public void setPrimaryHand(Hand primaryHand) {
		this.primaryHand = primaryHand;
	}

	/**
	 * Method getPrimaryHand.
	 * 
	 */
	public Hand getPrimaryHand() {
		return this.primaryHand;
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
	public abstract Hand getSplitHand();

	/**
	 * Method returnHand.
	 * 
	 */
	public abstract Collection<ICard> returnAllCards();
}
