package com.dat.whm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;

public class DDLGenerator {
	public static void main(String args[]) throws Exception {
		Map<String, String> propertiesMap = new HashMap<String, String>();
		propertiesMap.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.DROP_AND_CREATE); 
		propertiesMap.put(PersistenceUnitProperties.DDL_GENERATION_MODE, "sql-script"); 
		propertiesMap.put(PersistenceUnitProperties.CREATE_JDBC_DDL_FILE, "resources/create.sql"); 
		propertiesMap.put(PersistenceUnitProperties.DROP_JDBC_DDL_FILE, "resources/drop.sql");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory( "JPA", propertiesMap);
		emf.createEntityManager();
		emf.close();
		BufferedReader br = new BufferedReader(new FileReader("resources/create.sql"));
		String strLine = "";
		StringBuilder fileContent = new StringBuilder();
		while ((strLine = br.readLine()) != null) {
			fileContent.append(strLine);
			fileContent.append(";\n");
		}
		fileContent.append("INSERT INTO POSITION (ID, NAME, DESCRIPTION, VERSION) VALUES ('PST0001', 'IT-ADMIN', 'IT-ADMIN', 0);\n");
		fileContent.append("INSERT INTO USERS (ID, USERNAME, PASSWORD, FULLNAME, NRC, ADDRESS, PHONE, USER_ROLE, POST_ID, VERSION) "
        		+ "VALUES ('USR0001', 'admin', 'YWRtaW4=', 'Administrator', 'NRC/123456', 'YGN', '123456', 'ADMIN', 'PST0001', 0);\n");
		
		BufferedWriter out = new BufferedWriter(new FileWriter("resources/create.sql"));
		out.write(fileContent.toString());
		br.close();
		out.close();

		BufferedReader br_2 = new BufferedReader(new FileReader("resources/drop.sql"));
		String strLine_2 = "";
		StringBuilder fileContent_2 = new StringBuilder();
		while ((strLine_2 = br_2.readLine()) != null) {
			fileContent_2.append(strLine_2);
			fileContent_2.append(";\n");
		}
		BufferedWriter out_2 = new BufferedWriter(new FileWriter("resources/drop.sql"));
		out_2.write(fileContent_2.toString());
		br_2.close();
		out_2.close();
	}
}
