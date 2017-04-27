package com.sunspotgames.cardplayer.common.gamepiece;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Pot {

	private BigDecimal mainPot;
	
	private List<SidePot> sidePots;
	
	public Pot() {
		this.mainPot = new BigDecimal(0);
		this.sidePots = new ArrayList<SidePot>();
	}

	public void addMoney(BigDecimal amount) {
		this.mainPot.add(amount);
	}
	
	public void charge(Player player, BigDecimal amount) {
		
	}
	
	private void figurePotSplit() {
		
	}
	
	private void allocateSidePot(Player player, BigDecimal amount) {
		sidePots.add(new SidePot(player, amount));
	}
}
