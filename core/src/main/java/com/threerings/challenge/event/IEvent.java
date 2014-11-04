package com.threerings.challenge.event;

import com.threerings.challenge.player.Player;

public interface IEvent {

	/*
	 * Apply some event to the player and return String display text indicating
	 * what it was.
	 */
	public String doEvent(boolean good, Player player);

}
