package com.threerings.challenge.player;

import java.util.ArrayList;
import java.util.List;

import com.threerings.challenge.job.Job;
import com.threerings.challenge.job.JobGenerator;
import com.threerings.challenge.stats.Skills;
import com.threerings.challenge.util.Formatter;
import com.threerings.challenge.util.Rand;


/**
 * Holds turn data and performs next-turn calculations.
 * @author Robbie Scheidt
 *
 */
public class Turn {

	private static final int MAX_NEW_JOBS = 7;
	private static final int MONTHS_PER_YEAR = 12;
	private static final int UNEMPLY_MON_SALARY = 800; // guessing, i have no idea
	private static final int MO_JOB_INCR = 6;

	private int turn;
	private Player player;
	private JobGenerator jm;
	private List<Job> jobPostings;

	public Turn(Player player) {
		this.player = player;
		turn = 1;
		jm = new JobGenerator();
		jobPostings = getNewJobs();
	}

	/* Perform next turn calculations for finances, events, etc */
	public TurnData nextTurn() {
		TurnData td = new TurnData(turn++);

		jobPostings = getNewJobs();

		player.depositSavings(resolveFinances(td));
		
		Job job = player.getCurrentJob();
		if (job != null) {
			applyRandomEvents(td); // don't apply these if unemployed. Could be insta-kill.
			
			player.incrementJobTime();
			if (player.getTimeInJob() % MO_JOB_INCR == 0) {
				int jobClass = job.getJobClass();
				player.getSkills().boostStat(Job.jobSkillCrossRef[jobClass]);
				int randSkill = Rand.get(Skills.SKILLS.length);
				player.getSkills().boostStat(randSkill);
				td.add("You're skills are improving! " + Skills.SKILLS[Job.jobSkillCrossRef[jobClass]] + " +1. " + Skills.SKILLS[randSkill] + " +1");
			}
		}

		return td;
	}

	private List<Job> getNewJobs() {
		List<Job> newJobs = new ArrayList<Job>();
		int jobsToday = Rand.get(MAX_NEW_JOBS);
		for (int i = 0; i <= jobsToday; i++) {
			newJobs.add(jm.getJob());
		}
		return newJobs;
	}

	public int getTurn() {
		return turn;
	}

	public List<Job> getJobPostings() {
		return jobPostings;
	}

	/* handle main finance calculations */
	private int resolveFinances(TurnData td) {
		/* check one year */
		if (turn % 12 == 0) {
			td.add("Happy New Year! 3% inflation :(");
			player.setMonthlyExpenses((int) (player.getMonthlyExpenses() * 1.03));
		}
		
		int books = 0;
		/* debits */
		books -= player.getMonthlyExpenses();
		/* credits */
		Job job = player.getCurrentJob();
		if (job != null) {
			books += job.getSalary() / MONTHS_PER_YEAR;
			books -= getTaxDeduction(job.getSalary() / MONTHS_PER_YEAR);
		} else {
			books += UNEMPLY_MON_SALARY;
		}

		return books;
	}

	private int getTaxDeduction(int pay) {
		if (pay > 183_251) {
			return (int) (pay * 0.33);
		} else if (pay > 87_851) {
			return (int) (pay * 0.28);
		} else {
			return (int) (pay * 0.25);
		}
	}

	/* Randomly obtain and apply bad/good events */
	private void applyRandomEvents(TurnData td) {
		if (Rand.getSuccessForOdds(player.getTimeInJob()*0.01f)) {
			Event e = Event.getRandomBadEvent();
			td.add(e.getDesc() + " Cost is "
					+ Formatter.formatMoney(e.getAmount()));
			player.depositSavings(e.getAmount());
		}

		if (Rand.getSuccessForOdds(0.02f)) { // 2% good event, because that's life
			Event e = Event.getRandomGoodEvent();
			td.add(e.getDesc() + " Bonus is "
					+ Formatter.formatMoney(e.getAmount()));
			player.depositSavings(e.getAmount());
		}

	}

}
