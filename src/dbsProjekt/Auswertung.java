package dbsProjekt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;


/**
 * Klasse für die Auswertung von fiktiven Besucherzahldaten
 *
 */

public class Auswertung {
	
	public void evaluateVisitors(Connection con) {
		
		LinkedList<Tuple<String, Integer>> visitors = new LinkedList<Tuple<String, Integer>>();
		
		try {
			
			ResultSet res = SQL_Utility.getFromTable(con, "besucherzahlen", new String[]{"veranstaltung_id, besucherzahl"});
			
			while(res.next()) {
				
				visitors.add(new Tuple(res.getString("veranstaltung_id"), new Integer(res.getInt("besucherzahlen"))));	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < visitors.size(); i++) {
			
			System.out.println(visitors.get(i).getValue1() + ", " + visitors.get(i).getValue2());
			
		}
		
	}
	
	private class Tuple<V, W> {
		
		V value1;
		W value2;
		private Tuple(V value1, W value2) {
			this.value1 = value1;
			this.value2 = value2;
		}
		public V getValue1() {
			return value1;
		}
		public W getValue2() {
			return value2;
		}
		
	}
	
}
