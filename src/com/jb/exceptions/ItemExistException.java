package com.jb.exceptions;

public class ItemExistException extends Exception {

	public ItemExistException(String message) {
		super("Item already exists: " + message);
	}
}
