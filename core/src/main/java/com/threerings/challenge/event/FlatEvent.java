package com.threerings.challenge.event;

import com.threerings.challenge.player.Player;
import com.threerings.challenge.util.Rand;

public class FlatEvent implements IEvent {

	private static final String[] BAD_THINGS = {"Kid needs braces", "Moved into a bigger house", "Things didn't work out. Alimony payments.", "Your moron kid is going to collge, somehow.", "Need a lawyer for some... stuff."};
	private static final String[] GOOD_THINGS = {"Kid failed out of college. Savings! ", "You moved into cheaper home... in the ghetto.", "You are doing some modeling on the side!"};
	private static final int INCREMENT = 50;
	private static final int MIN_COST = 100;
	
	
	@Override
	public String doEvent(boolean good, Player player) {
		StringBuilder sb = new StringBuilder();
		int value = Rand.get(10)*INCREMENT + MIN_COST;
		
		int currentExpense = player.getMonthlyExpenses();
		
		if (good) {
			sb.append(GOOD_THINGS[Rand.get(GOOD_THINGS.length)]);
			sb.append(" -$").append(value).append("/month");
			player.depositSavings(value);
			player.setMonthlyExpenses(currentExpense - value);
		} else {
			sb.append(BAD_THINGS[Rand.get(BAD_THINGS.length)]);
			sb.append(" +$").append(value).append("/month");
			player.setMonthlyExpenses(currentExpense + value);
		}
		return sb.toString();
	}
	

}
