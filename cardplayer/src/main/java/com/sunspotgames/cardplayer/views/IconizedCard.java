package com.sunspotgames.cardplayer.views;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import com.sunspotgames.cardplayer.common.gamepiece.Card;

public class IconizedCard extends JLabel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4635710114703925031L;
	private static final String CARD_IMAGES_DIRECTORY = "src/main/java/com/sunspotgames/cardplayer/resources/";
	private static final String CARD_IMAGES_EXTENSION = ".gif";
	
	private Card card;
	private boolean faceUp;
	
	private static ImageIcon getImageIcon(Card card, boolean faceUp) {
		return new ImageIcon(CARD_IMAGES_DIRECTORY + card.getCardImageLocation(faceUp) + CARD_IMAGES_EXTENSION);
	}
	
	public IconizedCard(Card card, boolean faceUp) {
		super(getImageIcon(card, faceUp));
		card.setIconizedCard(IconizedCard.this);
		this.card = card;
		this.faceUp = faceUp;
	}
	
	public void flip() {
		this.faceUp = !this.faceUp;
		this.setIcon(getImageIcon(this.card, this.faceUp));
	}
	
	public void destroy() {
		if (getParent() instanceof JLayeredPane) {
			JLayeredPane jlp = (JLayeredPane) getParent();
			jlp.remove(IconizedCard.this);
			jlp.repaint();
		}
	}
}
