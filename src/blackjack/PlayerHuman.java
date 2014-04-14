package blackjack;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * This class implements human controlled player. It interacts with player using
 * standard input/ standard output.
 * 
 * @author Janusz Slawek
 * 
 */
public class PlayerHuman extends Player {
	/**
	 * Field MAX_SPLIT_HANDS. (value is 3)
	 */
	private final static int MAX_SPLIT_HANDS = 3;

	/**
	 * Field NON_BUSTED. (value is 21)
	 */
	private final static int NON_BUSTED = 21;

	/**
	 * Field br.
	 */
	private BufferedReader br;
	/**
	 * Field splitHands. Player can have up to three split hands.
	 */
	private Hand splitHands[];

	/**
	 * Field splitHandIndex. Shows next available hand.s
	 */
	private int splitHandIndex;

	/**
	 * Constructor for BJHumanPlayer.
	 * 
	 */
	public PlayerHuman(int initialAccount, int minBet, BufferedReader console) {
		super(initialAccount, minBet);
		br = console;

		setPrimaryHand(new Hand(0, true));

		/**
		 * Human player can have up to three additional hands, pre-allocating
		 * and indexing them here.
		 * 
		 */
		splitHands = new Hand[3];
		splitHands[0] = new Hand(1, false);
		splitHands[1] = new Hand(2, false);
		splitHands[2] = new Hand(3, false);
		splitHandIndex = 0;
	}

	/**
	 * Method play.
	 * 
	 */
	@Override
	public List<Hand> play(IDealer dealer, IBanker banker) {
		Stack<Hand> handsToPlay = new Stack<Hand>();
		List<Hand> handsPlayed = new LinkedList<Hand>();
		handsToPlay.add(getPrimaryHand());

		while (!handsToPlay.isEmpty()) {
			Hand currentHand = handsToPlay.pop();
			/**
			 * select an action
			 * 
			 */
			System.out
					.println("Player's hand: " + currentHand.toString() + ".");
			Collection<Hand> consecutiveHands = chooseAction(dealer, banker,
					currentHand).execute();
			if (consecutiveHands != null && consecutiveHands.size() > 0) {
				for (Iterator<Hand> i = consecutiveHands.iterator(); i
						.hasNext();) {
					handsToPlay.push(i.next());
				}
			} else {
				System.out.println("Player's hand finally: "
						+ currentHand.toString() + ".");
				handsPlayed.add(currentHand);
			}
		}
		return handsPlayed;
	}

	/**
	 * Method chooseAction.
	 * 
	 */
	private IAction chooseAction(IDealer dealer, IBanker banker,
			Hand currentHand) {
		int actionCount = 1;
		IAction selectedAction = null;
		Map<Integer, IAction> actionMap = new HashMap<Integer, IAction>();

		StringBuffer actionString = new StringBuffer();
		actionString.append("Type ");

		/**
		 * 
		 * If there's a chance to HIT, ask if HIT
		 * 
		 */
		if (currentHand.canHit() && currentHand.getOptimalValue() < NON_BUSTED) {
			actionString.append(actionCount + " to hit");
			actionMap.put(actionCount, new ActionHit(dealer, currentHand));
			actionCount++;
		}

		/**
		 * 
		 * If there's a chance to SPLIT, ask if SPLIT
		 * 
		 */
		if (currentHand.canSplit() && countSplitHands() < MAX_SPLIT_HANDS
				&& currentHand.getOptimalValue() < NON_BUSTED) {
			List<ICard> duplicates = currentHand.getDuplicatedCards();
			for (Iterator<ICard> i = duplicates.iterator(); i.hasNext();) {
				ICard duplicate = i.next();
				if (actionCount > 1) {
					actionString.append(", ");
				}
				actionString.append(actionCount + " to split (" + duplicate
						+ " -> new hand)");
				actionMap.put(actionCount, new ActionSplit(dealer, banker,
						this, currentHand, duplicate));
				actionCount++;
			}
		}

		/**
		 * 
		 * If there's a chance to DOUBLE, ask if DOUBLE
		 * 
		 */
		if (currentHand.canDouble()
				&& currentHand.getOptimalValue() < NON_BUSTED
				&& getAccountBalance() >= currentHand.getBet()) {
			if (actionCount > 1) {
				actionString.append(", ");
			}
			actionString.append(actionCount + " to double");
			actionMap.put(actionCount, new ActionDouble(dealer, banker, this,
					currentHand));
			actionCount++;
		}

		/**
		 * 
		 * If there's a chance to SURRENDER, ask if SURRENDER
		 * 
		 */
		if (currentHand.canSurrender()
				&& currentHand.getOptimalValue() < NON_BUSTED) {
			if (actionCount > 1) {
				actionString.append(", ");
			}
			actionString.append(actionCount + " to surrender");
			actionMap.put(actionCount, new ActionSurrender(currentHand));
			actionCount++;
		}

		actionString.append(". Press ENTER to stand: ");

		try {
			if (actionMap.size() > 0) {
				System.out.print(actionString.toString());
				int selection = Integer.parseInt(br.readLine());
				if (actionMap.containsKey(selection)) {
					selectedAction = actionMap.get(selection);
				} else {
					selectedAction = new ActionStand();
				}
			} else {
				selectedAction = new ActionStand();
			}
		} catch (NumberFormatException e) {
			selectedAction = new ActionStand();
		} catch (IOException e) {
			throw new RuntimeException(
					"Unable to determine user action from input stream");
		}

		return selectedAction;
	}

