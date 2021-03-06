/**
 * Diese Klasse erstellt Testwaren aller Abteilungen, die zum Pruefen des Gesamtsystems geeignet sind,
 * da ebenfalls Werte eingetragen werden, die durch eine User-Eingabe waehrend der Laufzeit nicht 
 * reproduzierbar sind, da diese abgefangen werden.
 * @author Louis, Tobi
 * @version 1.0
 */
public class Lager {

	/**
	 * Erstellt jeweils eine bestimmte Anzahl Waren fuer jede Abteilung.
	 *
	 * Wichtig! Zu Testzwecken werden auch negative Haltbarkeiten erstellt, die ansonsten bei der User-Eingabe
	 * abgefangen werden wuerden. Hierdurch lassen sich die Mindesthalbarkeits-Methoden testen.
	 *
	 * Die Namen laufen nach folgendem Schema:
	 * Fuer Lebensmittel = Lbm_test01, Lbm_test02, ...
	 * Fuer Getraenke = Gtr_test01, Gtr_test02, ...
	 * ...
	 * Die Attribute werden automatisch variiert.
	 * @param anzahlWaren wie viele Waren pro Abteilung angelegt werden sollen.
	 */
	public static void erstelleLager(int anzahlWaren) {
		// Deklaration aller Parameter, die an die Konstruktoren uebergeben werden.
		String name, beschreibung, untergruppe;
		double preis, gewicht;
		int haltbarkeit, alkoholgehalt;
		boolean bedarfKuehlung;

		// Test-Lebensmittel erstellen.
		for (int i=1; i<=anzahlWaren; i++) {
			// Trigger auf true, damit ein neues Lebensmittel-Objekt in das Array eingefuegt wird.
			// Bei der Backwaren-Erstellung muss auf false gesetzt werden, da ansonsten mit 
			// einem Aufruf sowohl Backwaren- als auch Lebensmittel-Objekte erstellt werden.
			Lebensmittel.setTrigger(true);

			name = String.format("Lbm_test%02d", i);
			gewicht = preis = i;
			bedarfKuehlung = false;
			
			// Insbesondere alle 3 Durchlauefe negative Haltbarkeit zum Testen der MHD-Methoden bereitstellen.
			if (i % 3 == 0)
				haltbarkeit = -1;
			else if (i % 2 == 0) {
				haltbarkeit = i;
				bedarfKuehlung = true;
			}
			else 
				haltbarkeit = i;
			
			// Neues Lebensmittel erstellen.
			new Lebensmittel(name, preis, gewicht, haltbarkeit, bedarfKuehlung);
		}

		// Test-Getraenke erstellen.
		for (int i=1; i<=anzahlWaren; i++) {
			name = String.format("Gtr_test%02d", i);

			alkoholgehalt = 0;
			preis = i;
			
			// Alkoholgehalt anhand vom Iterator eintragen.
			if (i % 2 == 0) {
				alkoholgehalt = i;
			}
			
			// Neues Getraenk erstellen.
			new Getraenk(name, preis, alkoholgehalt);
		}

		// Test-NonFood erstellen.
		for (int i=1; i<=anzahlWaren; i++) {
			name = String.format("Nf_test%02d", i);
			beschreibung = String.format("Nf_beschreibung_test%02d", i);

			preis = i;
			
			if (i % 2 == 0)
				untergruppe = String.format("Nf_kleidung_test%02d", i);
			else if (i % 3 == 0)
				untergruppe = String.format("Nf-drogerie_test%02d", i);
			else
				untergruppe = String.format("Nf_medien_test%02d", i);

			// Neues NonFood erstellen.
			new NonFoodArtikel(name, preis, beschreibung, untergruppe);
		}

		for (int i=1; i<=anzahlWaren; i++) {
			// Trigger auf false, damit KEIN neues Lebensmittel-Objekt in das Array eingefuegt wird,
			// da ansonsten mit einem Aufruf sowohl Backwaren- als auch Lebensmittel-Objekte erstellt werden.
			Lebensmittel.setTrigger(false);

			name = String.format("Bw_test%02d", i);
			gewicht = preis = i;
			bedarfKuehlung = false;
			
			// Insbesondere alle 3 Durchlauefe negative Haltbarkeit zum Testen der MHD-Methoden bereitstellen.
			if (i % 3 == 0)
				haltbarkeit = -1;
			else if (i % 2 == 0) {
				haltbarkeit = i;
				bedarfKuehlung = true;
			}
			else 
				haltbarkeit = i;
			
			// Neues Lebensmittel erstellen.
			new Backware(name, preis, gewicht, haltbarkeit, bedarfKuehlung);
		}
	}
}
