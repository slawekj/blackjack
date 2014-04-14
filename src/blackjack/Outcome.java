package blackjack;

/**
 * 
 * This class specifies a possible outcome of the game. Differend outcomes
 * relate to different ways of resolving the bet. A separate outcome is
 * evaluated for each human-player hand VS casino-player hand.
 * 
 * @author Janusz Slawek
 */
public enum Outcome {
	/**
	 * Field WIN_BJ. Human wins by having Black Jack against casino. Player wins
	 * the bet and gets an extra monetary bonus.
	 */
	WIN_BJ("PLAYER WINS 3:2", 1.25),
	/**
	 * Field WIN. Human wins, not by having Black Jack, but non-busted hand
	 * higher in value than casino. Player wins the bet.
	 */
	WIN("PLAYER WINS", 1.0),
	/**
	 * Field TIE. Human ties with casino. Player gets money back but doesn't
	 * make any money.
	 */
	TIE("PLAYER TIES", 0.5),
	/**
	 * Field LOSE. Player loses the bet.
	 */
	LOSE("PLAYER LOSES", 0.0),
	/**
	 * Field SURRENDER. Player loses half of the bet.
	 */
	SURRENDER("PLAYER SURRENDERS", 0.25);

	/**
	 * Field name.
	 */
	private final String name;
	/**
	 * Field base
	 */
	private final double multiplier;

	/**
	 * Constructor for Outcome.
	 * 
	 * @param name
	 *            String
	 * @param multiplier
	 *            double
	 */
	private Outcome(String name, double multiplier) {
		this.name = name;
		this.multiplier = multiplier;
	}

	/**
	 * Method toString.
	 * 
	 * 
	 * 
	 * @return String
	 */
	public String toString() {
		return this.name;
	}

	public double getMultiplier() {
		return this.multiplier;
	}
}
