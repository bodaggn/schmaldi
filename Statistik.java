import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Comparator;

/** 
 * Diese Klasse implementiert den individuellen Anforderungsteil.
 * Es wird eine Rangliste anhand der Verkaufsmengen ausgegeben. Der User kann entscheiden,
 * ob aufsteigend oder absteigend sortiert werden soll.
 * @author Louis, Tobi
 * @version 1.0
 */
public class Statistik {

	/**
	 * Ordnet alle Waren in einer sortierten Reihenfolge anhand der Verkaufszahlen ein und gibt diese aus.
	 * Orientiert anhand dieser Resource:
	 * <a href="https://www.delftstack.com/howto/java/how-to-sort-a-map-by-value-in-java/">Resource</a>
	 * @param input 1 = absteigend sortiert, 2 = aufsteigend sortiert.
	 * @param anzahlStatistik wie viele Elemente in der Liste ausgegeben werden sollen.
	 */
	public static void ausgabeStatistik(int input, int anzahlStatistik) {

		Map<String, Integer> warenTabelle = new HashMap<>();

		// Hier wird direkt auf alle angelegten Lebensmittel-Objekte zugegriffen.
		// Die Objekte in Position "[i][0]" sind die Hauptobjekte,
		// die den Wert "verkaufteMengen" enthalten, der hier interessant ist.
		Lebensmittel[][] datenLebensmittel = Lebensmittel.getDatenLebensmittel();
		for (int i=0; i<Ware.WARENLIMIT; i++) {
			if (datenLebensmittel[i][0] == null)
				continue;
			warenTabelle.put(datenLebensmittel[i][0].getName(), datenLebensmittel[i][0].getVerkaufteMengen());
		}

		// Klasse Getraenk wird ebenfalls der Map hinzugefuegt.
		Getraenk[][] datenGetraenk = Getraenk.getDatenGetraenk();
		for (int i=0; i<Ware.WARENLIMIT; i++) {
			if (datenGetraenk[i][0] == null)
				continue;
			warenTabelle.put(datenGetraenk[i][0].getName(), datenGetraenk[i][0].getVerkaufteMengen());
		}

		// Klasse NonFoodArtikel wird ebenfalls der Map hinzugefuegt.
		NonFoodArtikel[][] datenNonFoodArtikel = NonFoodArtikel.getDatenNonFoodArtikel();
		for (int i=0; i<Ware.WARENLIMIT; i++) {
			if (datenNonFoodArtikel[i][0] == null)
				continue;
			warenTabelle.put(datenNonFoodArtikel[i][0].getName(), datenNonFoodArtikel[i][0].getVerkaufteMengen());
		}

		// Klasse Backware wird ebenfalls der Map hinzugefuegt.
		Backware[][] datenBackware = Backware.getDatenBackware();
		for (int i=0; i<Ware.WARENLIMIT; i++) {
			if (datenBackware[i][0] == null)
				continue;
			warenTabelle.put(datenBackware[i][0].getName(), datenBackware[i][0].getVerkaufteMengen());
		}

		List<Entry<String, Integer>> list = new ArrayList<>(warenTabelle.entrySet());

		// absteigend sortiert
		if (input == 1)
			list.sort(Entry.comparingByValue(Comparator.reverseOrder()));
		// aufsteigend sortiert
		else 
			list.sort(Entry.comparingByValue());

		// Rangliste ausgeben und abbrechen, sollten gar nicht genug Waren vorhanden sein
		// fuer die vom User gewollte Warenanzahl, die in die Auflistung mit aufgenommen werden sollte.
		try {
			for (int i=0; i<anzahlStatistik; i++) {
				System.out.printf("%4d Einheiten verkauft von %s%n",  list.get(i).getValue(), list.get(i).getKey());
			}
		} catch (IndexOutOfBoundsException e) {
			// abbrechen
		}
	}

}