	/**
	 * Method bet.
	 * 
	 */
	public void bet(IBanker banker) {
		int bet = getMinimalBet();
		boolean isBetCorrect = false;
		if (getAccount() > getMinimalBet()) {
			String line;
			System.out
					.print("Type how much do you want to bet. Press ENTER to bet the minimal amount: ");
			do {
				try {
					line = br.readLine();
					if ("".equals(line)) {
						bet = getMinimalBet();
					} else {
						bet = Integer.parseInt(line);
					}
				} catch (NumberFormatException e) {
					bet = -1;
				} catch (IOException e) {
					throw new RuntimeException(
							"Game unable to read from standard input", e);
				}

				isBetCorrect = bet >= getMinimalBet() && bet <= getAccount();
				if (!isBetCorrect) {
					System.out.print("Your bet is incorrect. "
							+ "Type again how much do you want to bet. " + ""
							+ "Press Enter to bet the minimum amount: ");
				}
			} while (!isBetCorrect);
		}

		banker.placeBet(this, getPrimaryHand(), bet);
	}

	/**
	 * Method nextFreeSplitHand.
	 * 
	 */
	public Hand getSplitHand() {
		return splitHandIndex < MAX_SPLIT_HANDS ? splitHands[splitHandIndex++]
				: null;
	}

	/**
	 * Method decideIfQuit.
	 * 
	 */
	@Override
	public boolean decideIfQuit() {
		boolean decision = false;
		if (getAccountBalance() >= getMinimalBet()) {
			System.out.print("Your account is " + getAccountBalance()
					+ ". Type q if you want to (q)uit the game. "
					+ "Press ENTER otherwise: ");
			try {
				if (getAccountBalance() >= 1
						&& "q".equalsIgnoreCase(br.readLine())) {

					decision = true;
				}
			} catch (IOException e) {
				throw new RuntimeException(
						"Game unable to read from standard input", e);
			}
		} else {
			decision = true;
		}
		return decision;
	}

	/**
	 * Method returnHand.
	 * 
	 */
	@Override
	public Collection<ICard> returnAllCards() {
		getPrimaryHand().allowHit();
		getPrimaryHand().allowSplit();
		getPrimaryHand().allowDouble();
		getPrimaryHand().allowSurrender();
		getPrimaryHand().setSurrendered(false);
		Set<ICard> result = new HashSet<ICard>(getPrimaryHand().clear());
		for (int i = 0; i < splitHandIndex; i++) {
			splitHands[i].allowHit();
			splitHands[i].allowSplit();
			splitHands[i].allowDouble();
			splitHands[i].allowSurrender();
			splitHands[i].setSurrendered(false);
			result.addAll(splitHands[i].clear());
		}
		splitHandIndex = 0;
		return result;
	}

	/**
	 * Method countSplitHands.
	 * 
	 */
	public int countSplitHands() {
		return splitHandIndex;
	}
}
