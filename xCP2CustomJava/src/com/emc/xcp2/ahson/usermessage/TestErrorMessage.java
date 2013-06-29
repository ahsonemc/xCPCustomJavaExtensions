package com.emc.xcp2.ahson.usermessage;

import com.documentum.fc.common.DfException;

public class TestErrorMessage {

	/**
	 * @param args
	 * @throws DfException 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		try {
			SendErrorMessage.killProcessWithErrorMessage("whoomp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}