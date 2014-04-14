package blackjack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class implements the basic functionality of a BlackJack game.
 * 
 * 
 * @author Janusz Slawek
 * 
 */
public class BlackJackGame {
	/**
	 * Field BLACKJACK. (value is 21)
	 */
	private final static int BLACKJACK = 21;

	/**
	 * Field NOT_BUSTED. (value is 21). This field defines the maximal value of
	 * a hand considered not busted.
	 */
	private final static int NOT_BUSTED = 21;

	/**
	 * Field MINIMAL_BET. (value is 1)
	 */
	private final static int MINIMAL_BET = 1;

	/**
	 * Field HUMAN_INITIAL_BALANCE. (value is 100)
	 */
	private final static int HUMAN_INITIAL_BALANCE = 100;

	/**
	 * Field DEALER_INITIAL_BALANCE. (value is 10000). That's how much a Player
	 * has to win to break the bank.
	 */
	private final static int DEALER_INITIAL_BALANCE = 10000;

	/**
	 * Field dealer. The game will need one dealer to deal the cards. The
	 * functions of a dealer and a casino-player are different. They are defined
	 * in two different entities, i.e. IDealer and PlayerCPU.
	 */
	private IDealer dealer;

	/**
	 * Field banker. The game will need one banker to take care of bets. The
	 * functions of a banker and a dealer are different. They are defined in two
	 * different entities, i.e. IDealer and IBanker.
	 */
	private IBanker banker;

	/**
	 * Field soleCasino. There will be one casino-player. Casino player shares
	 * some code with human-player, they both extend the same Player class.
	 */
	private PlayerCPU soleCasino;

	/**
	 * Field soleHuman. There will be one human-player.
	 */
	private PlayerHuman soleHuman;

	/**
	 * Field gameCount.
	 */
	private int gameCount;

	/**
	 * Field interruptGame.
	 */
	private boolean interruptGame;

	/**
	 * Field console.
	 */
	private BufferedReader console;

	/**
	 * Method main. This method runs the game and takes care of catching
	 * exceptions, properly closing console. The game doesn't have any
	 * command-line arguments.
	 * 
	 */
	public static void main(String[] args) {
		BlackJackGame game = new BlackJackGame();
		try {
			try {
				game.playGame();
			} catch (Exception e) {
				System.err.println("Game ended unexpectedly.");
			} finally {
				game.cleanUp();
			}
		} catch (IOException e) {
			System.exit(1);
		}
	}

	/**
	 * Constructor for BlackJackGame.
	 * 
	 */
	public BlackJackGame() {
		this.console = new BufferedReader(new InputStreamReader(System.in));
		this.dealer = new Dealer(MINIMAL_BET, NOT_BUSTED);
		this.banker = (IBanker) this.dealer;
		this.soleCasino = new PlayerCPU(DEALER_INITIAL_BALANCE, MINIMAL_BET);
		this.soleHuman = new PlayerHuman(HUMAN_INITIAL_BALANCE, MINIMAL_BET,
				console);
		this.gameCount = 0;
		this.interruptGame = false;
	}

	/**
	 * 
	 * This method will close console stream.
	 * 
	 * 
	 * @throws IOException
	 */
	private void cleanUp() throws IOException {
		this.console.close();
	}

