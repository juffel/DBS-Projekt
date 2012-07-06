package dbsProjekt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Mithilfe dieser Klasse kann man eine Datenbank erzeugen, die unserem Schema entspricht.
 * @author julian
 *
 */
public class ImportData {
	
	/**
	 * erstellt eine neue Datenbank, auf welcher ein in der Datei file abgelegter SQL-Query ausgeführt wird
	 * @param file
	 */
	public void init(String file) {
		
	}
	
	/**
	 * baut Verbindung zur angegebenen Datenbank auf
	 */
	private Connection openConnection(String server, String port, String name, String user, String password) {
		
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
			conn = DriverManager.getConnection("jdbc:postgresql://" + server + ":" + port + "/" + name, user , password);
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
		return conn;
	}
	

}
