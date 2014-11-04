//
// Triple Play - utilities for use in PlayN-based games
// Copyright (c) 2011-2014, Three Rings Design, Inc. - All rights reserved.
// http://github.com/threerings/tripleplay/blob/master/LICENSE

package com.threerings.challenge.screens;

import static playn.core.PlayN.assets;
import playn.core.Image;
import playn.core.PlayN;
import react.Function;
import react.UnitSlot;
import tripleplay.game.ScreenStack;
import tripleplay.ui.Background;
import tripleplay.ui.Box;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;
import tripleplay.ui.ValueLabel;
import tripleplay.ui.layout.AxisLayout;

import com.threerings.challenge.job.Interview;
import com.threerings.challenge.job.Job;
import com.threerings.challenge.player.Player;
import com.threerings.challenge.stats.Skills;
import com.threerings.challenge.style.TopJobStyle;
import com.threerings.challenge.util.Formatter;
import com.threerings.challenge.util.Rand;

/**
 * Tests/demonstrates screen-related things.
 */
public class InterviewScreen extends AbstractScreen {

	final AbstractScreen interviewScreen = this;
	final HomeScreen home;

	Interview interview;
	Button exaggerateBtn;
	Job j;
	Player p;

	/* UI Components */
	Button lieBtn;
	react.Value<Float> chance;

	final Box chanceBox = new Box();

	protected static final Styles GREENBG = Styles.make(Style.BACKGROUND
			.is(Background.solid(0xFF99CC66).inset(5)));
	protected static final float EXAGGERATE_CHANCE = 0.75f;
	protected static final float LIE_CHANCE = 0.5f;

	public InterviewScreen(ScreenStack stack, HomeScreen home, Job j,
			Player player, Interview interview) {
		super(stack);
		this.interview = interview;
		this.j = j;
		this.p = player;
		// this.interview = new Interview(j, player);
		this.home = home;

	}

	@Override
	protected String name() {
		return "Interview";
	}

	@Override
	protected String title() {
		return "Interview";
	}

	@Override
	protected Group createIface() {
		chanceBox.set(new Label(Formatter.getPercentageFloat(interview
				.getCurrentChance())));

		Group chanceGrp = new Group(AxisLayout.horizontal()).add(new Label(
				"Estimated Chance: "), chanceBox);

		Group midGrp = new Group(AxisLayout.vertical()).add(new Group(
				AxisLayout.horizontal()).add(Skills.getSkillHeadersGroup(), j
				.getMinSkills().getSkillsDisplayGrp("Req"), p.getSkills()
				.getSkillsDisplayGrp("You"),
				new Label(getItvwrImage(j.getCompany()))), chanceGrp);

		Button commitBtn = new Button("Commit").onClick(new UnitSlot() {
			@Override
			public void onEmit() {
				if (Rand.getSuccessForOdds(interview.getCurrentChance())) {
					PlayN.log().info("Got the JOB!");
					p.setCurrentJob(j);
					// stack.remove(interviewScreen);
					stack.push(new ResultScreen(stack, home, j, true));

				} else {
					PlayN.log().info("Didn't get the job");
					stack.push(new ResultScreen(stack, home, j, false));
					// stack.remove(interviewScreen);
				}

			}
		});

		exaggerateBtn = new Button("Exaggerate").onClick(new UnitSlot() {
			@Override
			public void onEmit() {
				if (Rand.getSuccessForOdds(EXAGGERATE_CHANCE)) {
					PlayN.log()
							.info("Successful Exaggeration. Increasing chance modifier");
					interview.boostChanceModifier(1.1f);
					chanceBox.destroyContents().set(
							new Label(Formatter.getPercentageFloat(interview
									.getCurrentChance())));
				} else {
					PlayN.log().info("FAILED to exaggerate.");
					interview.boostChanceModifier(0.5f);
					chanceBox.destroyContents().set(
							new Label(Formatter.getPercentageFloat(interview
									.getCurrentChance())));
					exaggerateBtn.setEnabled(false);
				}

			}
		});

		lieBtn = new Button("Lie").onClick(new UnitSlot() {
			@Override
			public void onEmit() {
				if (Rand.getSuccessForOdds(LIE_CHANCE)) {
					PlayN.log().info(
							"Successful lie. Increasing chance modifier");
					interview.boostChanceModifier(1.3f);
					chanceBox.destroyContents().set(
							new Label(Formatter.getPercentageFloat(interview
									.getCurrentChance())));
				} else {
					PlayN.log().info("FAILED lie. Doomed.");
					interview.boostChanceModifier(0.1f);
					chanceBox.destroyContents().set(
							new Label(Formatter.getPercentageFloat(interview
									.getCurrentChance())));
					lieBtn.setEnabled(false);
				}
			}
		});

		Group optionsGrp = new Group(AxisLayout.horizontal().gap(10), GREENBG)
				.add(commitBtn, exaggerateBtn, lieBtn);

		Group root = new Group(AxisLayout.vertical())
				.add(new Label(j.getJobDisplayName())
						.addStyles(TopJobStyle.label32), midGrp, chanceGrp,
						optionsGrp);

		return root;
	}

	private Image getItvwrImage(int company) {
		String prefix = "images/interviewer/";
		String suffix = ".jpg";

		Image interviewerImage = assets().getImage(prefix + company + suffix);
		return interviewerImage;
	}

	protected Function<Float, String> FORMATTER = new Function<Float, String>() {
		public String apply(Float value) {
			return String.valueOf(value.intValue());
		}
	};

}
