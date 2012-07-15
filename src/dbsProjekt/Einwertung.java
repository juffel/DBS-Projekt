package dbsProjekt;


import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
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
	
	
	public static void fillTables(Connection con) throws SQLException {
		
		System.out.print("filling tables...");
	
		if(con == null) {
			System.out.println("cannot fill Tables, no connection to database");
			return;
		}
		
		StringBuffer buf = new StringBuffer();
		
		buf.append("INSERT INTO besucherzahlen VALUES \n");
		
		Random rnd = new Random();
		
		for(int i = 1; i <= 2111; i++) {
			buf.append("('"+ i + "', '"+ rnd.nextInt(10000) +"'),\n");
		}
		buf.delete(buf.length()-2, buf.length());
		buf.append(";");
		
		System.out.println(buf.toString());
		
		con.get().createStatement().executeUpdate(buf.toString());
		
		System.out.println("besucherzahlen table done.");
		
		System.out.println("filling tables done.");
		
	}
}
