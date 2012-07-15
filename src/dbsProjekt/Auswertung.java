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
	public static LinkedList<LinkedList<Triple<String, String, Integer>>> fetchVisitorData(Connection con, String jahr1, String jahr2) {
		
		LinkedList<LinkedList<Triple<String, String, Integer>>> ret = new LinkedList<LinkedList<Triple<String,String,Integer>>>();
		
		// TODO implement
		
		try {
			
			// wir wollen eine Auflistung folgender Form bekommen: |veranstaltungsID|besucherzahl|
			// String tmp = "SELECT veranstaltung_id, besucherzahl FROM besucherzahl;" ;
			String tmp = "(SELECT veranstaltung_id, lndw_year FROM besucherzahlen, veranstaltung WHERE lndw_year = "+ jahr1 +") INNER JOIN (" +
					"(SELECT veranstaltung_id, lndw_year FROM besucherzahlen, veranstaltung WHERE lndw_year = "+ jahr2 +");" ;
			ResultSet res_besucherzahlen = con.get().createStatement().executeQuery(tmp);
			
			SQL_Utility.printResultSet(res_besucherzahlen);
			
			// TODO weitermachen
			
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
	private class Triple<V, W, X> {
		
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
