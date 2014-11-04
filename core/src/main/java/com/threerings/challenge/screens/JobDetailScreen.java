package com.threerings.challenge.screens;

import tripleplay.game.ScreenStack;
import tripleplay.ui.Background;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Shim;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;
import tripleplay.ui.layout.AxisLayout;

import com.threerings.challenge.job.Job;
import com.threerings.challenge.stats.Skills;
import com.threerings.challenge.style.TopJobStyle;
import com.threerings.challenge.util.Formatter;


/**
 * Display minimum job requirements/skills
 * @author Robbie Scheidt
 *
 */
public class JobDetailScreen extends AbstractScreen {

	private Job job;

	protected static final Styles GREENBG = Styles.make(Style.BACKGROUND
			.is(Background.solid(0xFF99CC66).inset(5)));

	public JobDetailScreen(ScreenStack stack, Job j) {
		super(stack);
		this.job = j;
	}

	@Override
	protected Group createIface() {
		
		Group payGrp = new Group(AxisLayout.horizontal()).add(new Label("Annual Salary"), new Shim(20, 10), new Label(Formatter.formatMoney(job.getSalary())));
		
		Group root = new Group(AxisLayout.vertical()).add(new Label(job.getJobDisplayName()).addStyles(TopJobStyle.label32), payGrp.addStyles(TopJobStyle.lable24), new Label("Minimum Skills Required"));

		// TODO: this is a static method now and doesn't need to be here.
		Group attribGrp = new Group(AxisLayout.vertical());
		attribGrp.add(new Label("Analytics"));
		attribGrp.add(new Label("Communication"));
		attribGrp.add(new Label("Core Language"));
		attribGrp.add(new Label("Enterprise"));
		attribGrp.add(new Label("Front End"));
		attribGrp.add(new Label("Games"));
		attribGrp.add(new Label("Hardware and API"));
		attribGrp.add(new Label("Mobile"));
		attribGrp.add(new Label("Problem Solving"));
		attribGrp.add(new Label("Web"));

		Group valueGrp = new Group(AxisLayout.vertical());	
		valueGrp.add(new Label(String.valueOf(job.getMinSkills().getSkill(Skills.ANALYTICS))));
		valueGrp.add(new Label(String.valueOf(job.getMinSkills().getSkill(Skills.COMMUNICATION))));
		valueGrp.add(new Label(String.valueOf(job.getMinSkills().getSkill(Skills.CORE))));
		valueGrp.add(new Label(String.valueOf(job.getMinSkills().getSkill(Skills.ENTERPRISE))));
		valueGrp.add(new Label(String.valueOf(job.getMinSkills().getSkill(Skills.FRONTEND))));
		valueGrp.add(new Label(String.valueOf(job.getMinSkills().getSkill(Skills.GAME))));
		valueGrp.add(new Label(String.valueOf(job.getMinSkills().getSkill(Skills.HARDWARE))));
		valueGrp.add(new Label(String.valueOf(job.getMinSkills().getSkill(Skills.MOBILE))));
		valueGrp.add(new Label(String.valueOf(job.getMinSkills().getSkill(Skills.PROBLEM_SOLVING))));
		valueGrp.add(new Label(String.valueOf(job.getMinSkills().getSkill(Skills.WEB))));
		
		root.add(new Group(AxisLayout.horizontal()).add(attribGrp, new Shim(50, 10), valueGrp));

		return root;
	}


	@Override
	protected String name() {
		return "Job Detail";
	}

	@Override
	protected String title() {
		return "Job Detail";
	}

}
