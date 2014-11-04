package com.threerings.challenge.player;

import java.util.ArrayList;
import java.util.List;

import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.layout.AxisLayout;


/**
 * Return object from next turn processing.
 * Delivers information back to HomeScreen 
 * @author Robbie Scheidt
 *
 */
public class TurnData {
	
	private List<String> ntfcn;
	private int turn;
	
	public TurnData(int turn) {
		ntfcn = new ArrayList<String>(); 
		this.turn = turn;
	}

	public void add(String s) {
		ntfcn.add(s);
	}

	
	public List<String> getNtfcn() {
		return ntfcn;
	}
	
	/* not a good place for this but really convenient */
	public Group getNtfcGrp() {
		Group g = new Group(AxisLayout.vertical());
		for (String s : ntfcn) {
			g.add(new Label(s));
		}
 
		return g;
	}

	public int getTurn() {
		return turn;
	}
	
	

}
