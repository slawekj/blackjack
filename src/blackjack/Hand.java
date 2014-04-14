package blackjack;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * This class implements BlackJack-specific hand of cards.
 * 
 * @author Janusz Slawek
 * 
 */
public class Hand implements IHand {
	/**
	 * Field THRESHOLD. (value is 11)
	 */
	private final static int THRESHOLD = 11;

	/**
	 * Field GAIN_FROM_ONE_ACE. (value is 10). That's how much you gain if you
	 * count Ace as 11, not 1.
	 */
	private final static int GAIN_FROM_ONE_ACE = 10;

	/**
	 * Field hand.
	 */
	private List<ICard> hand;
	/**
	 * Field countAces.
	 */
	private int countAces;
	/**
	 * Field isRevealed.
	 */
	private boolean isRevealed;
	/**
	 * Field isPrimary.
	 */
	private final boolean isPrimary;
	/**
	 * Field index.
	 */
	private int index;
	/**
	 * Field bet.
	 */
	private int bet;
	/**
	 * Field hitPrivilege.
	 */
	private boolean hitPrivilege;
	/**
	 * Field splitPrivilege.
	 */
	private boolean splitPrivilege;
	/**
	 * Field doublePrivilege.
	 */
	private boolean doublePrivilege;
	/**
	 * Field surrenderPrivilege.
	 */
	private boolean surrenderPrivilege;
	/**
	 * Field isSurrendered. This field determines if a hand was surrendered or
	 * not.
	 */
	private boolean isSurrendered;

	/**
	 * Constructor for BJHand.
	 * 
	 */
	public Hand(int index, boolean isPrimary) {
		this.hand = new LinkedList<ICard>();
		this.index = index;
		this.countAces = 0;
		this.isRevealed = true;
		this.isPrimary = isPrimary;
		this.isSurrendered = false;
		allowHit();
		allowSplit();
		allowDouble();
		allowSurrender();
	}

	/**
	 * Method add. It keeps track of the number of aces.
	 * 
	 */
	@Override
	public void add(ICard card) {
		hand.add(card);
		if (card.getRank() == Rank.ACE) {
			countAces++;
		}
		if (card.getFace() == Face.DOWN) {
			isRevealed = false;
		}
	}

	/**
	 * Method clear.
	 * 
	 */
	@Override
	public Collection<ICard> clear() {
		List<ICard> copy = new LinkedList<ICard>(hand);
		hand.clear();
		countAces = 0;
		isRevealed = true;
		return copy;
	}

	/**
	 * Method getValue.
	 * 
	 */
	@Override
	public int getValue() {
		int value = 0;
		for (Iterator<ICard> i = hand.iterator(); i.hasNext();) {
			value += i.next().getValue();
		}
		return value;
	}

	/**
	 * Method iterator.
	 * 
	 */
	@Override
	public Iterator<ICard> iterator() {
		return hand.iterator();
	}

	/**
	 * Method countCards.
	 * 
	 */
	@Override
	public int countCards() {
		return hand.size();
	}

	/**
	 * Method isRevealed.
	 * 
	 */
	@Override
	public boolean isRevealed() {
		return isRevealed;
	}

	/**
	 * Method reveal.
	 * 
	 */
	@Override
	public void reveal() {
		for (Iterator<ICard> i = hand.iterator(); i.hasNext();) {
			i.next().setFace(Face.UP);
		}
		isRevealed = true;
	}

	/**
	 * Method getIndex.
	 * 
	 */
	@Override
	public int getIndex() {
		return this.index;
	}

	/**
	 * Method remove.
	 * 
	 */
	@Override
	public boolean remove(ICard card) {
		boolean result = hand.remove(card);
		if (result && card.getRank() == Rank.ACE) {
			countAces--;
		}
		return result;
	}

	/**
	 * Method setBet.
	 * 
	 */
	public void setBet(int bet) {
		this.bet = bet;
	}

	/**
	 * Method resetBet.
	 * 
	 */
	public void resetBet() {
		bet = 0;
	}

	/**
	 * Method getBet.
	 * 
	 */
	public int getBet() {
		return this.bet;
	}

