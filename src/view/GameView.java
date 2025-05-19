package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;

import model.GameModel;
import controller.GameController;

/**
 * GameView is-a JFrame that displays the Blackjack game interface,
 * including message banner, status panel, card grid, and control buttons.
 */
public class GameView extends JFrame {

    // Max cards constant
    public static final int MAX_CARDS = 11;
    // Window height and width constants
    public static final int WINDOW_WIDTH = 1100;
    public static final int WINDOW_HEIGHT = 800;
    // Layout and style constants
    public static final int CARD_GRID_HGAP = 5;
    public static final int CARD_GRID_VGAP = 5;
    public static final Dimension CARD_SIZE = new Dimension(100, 150);
    public static final Font MESSAGE_FONT = new Font("SansSerif", Font.BOLD, 18);
    public static final int BUTTON_HGAP = 10;
    public static final int BUTTON_VGAP = 10;

    // A GamePanel has-a buttonPanel
    private JPanel buttonPanel;
    // A GamePanel has-a messageLabel
    private JLabel messageLabel;
    // A GamePanel has-many cardSlots
    private JLabel[][] cardSlots = new JLabel[2][MAX_CARDS];
    // A GamePanel has-a winLossLabel, playerTotal, dealerTotal
    private JLabel winLossLabel, playerTotalLabel, dealerTotalLabel;
    // A GamePanel has-a backgroundimage
    private BufferedImage backgroundImage;
    // A GamePanel has-a dealButton, hitButton, standButton
    private JButton dealButton, hitButton, standButton;

    /**
     * Constructs the GameView by initializing frame properties, loading background
     * image,
     * creating UI components, and displaying the window.
     * 
     * @param model the GameModel providing game data
     */
    public GameView(GameModel model) {
        setTitle("Blackjack"); // set window title
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)); // set frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit when closed
        setResizable(false); // prevent resizing

        BackgroundPanel background = new BackgroundPanel(); // create background container
        background.setLayout(new BorderLayout(10, 10)); // set layout spacing
        setContentPane(background); // assign background as content pane

        loadBackgroundImage();

        createTopPanel(background);
        createCardPanel(background);
        createButtonPanel(background);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/tablebackground/table.jpg")); // load table
                                                                                                          // image
            this.setIconImage(backgroundImage); // set backgroundImage
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace(); // log loading errors
        }
    }

    /**
     * Creates and adds the top panel containing the message banner and status
     * labels.
     * 
     * @param background the main container to attach the panel to
     */
    private void createTopPanel(BackgroundPanel background) {
        // Message label at top
        messageLabel = new JLabel("Click Deal to begin", SwingConstants.CENTER);
        messageLabel.setFont(MESSAGE_FONT);
        // Status panel with totals and record
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        playerTotalLabel = new JLabel("Player: 0");
        dealerTotalLabel = new JLabel("Dealer: 0");
        winLossLabel = new JLabel("Wins: 0 Losses: 0");
        statusPanel.add(playerTotalLabel);
        statusPanel.add(dealerTotalLabel);
        statusPanel.add(winLossLabel);
        // Combine into top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(messageLabel, BorderLayout.NORTH);
        topPanel.add(statusPanel, BorderLayout.SOUTH);
        background.add(topPanel, BorderLayout.NORTH);
    }

    /**
     * Creates and adds the card grid panel with dealer and player rows of slots.
     * 
     * @param background the main container to attach the panel to
     */
    private void createCardPanel(BackgroundPanel background) {
        JPanel cardPanel = new JPanel();
        cardPanel.setOpaque(false);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.add(makeCardRow(0));
        cardPanel.add(makeCardRow(1));
        background.add(cardPanel, BorderLayout.CENTER);
    }

    /**
     * Creates and adds the control button panel with Deal, Hit, Stand.
     * 
     * @param background the main container to attach the panel to
     */
    private void createButtonPanel(BackgroundPanel background) {
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, BUTTON_HGAP, BUTTON_VGAP));
        dealButton = makeButton("Deal");
        hitButton = makeButton("Hit");
        standButton = makeButton("Stand");
        buttonPanel.add(dealButton);
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        background.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Helper: create a button with action command
    private JButton makeButton(String label) {
        JButton btn = new JButton(label);
        btn.setActionCommand(label);
        return btn;
    }

    // Helper: build a row of card slots
    private JPanel makeCardRow(int rowIndex) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, CARD_GRID_HGAP, CARD_GRID_VGAP));
        row.setOpaque(false);
        for (int col = 0; col < MAX_CARDS; col++) {
            cardSlots[rowIndex][col] = new JLabel();
            cardSlots[rowIndex][col].setHorizontalAlignment(SwingConstants.CENTER);
            row.add(cardSlots[rowIndex][col]);
        }
        return row;
    }

    /**
     * @return the Deal button for user input
     */
    public JButton getDealButton() {
        return dealButton;
    }

    /**
     * @return the Hit button for user input
     */
    public JButton getHitButton() {
        return hitButton;
    }

    /**
     * @return the Stand button for user input
     */
    public JButton getStandButton() {
        return standButton;
    }

    /**
     * Launches the application by initializing model, view, and controller.
     * 
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        GameModel model = new GameModel();
        GameView view = new GameView(model);
        GameController controller = new GameController(model, view);
        controller.initialize();
        controller.updateInitialDisplay();
    }

    /**
     * Updates the top message label with provided text.
     * 
     * @param msg text to display
     */
    public void setMessage(String msg) {
        messageLabel.setText(msg);
    }

    /**
     * Displays a scaled card image at the given row and slot index.
     * 
     * @param row   0 for dealer, 1 for player
     * @param index slot position for the card
     * @param img   image representing the card
     */
    public void showCard(int row, int index, BufferedImage img) {
        JLabel slot = cardSlots[row][index];
        // scale to thumbnail size
        Image scaledImg = img.getScaledInstance(CARD_SIZE.width, CARD_SIZE.height,
                Image.SCALE_SMOOTH);
        slot.setIcon(new ImageIcon(scaledImg));
        slot.setPreferredSize(CARD_SIZE);
    }

    /**
     * Clears all card slots icons.
     */
    private void clearCardSlots() {
        for (JLabel[] rowSlots : cardSlots) {
            for (JLabel slot : rowSlots) {
                slot.setIcon(null);
            }
        }
    }

    /**
     * Updates player and dealer total labels.
     * 
     * @param playerTotal player's total
     * @param dealerTotal dealer's up-card value
     */
    private void setTotals(int playerTotal, int dealerTotal) {
        playerTotalLabel.setText("Player: " + playerTotal);
        dealerTotalLabel.setText("Dealer: " + dealerTotal);
    }

    /**
     * Updates the displayed totals and win/loss record.
     * 
     * @param playerTotal current player total
     * @param dealerUp    dealer's up-card value
     * @param wins        total wins count
     * @param losses      total losses count
     */
    public void updateStatus(int playerTotal, int dealerUp, int wins, int losses) {
        setTotals(playerTotal, dealerUp);
        winLossLabel.setText("Wins: " + wins + " Losses: " + losses);
    }

    /**
     * Clears all card slots and resets totals and message to initial state.
     */
    public void resetBoard() {
        clearCardSlots();
        setMessage("Click Deal to begin");
        setTotals(0, 0);
    }
}