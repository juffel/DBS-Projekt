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
	 * Liest alle Orte mit Geodaten ein und berechnet paarweise alle Abstände, liefert diese als Liste zurück
	 * @return
	 */
	public static LinkedList<Triple<String, String, Double>> generateDistances(Connection con) {
		
		LinkedList<Triple<String, String, Double>> ret = new LinkedList<Auswertung.Triple<String,String,Double>>();
		LinkedList<Triple<String, Double, Double>> data = new LinkedList<Auswertung.Triple<String,Double,Double>>();
		
		try {
			
			ResultSet qry = SQL_Utility.getFromTable(con, "geodata", new String[] {"street", "nr", "latitude", "longitude"});
			
			while(qry.next()) {
				
				data.add(new Triple<String, Double, Double>(qry.getString("street")+" "+ qry.getString("nr"), qry.getDouble("latitude"), qry.getDouble("longitude")));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		for(int i = 0; i < data.size(); i++) {
			
			for(int j = i+1; j < data.size(); j++) {
				
				double lat1 = (double) data.get(i).value2;
				double lon1 = (double) data.get(i).value3;
				double lat2 = (double) data.get(j).value2;
				double lon2 = (double) data.get(j).value3;
				
				ret.add(new Triple<String, String, Double> (data.get(i).value1, data.get(j).value1, Distances.distance(lat1, lon1, lat2, lon2,'K')));
				
			}
			
		}
		
		System.out.printf("%-27s %-27s %-5s%n", "Ort2", "Ort2", "Entfernung");
		
		for(Triple<String, String, Double> tr:ret) {
			System.out.printf("%-27s %-27s %5fkm %n", tr.getValue1(), tr.getValue2(), (double) tr.getValue3());
		}
		
		return ret;
		
	}
	
	
	/**
	 * Dieser Methode wird als parameter eine LinkedList(Jahre) von LinkedLists(Tupel (Veranstaltung, Besucherzahl)) übergeben,
	 * welche evaluiert werden.
	 */
	public static void evaluateVisitors(Connection con) {
		
		for(Integer i = 2008; i <= 2012; i++) {
			
			for(Integer j = i; j <= 2012; j++) {
				
				LinkedList<Triple<String, Integer, Integer>> tmp = fetchVisitorData(con, i.toString(), j.toString());
				// System.out.println("Besucherzahlen für die Jahre " + i + " " + j + ":\n");
				System.out.printf("%20s Besucher %4s %4s %4s %n", "Veranstaltungsname", i, j, "Differenz");
				for(Triple<String, Integer, Integer> tr : tmp) {
					
					int value1 = tr.getValue2();
					int value2 = tr.getValue3();
					
					// System.out.print(tr.getValue1() +": "+ value1 +", "+ value2);
					System.out.printf("%20s Besucher %4s %4s", tr.getValue1(), tr.getValue2(), tr.getValue3());
					
					
					if(value1 > value2)  {
						// System.out.print(" - " + ((Integer) (value1-value2)).toString());
						System.out.printf("- %4d%n", value1-value2);
					}
					
					else if(value1 < value2) {
						// System.out.print(" + " + ((Integer) (value1-value2)).toString());
						System.out.printf("+ %4d%n", value1-value2);
					}
					
					else {
						// System.out.print(" = "  + ((Integer) (value1-value2)).toString());
						System.out.printf("= %4d%n", value1-value2);
					}
					
					System.out.println("");
					
				}
				
				System.out.println("");
				
			}
			
		}
		
	}
	
	/**
	 * Holt die zur Besucherzahlenauswertung benötigten Daten aus der Datenbank und verpackt sie schön in eine LinkedList
	 * mit Einträgen der Form: | veranstaltungsname | besucherzahlJahr1 | besucherzahlJahr2 |
	 */
	public static LinkedList<Triple<String, Integer, Integer>> fetchVisitorData(Connection con, String jahr1, String jahr2) {
		
		LinkedList<Triple<String, Integer, Integer>> ret = new LinkedList<Triple<String,Integer,Integer>>();
		
		try {
			
			// wir wollen eine Auflistung folgender Form bekommen: |veranstaltungsID|besucherzahlJahr1|besucherzahlJahr2|
			
			String tmp = "SELECT v2.lp_title, v1.besucherzahl AS besucherzahl_jahr1, v2.besucherzahl AS besucherzahl_jahr2 FROM " +
					"(veranstaltung INNER JOIN besucherzahlen ON veranstaltung.veranstaltung_id = besucherzahlen.veranstaltung_id) v1," +
					"(veranstaltung INNER JOIN besucherzahlen ON veranstaltung.veranstaltung_id = besucherzahlen.veranstaltung_id) v2 " +
					"WHERE v1.lp_title = v2.lp_title AND v1.lp_lndw_year = '2009' AND v2.lp_lndw_year = '2010';";
			
			ResultSet res_besucherzahlen = con.get().createStatement().executeQuery(tmp);
			
			while(res_besucherzahlen.next()) {
				
				ret.add(new Triple<String,Integer,Integer>(res_besucherzahlen.getString("lp_title"),
													     res_besucherzahlen.getInt("besucherzahl_jahr1"),
													     res_besucherzahlen.getInt("besucherzahl_jahr2")));
				
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
		
		public String toString() {
			return new String("(" + value1.toString() + ", " + value2.toString() + ")");
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
		
		public String toString() {
			return new String("(" + value1.toString() + ", " + value2.toString() + ", " + value3.toString() + ")");
		}
		
	}
	
	public static class Fourple<V, W, X, Y> {
		
		private V value1;
		private W value2;
		private X value3;
		private Y value4;
		private Fourple(V value1, W value2, X value3, Y value4) {
			super();
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			this.value4 = value4;
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
		public Y getValue4() {
			return value4;
		}
		
		public String toString() {
			return new String("(" + value1.toString() + ", " + value2.toString() + ", " + value3.toString() + ", " + value4.toString() + ")");
		}
		
	}
	
}
