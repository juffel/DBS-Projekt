package dbsProjekt;


import java.sql.SQLException;
import java.sql.Statement;
;

/**
 * Klasse zum Erzeugen der fiktiven Besucherdaten
 * 
 */

public class Einwertung {
	private Statement stmt;
	
	public void createTables(Connection con) throws SQLException {
		
		System.out.print("creating tables...");

		if(con == null) {
			System.out.println("cannot create Tables, no connection to database");
			return;
		} 
		
		stmt.executeUpdate("drop table if exists besucherzahlen;");
		stmt.executeUpdate("create table besucherzahlen (veranstaltung_id integer REFERENCES veranstaltung,besucherzahl integer);");
	}
	
public void fillTables(Connection con) throws SQLException {
		
		System.out.print("filling tables...");
	
		if(con == null) {
			System.out.println("cannot fill Tables, no connection to database");
			return;
		}
		
		stmt.executeUpdate("INSERT INTO besucherzahlen VALUES (1,555);");
		
		System.out.println("besucherzahlen table done.");
		
		System.out.println("filling tables done.");
		
	}
}
