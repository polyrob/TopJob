//
// Triple Play - utilities for use in PlayN-based games
// Copyright (c) 2011-2014, Three Rings Design, Inc. - All rights reserved.
// http://github.com/threerings/tripleplay/blob/master/LICENSE

package com.threerings.challenge.screens;

import static playn.core.PlayN.assets;

import com.threerings.challenge.style.TopJobStyle;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.PlayN;
import playn.core.util.Clock;
import react.UnitSlot;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.Shim;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.ui.layout.TableLayout;

/**
 * Displays a top-level menu of our various demo screens.
 */
public class MenuScreen extends AbstractScreen {
//	protected Root root;
//	protected final ScreenStack stack;

	public MenuScreen(ScreenStack stack) {
		super(stack);
		System.out.println("MenuScreen Constructor()");
//		stack = stack;
	}

	@Override
	public void wasShown() {
		System.out.println("MenuScreen wasShown()");
		// // create and add background image layer
		// Image i = assets().getImage("images/bg.png");
		// ImageLayer bgLayer = PlayN.graphics().createImageLayer(i);
		// PlayN.graphics().rootLayer().add(bgLayer);

		root = iface.createRoot(AxisLayout.vertical().gap(15),
				SimpleStyles.newSheet(), layer);

		Image bgImage = assets().getImage("images/hireme.jpg");
		root.addStyles(Style.BACKGROUND.is(Background.image(bgImage)));
		root.setSize(width(), height());

//		Styles backdrop = Styles.make(Style.BACKGROUND.is(Background.solid(
//				0x88FFFFFF).inset(5)));

		root.add(new Group(AxisLayout.horizontal(), TopJobStyle.GREENBG).add(new Label(
				"Top Job").addStyles(Style.FONT.is(AbstractScreen.SPLASH_FONT),
				Style.COLOR.is(0xFFFF0000), Style.TEXT_EFFECT.shadow)));

		Group grid = new Group(AxisLayout.horizontal().stretchByDefault(), TopJobStyle.BLUEBG);
		root.add(grid);

		
		
		grid.add(new Button("Start a New Game").onClick(new UnitSlot() {
			public void onEmit() {
				final AbstractScreen homeScreen = new HomeScreen(stack);
				
				stack.push(homeScreen);
				homeScreen.back.clicked().connect(new UnitSlot() {
					public void onEmit() {
						stack.remove(homeScreen);
					}
				});
			}
		}));
		
		
		grid.add(new Button("High Scores").onClick(new UnitSlot() {
			public void onEmit() {
				final AbstractScreen hsScreen = new HighScoresScreen(stack);
				stack.push(hsScreen);
				hsScreen.back.clicked().connect(new UnitSlot() {
					public void onEmit() {
						stack.remove(hsScreen);
					}
				});
			}
		}));

	}

	@Override
	public void wasHidden() {
		super.wasHidden();
		System.out.println("wasHidden()");
		iface.destroyRoot(root);
	}

	@Override
	protected String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String title() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Group createIface() {
		// TODO Auto-generated method stub
		return null;
	}

}
