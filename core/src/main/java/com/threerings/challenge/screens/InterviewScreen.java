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
import tripleplay.ui.layout.AxisLayout;

import com.threerings.challenge.job.Interview;
import com.threerings.challenge.job.Job;
import com.threerings.challenge.player.Player;
import com.threerings.challenge.sound.SoundManager;
import com.threerings.challenge.stats.Skills;
import com.threerings.challenge.style.TopJobStyle;
import com.threerings.challenge.util.Formatter;
import com.threerings.challenge.util.Rand;


/**
 * Where user weights chances at getting a job and can make
 * choices to influence their odds.
 * @author Robbie Scheidt
 *
 */
public class InterviewScreen extends AbstractScreen {
	
	protected static final float EXAGGERATE_CHANCE = 0.80f;
	protected static final float LIE_CHANCE = 0.5f;
	
	final HomeScreen home;

	private Interview interview;
	private Job j;
	private Player p;

	/* UI Components */
	private Button exaggerateBtn;
	private Button lieBtn;
	final Box chanceBox = new Box();


	public InterviewScreen(ScreenStack stack, HomeScreen home, Job j,
			Player player, Interview interview) {
		super(stack);
		this.interview = interview;
		this.j = j;
		this.p = player;
		this.home = home;

	}


	@Override
	public void wasShown() {
		super.wasShown();
		SoundManager.KNOCK.play();
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

		/* Complete your interview with current odds */
		Button commitBtn = new Button("Commit").onClick(new UnitSlot() {
			@Override
			public void onEmit() {
				if (Rand.getSuccessForOdds(interview.getCurrentChance())) {
					PlayN.log().info("Got the JOB!");
					p.setCurrentJob(j);
					stack.push(new ResultScreen(stack, home, j, true));

				} else {
					PlayN.log().info("Didn't get the job");
					stack.push(new ResultScreen(stack, home, j, false));
				}

			}
		});

		/* Slightly increase odds with a high probability */
		exaggerateBtn = new Button("Exaggerate").onClick(new UnitSlot() {
			@Override
			public void onEmit() {
				if (Rand.getSuccessForOdds(EXAGGERATE_CHANCE)) {
					SoundManager.BIG_TIME.play();
					interview.boostChanceModifier(1.2f);
					chanceBox.destroyContents().set(
							new Label(Formatter.getPercentageFloat(interview
									.getCurrentChance())));
				} else {
					SoundManager.WELL_YEA.play();
					interview.boostChanceModifier(0.5f); // cut in half if unsucessful
					chanceBox.destroyContents().set(
							new Label(Formatter.getPercentageFloat(interview
									.getCurrentChance())));
					exaggerateBtn.setEnabled(false);
				}

			}
		});

		/* Significantly improve odds with low probability */
		lieBtn = new Button("Lie").onClick(new UnitSlot() {
			@Override
			public void onEmit() {
				if (Rand.getSuccessForOdds(LIE_CHANCE)) {
					SoundManager.ABSOLUTELY.play();
					interview.boostChanceModifier(2.0f);
					chanceBox.destroyContents().set(
							new Label(Formatter.getPercentageFloat(interview
									.getCurrentChance())));
				} else {
					SoundManager.WELL_UH.play();
					interview.boostChanceModifier(0.2f);
					chanceBox.destroyContents().set(
							new Label(Formatter.getPercentageFloat(interview
									.getCurrentChance())));
					lieBtn.setEnabled(false);
				}
			}
		});

		Group optionsGrp = new Group(AxisLayout.horizontal().gap(10), TopJobStyle.GREENBG)
				.add(commitBtn, exaggerateBtn, lieBtn);

		Group root = new Group(AxisLayout.vertical())
				.add(new Label(j.getJobDisplayName())
						.addStyles(TopJobStyle.label32), midGrp, chanceGrp,
						optionsGrp);

		return root;
	}

	/* Get image for the interviewer based on their company */
	private Image getItvwrImage(int company) {
		String prefix = "images/interviewer/";
		String suffix = ".jpg";

		Image interviewerImage = assets().getImage(prefix + company + suffix);
		return interviewerImage;
	}


	@Override
	protected String name() {
		return "Interview";
	}

	@Override
	protected String title() {
		return "Interview";
	}
}
