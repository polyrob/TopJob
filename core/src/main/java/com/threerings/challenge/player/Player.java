package com.threerings.challenge.player;

import java.util.Map;

import com.threerings.challenge.job.Company;
import com.threerings.challenge.job.Job;
import com.threerings.challenge.stats.Skills;
import com.threerings.challenge.util.Rand;

public class Player {

	/*
	 * "Inventory" items to boost stats. These can be purchased with $ Suits
	 * Briefcases Shoes Pens Cell Phones? Forged diploma
	 */

	private static final int STARTING_SAVINGS = 5000;
	private static final int STARTING_EXPENSES = 2224; // based on SF
														// livingwage.mit.edu
	private Job currentJob;
	private Skills skills;
	// private Map<Item, Integer> inventoryMap;
	private Map<Company, Float> compAttitudeMap;
	private int savings;

	private int monthlyExpenses;
	private int timeInJob;

	public Player() {
		this.skills = getInitSkills();
		this.currentJob = null; // start unemployed?
		this.savings = STARTING_SAVINGS;
		this.monthlyExpenses = STARTING_EXPENSES;
	}

	private Skills getInitSkills() {
		Skills s = new Skills();
		/* Lets get some random skills to get the dude going */
		for (int i = 0; i < 10; i++) {
			s.boostStat(Rand.get(Skills.SKILLS.length));
		}

		return s;
	}

	public Job getCurrentJob() {
		return currentJob;
	}

	public void setCurrentJob(Job currentJob) {
		this.currentJob = currentJob;
		timeInJob = 0; // if you change jobs reset
	}

	public Skills getSkills() {
		return skills;
	}

	public void setSkills(Skills skills) {
		this.skills = skills;
	}

	public Map<Company, Float> getCompAttitudeMap() {
		return compAttitudeMap;
	}

	public void setCompAttitudeMap(Map<Company, Float> compAttitudeMap) {
		this.compAttitudeMap = compAttitudeMap;
	}

	public int getSavings() {
		return savings;
	}

	public void depositSavings(int amt) {
		this.savings += amt;
	}

	public int getMonthlyExpenses() {
		return monthlyExpenses;
	}

	public void adustMonthlyExpenses(int amt) {
		this.monthlyExpenses += amt;
	}

	public void setMonthlyExpenses(int monthlyExpenses) {
		this.monthlyExpenses = monthlyExpenses;
	}

	public void incrementJobTime() {
		this.timeInJob++;
	}

	public int getTimeInJob() {
		return timeInJob;
	}

}
