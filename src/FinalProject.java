import java.awt.event.KeyEvent;

import engine.Engine;
import engine.GameObject.Point;

public class FinalProject extends Engine {

	// The name of the game.
	private static final String NAME = "Pong";

	// The number of players to use.
	private static final int N_PLAYERS = 5;

	// Predefined list of keys to assign to players.
	private static final int[][] PLAYER_KEYS = {
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
			{ KeyEvent.VK_UP, KeyEvent.VK_DOWN },
	/*
	 * { KeyEvent.VK_Q, KeyEvent.VK_A }, { KeyEvent.VK_W, KeyEvent.VK_S }, {
	 * KeyEvent.VK_E, KeyEvent.VK_D }, { KeyEvent.VK_R, KeyEvent.VK_F }, {
	 * KeyEvent.VK_T, KeyEvent.VK_G },
	 */
	};

	// The point on the screen that the Pong ball should start at.
	private static final Point BALL_START_POSITION = new Point(0, 0); // Center
																		// of
																		// screen

	private Paddle[] paddles;

	private Ball ball;

	/**
	 * Constructor
	 */
	public FinalProject() {

		// Initialize the game engine with a name.
		super(NAME);
	}

	/**
	 * Initialize all game objects.
	 */
	public void initialize() {

		initializePlayers();

		ball = new Ball(BALL_START_POSITION);

		addObject(ball);
	}

	/**
	 * Splits the circumference of a circle into `2*N_PLAYERS - 1` points to act
	 * as the boundaries for `N_PLAYERS` player paddles, arranged in a polygon
	 * with `N_PLAYERS` sides.
	 */
	private void initializePlayers() {

		final double angleIncrement = 2 * Math.PI / N_PLAYERS;

		paddles = new Paddle[N_PLAYERS];

		Point a, b;

		for (int i = 0; i < N_PLAYERS; i++) {

			a = new Point(WINDOW_SCALE * Math.cos(i * angleIncrement),
					WINDOW_SCALE * Math.sin(i * angleIncrement));
			b = new Point(WINDOW_SCALE * Math.cos((i + 1) * angleIncrement),
					WINDOW_SCALE * Math.sin((i + 1) * angleIncrement));

			paddles[i] = new Paddle(a, b, PLAYER_KEYS[i][0], PLAYER_KEYS[i][1]);

			addObject(paddles[i]);

		}
	}

	public static void main(String[] args) {
		new FinalProject();
	}
}
