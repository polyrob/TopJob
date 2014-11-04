package com.threerings.challenge.player;

import java.util.ArrayList;
import java.util.List;

import com.threerings.challenge.event.FlatEvent;
import com.threerings.challenge.event.IEvent;
import com.threerings.challenge.event.MultiEvent;
import com.threerings.challenge.job.Job;
import com.threerings.challenge.job.JobGenerator;
import com.threerings.challenge.stats.Skills;
import com.threerings.challenge.util.Rand;

/**
 * Holds turn data and performs next-turn calculations.
 * 
 * @author Robbie Scheidt
 *
 */
public class Turn {

	private static final int MAX_NEW_JOBS = 7;
	private static final int MONTHS_PER_YEAR = 12;
	private static final int MO_JOB_INCR = 6;

	private static final float GOOD_CHANCE = 0.1f;
	private static final float BAD_CHANCE = 0.05f;
	private static final float FIFTY_FIFTY = 0.5f;
	private static final float TIME_IN_JOB_MULTIPLY = 0.01f;

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
			applyRandomEvents(td); // don't apply these if unemployed. Could be
									// insta-kill.

			player.incrementJobTime();
			if (player.getTimeInJob() % MO_JOB_INCR == 0) {
				int jobClass = job.getJobClass();
				if (player.getSkills().getSkill(jobClass) < job.getMinSkills().getSkill(jobClass)) {
					/* player hasn't yet maxed on the jobClass at this place */
					player.getSkills().boostStat(Job.jobSkillCrossRef[jobClass]);
					td.add("You've improved your job skill, " + Skills.SKILLS[Job.jobSkillCrossRef[jobClass]]);
				} else {
					td.add("This job can teach you no more about " + Skills.SKILLS[Job.jobSkillCrossRef[jobClass]]);
				}
				
				/* always boost a random skill when staying in job */
				int randSkill = Rand.get(Skills.SKILLS.length);
				player.getSkills().boostStat(randSkill);
				td.add("Random skill bonus, " + Skills.SKILLS[randSkill] + " +1");
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
			books += Job.UNEMPLY_MON_SALARY;
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

		/* BAD_CHANCE - influenced by spending too much time in same job */
		System.out.println("bad chance: " + (BAD_CHANCE
				+ (TIME_IN_JOB_MULTIPLY * player.getTimeInJob())));
		if (Rand.getSuccessForOdds(BAD_CHANCE
				+ (TIME_IN_JOB_MULTIPLY * player.getTimeInJob()))) {
			IEvent e;
			if (Rand.getSuccessForOdds(FIFTY_FIFTY)) {
				e = new FlatEvent();
			} else {
				e = new MultiEvent();
			}
			td.add(e.doEvent(false, player));
		}

		/* GOOD_CHANCE */
		if (Rand.getSuccessForOdds(GOOD_CHANCE)) {
			IEvent e;
			if (Rand.getSuccessForOdds(FIFTY_FIFTY)) {
				e = new FlatEvent();
			} else {
				e = new MultiEvent();
			}
			td.add(e.doEvent(true, player));
		}

	}

}
