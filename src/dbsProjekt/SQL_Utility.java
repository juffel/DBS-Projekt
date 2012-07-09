package dbsProjekt;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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

}
