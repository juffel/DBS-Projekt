package dbsProjekt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConnection {
	public static void test() {
		Connection connection = new Connection("localhost", "5432", "testdb", "testuser", "password");
		
		try {
			Statement stmt = connection.get().createStatement();
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Veranstalter (" +
										"Arbeitsgruppe varchar(255)," +
										"Institut varchar(255)," +
										"Fachbereich varchar(255)," +
										"Kommentar varchar(255)" +
									");");
			
			System.out.println("Tabelle erfolgreich erstellt");
			
			stmt.executeUpdate("INSERT INTO Veranstalter VALUES ('A-Team','Spaßinstitut', 'Chromosomik', 'Kein Kommentar'); ");
			stmt.executeUpdate("INSERT INTO Veranstalter VALUES ('Teletubbies','Übermorgenland', 'Gesellschaftswissenschaften', 'Pudding!'); ");
			
			System.out.println("2 Zeilen eingefügt");
			
			ResultSet res = stmt.executeQuery("SELECT * FROM Veranstalter;");
			
			while(res.next()) {
				String AG = res.getString("Arbeitsgruppe");
				String Ins = res.getString("Institut");
				String FB = res.getString("Fachbereich");
				String Km = res.getString("Kommentar");
				
				System.out.println(AG + "\t" + Ins + "\t" + FB + "\t" + Km);
				
			}
			
			stmt.executeUpdate("DROP TABLE Veranstalter;");
			
			System.out.println("Beende Testfunktion");
			
			System.exit(0);		
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
