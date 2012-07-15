package dbsProjekt;
/**
 * 
 * @author andre
 * Distanzberechnung mit Hilfe der Semiversus-Formel
 * es werden zwei Koordinaten als Eingabewerte benötigt
 * zurückgegeben wird ein double mit der Entfernung in Kilometern
 * Erdradius: Äquatorialradius laut aktueller Messung IERS 2003
 */
public class Distances {
	 static double erdradius = 6378.1366; // IERS 2003
	 public static double distance(double lat1, double lon1, double lat2, double lon2){
		 
		 double dist_lat = (lat2-lat1) * Math.PI/180;
		 double dist_lon = (lon2-lon1) * Math.PI/180;
		 
		 double haver_a = Math.sin(dist_lat/2) * Math.sin(dist_lat/2) +  
			        Math.cos(lat1*Math.PI/180) * Math.cos(lat2*Math.PI/180) *   
			        Math.sin(dist_lon/2) * Math.sin(dist_lon/2);
		 
		 double haver_c = 2 * Math.asin((Math.sqrt(haver_a)));
		 
		 double haver_d = erdradius * haver_c;
		 
		 return haver_d;
	 }

}
