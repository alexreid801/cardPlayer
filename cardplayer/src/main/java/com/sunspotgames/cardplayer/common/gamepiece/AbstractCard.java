package com.sunspotgames.cardplayer.common.gamepiece;

import com.sunspotgames.cardplayer.views.IconizedCard;

public abstract class AbstractCard implements Card {
	
	private static final String BACK_OF_CARD_FILE_NAME = "back";
	
	private IconizedCard component;
	
	public String getCardImageLocation(boolean faceUp) {
    	
    	String imageLocation;
    	if(faceUp) {
    		imageLocation = this.getCardImageLocation();
    	} else {
    		imageLocation = BACK_OF_CARD_FILE_NAME;
    	}
    	
		return imageLocation;
	}
	
	public IconizedCard getIconizedCard() {
		return component;
	}

	public void setIconizedCard(IconizedCard component) {
		this.component = component;
	}
}
