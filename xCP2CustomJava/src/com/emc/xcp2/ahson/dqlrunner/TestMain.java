package com.emc.xcp2.ahson.dqlrunner;

import com.documentum.fc.client.*;
import com.documentum.fc.common.*;


public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int mode = 2; 
		String dqlStatement = "select r_object_id, object_name, r_modify_date from dm_document where object_name like 'Blank%'";
		
		IDfSession idfSession = null;
		try {
			idfSession = repoConnect ("dmadmin","password","xcp");
		} catch (DfException dfe) {
			System.out.println(dfe.getMessage());
		}
	
		String result = null;
		try {
			result = RunDQL.executeDQL(dqlStatement, mode, idfSession);
		}
		catch (DfException dfe) {
			result = dfe.getLocalizedMessage();
		}
		System.out.println ( result );		
	}
	
	private static IDfSession repoConnect(String username, String password, String repoName) throws DfException {
		// connect to the repo in dfc.props and log in
		IDfSession idfSession = null;
		IDfSessionManager sessMgr = null;
		IDfLoginInfo login = new DfLoginInfo();
		login.setUser(username);
		login.setPassword(password);
		IDfClient client = new DfClient();
		sessMgr = client.newSessionManager();
		sessMgr.setIdentity(repoName, login);
		idfSession = sessMgr.getSession(repoName);
		if ( idfSession != null )
		System.out.println("Session created successfully");
		
		return idfSession;
	}
}
