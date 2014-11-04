package com.threerings.challenge.util;

import java.text.NumberFormat;
import java.util.Locale;

public class Formatter {
	private static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);;
	
	static {
		currencyFormatter.setMaximumFractionDigits(0); // don't need cents displayed
	}
	
	
	public static String formatMoney(int in) {
		return currencyFormatter.format(in);
	}
	
	public static String getPercentageFloat(float f) {
		return String.format("%,.1f%%", f*100);
	}
}
