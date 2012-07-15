package dbsProjekt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;


/**
 * Klasse für die Auswertung von fiktiven Besucherzahldaten
 *
 */
public class Auswertung {
	
	/**
	 * Dieser Methode wird als parameter eine LinkedList(Jahre) von LinkedLists(Tupel (Veranstaltung, Besucherzahl)) übergeben,
	 * welche evaluiert werden.
	 */
	public static void evaluateVisitors(Connection con) {
		
		for(Integer i = 2008; i <= 2012; i++) {
			
			for(Integer j = i; j <= 2012; j++) {
				
				LinkedList<Triple<String, String, String>> tmp = fetchVisitorData(con, i.toString(), j.toString());
				System.out.println("Besucherzahlen für die Jahre " + i + " " + j + ":\n");
				
				for(Triple<String, String, String> tr : tmp) {
					
					int value1 = Integer.parseInt(tr.getValue2());
					int value2 = Integer.parseInt(tr.getValue3());
					
					System.out.print(tr.getValue1() +": "+ value1 +", "+ value2);
					
					
					if(value1 > value2)  {
						System.out.print(" - " + ((Integer) (value1-value2)).toString());
					}
					
					else if(value1 < value2) {
						System.out.print(" + " + ((Integer) (value1-value2)).toString());
					}
					
					else {
						System.out.print(" = "  + ((Integer) (value1-value2)).toString());
					}
					
					System.out.println("");
					
				}
				
				System.out.println("");
				
			}
			
		}
		
	}
	
	/**
	 * Holt die zur Besucherzahlenauswertung benötigten Daten aus der Datenbank und verpackt sie schön in eine LinkedList
	 */
	public static LinkedList<Triple<String, String, String>> fetchVisitorData(Connection con, String jahr1, String jahr2) {
		
		LinkedList<Triple<String, String, String>> ret = new LinkedList<Triple<String,String,String>>();
		
		try {
			
			// wir wollen eine Auflistung folgender Form bekommen: |veranstaltungsID|besucherzahlJahr1|besucherzahlJahr2|
			
			String tmp = "SELECT v2.lp_title, v1.besucherzahl AS besucherzahl_jahr1, v2.besucherzahl AS besucherzahl_jahr2 FROM " +
					"(veranstaltung INNER JOIN besucherzahlen ON veranstaltung.veranstaltung_id = besucherzahlen.veranstaltung_id) v1," +
					"(veranstaltung INNER JOIN besucherzahlen ON veranstaltung.veranstaltung_id = besucherzahlen.veranstaltung_id) v2 " +
					"WHERE v1.lp_title = v2.lp_title AND v1.lp_lndw_year = '2009' AND v2.lp_lndw_year = '2010';";
			
			ResultSet res_besucherzahlen = con.get().createStatement().executeQuery(tmp);
			
			while(res_besucherzahlen.next()) {
				
				ret.add(new Triple<String,String,String>(res_besucherzahlen.getString("lp_title"),
													     res_besucherzahlen.getString("besucherzahl_jahr1"),
													     res_besucherzahlen.getString("besucherzahl_jahr2")));
				
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return ret;
		
	}
	
	
	
	/**
	 * mänsch, warum gibts keine Tupelz in java -.-
	 */
	public static class Tuple<V, W> {
		
		private V value1;
		private W value2;
		public Tuple(V value1, W value2) {
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
	
	/**
	 * mänsch, warum gibts keine Tupelz in java -.-
	 */
	public static class Triple<V, W, X> {
		
		private V value1;
		private W value2;
		private X value3;
		public Triple(V value1, W value2, X value3) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
		}
		public V getValue1() {
			return value1;
		}
		public W getValue2() {
			return value2;
		}
		public X getValue3() {
			return value3;
		}
		
	}
	
}
