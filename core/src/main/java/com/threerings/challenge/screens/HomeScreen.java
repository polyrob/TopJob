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
import com.threerings.challenge.player.TurnManager;
import com.threerings.challenge.player.TurnData;
import com.threerings.challenge.sound.SoundManager;
import com.threerings.challenge.stats.Skills;
import com.threerings.challenge.style.TopJobStyle;
import com.threerings.challenge.util.Formatter;

/**
 * Home Screen is where user starts their day.
 * This screen is responsible for displaying current data to user
 * and buttons navigating to jobs, inventory, etc.
 * @author Robbie Scheidt
 *
 */
public class HomeScreen extends AbstractScreen {

	private Player player;
	private TurnManager turn;

	/* UI Components */
	private HomeScreen home = this;
	private Button jobSearch;
	final Box currentJobBox = new Box();
	final Box playerSkillsBox = new Box();
	final Box savingsBox = new Box();
	final Box expensesBox = new Box();
	final Box salaryBox = new Box();
	final Box turnBox = new Box();
	final Box msgBox = new Box();
	

	public HomeScreen(ScreenStack stack) {
		super(stack);

		/* init game objects */
		player = new Player();
		turn = new TurnManager(player);

		currentJobBox.set(new Label("Unemployed"));
		salaryBox.set(new Label("$" + Job.UNEMPLY_MON_SALARY));
		expensesBox.set(new Label(Formatter.formatMoney(player
				.getMonthlyExpenses())));
		savingsBox.set(new Label(Formatter.formatMoney(player
				.getSavings())));
		turnBox.set(new Label(String.valueOf(turn.getTurn())));
		playerSkillsBox.set(player.getSkills()
				.getSkillsDisplayGrp("Skills"));
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
				SoundManager.CLICK.play();
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
				TurnData td = turn.nextTurn();
				jobBtnEnable(true);
				refreshHome(td);
			}
		});

		Group statsGroup = new Group(AxisLayout.horizontal());
		statsGroup.add(Skills.getSkillHeadersGroup(), playerSkillsBox);

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
			SoundManager.TURN.play();
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
			playerSkillsBox.destroyContents().set(player.getSkills()
				.getSkillsDisplayGrp("Skills"));

			if (td != null) {
				turnBox.destroyContents().set(
						new Label(String.valueOf(td.getTurn())));
				msgBox.destroyContents().set(td.getNtfcGrp());
			}
		}
	}

	public void jobBtnEnable(boolean val) {
		jobSearch.setEnabled(val);	
	}

}
