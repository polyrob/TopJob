package com.threerings.challenge.screens;

import static playn.core.PlayN.assets;
import playn.core.Image;
import tripleplay.game.ScreenStack;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.layout.AxisLayout;

import com.threerings.challenge.player.TurnManager;
import com.threerings.challenge.sound.SoundManager;

/**
 * Game over screen. Display message and back button to start over.
 * 
 * @author Robbie Scheidt
 *
 */
public class LoseScreen extends AbstractScreen {
	private Label resultLabel;
	private Image bgImage;

	public LoseScreen(ScreenStack stack, TurnManager turn) {
		super(stack);
		resultLabel = new Label("You're out of money, you bum! Game over.");
		bgImage = assets().getImage("images/game_over.jpg");
	}
	
	

	@Override
	public void wasShown() {
		super.wasShown();
		SoundManager.LOSER.play();
	}



	@Override
	protected Group createIface() {

		Group bgGrp = new Group(AxisLayout.horizontal())
				.add(new Label(bgImage));
		Group root = new Group(AxisLayout.vertical()).add(bgGrp, resultLabel);

		return root;
	}

	@Override
	protected String name() {
		return "Game Over";
	}

	@Override
	protected String title() {
		return "Game Over";
	}
}
