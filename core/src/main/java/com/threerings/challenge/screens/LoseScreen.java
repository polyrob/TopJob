package com.threerings.challenge.screens;

import static playn.core.PlayN.assets;
import playn.core.Image;
import tripleplay.game.ScreenStack;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.layout.AxisLayout;

import com.threerings.challenge.player.Turn;

public class LoseScreen extends AbstractScreen {
	Label resultLabel;
	Image bgImage;

	public LoseScreen(ScreenStack stack, Turn turn) {
		super(stack);
		resultLabel = new Label("You're out of money, you bum! Game over.");
		bgImage = assets().getImage("images/game_over.jpg");
	}

	@Override
	protected String name() {
		return "Game Over";
	}

	@Override
	protected String title() {
		return "Game Over";
	}

	@Override
	protected Group createIface() {

		Group bgGrp = new Group(AxisLayout.horizontal())
				.add(new Label(bgImage));
		Group root = new Group(AxisLayout.vertical()).add(bgGrp, resultLabel);

		return root;
	}

}