	/**
	 * 
	 * This method implements the basic logic of the Black Jack game between two
	 * players: casino-player and human-player.
	 * 
	 * Casino-player follows simple "stand on 17" rule.
	 * 
	 * Human-player will interact with user via console to determine actions:
	 * 
	 * HIT - player takes one card
	 * 
	 * SPLIT - player splits a hand into two separate hands
	 * 
	 * DOUBLE - player doubles the bet on a hand
	 * 
	 * STAND - player don't take any more actions
	 * 
	 * SURRENDER - player gives up half of the bet
	 * 
	 * Various actions might have further specific limitations.
	 * 
	 * The game will end if: a) any of the player runs out of money or b)
	 * human-player quits the game.
	 * 
	 */
	public void playGame() {
		while (!interruptGame && soleHuman.getAccountBalance() > 0
				&& soleCasino.getAccountBalance() > 0) {
			gameCount++;
			System.out.println("Game " + gameCount + ".");

			/**
			 * Shuffle the deck. It's unrealistic that the Dealer would shuffle
			 * before each game, but otherwise in a one-on-one game with only
			 * one deck it would be too easy for a Player to count the cards.
			 */
			dealer.shuffle();

			/**
			 * Player makes initial bet
			 */
			System.out.println("Player's account: "
					+ soleHuman.getAccountBalance() + ".");
			soleHuman.bet(banker);

			/**
			 * Initially deal two cards
			 */
			dealer.dealOneCard(soleHuman, Face.UP);
			dealer.dealOneCard(soleCasino, Face.UP);
			dealer.dealOneCard(soleHuman, Face.UP);
			dealer.dealOneCard(soleCasino, Face.DOWN);

			/**
			 * Display Dealer's hand, i.e. one card face-up as rank and suit,
			 * one card face-down as X.
			 */
			System.out
					.println("Dealer's hand: " + soleCasino.showPrimaryHand());

			Outcome result = null;
			if (soleCasino.getPrimaryHand().getOptimalValue() == BLACKJACK) {
				/**
				 * 
				 * Dealer has a Black Jacks. Dealer must reveal cards. No more
				 * cards will be dealt.
				 * 
				 */
				soleCasino.revealHand();
				System.out.println("Player's hand "
						+ soleHuman.showPrimaryHand());
				System.out.println("Dealer has a black jack "
						+ soleCasino.showPrimaryHand());
				if (soleHuman.getPrimaryHand().getOptimalValue() == BLACKJACK) {
					result = Outcome.TIE;
				} else {
					result = Outcome.LOSE;
				}
				banker.resolveBet(soleHuman, soleCasino,
						soleHuman.getPrimaryHand(), result);
				System.out.println(result + ". Player's hand: "
						+ soleHuman.getPrimaryHand().toString()
						+ " VS Dealer's hand: " + soleCasino.showPrimaryHand());
			} else {
				/**
				 * 
				 * Dealer doesn't have a Black Jack.
				 * 
				 */
				if (soleHuman.getPrimaryHand().getOptimalValue() == BLACKJACK) {
					/**
					 * 
					 * Dealer peeked at the bottom card. Dealer doesn't have a
					 * Black Jack but human does have a Black Jack. Dealer
					 * doesn't reveal hand. Player wins.
					 * 
					 */
					System.out.println("Player's hand "
							+ soleHuman.showPrimaryHand() + ".");
					System.out.println("Dealer doesn't have a black jack.");
					result = Outcome.WIN_BJ;
					banker.resolveBet(soleHuman, soleCasino,
							soleHuman.getPrimaryHand(), result);
					System.out.println(result + ". Player's hand: "
							+ soleHuman.getPrimaryHand().toString()
							+ " VS Dealer's hand: "
							+ soleCasino.showPrimaryHand());
				} else {
					/**
					 * 
					 * Dealer peeked at the bottom card. Dealer doesn't have a
					 * Black Jack and knows that human doesn't have a Black Jack
					 * either. Player will play. It might result in more than
					 * one hand (if player splits).
					 * 
					 */
					Collection<Hand> humanHands = soleHuman
							.play(dealer, banker);

					/**
					 * 
					 * Let's check if human offered any valid, i.e. not busted,
					 * and not surrendered hands to play against. If human
					 * busted or surrendered all hands then there's no point for
					 * Dealer to play or even reveal a hand.
					 * 
					 */
					boolean atLeastOneValidHand = false;
					for (Iterator<Hand> i = humanHands.iterator(); !atLeastOneValidHand
							&& i.hasNext();) {
						Hand hand = i.next();
						if (hand.getValue() <= NOT_BUSTED
								&& !hand.isSurrendered()) {
							atLeastOneValidHand = true;
						}
					}

					/**
					 * 
					 * Human has at least one hand to compete against. Dealer
					 * should reveal the initial hand, play, show the final
					 * hand.
					 * 
					 */
					if (atLeastOneValidHand) {
						soleCasino.revealHand();
						System.out.println("Dealer's hand: "
								+ soleCasino.showPrimaryHand());
						soleCasino.play(dealer, banker);
						System.out.println("Dealer's hand finally: "
								+ soleCasino.showPrimaryHand());
					}

					for (Iterator<Hand> i = humanHands.iterator(); i.hasNext();) {
						Hand humanHand = i.next();
						/**
						 * Check who won the game, casino-player or
						 * human-player?
						 * 
						 */
						result = banker.compareHands(humanHand,
								soleCasino.getPrimaryHand());
						/**
						 * Dealer resolves the bet
						 * 
						 */
						banker.resolveBet(soleHuman, soleCasino, humanHand,
								result);

						StringBuffer summary = new StringBuffer();
						summary.append(result + ". Player's hand: "
								+ humanHand.toString());
						if (humanHand.getValue() <= 21) {
							summary.append(" VS Dealer's hand: "
									+ soleCasino.showPrimaryHand());
						}
						System.out.println(summary);
					}
				}
			}

			/**
			 * Dealer collects cards from players
			 * 
			 */
			dealer.collectCards(soleCasino.returnAllCards());
			dealer.collectCards(soleHuman.returnAllCards());

			interruptGame = soleHuman.decideIfQuit()
					|| soleCasino.decideIfQuit();
			System.out.println("");
		}

		System.out.println("\nThank you for the game. You played " + gameCount
				+ " games. You started with " + HUMAN_INITIAL_BALANCE
				+ ". You ended with " + soleHuman.getAccountBalance() + ".");
	}
}
