package com.sunspotgames.cardplayer.common.gamepiece;

import com.sunspotgames.cardplayer.views.IconizedCard;

public interface Card {

	public String getCardImageLocation(boolean faceUp);
	
	public String getCardImageLocation();

	public void setIconizedCard(IconizedCard iconizedCard);
	
	public IconizedCard getIconizedCard();
}
