package com.threerings.challenge.test;

import org.junit.Before;
import org.junit.Test;

import com.threerings.challenge.player.Player;
import com.threerings.challenge.player.Turn;

public class TurnTest {

	private Turn turn;
	
	@Before
	public void init() {
		 turn = new Turn(new Player());
	}
	
	@Test
	public void test() {
		for (int i = 0; i < 50; i++) {
			turn.nextTurn();
			System.out.println("Turn: " + i + ".  Jobs: " + turn.getJobPostings().size());
		}
	}

}
