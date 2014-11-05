package com.threerings.challenge.job;

import com.threerings.challenge.player.Player;
import com.threerings.challenge.stats.Skills;
import com.threerings.challenge.util.Rand;


/**
 * Class to handle Interview data and chance calculations.
 * @author Robbie Scheidt
 *
 */
public class Interview {

	private static final float BASE_CHANCE = 0.3f;
	private Job job;
	private Player player;
	
	private float currentChance;
	private float chanceModifier;
	
	public Interview(Job job, Player player) {
		super();
		this.job = job;
		this.player = player;
		
		/* make chanceModifier initial semi random */
		chanceModifier = 0.9f + Rand.getf()/5;
		chanceModifier -= (float) job.getCompany() / 100; // better companies are harder
		
		caclulateChance();
		//System.out.println("Chance for " + job.getJobDisplayName() + ": " + currentChance);
	}
	
	
	private void caclulateChance() {
		currentChance = BASE_CHANCE;
		Skills js = job.getMinSkills();
		Skills ps = player.getSkills();
		
		/* reduce/increase chances based on skills compared to job minimums */ 
		for (int i = 0; i < Skills.SKILLS.length; i++) {
			int delta = ps.getSkill(i) - js.getSkill(i);
			currentChance += (float) delta / 50;
		}
		
		currentChance += getMainSkillFactor(js, ps);
		if (currentChance < 0) currentChance = 0f;
		if (currentChance > 1.0f) currentChance = 0.5f;
	}

	
	private float getMainSkillFactor(Skills js, Skills ps) {
		int jobClass = job.getJobClass();
		int pMainSkill = ps.getSkill(Job.jobSkillCrossRef[jobClass]);
		int jMainSkill =  js.getSkill(Job.jobSkillCrossRef[jobClass]);
		
		return 0.1f * (pMainSkill - jMainSkill);
	}
	
	public float getCurrentChance() {
		System.out.println("Current Chance: " + currentChance +".  Modifier: " + chanceModifier );
		return currentChance * chanceModifier;
	}

	public float getChanceModifier() {
		return chanceModifier;
	}

	public void boostChanceModifier(float chanceModifier) {
		this.chanceModifier *= chanceModifier;
	}

	
}
