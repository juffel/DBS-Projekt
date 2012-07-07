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
public class ImportData {
	
	public void initEasy(String path) {
		
		Connection con = new Connection("localhost", "5432", "lndw", "testuser", "password");
		
		Statement stmt;
		
		try {
			
			stmt = con.get().createStatement();
			stmt.executeUpdate("\\i " + path);
			
		} catch(SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
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
			int character, previous = 0;
			StringBuffer buf = new StringBuffer(0);
			
			// und die einzelnen Queries zusammenfassen, also immer bis zu einem Semikolons
			while((character = rdr.read()) != -1) {
				
				char c = (char) character;

				switch (c) {
				
				case ';':
					
					buf.append(';');
					queries.add(buf.toString()); // Query in queries abheften
					buf = new StringBuffer(0); // und Buffer reseten
					
					previous = 0;
					break;
					
				case '-':
					
					if(((char) previous) == '-') {
						previous = 0;
						buf.deleteCharAt(buf.length()-1);
						rdr.readLine();
					}
					else {
						buf.append('-');
						previous = character;
					}
					
					break;

				case '*':
					
					if(((char) previous) == '/') {
						previous = 0;
						buf.deleteCharAt(buf.length()-1);
						rdr.readLine();
						// TODO das stimmt so noch nicht, es muss nicht nur die nächste Zeile verschluckt werden, sondern bis zum nächsten '*/'
					}
					else {
						buf.append('*');
						previous = character;
					}
					
					break;
					
				case Character.LINE_SEPARATOR:

					previous = 0;
					break;
					
				case ' ':
					if(((char) previous) != ' ') {
						buf.append(' ');
					}

					previous = character;
					break;
					
				default:
					
					buf.append((char) c);

					previous = character;
					break;
				}
								
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Queries ausgeben
		
		for(int i = 0; i < 30; i++) {
			System.out.println(queries.get(i));
		}
		
		/* for(String s:queries) {
			System.out.println(s);
		} */

		
	}
	

}
