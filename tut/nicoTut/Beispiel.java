package nicoTut;

//Imports
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



/**
* Erstellt eine Verbindung zur Datenbank und ermöglicht Abfragen.
* @author N. Lehmann, T. Bullmann
*/
public class Beispiel {

	/**
	 * @author N. Lehmann, T. Bullmann
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		
		// TEIL 1 - eine Verbindung zu einer Datenbank herstellen
		// benötigte Imports: DriverManager, Connection, SQLException
		
		// Datenbank - Einstellungen
		String dbServer = "localhost";
		String dbPort   = "5432";
		String dbName   = "MeineDatenbank";
		String dbUser   = "postgres";
		String password = "fu-berlin";
		
		try{
			// Treiber laden
			Class.forName("org.postgresql.Driver");
		}
		catch(ClassNotFoundException cnfe){
			
			// Treiber konnte nicht geladen werden!
			System.out.println("PostgreSQL Treiber konnte nicht gefunden werden.");
			
			// Stacktrace ausgeben um mögliche Fehler nachzuvollziehen
			cnfe.printStackTrace();
			System.exit(0);
		}
		
		// Treiber konnte geladen werden!
		System.out.println("Der Treiber wurde erfolgreich registriert!");
		
		// Kann eine Verbindung zur Datenbank etabliert werden?
		Connection conn = null;
		
		try{
			// Verbindung zur Datenbank herstellen
			conn = DriverManager.getConnection("jdbc:postgresql://" + dbServer + ":" + dbPort + "/" + dbName, dbUser , password);
		}
		catch (SQLException sqle){
			// Verbindung konnte nicht hergestellt werden!
			System.out.println("Verbindungkonnte nicht hergestellt werden!");
			
			// Stacktrace ausgeben um mögliche Fehler nachzuvollziehen
			sqle.printStackTrace();
			System.exit(0);
		}
		
		// Verbindung konnte hergestellt werden!
		System.out.println("Die Verbingung zur Datenbank wurde hergestellt.");
		
		
		// TEIL 2 - Abfragen an die Datenbank senden
		// benötigte Imports: Statement, PreparedStatement, ResultSet
		
		// TEIL 2.1 - STATEMENTS
		Statement stmt = conn.createStatement();
		ResultSet rset1 = stmt.executeQuery("SELECT * FROM \"Tabelle1\", \"Tabelle2\" WHERE \"Tabelle1\".id = \"Tabelle2\".id AND \"Tabelle1\".id = 17");
		
		// TEIL 2.2 - PREPARED STATEMENTS (etwas schneller)
		PreparedStatement prepstmt = conn.prepareStatement("SELECT * FROM \"Tabelle1\", \"Tabelle2\" WHERE \"Tabelle1\".id = \"Tabelle2\".id AND \"Tabelle1\".id = ?");
		
		prepstmt.setInt(1, 17);
		
		ResultSet rset2 = prepstmt.executeQuery();
		

		// TEIL 3 - RESULTSETS
		System.out.println("Es folgen die Abfragen:");
		
		while (rset1.next()){
			System.out.print(rset1.getInt(1) + " -> ");
			System.out.println(rset1.getString(2) + " (Statement)");
		}
		
		while (rset2.next()){
			System.out.print(rset2.getInt("id") + " -> ");
			System.out.println(rset2.getString("wert") + " (Prepared Statement");
		}
		
	} // eom

} // eoc