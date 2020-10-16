package com.jb.exceptions;

public class IDDoesntExistException extends Exception {

	public IDDoesntExistException() {
		super("The ID provided doesn't exist");
	}
}
