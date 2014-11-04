package com.threerings.challenge.screens;

import static playn.core.PlayN.assets;
import playn.core.Image;
import react.UnitSlot;
import tripleplay.game.ScreenStack;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.layout.AxisLayout;

import com.threerings.challenge.job.Job;


public class ResultScreen extends AbstractScreen {

	HomeScreen home;
	Job job;

	Label resultLabel;
	Image bgImage;

	public ResultScreen(ScreenStack stack, HomeScreen home, Job job,
			boolean offer) {
		super(stack);
		this.job = job;
		this.home = home;

		if (offer) {
			resultLabel = new Label("Congratulations! Welcome to "
					+ Job.COMPANIES[job.getCompany()] + "!");
			bgImage = assets().getImage("images/gotjob.png");
		} else {
			resultLabel = new Label("We're seeking other applicants. Get lost!");
			bgImage = assets().getImage("images/rejected.png");
		}
	}

	@Override
	protected String name() {
		return "Interview Results";
	}

	@Override
	protected String title() {
		return "Interview Results";
	}

	@Override
	protected Group createIface() {

		Button homeBtn = new Button("Back to home").onClick(new UnitSlot() {
			@Override
			public void onEmit() {
				goHome();
			}
		});
		
		Group bgGrp = new Group(AxisLayout.horizontal()).add(new Label(bgImage));
		Group root = new Group(AxisLayout.vertical()).add(bgGrp, resultLabel, homeBtn);

		return root;
	}

	private void goHome() {
		home.refreshHome(null);
		stack.popTo(home);
		
	}

}
