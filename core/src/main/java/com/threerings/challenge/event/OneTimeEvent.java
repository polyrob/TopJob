package com.threerings.challenge.event;

import com.threerings.challenge.player.Player;
import com.threerings.challenge.util.Rand;

public class OneTimeEvent implements IEvent {

	private static final String[] BAD_THINGS = {"Your car broke down.", "Escort stole your wallet.", "Took a vacation away from all this", "Time to upgrade that TV!"};
	private static final String[] GOOD_THINGS = {"Merit Bonus, just for being you.", "You found a bag full of money at a bus stop.", "Tax Refund Time!!!", "Your least favorite uncle died. Sweet inheritance.", "It rained money. Just like that."};
	private static final int INCREMENT = 500;
	private static final int MIN_COST = 500;
	
	
	@Override
	public String doEvent(boolean good, Player player) {
		StringBuilder sb = new StringBuilder();
		int value = Rand.get(20)*INCREMENT + MIN_COST;
		
		if (good) {
			sb.append(GOOD_THINGS[Rand.get(GOOD_THINGS.length)]);
			sb.append(" +").append(value);
			player.depositSavings(value);
		} else {
			sb.append(BAD_THINGS[Rand.get(BAD_THINGS.length)]);
			sb.append(" -").append(value);
			player.depositSavings(-1*value);
		}
		return sb.toString();
	}
	

}
