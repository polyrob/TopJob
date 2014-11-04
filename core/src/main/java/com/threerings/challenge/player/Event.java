package com.threerings.challenge.player;

import com.threerings.challenge.util.Rand;

public class Event {

	private static final String[] BAD_THINGS = {"Your car broke down.", "Having another baby. Super.", "Oh brother... another DUI.", "Escort stole your wallet."};
	private static final String[] GOOD_THINGS = {"You found a bag full of money at a bus stop.", "Tax Refund Time!!!", "Your least favorite uncle died and left you some inheritance", "It rained money. Just like that."};
	
	
	private String desc;
	private int amount;

	
	public Event(String desc, int amount) {
		this.desc = desc;
		this.amount = amount;
	}

	public static Event getRandomBadEvent() {
		String desc = BAD_THINGS[Rand.get(BAD_THINGS.length)];
		int cost = -1 * Rand.get(10)*500; 
		return new Event(desc, cost);
	}
	
	public static Event getRandomGoodEvent() {
		String desc = GOOD_THINGS[Rand.get(GOOD_THINGS.length)];
		int cost = Rand.get(5)*500; 
		return new Event(desc, cost);
	}

	public String getDesc() {
		return desc;
	}

	public int getAmount() {
		return amount;
	}	
	
}
