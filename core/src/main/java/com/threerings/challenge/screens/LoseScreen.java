//
// Triple Play - utilities for use in PlayN-based games
// Copyright (c) 2011-2014, Three Rings Design, Inc. - All rights reserved.
// http://github.com/threerings/tripleplay/blob/master/LICENSE

package com.threerings.challenge.screens;

import static playn.core.PlayN.assets;
import playn.core.Image;
import playn.core.PlayN;
import react.UnitSlot;

import com.threerings.challenge.core.Topjob;
import com.threerings.challenge.job.Job;
import com.threerings.challenge.player.Turn;
import com.threerings.challenge.util.Formatter;

import tripleplay.game.ScreenStack;
import tripleplay.ui.Background;
import tripleplay.ui.Box;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Shim;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;
import tripleplay.ui.layout.AxisLayout;

/**
 * Tests/demonstrates screen-related things.
 */
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
