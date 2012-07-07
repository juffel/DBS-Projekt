package dbsProjekt;

public class Main {
	
	public static void main(String args[]) {
		
		ImportData imp = new ImportData();
		imp.initEasy("/home/postgres/lange_nacht_postgresql.sql");
		
	}

}
