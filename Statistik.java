import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Comparator;

/**
 * Ordnet alle Waren in einer sortierten Reihenfolge anhand der Verkaufszahlen ein und gibt diese aus.
 * Orientiert anhand dieser Resource:
 * <a href="https://www.delftstack.com/howto/java/how-to-sort-a-map-by-value-in-java/">Resource</a>
 * @param input 1 = absteigend sortiert, 2 = aufsteigend sortiert.
 */
public class Statistik {

	public static void ausgabeStatistik(int input) {

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

		// Die Zeile war ein bisschen Fummelarbeit bis es geklappt hat. :D
		 list.forEach((i) -> System.out.printf("%4d Einheiten verkauft von %s%n",  i.getValue(), i.getKey()));
	}

}
