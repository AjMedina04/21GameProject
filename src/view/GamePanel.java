package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

	public static final int FPS = 60;
	public static final int WINDOW_WIDTH = 1100;
	public static final int WINDOW_HEIGHT = 800;

	private Thread gameThread;

	public GamePanel() {
		setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		setBackground(new Color(49, 46, 43));

	}

	public void startGameLoop() {
		gameThread = new Thread(this);// instantiate the gameThread
		gameThread.start();// used to call the run method

	}

	// Game loop implementation
	// Maintains consistent 60 FPS for smooth animation
	@Override
	public void run() {

		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) { // keeps the game loop running while the program is running

			// Add time since last frame
			currentTime = System.nanoTime(); // check current nano sec time continuously
			delta += (currentTime - lastTime) / drawInterval; // actual rate of time / by our desired rate of time
			lastTime = currentTime; // update last time during this process

			// When enough time has passed, update and render
			if (delta >= 1) { // when we get our desired frame rate
				update(); // Update game state
				repaint(); // Rendering
				delta--; // Reset delta
			}
		}
	}

	public void update() {

	}

}
