public class Backware extends Lebensmittel {

	private boolean gebacken;

	// Insgesamt X verschiedene Arten und X gleichzeitig gelagerte Bestellungen pro Ware moeglich.
	private static Backware datenBackware[][] = new Backware[WARENLIMIT][LIMITBESTELLUNGEN];

	public Backware(String name, double preis, double gewicht, int haltbarkeit, boolean bedarfKuehlung) {
		super(name, preis, gewicht, haltbarkeit, bedarfKuehlung);
		this.gebacken = false;
		
		// Fuegt das neu erstellte Backware in den ersten freien Array-Slot ein.
		for (int i=0; i<WARENLIMIT; i++) {
			if (datenBackware[i][0] != null)
				continue;
			datenBackware[i][0] = this;
			
			System.out.println();
			// Maximal Menge direkt bestellen
			datenBackware[i][0].nachbestellen(LAGERKAPAZITAET);
			break;
		}
	}
	
	// Bestellung
	public Backware(int anzahl, int haltbarkeit) {
		super(anzahl, haltbarkeit);
	}

	// Kurzes MHD
	public Backware(String name, int anzahl) {
		super(name, anzahl);
	}

	/**
	 * Getter fuer Array mit allen Backwaren-Daten.
	 */
	public static Backware[][] getDatenBackware() {
		return datenBackware;
	}

	public String returnName() {
		return this.getName();
	}





}
