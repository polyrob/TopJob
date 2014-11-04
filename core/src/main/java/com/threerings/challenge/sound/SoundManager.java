package com.threerings.challenge.sound;

import static playn.core.PlayN.assets;
import playn.core.Sound;

/**
 * Class to load and make sounds available. Since the sound resources are so
 * small/few, no need to consider load/unloading. Just make them statically
 * avaialble.
 * 
 * @author Robbie Scheidt
 *
 */
public class SoundManager {
	

//	public static final Sound ABSOLUTELY = assets().getSound("sound/absolutely");
//	public static final Sound BIG_TIME= assets().getSound("sound/big_time");
//	public static final Sound CLICK = assets().getSound("sound/click");
//	public static final Sound LOSER = assets().getSound("sound/loser");
//	public static final Sound PAPER = assets().getSound("sound/paper");
//	public static final Sound KNOCK = assets().getSound("sound/knock");
//	public static final Sound SPIT = assets().getSound("sound/spit");
//	public static final Sound TURN = assets().getSound("sound/turn");
//	public static final Sound WELL_UH = assets().getSound("sound/well_uh");
//	public static final Sound WELL_YEA = assets().getSound("sound/well_yea");
//	public static final Sound WOO = assets().getSound("sound/woo");
//	public static final Sound WRITING = assets().getSound("sound/writing");
	
	
	public static  Sound ABSOLUTELY;
	public static  Sound BIG_TIME;
	public static  Sound CLICK;
	public static  Sound LOSER;
	public static  Sound PAPER;
	public static  Sound KNOCK;
	public static  Sound SPIT;
	public static  Sound TURN;
	public static  Sound WELL_UH;
	public static  Sound WELL_YEA;
	public static  Sound WOO;
	public static  Sound WRITING;
	
	public static void init() {
		ABSOLUTELY = assets().getSound("sound/absolutely");
		BIG_TIME= assets().getSound("sound/big_time");
		CLICK = assets().getSound("sound/click");
		LOSER = assets().getSound("sound/loser");
		KNOCK = assets().getSound("sound/knock");
		SPIT = assets().getSound("sound/spit");
		TURN = assets().getSound("sound/turn");
		WELL_UH = assets().getSound("sound/well_uh");
		WELL_YEA = assets().getSound("sound/well_yea");
		WOO = assets().getSound("sound/woo");
		WRITING = assets().getSound("sound/writing");
	}

	// TODO: may want to implement static method to play from here. Utilize
	// isPlaying(). Not sure if users will click fast enough to me it matter.

}
