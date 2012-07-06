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
			
		// hoffentlich gibts eine schönere Methode Ergebnisse von Queries auszuwerten...
			int i = 0;
			while(!res.isLast()) {
				i++;
				System.out.println(res.getNString(i));
				res.next();
			}
			
			if(i==0) {
				System.out.println("Einfügen von Zeilen bzw. Abfragen war nicht erfolgreich.");
			}
			else {
				System.out.println("Einfügen war vmtl. erfolgreich!");
			}
			
			stmt.executeUpdate("DROP TABLE Veranstalter;");
			
			res = stmt.executeQuery("SELECT * FROM Veranstalter;");
			
			if(res.getString(0).equals("ERROR:  relation \"veranstaltung\" does not exist")) {
				System.out.println("Tabelle erfolgreich gelöscht");
			} else
				System.out.println("Löschung nicht erfolgreich");
			
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
