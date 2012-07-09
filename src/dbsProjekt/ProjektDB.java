package dbsProjekt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * Mithilfe dieser Klasse kann man eine Datenbank erzeugen, die unserem Schema entspricht.
 * @author julian
 *
 */
public class ProjektDB {
	
	private Connection con;
	private Statement stmt;
	
	/**
	 * erstellt eine neue Datenbank, auf welcher ein in der Datei file abgelegter SQL-Query ausgeführt wird
	 * @param file
	 */
	public void init(String path) {
		
		File input = new File(path);
		BufferedReader rdr;
		
		try {
			
			rdr = new BufferedReader(new FileReader(input));
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			return;
		}
		
		LinkedList<String> queries = new LinkedList<String>();
		
		try {
			
			// die Datei Zeichen für Zeichen auslesen
			int character = 0;
			boolean prev_semikolon = false;
			StringBuffer buf = new StringBuffer(0);
			
			// und die einzelnen Queries zusammenfassen
			// immer wenn ein newline-Zeichen auf ein Semikolon folgt wird das als Ende eines Statements betrachtet
			while((character = rdr.read()) != -1) {
				
				char c = (char) character;			

				switch (c) {
				
				case ';':
					
					prev_semikolon = true;
					buf.append(';');
					break;
					
				case '\n':

					buf.append('\n');
					
					if(prev_semikolon) {

						queries.add(buf.toString()); // Query in queries abheften
						buf = new StringBuffer(0); // und Buffer reseten
						
					}
					
					prev_semikolon = false;
					break;

				default:
					
					prev_semikolon = false;
					buf.append((char) c);

					break;
				}
								
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		// dafür sorgen, dass eine frische Datenbank mit dem Namen lndw angelegt wird.
		try {

			// Verbindung zur DB aufbauen
			Connection con = new Connection("localhost", "5432", "template1", "user", "password");
			
			stmt = con.get().createStatement();
			stmt.executeUpdate("DROP DATABASE IF EXISTS lndw;");
			stmt.executeUpdate("CREATE DATABASE lndw;");
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		// Queries an die DB schicken, dafür neue Verbindung mit Benutzernamen aufbauen
		try {
			
			Connection con = new Connection("localhost", "5432", "lndw", "user", "password");
			Statement stmt = con.get().createStatement();
			
			for(String s:queries) {
				
				System.out.print("Executing \"" + s + "\"");
				stmt.executeUpdate(s);
				System.out.println("...done");
			
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * erstellt die von uns entworfenen Relationstabellen
	 * darf erst nach init() aufgerufen werden, bzw. wenn eine Verbindung zur DB besteht
	 */
	public void createTables() {
		
		try {
			
			if(con.get().isClosed()) {
				System.out.println("cannot create Tables, connection to database closed");
			}
			
			// TODO Create Tables
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
	}
	
}