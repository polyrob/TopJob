package com.threerings.challenge.core;

import playn.core.Game;
import playn.core.PlayN;
import playn.core.util.Clock;
import tripleplay.game.ScreenStack;

import com.threerings.challenge.screens.MenuScreen;


/**
 * Starting Game class.
 * Builds ScreenStack and gives it to the MenuScreen.
 * @author Robbie Scheidt
 *
 */
public class Topjob extends Game.Default {
	
	public static final int UPDATE_RATE = 30;

	public Topjob() {
		super(UPDATE_RATE); 
	}
	
    protected final Clock.Source _clock = new Clock.Source(UPDATE_RATE);
    
    @Override public void init () {
    	 stack.push(new MenuScreen(stack));
    }

    @Override public void update (int delta) {
        _clock.update(delta);
        stack.update(delta);
    }

    @Override public void paint (float alpha) {
        _clock.paint(alpha);
        stack.paint(_clock);
    }
    
    protected final ScreenStack stack = new ScreenStack() {
        @Override protected void handleError (RuntimeException error) {
            PlayN.log().warn("Screen failure", error);
        }
        @Override protected Transition defaultPushTransition () {
            return slide();
        }
        @Override protected Transition defaultPopTransition () {
            return slide().right();
        }
    };

}
