package com.emc.smiles;

import java.util.ArrayList;
import java.util.Arrays;

public class TestMain {

	public static void main(String[] args) {
		
		if (args[0].equals("ldap")) {
			getUser(args[1]);
		}
		else if (args[0].equals("rest")) {
			ArrayList<String> jsonPathsAL = new ArrayList<String>();
			jsonPathsAL.add(args[3]);
			jsonPathsAL.add(args[4]);
			String[] jsonPaths = new String [jsonPathsAL.size()];
			jsonPathsAL.toArray(jsonPaths);
			callRESTfulService(args[2],jsonPaths);
		}
	}
	
	static void getUser(String corpid) {
		User u = LDAPUserServices.createUserFromLDAP(corpid);
		System.out.println(u.toString());
	}
	
	static void callRESTfulService(String url, String[] jsonPaths) {
		String[] values = InvokeRESTfulService.callRESTfulService(url,jsonPaths);
		System.out.println(Arrays.toString(values));
	}

}
