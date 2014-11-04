package com.threerings.challenge.job;

import com.threerings.challenge.stats.Skills;

public class Job {

	public static String[] COMPANIES = {"BSOD Inc.", "SpiWare", "BigBug Software", "LackLuster Technologies", "Bethesdo", "Wahoo", "IPM", "Banana Computers", "Macrosoft", "Goodle"};
	public static String[] JOBLEVEL = {"Intern", "Entry Level", "Intermediate", "Senior", "Lead", "Director of"};
	public static String[] JOBCLASS = {"Software", "Game", "Web", "Mobile", "Front End", "API", "Enterprise"};
	public static String[] JOBSUFFIX = {"Engineer", "Developer", "Analyst", "Programmer" , "Architect"};
	public static String[] DIRECTORSUFFIX = {"Engineering", "Development", "Analysis", "Programming", "Architecture"};
	
	public static int[] jobSkillCrossRef = {2, 5, 9, 7, 4, 6, 3};

	private int company;
	private int jobClass;
	private int jobLevel;
	private int jobSuffix;
	
	private int salary;
	private float satisfaction;
	
	private Skills minSkills;
	

	public int getCompany() {
		return company;
	}

	public void setCompany(int company) {
		this.company = company;
	}

	public int getJobClass() {
		return jobClass;
	}

	public void setJobClass(int jobClass) {
		this.jobClass = jobClass;
	}

	public int getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(int jobLevel) {
		this.jobLevel = jobLevel;
	}

	public int getJobSuffix() {
		return jobSuffix;
	}

	public void setJobSuffix(int jobSuffix) {
		this.jobSuffix = jobSuffix;
	}

	public Skills getMinSkills() {
		return minSkills;
	}

	public void setMinSkills(Skills minSkills) {
		this.minSkills = minSkills;
	}
	
	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	public float getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(float satisfaction) {
		this.satisfaction = satisfaction;
	}

	public String getJobDisplayName() {
		StringBuilder sb = new StringBuilder();
		sb.append(JOBLEVEL[jobLevel]).append(" ").append(JOBCLASS[jobClass]).append(" ");
		if (jobLevel == 5) {
			sb.append(DIRECTORSUFFIX[jobSuffix]);
		} else {
			sb.append(JOBSUFFIX[jobSuffix]);
		}
		sb.append(" with ").append(COMPANIES[company]);
		return sb.toString();
	}

	
	
}
