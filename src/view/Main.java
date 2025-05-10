package view;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		JFrame window = new JFrame("Blackjack");// create new JFrame to be a viewing window for the user
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// needed to close the window properly
		window.setResizable(false);// user cannot resize window to simplify coding

		// add gamePanel to the window
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel); // everything drawed to the gamePanel will be visible in the window
		window.pack();// window adjusts size to gamePanel size

		window.setLocationRelativeTo(null);// center the window position more convenient for the user
		window.setVisible(true); // user can now see

		gamePanel.startGameLoop();
	}

}
