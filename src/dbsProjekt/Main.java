package dbsProjekt;

public class Main {
	
	public static void main(String args[]) {
		
		if(args.length != 1) {
			System.out.println("Beim Programmaufruf Pfad der einzulesenden sql Datei angeben");
			System.exit(0);
		}
		
		ImportData imp = new ImportData();
<<<<<<< HEAD
		imp.init("/home/andre/lange_nacht_postgres.sql");
=======
		imp.init(args[0]);
>>>>>>> c1467ed8f23fbed2521347e62cc7144e147d9316
		
	}

}
