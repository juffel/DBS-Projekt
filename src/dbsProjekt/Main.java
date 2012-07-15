package dbsProjekt;

import java.sql.SQLException;

public class Main {
	
	public static void main(String args[]) {
		
		if(args.length != 1) {
			System.out.println("Beim Programmaufruf Pfad der einzulesenden sql Datei angeben");
			System.exit(0);
		}
		
		ProjektDB imp = new ProjektDB();
		

		try {
			
//			imp.init(args[0]);
//			imp.createTables();			
//			imp.fillTables();
			
			Connection con = new Connection("localhost", "5432", "lndw", "user", "password");
			
// 			Einwertung.fillTables(con);
			Auswertung.fetchVisitorData(con, "2009", "2010");
			
		}
		catch(Exception e){
			e.printStackTrace();
		} 
		
	}

}
