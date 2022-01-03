package net.praffuln.handlers.service.impl;

import net.praffuln.handlers.service.Pollable;

public class FileSystemPollar implements Pollable {

	@Override
	public void poll() {
		System.out.println("fileSystem polllar......");
		
	}
}
