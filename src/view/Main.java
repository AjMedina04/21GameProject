package view;

import javax.swing.JFrame;

/*
 * 
 */
public class Main {
	public static void main(String[] args) {
		// Instantiate window
		JFrame window = new JFrame("Blackjack");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		// Instantiate and add gamePanel to window and make visible
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack();
		window.setVisible(true);
	}

}
