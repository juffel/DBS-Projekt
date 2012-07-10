package dbsProjekt;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

public class SQL_Utility {
	
	/**
	 * Methode um ein ResultSet auf der Kommandozeile auszugeben
	 * @throws SQLException
	 */
	public static void printResultSet(ResultSet res) throws SQLException {
		
		ResultSetMetaData rsmd = res.getMetaData();
		int columns = rsmd.getColumnCount();
		
		while(res.next()) {
			for(int i = 1; i <= columns; i++) {
				System.out.print(res.getObject(i).toString() + "\t\t");
			}
			System.out.print("\n");
		}
	}
	
	/**
	 * Fragt alle Werte der mit fields übergebenen Spalten von table an und liefert diese als ResultSet zurück.
	 * Die Verbindung zur Datenbank wird beim Aufruf mit übergeben.
	 */
	public static ResultSet getFromTable(Connection con, String table, String[] fields) throws SQLException {
		
		Statement stmt = con.get().createStatement();
		
		StringBuffer buf = new StringBuffer();
		for(String s:fields) {
			buf.append(s + ',');
		}
		buf.delete(buf.length()-1, buf.length());
		
		String tmp = "SELECT " + buf.toString() + " FROM " + table + ";";
		ResultSet res = stmt.executeQuery(tmp);
		return res;
		
	}
	
	
	/**
	 * Fügt das übergebene ResultSet in die ersten <Länge des ResultSets> Spalten des übergebenen tables ein.
	 * Die Verbindung zur Datenbank wird beim Aufruf mit übergeben.
	 */
	public static void fillTableWith(Connection con, String table, ResultSet input) throws SQLException {

		Statement stmt = con.get().createStatement();
		
		int columns = input.getMetaData().getColumnCount();
		
		while(input.next()) {
		
			StringBuffer buf = new StringBuffer();
			for(int i = 1; i < columns+1; i++) {
				
				String tmp = input.getString(i);
				if(tmp == null) {
					buf.append("null");
				}
				
				// falls in den daten ein hochkomma vorkommt stört das die weitere verarbeitung, alle vorkommen müssen um ein weiteres hochkomma ergänzt werden
				else if(tmp.contains("'")){
//					String tmp2 = "'" + tmp.substring(0, tmp.indexOf("'")+1) + "'" + tmp.substring(tmp.indexOf("'")+1, tmp.length()) + "'";
//					buf.append(tmp2);
					StringTokenizer tok = new StringTokenizer(tmp, "'");
					buf.append("'");
					while(tok.hasMoreTokens()) {
						buf.append(tok.nextToken() + "''");
					}
					buf.delete(buf.length()-1, buf.length());
				}
				
				else {
					buf.append("'");
					buf.append(tmp);
					buf.append("'");
				}
				buf.append(',');
			
			}
			// letztes komma wieder rauslöschen
			buf.delete(buf.length()-1, buf.length());
			
			String tmp = "INSERT INTO " + table + " VALUES (" + buf.toString() + ");";
//			System.out.println(tmp);
			stmt.executeUpdate(tmp);
			
		}
		
	}
	
	
	public static void addUUID() {
		
	}

}
