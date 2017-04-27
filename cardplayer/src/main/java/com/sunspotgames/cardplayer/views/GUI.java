package com.sunspotgames.cardplayer.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.sunspotgames.cardplayer.common.gamepiece.Card;
import com.sunspotgames.cardplayer.common.gamepiece.Game;
import com.sunspotgames.cardplayer.common.gamepiece.Hand;
import com.sunspotgames.cardplayer.common.gamepiece.Player;

public class GUI extends JFrame {
	
	private static final Color TABLE_COLOR = Color.decode("#006500");
	private static final Integer LABEL_LAYER = new Integer(1);
	private static final int PLAYER_LABEL_HAND_SPACING = 10;
	private static final int PLAYER_LABEL_HEIGHT = 18;
	private static final int X_MARGIN = 40;
	private static final int CARD_WIDTH = 79;
	private static final int CARD_HEIGHT = 123;
	private static final int CARD_SPACING = 35;
	
	private Game game = new Game();
	
	public static void main(String[] args) {
		
		GUI sl = new GUI();
		sl.setVisible(true);
	}
	  
	public GUI() {
		super("Alex's Poker Game");
		setSize(1200, 800);
		getContentPane().setBackground(TABLE_COLOR);
		getLayeredPane().setOpaque(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		int numberOfDecks = 1;
		int cardsPerPlayer = 2;
		int numberOfPlayers = 6;
		BigDecimal startingAntePrice = new BigDecimal(10);
		
		//Initialize the game
		game.initializeGame(numberOfDecks, numberOfPlayers, cardsPerPlayer, startingAntePrice);
		
		//Deal players thier cards
		game.dealPlayers(cardsPerPlayer);
		
		//Display players (includes labels and card icons)
		displayPlayers(game.getPlayers(), cardsPerPlayer);
		
		displayButton(300, 400);
	}
	
	private void displayPlayers(List<Player> players, int cardsPerPlayer) {
		
		int playersPerRow = players.size() / 2;
		int playerDistance = calculatePlayerDisplayDistance(cardsPerPlayer, playersPerRow);
		
		for(int i=0; i<players.size(); i++) {
			Player player = players.get(i);
			TableSide tableSide;
			int xOffset;
			int yOffset;
			if(i < playersPerRow) {
				tableSide = TableSide.TOP;
				xOffset = X_MARGIN + (playerDistance * i);
				yOffset = 50;
			}
			else {
				tableSide = TableSide.BOTTOM;
				xOffset = X_MARGIN + (playerDistance * (i - playersPerRow));
				yOffset = 580;
			}
			displayPlayer(xOffset, yOffset, tableSide, player, true);
		}
	}
	
	private void displayPlayer(int x, int y, TableSide tableSide, Player player, boolean faceUp) {
		
		//Calculate label width and player label offset
		int playerLabelOffset = calculatePlayerLabelOffset(y, tableSide);
		int labelWidth = calculatePlayerLabelWidth(player.getHand().size());
		
		//Display the label and the hand of cards
		displayLabel(x, playerLabelOffset, labelWidth, player.getLabel());
		displayHand(x, y, player.getHand(), faceUp);
	}
	
	private int calculatePlayerDisplayDistance(int numberOfCards, int playersPerSide) {
		
		//Calculate distance without margins and player width
		int totalDistance = 1200 - (X_MARGIN * 2);
		int playerWidth = calculatePlayerLabelWidth(numberOfCards);
		
		//Calculate the distance using a formula that works
		int distance = (int) Math.floor((totalDistance - playerWidth) / (playersPerSide - 1));
		
		//Return off-setting by 3 to correct a margin error
		return distance - 3;
	}
	
	private int calculatePlayerLabelWidth(int numberOfCards) {
		
		//Default to one card width
		int labelWidth = CARD_WIDTH;
		
		//If there are cards, calculate the width they all take up
		if(numberOfCards > 0) {
			labelWidth = ((numberOfCards - 1) * CARD_SPACING) + CARD_WIDTH;
		}
		return labelWidth;
	}
	
	private int calculatePlayerLabelOffset(int y, TableSide tableSide) {
		
		//Figure the player label offset which differs from top to bottom
		int playerLabelOffset = 0;
		switch(tableSide) {
		case TOP:
			playerLabelOffset = y + CARD_HEIGHT + PLAYER_LABEL_HAND_SPACING;
			break;
		case BOTTOM:
			playerLabelOffset = y - PLAYER_LABEL_HEIGHT - PLAYER_LABEL_HAND_SPACING;
			break;
		}
		return playerLabelOffset;
	}
	
	private void displayLabel(int x, int y, int labelWidth, String labelText) {
		JLabel label = new JLabel(labelText, SwingConstants.CENTER);
		Font labelFont = label.getFont();
		label.setFont(new Font(labelFont.getName(), Font.PLAIN, 16));
		label.setForeground(Color.WHITE);
		label.setBounds(x, y, labelWidth, PLAYER_LABEL_HEIGHT);
		getLayeredPane().add(label, LABEL_LAYER);
	}
		
	private void displayHand(int x, int y, Hand<Card> cards, boolean faceUp) {
		for(int i=0; i<cards.size(); i++) {
			JLabel card = new IconizedCard(cards.get(i), faceUp);
			card.setBounds(x, y, CARD_WIDTH, CARD_HEIGHT);
			getLayeredPane().add(card, new Integer(i+1));
			x = x + CARD_SPACING;
		}
		new OverlapLayout();
	}
	
	private void displayButton(int x, int y) {
		JButton button = new JButton("Test");
		button.setBounds(x, y, 80, 35);
		getLayeredPane().add(button, new Integer(3));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.getPlayers().get(4).getHand().get(1).getIconizedCard().flip();
			}
		});
	}
	
	public enum TableSide {
		TOP(), BOTTOM();
	}
}
