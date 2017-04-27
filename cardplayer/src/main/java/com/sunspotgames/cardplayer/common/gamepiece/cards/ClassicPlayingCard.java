package com.sunspotgames.cardplayer.common.gamepiece.cards;

import com.sunspotgames.cardplayer.common.gamepiece.AbstractCard;
import com.sunspotgames.cardplayer.common.gamepiece.Card;

public class ClassicPlayingCard extends AbstractCard implements Card {

	private Rank rank;
	private Suit suit;
	private Color color;
	
	public ClassicPlayingCard() {}
	
	public ClassicPlayingCard(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
		setColor();
	}
	
	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
		setColor();
	}

	public Color getColor() {
		return color;
	}

	private void setColor() {
		switch(this.suit) {
		case DIAMONDS:
		case HEARTS: 
			this.color = Color.RED;
			break;
		case CLUBS:
		case SPADES:
			this.color = Color.BLACK;
			break;
		}
	}

	public enum Rank {
        DEUCE(2, '2'), THREE(3, '3'), FOUR(4, '4'), FIVE(5, '5'), SIX(6, '6'), SEVEN(7, '7'), EIGHT(8, '8'), NINE(
                9, '9'), TEN(10, 't'), JACK(10, 'j'), QUEEN(10, 'q'), KING(10, 'k'), ACE(11, 'a');

        private int Rankpoints;
        private char Character;

        Rank(int points, char character) {
            this.Rankpoints = points;
            this.Character = character;
        }

        public int getRankpoints() {
            return this.Rankpoints;
        }
        
        public char getCharacter() {
        	return this.Character;
        }

    }

    public enum Suit {
        CLUBS(2, 'c'), DIAMONDS(3, 'd'), HEARTS(4, 'h'), SPADES(1, 's');

        private int Suitpoints;
        private char Character;

        Suit(int points, char character) {
            this.Suitpoints = points;
            this.Character = character;
        }

        public int getSuitpoints() {
            return this.Suitpoints;
        }
        
        public char getCharacter() {
        	return this.Character;
        }

    }
    
    public enum Color {
        BLACK(1), RED(2);

        private int Colorpoints;

        Color(int points) {
            this.Colorpoints = points;
        }

        public int getColorpoints() {
            return this.Colorpoints;
        }

    }
    
    public String getCardImageLocation() {
		char prefix = getRank().getCharacter();
    	char suffix = getSuit().getCharacter(); 	
    	return new StringBuilder().append(prefix).append(suffix).toString();
    }
    
}
