package com.emc.smiles;

import java.io.IOException;
import java.util.ArrayList;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;

public class InvokeRESTfulService {
	
	public static String[] callRESTfulService(String url, String[] jsonPaths) {
		String encodedUrl = null;
		if (url.startsWith("http://")) {
			encodedUrl = url.replace(" ", "+");
		}
		else {
			encodedUrl = "http://" + url.replace(" ", "+");
		}
		JSONResource json;
		ArrayList<String> returnValuesAL = new ArrayList<String>();
		Resty r = new Resty();
		try {
			json = r.json(encodedUrl);
			int size = jsonPaths.length;
			for (int i=0; i<size; i++) {
				returnValuesAL.add(json.get(jsonPaths[i]).toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] returnValues = new String [returnValuesAL.size()];
		returnValuesAL.toArray(returnValues);
		
		return returnValues;
	}
}
