package com.threerings.challenge.stats;

import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Shim;
import tripleplay.ui.layout.AxisLayout;

import com.threerings.challenge.job.Job;
import com.threerings.challenge.util.Rand;

public class Skills {

	public static final int ANALYTICS = 0;
	public static final int COMMUNICATION = 1;
	public static final int CORE = 2;
	public static final int ENTERPRISE = 3;
	public static final int FRONTEND = 4;
	public static final int GAME = 5;
	public static final int HARDWARE = 6;
	public static final int MOBILE = 7;
	public static final int PROBLEM_SOLVING = 8;
	public static final int WEB = 9;

	public static final String[] SKILLS = { "Analytics", "Communication",
			"Core Languages", "Enterprise", "Front End Development",
			"Game Development", "Hardware and API", "Mobile Development",
			"Problem Solving", "Web Development" };


	private int[] skills = new int[10];

	public int getSkill(int i) {
		return skills[i];
	}

	public void boostStat(int skill) {
		skills[skill]++;
	}

	public Group getSkillsDisplayGrp(String header) {		
		Group vGrp = new Group(AxisLayout.vertical());
		vGrp.add(new Label(header));
		vGrp.add(new Label(String.valueOf(getSkill(Skills.ANALYTICS))));
		vGrp.add(new Label(String.valueOf(getSkill(Skills.COMMUNICATION))));
		vGrp.add(new Label(String.valueOf(getSkill(Skills.CORE))));
		vGrp.add(new Label(String.valueOf(getSkill(Skills.ENTERPRISE))));
		vGrp.add(new Label(String.valueOf(getSkill(Skills.FRONTEND))));
		vGrp.add(new Label(String.valueOf(getSkill(Skills.GAME))));
		vGrp.add(new Label(String.valueOf(getSkill(Skills.HARDWARE))));
		vGrp.add(new Label(String.valueOf(getSkill(Skills.MOBILE))));
		vGrp.add(new Label(String.valueOf(getSkill(Skills.PROBLEM_SOLVING))));
		vGrp.add(new Label(String.valueOf(getSkill(Skills.WEB))));

		return vGrp;
//		return new Group(AxisLayout.horizontal()).add(hGrp, new Shim(40,
//				10), vGrp);
	}
	
	public static Group getSkillHeadersGroup() {
		Group hGrp = new Group(AxisLayout.vertical());
		hGrp.add(new Shim(10, 10));
		hGrp.add(new Label("Analytics"));
		hGrp.add(new Label("Communication"));
		hGrp.add(new Label("Core Language"));
		hGrp.add(new Label("Enterprise"));
		hGrp.add(new Label("Front End"));
		hGrp.add(new Label("Games"));
		hGrp.add(new Label("Hardware/API"));
		hGrp.add(new Label("Mobile"));
		hGrp.add(new Label("Problem Solving"));
		hGrp.add(new Label("Web"));
		return hGrp;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < SKILLS.length; i++) {
			sb.append("   ").append(SKILLS[i]).append(":").append(skills[i])
					.append('\n');
		}
		return sb.toString();
	}

}
