package com.jb.exceptions;

public class LoginFailed extends Exception {

	public LoginFailed() {
		super("The Email or Password provided doesnt exist");
	}
}
