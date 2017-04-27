package com.sunspotgames.cardplayer.common.gamepiece.deck;

import java.util.ArrayList;
import java.util.List;

import com.sunspotgames.cardplayer.common.gamepiece.Card;
import com.sunspotgames.cardplayer.common.gamepiece.Deck;
import com.sunspotgames.cardplayer.common.gamepiece.cards.ClassicPlayingCard;
import com.sunspotgames.cardplayer.common.gamepiece.cards.ClassicPlayingCard.Rank;
import com.sunspotgames.cardplayer.common.gamepiece.cards.ClassicPlayingCard.Suit;

public class ClassicPlayingCardDeck extends Deck<Card> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4833531563382332291L;
	
	private static final List<Card> singleDeck = new ArrayList<Card>();
	
	static {
		for (Suit suit : Suit.values())
            for (Rank rank : Rank.values())
            	singleDeck.add(new ClassicPlayingCard(rank, suit));
	}

	public ClassicPlayingCardDeck(int multiplier) {
		for(int i=0; i<multiplier; i++) {
			addAll(singleDeck);
		}
			
	}
}
