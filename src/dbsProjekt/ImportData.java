package dbsProjekt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

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
			int c;
			StringBuffer buf = new StringBuffer();
			
			// und die einzelnen Queries zusammenfassen, also immer bis zu einem Semikolons
			while((c = rdr.read()) != -1) {

				if(c == 59) { // ascii 59 = ';'
					
					queries.add(buf.toString()); // Query in queries abheften
					buf.delete(0, buf.length()); // und Buffer reseten
					
				} else {
					
					buf.append((char) c);
					
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Queries ausgeben
		for(String s:queries) {
			System.out.println(s);
		}

		
	}
	

}