	/**
	 * Method toString.
	 * 
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (!isPrimary) {
			sb.append("(Split Hand " + getIndex() + "):");
		}
		if (countCards() > 0) {
			for (Iterator<ICard> i = iterator(); i.hasNext();) {
				sb.append(" " + i.next());
			}
			sb.append('.');
			if (isRevealed()) {
				sb.append(" Value:");
				if (getOptimalValue() == 21) {
					sb.append(" 21");
				} else {
					for (int i = 0; i < countAces() + 1; i++) {
						sb.append(" " + (getValue() + i * 10));
					}
				}
				sb.append('.');
			}
		} else {
			sb.append("empty");
		}
		if (getBet() > 0) {
			sb.append(" Bet: " + getBet());
		}
		return sb.toString().trim();
	}

	/**
	 * Method isPrimary.
	 * 
	 */
	public boolean isPrimary() {
		return this.isPrimary;
	}

	/**
	 * Method countAces.
	 * 
	 */
	public int countAces() {
		return countAces;
	}

	/**
	 * 
	 * Method getOptimalValue. This method gets the optimal value of the hand,
	 * e.g., if a Player have an Ace and Eight then the optimal value will be
	 * 19, not 10 (Ace is counted as 11). On the other hand, if a Player have an
	 * Ace, Eight, and Five then the optimal value will be 14, not 24 (Ace is
	 * counted as 1).
	 * 
	 * 
	 */
	public int getOptimalValue() {
		int optimalValue = getValue();
		if (optimalValue <= THRESHOLD && countAces() > 0) {
			optimalValue += GAIN_FROM_ONE_ACE;
		}
		return optimalValue;
	}

	/**
	 * Method getDuplicatedCards. This method returns duplicates of evenly
	 * valued cards, e.g. Jack is a duplicate of King, because Jack has value
	 * 10, and King has value 10.
	 * 
	 */
	public List<ICard> getDuplicatedCards() {
		Set<Integer> cardValues = new HashSet<Integer>();
		List<ICard> duplicates = new LinkedList<ICard>();
		for (Iterator<ICard> i = hand.iterator(); i.hasNext();) {
			ICard c = i.next();
			if (cardValues.contains(c.getValue())) {
				duplicates.add(c);
			} else {
				cardValues.add(c.getValue());
			}
		}
		return duplicates;
	}

	/**
	 * Method canHit.
	 * 
	 */
	public boolean canHit() {
		return hitPrivilege;
	}

	/**
	 * Method allowHit.
	 * 
	 */
	public void allowHit() {
		hitPrivilege = true;
	}

	/**
	 * Method revokeHit.
	 * 
	 */
	public void revokeHit() {
		hitPrivilege = false;
	}

	/**
	 * Method canSplit.
	 * 
	 */
	public boolean canSplit() {
		return splitPrivilege;
	}

	/**
	 * Method allowSplit.
	 * 
	 */
	public void allowSplit() {
		splitPrivilege = true;
	}

	/**
	 * Method revokeSplit.
	 * 
	 */
	public void revokeSplit() {
		splitPrivilege = false;
	}

	/**
	 * Method canDouble.
	 * 
	 */
	public boolean canDouble() {
		return doublePrivilege;
	}

	/**
	 * Method allowDouble.
	 * 
	 */
	public void allowDouble() {
		doublePrivilege = true;
	}

	/**
	 * Method revokeDouble.
	 * 
	 */
	public void revokeDouble() {
		doublePrivilege = false;
	}

	/**
	 * Method canSurreder
	 * 
	 */
	public boolean canSurrender() {
		return surrenderPrivilege;
	}

	/**
	 * Method allowSurrender()
	 * 
	 */
	public void allowSurrender() {
		surrenderPrivilege = true;
	}

	/**
	 * Method revokeSurrender
	 * 
	 */
	public void revokeSurrender() {
		surrenderPrivilege = false;
	}

	/**
	 * Method isSurrendered
	 * 
	 */
	public boolean isSurrendered() {
		return isSurrendered;
	}

	/**
	 * Method setSurrendered
	 * 
	 */
	public void setSurrendered(boolean isSurrendered) {
		if (canSurrender()) {
			this.isSurrendered = isSurrendered;
		}
	}
}
