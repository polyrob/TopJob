package com.threerings.challenge.screens;

import static playn.core.PlayN.assets;
import playn.core.Image;
import react.UnitSlot;
import tripleplay.game.ScreenStack;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;

import com.threerings.challenge.style.TopJobStyle;


public class MenuScreen extends AbstractScreen {

	public MenuScreen(ScreenStack stack) {
		super(stack);
		System.out.println("MenuScreen Constructor()");
	}

	@Override
	public void wasShown() {
		System.out.println("MenuScreen wasShown()");

		root = iface.createRoot(AxisLayout.vertical().gap(15),
				SimpleStyles.newSheet(), layer);

		Image bgImage = assets().getImage("images/hireme.jpg");
		root.addStyles(Style.BACKGROUND.is(Background.image(bgImage)));
		root.setSize(width(), height());

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
