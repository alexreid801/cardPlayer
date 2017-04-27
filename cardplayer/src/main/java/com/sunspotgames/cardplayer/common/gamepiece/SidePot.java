package com.sunspotgames.cardplayer.common.gamepiece;

import java.math.BigDecimal;

public class SidePot {

	private Player player;
	
	private BigDecimal amount;
	
	public SidePot(Player player, BigDecimal amount) {
		this.player = player;
		this.amount = amount;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
}
