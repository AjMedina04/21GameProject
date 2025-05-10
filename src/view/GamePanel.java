package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Purpose: Creates the view for the model
 */
public class GamePanel extends JPanel {
	// A GamePanel has-a window height and width
	public static final int WINDOW_WIDTH = 1100;
	public static final int WINDOW_HEIGHT = 800;
	// A GamePanel has-a dealButton, standButton, hitButton
	private JButton dealButton, standButton, hitButton;
	// A GamePanel has-a layout
	private BorderLayout layout;
	// A GamePanel has-many cardSlots
	private JLabel[][] cardSlots = new JLabel[2][11];
	// A GamePanel has-a winLossLabel, playerTotal, dealerTotal
	private JLabel winLossLabel, playerTotal, dealerTotal;

	/*
	 * Normal constructor
	 */
	public GamePanel() {
		setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		setBackground(new Color(49, 46, 43));
		setLayout(layout);
	}

}
