package com.jb.exceptions;

public class InvalidAction extends Exception {

	public InvalidAction(String s) {
		super("Invalid action-  "+s);
	}
}
