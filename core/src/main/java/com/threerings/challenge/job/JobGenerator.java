package com.threerings.challenge.job;

import com.threerings.challenge.stats.Skills;
import com.threerings.challenge.util.Rand;

public class JobGenerator {

	private static final int MIN_BASE_SALARY = 30_000;

	public Job getJob() {
		Job job = new Job();
		job.setCompany(Rand.get(Job.COMPANIES.length));
		job.setJobLevel(Rand.get(Job.JOBLEVEL.length));
		job.setJobClass(Rand.get(Job.JOBCLASS.length));
		if (job.getJobLevel() == 4) {
			job.setJobSuffix(Rand.get(Job.DIRECTORSUFFIX.length));
		} else {
			job.setJobSuffix(Rand.get(Job.JOBSUFFIX.length));
		}
		
		job.setSalary(getSalaryForJob(job));
		job.setMinSkills(getSkillsForJob(job));
		
		return job;
	}
	
	private Skills getSkillsForJob(Job job) {
		Skills s = new Skills();
		/*  boost target class skill plus random skill for each level*/
		for (int i = 0; i <= job.getJobLevel(); i++) {
			s.boostStat(Job.jobSkillCrossRef[job.getJobClass()]);
			s.boostStat(Rand.get(Skills.SKILLS.length));
			s.boostStat(Rand.get(Skills.SKILLS.length));
			s.boostStat(Rand.get(Skills.SKILLS.length));
		}
		return s;
	}

	private int getSalaryForJob(Job j) {
		int s = MIN_BASE_SALARY;
		s += j.getCompany()*6000;
		s += j.getJobLevel()*16000;
		s += j.getJobClass()*5000;
		return s;
	}

}

