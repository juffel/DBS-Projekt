package dbsProjekt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Mithilfe dieser Klasse kann man eine Datenbank erzeugen, die unserem Schema entspricht.
 * @author julian
 *
 */
public class ProjektDB {
	
	// hab hier das Connection Objekt als Feld festgelegt, damit nicht jede Methode neu eine Verbindung zur DB aufbauen muss
	private Connection con;
	private Statement stmt;
	
	/**
	 * erstellt eine neue Datenbank, auf welcher ein in der Datei file abgelegter SQL-Query ausgeführt wird
	 * @param file
	 */
	public void init(String path) throws SQLException {
		
		File input = new File(path);
		BufferedReader rdr;
		
		try {
			
			rdr = new BufferedReader(new FileReader(input));
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			return;
		}
		
		LinkedList<String> queries = new LinkedList<String>();
		
		// die Datei Zeichen für Zeichen auslesen
		int character = 0;
		boolean prev_semikolon = false;
		StringBuffer buf = new StringBuffer(0);
		
		// und die einzelnen Queries zusammenfassen
		// immer wenn ein newline-Zeichen auf ein Semikolon folgt wird das als Ende eines Statements betrachtet
		try {
			
			while((character = rdr.read()) != -1) {
			
				char c = (char) character;			
				
				switch (c) {
			
				case ';':
				
					prev_semikolon = true;
					buf.append(';');
					break;
				
				case '\n':
				
					buf.append('\n');
				
					if(prev_semikolon) {
						queries.add(buf.toString()); // Query in queries abheften
						buf = new StringBuffer(0); // und Buffer reseten
					}
					prev_semikolon = false;
					break;
				
				default:
				
					prev_semikolon = false;
					buf.append((char) c);
						break;
					
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
			return;
		}

		// dafür sorgen, dass eine frische Datenbank mit dem Namen lndw angelegt wird.

		// Verbindung zur DB aufbauen
		con = new Connection("localhost", "5432", "template1", "user", "password");
			
		stmt = con.get().createStatement();
		stmt.executeUpdate("DROP DATABASE IF EXISTS lndw;");
		stmt.executeUpdate("CREATE DATABASE lndw;");
		
		
		// Queries an die DB schicken, dafür neue Verbindung mit Benutzernamen aufbauen
			
		con = new Connection("localhost", "5432", "lndw", "user", "password");
		stmt = con.get().createStatement();
			
		for(String s:queries) {
				
			System.out.print("Executing \"" + s + "\"");
			stmt.executeUpdate(s);
			System.out.println("...done");
			
		}
		
		System.out.println("import done.");
	}
	
	
	/**
	 * erstellt die von uns entworfenen Relationstabellen
	 * darf erst nach init() aufgerufen werden, bzw. wenn eine Verbindung zur DB besteht
	 */
	public void createTables() throws SQLException {
		
		System.out.print("creating tables...");

		if(con == null) {
			System.out.println("cannot create Tables, no connection to database");
			return;
		}
			
		// Create Tables

		stmt.executeUpdate("create table veranstalter (lp_pa_name text,lp_pa_i_name text,lp_pa_i_fb_name text,lp_pa_notes text,veranstalter_id uuid);");
		stmt.executeUpdate("create table ort (lp_fp_street varchar(255),lp_fp_nr varchar(10),lp_fp_location varchar(255),lp_fp_plz int,lp_fp_city varchar(255),lp_fp_name varchar(255),lp_fp_cashplace int,lp_fp_barrierfree smallint);");
		stmt.executeUpdate("create table veranstaltung (lp_title text,lp_lndw_year varchar(4),lp_user_comment text,lp_content_short text,lp_start_time time,lp_end_time time,lp_continuous smallint,lp_period int,lp_time_necessary int,lp_time_is_recommended smallint,lp_time_comment text,lp_signingdate timestamp,lp_kinderprogramm smallint);");
		
		System.out.println("done.");
	}
	
	
	
	/**
	 * extrahiert die Daten aus dem großen red_table und fügt sie in die jeweiligen Tabellen ein
	 */
	public void fillTables() throws SQLException {
		
		System.out.print("filling tables...");
	
		if(con == null) {
			System.out.println("cannot fill Tables, no connection to database");
			return;
		}
		
		ResultSet res_veranstalter = stmt.executeQuery("SELECT lp_pa_name, lp_pa_i_name, lp_pa_i_fb_name, lp_pa_notes FROM lp_red_table;");
		ResultSet res_veranstaltung = stmt.executeQuery("SELECT lp_fp_street, lp_fp_nr, lp_fp_location, lp_fp_plz, lp_fp_city, lp_fp_name, lp_fp_cashplace, lp_fp_barrierfree FROM lp_red_table;");
		ResultSet res_ort = stmt.executeQuery("SELECT lp_title, lp_lndw_year, lp_user_comment, lp_content_short, lp_start_time, lp_end_time, lp_continuous, lp_period, lp_time_necessary, lp_time_is_recommended, lp_time_comment, lp_signingdate, lp_kinderprogramm FROM lp_red_table;");

		
		// fülle veranstalter tabelle
		while(res_veranstalter.next()) {
			
			StringBuffer tmp = new StringBuffer();
			
			for(int i = 0; i < 5; i++) {
				tmp.append(res_veranstalter.getString(i));
			}
			
			stmt.executeUpdate("INSERT INTO veranstalter VALUES (" + tmp + UUID.randomUUID() + ");");
		}
		
		// fülle veranstaltungs tabelle
		
		// fülle ort tabelle
	
		System.out.println("done.");
	}
	
}
