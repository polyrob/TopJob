//
// Triple Play - utilities for use in PlayN-based games
// Copyright (c) 2011-2014, Three Rings Design, Inc. - All rights reserved.
// http://github.com/threerings/tripleplay/blob/master/LICENSE

package com.threerings.challenge.screens;

import static playn.core.PlayN.assets;
import playn.core.Image;
import playn.core.PlayN;
import react.UnitSlot;
import tripleplay.game.ScreenStack;
import tripleplay.ui.Box;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Shim;
import tripleplay.ui.layout.AxisLayout;

import com.threerings.challenge.job.Job;
import com.threerings.challenge.player.Player;
import com.threerings.challenge.player.Turn;
import com.threerings.challenge.player.TurnData;
import com.threerings.challenge.stats.Skills;
import com.threerings.challenge.style.TopJobStyle;
import com.threerings.challenge.util.Formatter;


/**
 * Tests/demonstrates screen-related things.
 */
public class HomeScreen extends AbstractScreen {

	private Player player;
	private Turn turn;

	/* UI Components */
	HomeScreen home = this;
	Button jobSearch;
	final Box currentJobBox = new Box();
	final Box savingsBox = new Box();
	final Box expensesBox = new Box();
	final Box salaryBox = new Box();
	final Box turnBox = new Box();
	final Box msgBox = new Box();

	public HomeScreen(ScreenStack stack) {
		super(stack);

		/* init game objects */
		player = new Player();
		turn = new Turn(player);

		currentJobBox.set(new Label("Unemployed"));
		salaryBox.set(new Label("$0"));
		expensesBox.set(new Label(Formatter.formatMoney(player
				.getMonthlyExpenses())));
		savingsBox.set(new Label(Formatter.formatMoney(player
				.getSavings())));
		turnBox.set(new Label(String.valueOf(turn.getTurn())));
	}

	@Override
	protected String name() {
		return "Start a new game";
	}

	@Override
	protected String title() {
		return "Top Job";
	}

	@Override
	protected Group createIface() {

		Group headerGroup = new Group(AxisLayout.horizontal()).add(new Label(
				"Month: "), turnBox, new Shim(50, 10));
		headerGroup.add(new Label("Savings: "), savingsBox, new Shim(50, 10),
				new Label("Mo. Expenses: "), expensesBox);

		Group jobGroup = new Group(AxisLayout.horizontal()).add(currentJobBox,
				new Shim(50, 10), salaryBox);

		Image jobsImage = assets().getImage("images/jobs.png");

		jobSearch = new Button("Search today's jobs").onClick(new UnitSlot() {
			@Override
			public void onEmit() {
				PlayN.log().info("Job Button Clicked.");
				final AbstractScreen jobScreen = new JobScreen(stack, home,
						turn.getJobPostings(), player);
				stack.push(jobScreen);
				jobScreen.back.clicked().connect(new UnitSlot() {
					public void onEmit() {
						stack.remove(jobScreen);
					}
				});
			}
		});

		Button nextTurn = new Button("Next Turn").onClick(new UnitSlot() {
			@Override
			public void onEmit() {
				PlayN.log().info("Turn Button Clicked.");
				TurnData td = turn.nextTurn();
				refreshHome(td);
			}
		});

		Group statsGroup = new Group(AxisLayout.horizontal());
		statsGroup.add(Skills.getSkillHeadersGroup(), player.getSkills()
				.getSkillsDisplayGrp("Skills"));

		@SuppressWarnings("deprecation")
		Group searchGrp = new Group(AxisLayout.vertical().gap(15),
				TopJobStyle.GREENBG).add(new Label(jobsImage), jobSearch);

		Group midGroup = new Group(AxisLayout.horizontal().gap(15),
				TopJobStyle.BLUEBG).add(statsGroup, searchGrp);

		Group root = new Group(AxisLayout.vertical()).add(headerGroup,
				jobGroup, midGroup, nextTurn, msgBox);

		return root;
	}

	/* how the heck do you use listeners with this framework! */
	public void refreshHome(TurnData td) {
		PlayN.log().info("Refreshing home PlayN.");
		if (player.getSavings() < 0) {
			final AbstractScreen loseScreen = new LoseScreen(stack, turn);
			stack.push(loseScreen);
			loseScreen.back.clicked().connect(new UnitSlot() {
				public void onEmit() {
					stack.remove(loseScreen);
					stack.remove(home);
				}
			});
		} else {
			Job job = player.getCurrentJob();
			if (job != null) {
				currentJobBox.destroyContents().set(
						new Label(job.getJobDisplayName()));
				salaryBox.destroyContents()
						.set(new Label(Formatter.formatMoney(job
								.getSalary())));
			}

			savingsBox.destroyContents()
					.set(new Label(Formatter.formatMoney(player
							.getSavings())));
			expensesBox.destroyContents().set(
					new Label(Formatter.formatMoney(player
							.getMonthlyExpenses())));

			if (td != null) {
				turnBox.destroyContents().set(
						new Label(String.valueOf(td.getTurn())));
				msgBox.destroyContents().set(td.getNtfcGrp());
			}
		}
	}

	public void disableJobSearch() {
		jobSearch.setEnabled(false);
	}

}
