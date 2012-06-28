package tutorial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Quelle: http://zetcode.com/db/postgresqljavatutorial/
 * @author julian
 *
 */
public class Tutorial {
	
	public static void main(String args[]) {
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		String url = "jdbc:postgresql://localhost/lndw";
		String user = "user";
		String password = "9la6efJ3S7";
		
		try {
			
			con = DriverManager.getConnection(url, user, password);
			st = con.createStatement();
			rs = st.executeQuery("SELECT VERSION()");
			
			if(rs.next()) {
				System.out.println(rs.getString(1));
			}
		} 
		catch (SQLException e) {
			Logger lgr = Logger.getLogger(Tutorial.class.getName());
			lgr.log(Level.SEVERE, e.getMessage(), e);
		}
		finally {
			try {
				if(rs != null)
					rs.close();
				if(st != null)
					st.close();
				if(con != null)
					con.close();
				
			}
			catch(SQLException e) {
				Logger lgr = Logger.getLogger(Tutorial.class.getName());
				lgr.log(Level.WARNING, e.getMessage(), e);
			}
		}
	}

}
