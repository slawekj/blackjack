package blackjack;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
		hands.add(new Hand(this, 0, true));

		/**
		 * Human player can have up to three additional hands, pre-allocating
		 * and indexing them here.
		 * 
		 */
		hands.add(new Hand(this, 1, false));
		hands.add(new Hand(this, 2, false));
		hands.add(new Hand(this, 3, false));

		splitHandIndex = 1;
	}

	/**
	 * Method play.
	 * 
	 */
	@Override
	public List<Hand> play(IDealer dealer, IBanker banker) {
		Stack<Hand> handsToPlay = new Stack<Hand>();
		List<Hand> handsPlayed = new LinkedList<Hand>();
		handsToPlay.add(getHand());

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
		if (currentHand.isAllowedHit()
				&& currentHand.getOptimalValue() < NON_BUSTED) {
			actionString.append(actionCount + " to hit");
			actionMap.put(actionCount, new ActionHit(dealer, currentHand,
					NON_BUSTED));
			actionCount++;
		}

		/**
		 * 
		 * If there's a chance to SPLIT, ask if SPLIT
		 * 
		 */
		if (currentHand.isAllowedSplit() && countSplitHands() < MAX_SPLIT_HANDS
				&& currentHand.getOptimalValue() < NON_BUSTED
				&& getAccountBalance() >= banker.getBet(currentHand)) {
			List<ICard> duplicates = currentHand.getDuplicatedCards();
			for (Iterator<ICard> i = duplicates.iterator(); i.hasNext();) {
				ICard duplicate = i.next();
				if (actionCount > 1) {
					actionString.append(", ");
				}
				actionString.append(actionCount + " to split (" + duplicate
						+ " -> new hand)");
				actionMap.put(actionCount, new ActionSplit(dealer, banker,
						currentHand, duplicate));
				actionCount++;
			}
		}

		/**
		 * 
		 * If there's a chance to DOUBLE, ask if DOUBLE
		 * 
		 */
		if (currentHand.isAllowedDouble()
				&& currentHand.getOptimalValue() < NON_BUSTED
				&& getAccountBalance() >= banker.getBet(currentHand)) {
			if (actionCount > 1) {
				actionString.append(", ");
			}
			actionString.append(actionCount + " to double");
			actionMap.put(actionCount, new ActionDouble(dealer, banker,
					currentHand));
			actionCount++;
		}

		/**
		 * 
		 * If there's a chance to SURRENDER, ask if SURRENDER
		 * 
		 */
		if (currentHand.isAllowedSurrender()
				&& currentHand.getOptimalValue() < NON_BUSTED) {
			if (actionCount > 1) {
				actionString.append(", ");
			}
			actionString.append(actionCount + " to surrender");
			actionMap.put(actionCount, new ActionSurrender(currentHand));
			actionCount++;
		}

		actionString.append(". Press ENTER to stand: ");
		actionMap.put(-1, new ActionStand());

		if (actionMap.size() > 1) {
			String line = "";
			int selection = -2;
			System.out.print(actionString.toString());
			do {
				try {
					line = br.readLine();
					if ("".equals(line)) {
						selection = -1;
					} else {
						selection = Integer.parseInt(line);
					}
				} catch (NumberFormatException e) {
					selection = -2;
				} catch (IOException e) {
					throw new RuntimeException(
							"Unable to determine user action from input stream");
				}
				if (!actionMap.containsKey(selection)) {
					System.out.print("Your selection is incorrect. "
							+ actionString.toString());
				}
			} while (!actionMap.containsKey(selection));
			selectedAction = actionMap.get(selection);
		} else {
			selectedAction = actionMap.get(-1);
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
			System.out.print("Type how much do you want to bet. "
					+ "Press ENTER to bet the minimal amount: ");
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

		banker.placeBet(this, getHand(), bet);
	}

	/**
	 * Method nextFreeSplitHand.
	 * 
	 */
	public Hand nextHand() {
		return splitHandIndex <= MAX_SPLIT_HANDS ? hands.get(splitHandIndex++)
				: null;
	}

	/**
	 * Method decideIfQuit.
	 * 
	 */
	@Override
	public boolean decideIfQuit() {
		boolean decision = false;
		boolean correctSelection = false;
		String line = null;
		if (getAccountBalance() >= getMinimalBet()) {
			System.out.print("Your account is " + getAccountBalance()
					+ ". Type q if you want to (q)uit the game. "
					+ "Press ENTER otherwise: ");
			do {
				try {
					line = br.readLine();
					if ("q".equalsIgnoreCase(line)) {
						correctSelection = true;
						decision = true;
					} else if ("".equals(line)) {
						correctSelection = true;
					}
				} catch (IOException e) {
					throw new RuntimeException(
							"Game unable to read from standard input", e);
				}
				if (!correctSelection) {
					System.out.print("Your command is incorrect. "
							+ "Type q if you want to (q)uit the game. "
							+ "Press ENTER otherwise: ");
				}
			} while (!correctSelection);
		} else {
			decision = true;
		}
		return decision;
	}

	/**
	 * Method returnAllCards return all cards from all Player's hands and reset
	 * the state of the hands for future games.
	 * 
	 */
	@Override
	public Collection<ICard> returnAllCards() {
		List<ICard> result = new LinkedList<ICard>();
		for (Iterator<Hand> i = hands.iterator(); i.hasNext();) {
			Hand hand = i.next();
			hand.setAllowedHit(true);
			hand.setAllowedSplit(true);
			hand.setAllowedDouble(true);
			hand.setAllowedSurrender(true);
			hand.setSurrendered(false);
			if (hand.countElements() > 0) {
				result.addAll(hand.clear());
			}
		}
		splitHandIndex = 1;
		return result;
	}

	/**
	 * Method countSplitHands.
	 * 
	 */
	public int countSplitHands() {
		return splitHandIndex - 1;
	}
}
