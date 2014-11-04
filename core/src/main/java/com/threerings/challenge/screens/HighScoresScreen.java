package com.threerings.challenge.screens;

import tripleplay.game.ScreenStack;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Shim;
import tripleplay.ui.layout.AxisLayout;

import com.threerings.challenge.style.TopJobStyle;
import com.threerings.challenge.util.Formatter;


public class HighScoresScreen extends AbstractScreen {

	public HighScoresScreen(ScreenStack stack) {
		super(stack);
	}

	@Override
	protected String name() {
		return "High Scores";
	}

	@Override
	protected String title() {
		return "High Scores";
	}

	@Override
	protected Group createIface() {

		/* Get highscores and build Group layout */
		Group namesGrp = new Group(AxisLayout.vertical());
		for (int i = 1; i <= 8; i++) {
			namesGrp.add(new Label("Player " + i));
		}

		Group scoresGrp = new Group(AxisLayout.vertical());
		for (int i = 1; i <= 8; i++) {
			scoresGrp.add(new Label(Formatter.formatMoney(50000 - i * 5500)));
		}
		Group root = new Group(AxisLayout.vertical()).add(new Group(AxisLayout
				.horizontal().gap(15), TopJobStyle.BLUEBG).add(namesGrp, new Shim(50, 10), scoresGrp));

		return root;
	}

}
