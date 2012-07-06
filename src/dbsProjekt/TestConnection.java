package dbsProjekt;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConnection {
	
	public void test() {
		
		ImportData tmp = new ImportData();
		Connection con = tmp.openConnection("localhost", "5432", "testDB", "testuser", "password");
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeQuery("CREATE TABLE Veranstalter (" +
										"Arbeitsgruppe varchar(255)," +
										"Institut varchar(255)," +
										"Fachbereich varchar(255)," +
										"Kommentar varchar(255)" +
									");");
			
			System.out.println("Tabelle erfolgreich erstellt");
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
