package com.threerings.challenge.screens;

import static playn.core.PlayN.graphics;
import playn.core.Font;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.Stylesheet;
import tripleplay.ui.layout.AxisLayout;


/**
 * The main screen that all others will inherit
 * @author Robbie Scheidt
 *
 */
public abstract class AbstractScreen extends UIScreen
{
    public static final Font SPLASH_FONT = graphics().createFont("Helvetica", Font.Style.PLAIN, 48);
    public static final Font TITLE_FONT = graphics().createFont("Helvetica", Font.Style.PLAIN, 32);
    
    protected final ScreenStack stack;
    protected Root root; 	// root to which all UI groups will be added
    public Button back; 	// public so it's emit() can be overridden where needed
    
    public AbstractScreen(ScreenStack stack) {
		this.stack = stack;
	}

    @Override public void wasAdded () {
        super.wasAdded();
        
        root = iface.createRoot(AxisLayout.vertical().gap(0).offStretch(), stylesheet(), layer);
        root.addStyles(Style.BACKGROUND.is(background()), Style.VALIGN.top);
        root.setSize(width(), height());
        Background bg = Background.solid(0xFFCC99FF).inset(0, 0, 5, 0);
        root.add(new Group(AxisLayout.horizontal(), Style.HALIGN.left, Style.BACKGROUND.is(bg)).add(
                      this.back = new Button("Back"),
                      new Label(title()).addStyles(Style.FONT.is(TITLE_FONT), Style.HALIGN.center).
                      setConstraint(AxisLayout.stretched())));
        if (subtitle() != null) root.add(new Label(subtitle()));
        Group iface = createIface();
        if (iface != null) root.add(iface.setConstraint(AxisLayout.stretched()));
    }

    @Override public void wasRemoved () {
        super.wasRemoved();
        iface.destroyRoots();
        while (layer.size() > 0) layer.get(0).destroy();
    }

    /** The label to use on the button that displays this demo. */
    protected abstract String name ();

    /** Returns the title of this demo. */
    protected abstract String title ();

    /** Returns an explanatory subtitle for this demo, or null. */
    protected String subtitle () { return null; }

    /** Override this method and return a group that contains your main UI, or null. */
    protected abstract Group createIface ();

    /** Returns the stylesheet to use for this screen. */
    protected Stylesheet stylesheet () {
        return SimpleStyles.newSheet();
    }

    /** Returns the background to use for this screen. */
    protected Background background () {
        return Background.bordered(0xFFCCCCCC, 0xFFCC99FF, 5).inset(5);
    }


}
