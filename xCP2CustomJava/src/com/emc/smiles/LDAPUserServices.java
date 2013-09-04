package com.emc.smiles;

import java.sql.*;
import javax.xml.bind.DatatypeConverter;

public class LDAPUserServices {
	
	public User getUser(String corpid) {
		return createUserFromLDAP (corpid);
	}
	
	static User createUserFromLDAP (String corpid) {
		User u = new User();
		String ldapConnectString = "jdbc:ldap://corpcascv1.CORP.EMC.COM:389/DC=corp,DC=emc,DC=com?SEARCH_SCOPE:=subTreeScope";
		java.sql.Connection con = null;
		try {
			DriverManager.registerDriver(new com.octetstring.jdbcLdap.sql.JdbcLdapDriver());
			con = DriverManager.getConnection(ldapConnectString, "CN=Smith\\, Joe,OU=IaaS Accounts,OU=US Security,DC=corp,DC=emc,DC=com", "XXX");
			Statement s = con.createStatement();
			String sql = "SELECT sAMAccountName, sn, givenName, co, physicalDeliveryOfficeName, postalCode, st, title, streetAddress, division, department, telephoneNumber, manager, mail FROM subTreeScope;DC=corp,DC=emc,DC=com WHERE sAMAccountName='"+corpid+"'";
			s.executeQuery(sql);
			ResultSet rs = s.getResultSet();
			//while (rs.next()) {
			rs.next();
				u.setCorpID(rs.getString("sAMAccountName"));
				u.setFirstName(rs.getString("givenName"));
				u.setLastName(rs.getString("sn"));
				u.setCountry(rs.getString("co"));
				u.setCity(rs.getString("physicalDeliveryOfficeName"));
				u.setZipCode(rs.getString("postalCode"));
				u.setState(rs.getString("st"));
				u.setJobTitle(rs.getString("title"));
				u.setStreetAddress(new String(DatatypeConverter.parseBase64Binary(rs.getString("streetAddress"))));
				u.setDivision(rs.getString("division"));
				u.setDepartment(rs.getString("department"));
				u.setPhoneNumber(rs.getString("telephoneNumber"));
				u.setManager(rs.getString("manager"));
				u.setEmailAddress(rs.getString("mail"));
			//}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u;
	}

}
