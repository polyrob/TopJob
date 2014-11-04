package com.threerings.challenge.event;

import com.threerings.challenge.player.Player;
import com.threerings.challenge.util.Formatter;
import com.threerings.challenge.util.Rand;

public class MultiEvent implements IEvent {

	private static final String[] BAD_THINGS = {"Having another baby. Super.", "Oh brother... another DUI.", "Alimony payments for your failed marriage.", "Your spouse needs another car", "Your idiot brother is having legal problems, again."};
	private static final String[] GOOD_THINGS = {"They lowered your rent! That never happens.", "Gas prices on the decline!"};

	private static final float MIN = 0.01f;
	private static final int MAX_PERCENT = 10;
	
	
	@Override
	public String doEvent(boolean good, Player player) {
		StringBuilder sb = new StringBuilder();
		float value = Rand.get(MAX_PERCENT)*0.01f + MIN;
		
		int currMoExpense = player.getMonthlyExpenses();
		
		if (good) {
			sb.append(GOOD_THINGS[Rand.get(GOOD_THINGS.length)]);
			sb.append("Expenses -").append(Formatter.getPercentageFloat(value));
			player.setMonthlyExpenses((int) (currMoExpense - currMoExpense*value));
		} else {
			sb.append(BAD_THINGS[Rand.get(BAD_THINGS.length)]);
			sb.append("Expenses +").append(Formatter.getPercentageFloat(value));
			player.setMonthlyExpenses((int) (currMoExpense + currMoExpense*value));
		}
		return sb.toString();
	}
	

}
