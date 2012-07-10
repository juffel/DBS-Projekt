package dbsProjekt;

import java.sql.SQLException;

public class Main {
	
	public static void main(String args[]) {
		
		if(args.length != 1) {
			System.out.println("Beim Programmaufruf Pfad der einzulesenden sql Datei angeben");
			System.exit(0);
		}
		
		ProjektDB imp = new ProjektDB();
		
		imp.init(args[0]);
		imp.createTables();
		try {imp.fillTables();}catch(SQLException e){}
		
	}

}
