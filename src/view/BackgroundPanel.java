package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BackgroundPanel extends JPanel {
	private BufferedImage backgroundImage;

	public BackgroundPanel() {
		// Load the background once
		try {
			backgroundImage = ImageIO.read(getClass().getResourceAsStream("/tablebackground/table.jpg"));
		} catch (IOException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		// Use a layout so you can add your other panels to this one
		setLayout(new BorderLayout(10, 10));
		setOpaque(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draw the image to fill the entire panel
		if (backgroundImage != null) {
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
	}
}
