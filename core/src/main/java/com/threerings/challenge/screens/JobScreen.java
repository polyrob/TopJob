package com.threerings.challenge.screens;

import java.util.List;

import playn.core.PlayN;
import react.UnitSlot;
import tripleplay.game.ScreenStack;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;
import tripleplay.ui.layout.AxisLayout;

import com.threerings.challenge.job.Interview;
import com.threerings.challenge.job.Job;
import com.threerings.challenge.player.Player;
import com.threerings.challenge.util.Formatter;

/**
 * Display today's job listings.
 * 
 * @author Robbie Scheidt
 *
 */
public class JobScreen extends AbstractScreen {

	/*
	 * Need to pass a reference to home so it's available at the end of the
	 * stack. Doesn't seem to work letting me popTo or go to top.
	 */
	private HomeScreen home;

	private List<Job> jobs;
	private Player player;

	private Button jobApplyBtn;

	protected static final Styles GREENBG = Styles.make(Style.BACKGROUND
			.is(Background.solid(0xFF99CC66).inset(5)));

	public JobScreen(ScreenStack stack, HomeScreen home, List<Job> list,
			Player player) {
		super(stack);
		this.home = home;
		this.jobs = list;
		this.player = player;
	}

	@Override
	protected Group createIface() {
		Group root = new Group(AxisLayout.vertical());

		if (jobs == null || jobs.size() == 0) {
			root.add(new Label("There are no jobs available today."));
			return root;
		}

		System.out.println("There are " + jobs.size() + " jobs today.");
		for (final Job j : jobs) {

			Button jobInfoBtn = new Button("Info").onClick(new UnitSlot() {
				@Override
				public void onEmit() {
					PlayN.log().info("Job Info Button Clicked.");
					final AbstractScreen detailScreen = new JobDetailScreen(
							stack, j);
					stack.push(detailScreen);
					detailScreen.back.clicked().connect(new UnitSlot() {
						public void onEmit() {
							stack.remove(detailScreen);
						}
					});
				}
			});

			final Interview interview = new Interview(j, player);

			if (interview.getCurrentChance() > 0f) {
				jobApplyBtn = new Button("Apply").onClick(new UnitSlot() {
					@Override
					public void onEmit() {
						PlayN.log().info("Job Apply Button Clicked.");

						jobs.remove(j);
						final AbstractScreen interviewScreen = new InterviewScreen(
								stack, home, j, player, interview);
						stack.push(interviewScreen);

						interviewScreen.back.setEnabled(false);
					}
				});
			} else {
				jobApplyBtn = new Button("Not Eligible");
				jobApplyBtn.setEnabled(false);
			}

			Group jobInfoGrp = new Group(AxisLayout.horizontal()).add(
					new Label(Formatter.formatMoney(j.getSalary())),
					jobInfoBtn, jobApplyBtn);
			Group jobHeaderGrp = new Group(AxisLayout.vertical().gap(10),
					GREENBG).add(new Label(j.getJobDisplayName()), jobInfoGrp);
			root.add(jobHeaderGrp);
		}
		return root;
	}

	@Override
	protected String name() {
		return "Jobs";
	}

	@Override
	protected String title() {
		return "Today's Jobs";
	}
}
