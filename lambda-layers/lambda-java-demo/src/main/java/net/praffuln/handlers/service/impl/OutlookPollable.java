package net.praffuln.handlers.service.impl;

import net.praffuln.handlers.service.Pollable;

public class OutlookPollable implements Pollable {

	@Override
	public void poll() {
		System.out.println("outlook polllar......");
		
	}

}
