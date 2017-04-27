package com.sunspotgames.cardplayer.common.gamepiece;

import java.math.BigDecimal;

public class Player {

	private Hand<Card> hand;
	
	private BigDecimal money;
	
	private BlindState blindState;
	
	private boolean isDealer;
	
	private boolean isHuman;
	
	private int playerNumber;

	public Hand<Card> getHand() {
		return hand;
	}

	public void setHand(Hand<Card> hand) {
		this.hand = hand;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BlindState getBlindState() {
		return blindState;
	}

	public void setBlindState(BlindState blindState) {
		this.blindState = blindState;
	}
	
	public boolean isDealer() {
		return isDealer;
	}

	public void setDealer(boolean isDealer) {
		this.isDealer = isDealer;
	}

	public void charge(BigDecimal amount) {
		this.money = this.money.subtract(amount);
	}
	
	public void pay(BigDecimal amount) {
		this.money = this.money.add(amount);
	}

	public void recieveCard(Card card) {
		this.hand.add(card);
	}

	public String getLabel() {
		return "Player " + playerNumber;
	}

	public boolean isHuman() {
		return isHuman;
	}

	public void setHuman(boolean isHuman) {
		this.isHuman = isHuman;
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

}
