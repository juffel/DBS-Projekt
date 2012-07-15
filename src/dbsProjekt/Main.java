package dbsProjekt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.StringTokenizer;

public class Main {
	
	public static void main(String args[]) {
		
		if(args.length != 1) {
			System.out.println("Beim Programmaufruf Pfad des Verzeichnisses angeben in\n" +
					           "dem die \"lange_nacht_postgres.sql\" und die \"ort.txt\" liegen.");
			System.exit(0);
		}
		
		ProjektDB imp = new ProjektDB();		

		System.out.println("Willkommen im DBS-Projekt von Julian Dobmann, Samuel Gfrörer und André Röhrig.\n" +
						   "Es wird jetzt eine neue Datenbank angelegt und initialisiert.\n" +
						   "Das kann eine Weile dauern.");	
		
		try {
			
			imp.init(args[0]);
			imp.createTables();			
			imp.fillTables(args[0]);
			
		} catch(SQLException e) {
			e.printStackTrace();
			return;
		}
		
		System.out.println("Die Initialisierung ist vollständig abgeschlossen.\n");
				
		boolean run = true;	
		while(run) {
			
			System.out.println("Sie haben folgende Auswahlmöglichkeiten: (D|B|E)\n" +
							   "	Ausgabe aller Distanzen aller in der Datenbank enthaltenen Orte (D)\n" + 
							   "	Ausgabe der Besucherschwankungen zwischen zwei Jahren (B)\n" +
							   "	Programm verlassen (E)");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String eingabe = null;
			
			try {
				
				eingabe = br.readLine();
				
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			
			if(eingabe.equals("B") || eingabe.equals("b")) {
				
				Connection con = new Connection("localhost", "5432", "lndw", "user", "password");
				System.out.print("Besucherzahlen werden generiert...");
				try {
					Einwertung.createTables(con);
					Einwertung.fillTables(con);
				} catch (SQLException e) {
					e.printStackTrace();
					return;
				}
				System.out.println("done.\n");
				
				System.out.println("Für welche zwei Jahre möchten Sie die Differenz der Besucherzahlen betrachten?\n" +
								   "(2008|2009|2010|2011|2012) z.B.: \"2008 2009\"");
				try {
					eingabe = br.readLine();
				} catch(IOException e) {
					e.printStackTrace();
					return;
				}
				
				StringTokenizer strTok = new StringTokenizer(eingabe);
				String jahr1 = strTok.nextToken();
				String jahr2 = strTok.nextToken();
				
				Auswertung.evaluateVisitors(con, jahr1, jahr2);
				
			}
			else if(eingabe.equals("D") || eingabe.equals("d")) {
				
				
				
			}
			else if(eingabe.equals("E") || eingabe.equals("e")) {
				
				System.out.println("Programm wirklich Verlassen? (y/n)");
				try {
					eingabe = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(eingabe.equals("Y") || eingabe.equals("y")) {
					
					run = false;
					System.out.println("Auf Wiedersehen!");
					
				} else {
					
					System.out.println("Verlassen abgebrochen.");
				}
				
			}
			else {
				
				
				
			}
			
		}
		
	}

}
