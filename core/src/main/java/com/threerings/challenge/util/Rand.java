package com.threerings.challenge.util;

import java.util.Random;

/**
 * Custom class to handle to random things.
 * 
 * @author Robbie Scheidt
 *
 */
public class Rand {

	private static Random r = new Random();

	public static int get(int max) {
		return r.nextInt(max);
	}

	public static boolean getSuccessForOdds(float chance) {
		if (r.nextFloat() < chance) {
			return true;
		}
		return false;
	}
}
