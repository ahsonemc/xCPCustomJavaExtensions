package com.emc.xcp2.ahson.dqlrunner;

import java.io.StringWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.*;
import com.documentum.fc.client.*;
import com.documentum.fc.common.*;
import com.documentum.xml.xdql.*;

public class RunDQL extends DfSingleDocbaseModule {
	
	/* valid modes are
	 * 0 - xcp2 style table (this will use xml + xslt)
	 * 1 - comma separated values (csv)
	 * 2 - raw xml
	 */
	public String getDQLResults(String query, int mode) {
		String result = null;
		try 
		{
			IDfSession idfSession = getSession();
			result = executeDQL(query, mode, idfSession);
		}	
		catch (DfException dfe) {
			//dfe.printStackTrace();
			result = dfe.getLocalizedMessage();
		}
			
		return ( result );
	}
	
	static String executeDQL(String query, int mode, IDfSession idfSession) throws DfException {
		String result = null;
		Document xmlDoc = null;

		if (mode == 0) {
			xmlDoc = RunDQL.runXDQL(idfSession,query);
			result = RunDQL.xmlTransform(xmlDoc, "xDQL to xCP2 Style Table.xslt");
		} else if (mode == 2) {
			xmlDoc = RunDQL.runXDQL(idfSession,query);
			result = RunDQL.xmlTransform(xmlDoc, null);				
		} 

		if (xmlDoc == null || mode == 1) {
			
			IDfCollection coll = RunDQL.runDQL(idfSession,query);
			result = RunDQL.toCSV(coll);	
			
			if ( coll != null )
				coll.close();		
		}
		
		//unsupported mode: simple html table
		//result = toHTMLTable(coll);
			
		return ( result );
	}

	static String xmlTransform(Document xmlDoc, String xslt) {
		javax.xml.transform.TransformerFactory tfactory = TransformerFactory.newInstance();
		javax.xml.transform.Transformer xform = null;
		try {
			if (xslt == null || xslt.isEmpty()) {
				xform = tfactory.newTransformer();
				xform.setOutputProperty(OutputKeys.INDENT, "yes");
				xform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");
			} else {
				xform = tfactory.newTransformer(new javax.xml.transform.stream.StreamSource(xslt));
			}
		} catch (TransformerConfigurationException tce) {
			tce.printStackTrace();
			return tce.getMessage();
		}
		javax.xml.transform.Source src = new DOMSource(xmlDoc);
		java.io.StringWriter writer = new StringWriter();
		Result result = new javax.xml.transform.stream.StreamResult(writer);
		try {
			xform.transform(src, result);
		} catch (TransformerException te) {
			te.printStackTrace();
			return te.getMessage();
		}
		//System.out.println(writer.toString());
		return writer.toString();
	}

	static IDfCollection runDQL(IDfSession sess, String q) throws DfException {
		// issue a dql and print the results
		IDfQuery query = new DfQuery();
		query.setDQL(q);
		IDfCollection coll = query.execute(sess, IDfQuery.DF_QUERY);
		
		return coll;
	}

	static Document runXDQL(IDfSession sess, String q) throws DfException {
		// issue a dql and print the results
		IDfXmlQuery xmlQuery = new DfXmlQuery();
		xmlQuery.setDql(q);
		xmlQuery.execute(IDfQuery.DF_QUERY, sess);

		Document xmlResults = xmlQuery.getXMLDOM();
		
		if (xmlResults.getFirstChild().getNodeName().equals("error")) {
			return null;
		}
		
		//System.out.println(xmlQuery.getXMLString());
		
		return xmlResults;
	}
	
	static String toCSV(IDfCollection coll) throws DfException {
		// check if coll is null   
		if (coll == null) {
			return ""; // Depending on how you want to deal with this case...
		}
		
		// init stringbuilder
		StringBuilder result = new StringBuilder();
		
		// init iteration counter
		int i = 0;
		
		// create header row
		while ( coll.next() ) {
			IDfTypedObject typeObj = (IDfTypedObject) coll.getTypedObject();
			
			int j = 0;
			if (i == 0) {
				while (j < typeObj.getAttrCount()) {
					//System.out.println(typeObj.getAttr(j).getName());
					
					if (j == 0)
						result.append("'").append(typeObj.getAttr(j).getName()).append("'");
					else
						result.append(";").append("'").append(typeObj.getAttr(j).getName()).append("'");
					
					j++;
				}
				result.append("\n");
			}
			
			// append values for each row
			j = 0;
			while (j < typeObj.getAttrCount()) {
				//System.out.println(typeObj.getValueAt(j));
				
				if (j == 0) {
					if (typeObj.getAttr(j).getDataType() == 2) {
						result.append("'").append(typeObj.getValueAt(j)).append("'");
					} 
					else {
						result.append(typeObj.getValueAt(j));
					}
				}
				else {
					if (typeObj.getAttr(j).getDataType() == 2) {
						result.append(";").append("'").append(typeObj.getValueAt(j)).append("'");
					}
					else {
						result.append(";").append(typeObj.getValueAt(j));
					}
				}
				
				j++;
			}
			result.append("\n");
			i++;
		}
	
		return result.toString();
}

	static String toHTMLTable(IDfCollection coll) throws DfException {
		// check if coll is null   
		if (coll == null) {
			return ""; // Depending on how you want to deal with this case...
		}
			
		// init stringbuilder
		StringBuilder result = new StringBuilder();
		
		result.append("<table>\n");
		
		// init iteration counter
		int i = 0;
		
		// create header row
		while ( coll.next() ) {
			IDfTypedObject typeObj = (IDfTypedObject) coll.getTypedObject();
			
			int j = 0;
			if (i == 0) {
				while (j < typeObj.getAttrCount()) {
					//System.out.println(typeObj.getAttr(j).getName());
					
					if (j == 0)
						result.append("   <thead>\n      <tr>\n         <td>").append(typeObj.getAttr(j).getName()).append("</td>\n");
					else
						result.append("         <td>").append(typeObj.getAttr(j).getName()).append("</td>\n");
					
					j++;
				}
				result.append("      </tr>\n   </thead>\n   <tbody>\n");
			}
				
			// append values for each row
			j = 0;
			while (j < typeObj.getAttrCount()) {
				//System.out.println(typeObj.getValueAt(j));
				
				if (j == 0) {
					if (typeObj.getAttr(j).getDataType() == 2) {
						result.append("      <tr>\n         <td>'").append(typeObj.getValueAt(j)).append("'</td>\n");
					} 
					else {
						result.append("      <tr>\n         <td>").append(typeObj.getValueAt(j)).append("</td>\n");
					}
				}
				else {
					if (typeObj.getAttr(j).getDataType() == 2) {
						result.append("         <td>'").append(typeObj.getValueAt(j)).append("'</td>\n");
					}
					else {
						result.append("         <td>").append(typeObj.getValueAt(j)).append("</td>\n");
					}
				}
				
				j++;
				
			}
			result.append("      </tr>\n");
			i++;
		}
		if (i > 0) {
			result.append("   </tbody>\n");
		}
		result.append("</table>");
	
		return result.toString();
	}
}
