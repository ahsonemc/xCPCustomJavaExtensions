package com.emc.xcp2.ahson.usermessage;

public class SendErrorMessage {

	/**
	 * SendErrorMessage xCelerator
	 * written by Ahson Ahmad
	 * 
	 * arguments are:
	 * 
	 * String message - this is the error message that should be passed to the user
	 * 
	 * returns: void
	 * 
	 */
	public static void killProcessWithErrorMessage (String message) throws Exception {
		throw (new Exception("<br/><br/><b>" + message + "</b>"));
	}

}
