package com.sunspotgames.cardplayer.common.gamepiece;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.sunspotgames.cardplayer.common.gamepiece.Deck.Position;
import com.sunspotgames.cardplayer.common.gamepiece.deck.ClassicPlayingCardDeck;

public class Game {
	
	private ClassicPlayingCardDeck deck;
	private List<Card> communityCards;
	private Pot pot;
	private List<Player> players;

	public static void main(String[] args) {
		
		

	}
	
	public void initializeGame(int numberOfDecks, int numberOfPlayers, int cardsPerPlayer, BigDecimal startingPrice) {
		
		//Initialize the deck
		initializeDeck(numberOfDecks);
		
		//Initialize the community cards
		initializeCommunityCards();
		
		//Initialize the players
		initializePlayers(numberOfPlayers);
		
		//Ante all players
		//antePlayers(startingPrice);	
	}
	
	public void initializeDeck(int numberOfDecks) {
		this.deck = new ClassicPlayingCardDeck(numberOfDecks);
	}
	
	public void initializeCommunityCards() {
		this.communityCards = new ArrayList<Card>();
	}
	
	public void initializePlayers(int numberOfPlayers) {
		
		this.players = new ArrayList<Player>();
		
		//Create the player objects
		for(int i=0; i<numberOfPlayers; i++) {
			
			Player player = new Player();
			player.setDealer(false);
			player.setBlindState(BlindState.IS_NOT_BLIND);
			player.setHuman(false);
			player.setHand(new Hand<Card>());
			player.setMoney(new BigDecimal(1000));
			player.setPlayerNumber(i + 1);
			
			switch(i) {
			case 0:
				player.setDealer(true);
				player.setHuman(true);
				break;
			case 1:
				player.setBlindState(BlindState.IS_SMALL_BLIND);
				break;
			case 2:
				player.setBlindState(BlindState.IS_BIG_BLIND);
				break;
			}
			
			this.players.add(player);
		}
	}
	
	public void dealPlayers(int cardsPerPlayer) {
		
		//Shuffle the deck
		this.deck.shuffle();
		
		//For the number of cards each player should have
		for(int i=0; i<cardsPerPlayer; i++) {
			
			//Give each player a card from the top of the deck
			for(Player player : this.players) {
				player.recieveCard(this.deck.getCard(Position.TOP));
			}
		}
	}
	
	public void antePlayers(BigDecimal antePrice) {
		
		//For each player
		for(Player player : this.players) {
			
			//Default player ante to zero
			BigDecimal playerAnte = new BigDecimal(0);
			
			//Figure out how much the player should pay
			switch(player.getBlindState()) {
			case IS_SMALL_BLIND:
				playerAnte = antePrice.divide(new BigDecimal(2), RoundingMode.FLOOR);
				break;
			case IS_BIG_BLIND:
				playerAnte = antePrice;
				break;
			case IS_NOT_BLIND:
				playerAnte = antePrice;
				break;
			}
			
			//Charge the player and add it to the pot
			player.charge(playerAnte);
			this.pot.addMoney(playerAnte);
		}
	}
	
	public List<Player> getPlayers() {
		return players;
	}

}
