package com.threerings.challenge.style;

import playn.core.Font;
import playn.core.PlayN;
import tripleplay.ui.Background;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;

public class TopJobStyle {
	
	public static final Styles label32 = Styles.make(
            Style.FONT.is(PlayN.graphics().createFont("Times New Roman", Font.Style.PLAIN, 32)),
            Style.HALIGN.center);

	public static final Styles lable24 = Styles.make(
            Style.FONT.is(PlayN.graphics().createFont("Times New Roman", Font.Style.PLAIN, 24)),
            Style.HALIGN.center);
	
	public static final Styles GREENBG = Styles.make(Style.BACKGROUND
			.is(Background.solid(0xFF99CC66).inset(5)));
	
	public static final Styles BLUEBG = Styles.make(Style.BACKGROUND
			.is(Background.solid(0x662222DD).inset(5)));
}
