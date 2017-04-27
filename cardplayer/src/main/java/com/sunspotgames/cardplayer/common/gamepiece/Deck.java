package com.sunspotgames.cardplayer.common.gamepiece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Deck<T extends Card> extends ArrayList<Card> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2876272721050042246L;
	
	private int getRandomIndex() {
		return ThreadLocalRandom.current().nextInt(0, this.size());
	}
	
	public void shuffle() {
		Collections.shuffle(this);
	}
	
	public Card getCard(Position position) {
		switch(position) {
		case TOP:
			return remove(0);
		case BOTTOM:
			return remove(this.size() - 1);
		case RANDOM:
			return remove(getRandomIndex());
		}
		return null;
	}
	
	public void addCard(Card card, Position position) {
		switch(position) {
		case TOP:
			add(0, card);
			break;
		case BOTTOM:
			add(card);
			break;
		case RANDOM:
			add(getRandomIndex(), card);
			break;
		}
	}
	
	public enum Position {
		TOP(), BOTTOM(), RANDOM();
	}
}
