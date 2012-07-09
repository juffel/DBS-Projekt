package dbsProjekt;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
	
	
	private java.sql.Connection connection;

	
	/**
	 * baut Verbindung zur angegebenen Datenbank auf
	 */
	public Connection(String server, String port, String name, String user, String password) {
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
		connection = null;
		
		try{
			// Verbindung zur Datenbank herstellen
			
			connection = DriverManager.getConnection("jdbc:postgresql://" + server + ":" + port + "/" + name, user , password);
		}
		catch (SQLException sqle){
			// Verbindung konnte nicht hergestellt werden!
			System.out.println("Verbindung konnte nicht hergestellt werden!");
			
			// Stacktrace ausgeben um mögliche Fehler nachzuvollziehen
			sqle.printStackTrace();
			System.exit(0);
		}
		
		// Verbindung konnte hergestellt werden!
		System.out.println("Die Verbingung zur Datenbank wurde hergestellt.");
	}
	
	
	public java.sql.Connection get() {
		return connection;
	}
	
}
