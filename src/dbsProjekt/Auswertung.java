package dbsProjekt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;


/**
 * Klasse für die Auswertung von fiktiven Besucherzahldaten
 *
 */
public class Auswertung {
	
	/**
	 * Dieser Methode wird als parameter eine LinkedList(Jahre) von LinkedLists(Tupel (Veranstaltung, Besucherzahl)) übergeben,
	 * welche evaluiert werden.
	 * @param con
	 */
	public void evaluateVisitors(Connection con, LinkedList<LinkedList<Tuple<String, Integer>>> list) {
		
		// TODO implement
		
	}
	
	/**
	 * Holt die zur Besucherzahlenauswertung benötigten Daten aus der Datenbank und verpackt sie schön in Listen
	 * @param con
	 * @return
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
													     res_besucherzahlen.getString("besucherzahl_jahr2)")));
				
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return ret;
		
	}
	
	
	
	/**
	 * mänsch, warum gibts keine Tupelz in java -.-
	 */
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
	
	/**
	 * mänsch, warum gibts keine Tupelz in java -.-
	 */
	private static class Triple<V, W, X> {
		
		V value1;
		W value2;
		X value3;
		private Triple(V value1, W value2, X value3) {
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
