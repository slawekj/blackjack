package blackjack;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 
 * This class implements BlackJack-specific hand of cards. It is a superset of
 * IHand's functionality.
 * 
 * A Hand in Black Jack is very important, human-player bets on hands. Hands of
 * casino-player and human-player are evaluated against each other. Hands
 * undergo various actions, e.g. HIT, SPLIT, DOUBLE, SURRENDER, STAND.
 * 
 * @author Janusz Slawek
 * 
 */
public class Hand implements IHand<ICard> {
	/**
	 * This is a static variable, shared by all instances of Hand. It defines
	 * the default mapping of a card rank to a value. By default Ace counts as
	 * 1. This mapping relies on the index of Rank, defined in Rank.
	 * 
	 */
	private final static int VALUES[] = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10,
			10, 1 };

	/**
	 * Field ACE_THRESHOLD helps calculating the optimal value of a hand. (value
	 * is 11)
	 */
	private final static int ACE_THRESHOLD = 11;

	/**
	 * Field GAIN_FROM_ONE_ACE. (value is 10). That's how much you gain if you
	 * count Ace as 11, not 1.
	 */
	private final static int GAIN_FROM_ONE_ACE = 10;

	/**
	 * Field owner is final. Once a hand is pre-allocated, it never changes the
	 * owner.
	 */
	private final Player owner;

	/**
	 * Field hand keeps Cards.
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
	 * Field index specifies index of a hand. It is useful when distinguishing
	 * multiple hands of the same Player.
	 */
	private final int index;

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
	public Hand(Player owner, int index, boolean isPrimary) {
		this.owner = owner;
		this.hand = new LinkedList<ICard>();
		this.index = index;
		this.countAces = 0;
		this.isRevealed = true;
		this.isPrimary = isPrimary;
		this.isSurrendered = false;
		setAllowedHit(true);
		setAllowedSplit(true);
		setAllowedDouble(true);
		setAllowedSurrender(true);
	}

	@Override
	/**
	 * Method getOwner.
	 * 
	 */
	public Player getOwner() {
		return this.owner;
	}

	/**
	 * Method addElement. It keeps track of the number of aces.
	 * 
	 */
	@Override
	public void addElement(ICard card) {
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
			value += VALUES[i.next().getRank().getIndex()];
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
	 * Method countElements.
	 * 
	 */
	@Override
	public int countElements() {
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
	 * Method toString.
	 * 
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (!isPrimary) {
			sb.append("(Split Hand " + getIndex() + "):");
		}
		if (countElements() > 0) {
			for (Iterator<ICard> i = iterator(); i.hasNext();) {
				sb.append(" " + i.next());
			}
		} else {
			sb.append("empty");
		}
		return sb.toString().trim();
	}

	/**
	 * Method countAces.
	 * 
	 */
	private int countAces() {
		return countAces;
	}

	/**
	 * 
	 * Method getOptimalValue. This method gets the optimal value of the hand,
	 * e.g., if a Player have an Ace and Eight then the optimal value will be
	 * 19, not 10 (Ace is counted as 11). On the other hand, if a Player has an
	 * Ace, Eight, and Five then the optimal value will be 14, not 24 (Ace is
	 * counted as 1).
	 * 
	 * 
	 */
	public int getOptimalValue() {
		int optimalValue = getValue();
		if (optimalValue <= ACE_THRESHOLD && countAces() > 0) {
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
			if (cardValues.contains(VALUES[c.getRank().getIndex()])) {
				duplicates.add(c);
			} else {
				cardValues.add(VALUES[c.getRank().getIndex()]);
			}
		}
		return duplicates;
	}

	/**
	 * Method isAllowedHit.
	 * 
	 */
	public boolean isAllowedHit() {
		return hitPrivilege;
	}

	/**
	 * Method setAllowedHit.
	 * 
	 */
	public void setAllowedHit(boolean allowedHit) {
		this.hitPrivilege = allowedHit;
	}

	/**
	 * Method isAllowedSplit.
	 * 
	 */
	public boolean isAllowedSplit() {
		return splitPrivilege;
	}

	/**
	 * Method setAllowedSplit.
	 * 
	 */
	public void setAllowedSplit(boolean allowedSplit) {
		this.splitPrivilege = allowedSplit;
	}

	/**
	 * Method isAllowedDouble.
	 * 
	 */
	public boolean isAllowedDouble() {
		return doublePrivilege;
	}

	/**
	 * Method setAllowedDouble.
	 * 
	 */
	public void setAllowedDouble(boolean allowedDouble) {
		this.doublePrivilege = allowedDouble;
	}

	/**
	 * Method isAllowedSurrender
	 * 
	 */
	public boolean isAllowedSurrender() {
		return surrenderPrivilege;
	}

	/**
	 * Method allowSurrender()
	 * 
	 */
	public void setAllowedSurrender(boolean allowedSurrender) {
		this.surrenderPrivilege = allowedSurrender;
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
		if (isAllowedSurrender()) {
			this.isSurrendered = isSurrendered;
		}
	}
}
