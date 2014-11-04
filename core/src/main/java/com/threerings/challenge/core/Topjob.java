package com.threerings.challenge.core;

import playn.core.Game;
import playn.core.PlayN;
import playn.core.util.Clock;
import tripleplay.game.ScreenStack;

import com.threerings.challenge.screens.AbstractScreen;
import com.threerings.challenge.screens.MenuScreen;

public class Topjob extends Game.Default {
	
	public static final int UPDATE_RATE = 30;
	
	private AbstractScreen currentScreen;

	public Topjob() {
		super(UPDATE_RATE); 
	}
	
    
    protected final Clock.Source _clock = new Clock.Source(UPDATE_RATE);
    
    
    @Override public void init () {
//        currentScreen = new MenuScreen();
    	 stack.push(new MenuScreen(stack));
        

//        // propagate events so that things like buttons inside a scroller work
//        if (!Arrays.asList(mainArgs).contains("--no-propagate")) {
//            PlayN.setPropagateEvents(true);
//        }
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

//    
//
//	@Override
//	public void init() {
//		 // create and add background image layer
//		 Image bgImage = assets().getImage("images/bg.png");
//		 ImageLayer bgLayer = graphics().createImageLayer(bgImage);
//		 graphics().rootLayer().add(bgLayer);
//
//		CanvasImage image = graphics().createImage(300, 300);
//		Canvas canvas = image.canvas();
////		canvas.setStrokeWidth(2);
////		canvas.setStrokeColor(0xff00ffff);
////		canvas.strokeRect(1, 1, 46, 46);
//		
//		canvas.setFillColor(0xff00ffff);
//		canvas.fillRoundRect(1, 1, 46, 200, 5);
//
//		ImageLayer layer = graphics().createImageLayer(image);
//		//layer.setTranslation(25, 25);
//		graphics().rootLayer().add(layer);
//
//		// whatever this is:
//		TextFormat fmt2 = new TextFormat(graphics().createFont("Helvetica", playn.core.Font.Style.PLAIN, 18), true);
//		
//		TextFormat fmt1 = new TextFormat().withWrapWidth(graphics().width()).withFont(
//			      graphics().createFont("Helvetica", playn.core.Font.Style.PLAIN, 32));
//		
//
////		.align()withWrapWidth(graphics().width())
////				.withFont(
////						graphics().createFont("Helvetica",
////								playn.core.Font.Style.PLAIN, 12));
//		
//		TextLayout layout = graphics().layoutText(
//				"Click anywhere to log flurry event.", fmt2);
//		
//		CanvasImage image1 = graphics().createImage(layout.width(),
//				layout.height());
//		image1.canvas().fillText(layout, 0, 0);
//		graphics().rootLayer().addAt(graphics().createImageLayer(image1),
//				(graphics().width() - image1.width()) / 2,
//				(graphics().height() - image1.height()) / 2);
//
//		layer.addListener(new Pointer.Adapter() {
//			public void onPointerStart(Pointer.Event event) {
//				System.out.println("pointer_clicked" + "time"
//						+ (int) event.time() + "x" + (int) event.x() + "y"
//						+ (int) event.y());
//			}
//		});
//
////		// add a listener for pointer (mouse, touch) input
////		mouse().setListener(new Mouse.Adapter() {
////			@Override
////			public void onMouseDown(ButtonEvent event) {
////				System.out.println("MouseDown! " + event.localX() + ", "
////						+ event.localY());
////
////			}
////		});
//
//	}
//
//	@Override
//	public void update(int delta) {
//	}
//
//	@Override
//	public void paint(float alpha) {
//		// the background automatically paints itself, so no need to do anything
//		// here!
//	}

}
