public class NonFoodArtikel extends Ware {

	private String beschreibung, untergruppe;

	// Insgesamt X verschiedene Arten und X gleichzeitig gelagerte Bestellungen pro Ware moeglich.
	private static NonFoodArtikel datenNonFoodArtikel[][] = new NonFoodArtikel[WARENLIMIT][LIMITBESTELLUNGEN];

	public NonFoodArtikel(String name, double preis, String beschreibung, String untergruppe) {
		super(EinAusgabe.ersterBuchstabeGross(name), preis);
		this.beschreibung = beschreibung;
		this.untergruppe = untergruppe;

		// Fuegt das neu erstellte NonFoodArtikel in den ersten freien Array-Slot ein.
		for (int i=0; i<WARENLIMIT; i++) {
			if (datenNonFoodArtikel[i][0] != null)
				continue;
			datenNonFoodArtikel[i][0] = this;
			
			// Maximal Menge direkt bestellen
			datenNonFoodArtikel[i][0].nachbestellen(LAGERKAPAZITAET, false);
			break;
		}
	}

	// Bestellung
	public NonFoodArtikel(int anzahl) {
		super(anzahl);
	}

	/**
	 * Getter fuer Array mit allen NonFoodArtikel-Daten.
	 */
	public static NonFoodArtikel[][] getDatenNonFoodArtikel() {
		return datenNonFoodArtikel;
	}

	// Fuer User-Auswahl-Liste gedacht
	public static int gebeNFArtikelAus(int counter) {
		for (int i=0; i<WARENLIMIT; i++) {
			if (datenNonFoodArtikel[i][0] == null) {
				continue;
			}
			System.out.printf("(%d) %s%n", counter, datenNonFoodArtikel[i][0].getName());
			// Die Variable Counter hilft bei der Formatierung der Ausgabe.
			counter++;
		}
		return counter-1;
	}

	public boolean nachbestellen(int menge, boolean laut) {
		// Hilfsvariablen
		int mengeVorBestellung = this.getAnzahl();
		int mengeNachBestellung = this.getAnzahl() + menge;

		if (mengeVorBestellung >= 100) {
			// Lager bereits voll.
			System.out.printf("Das Lager hat bereits %d Einheiten %s und ist somit voll.%n"
					, LAGERKAPAZITAET, this.getName());
			return false;
		} else if (mengeNachBestellung > LAGERKAPAZITAET) {
			// Bestellmenge + bereits vorhanden ueberschreitet Kapazitaet.
			int angepassteBestellung = LAGERKAPAZITAET - mengeVorBestellung;
			System.out.printf("Das Lager hat bereits %d Einheiten %s. Es wurden nur %d Einheiten bestellt.%n"
					, mengeVorBestellung, this.getName(), angepassteBestellung);
			this.bestellungHinzufuegen(angepassteBestellung);
			this.setAnzahl(LAGERKAPAZITAET);
			return true;
		} else {
			// Bestellmenge in Ordnung.
			this.setAnzahl(mengeNachBestellung);
			this.bestellungHinzufuegen(menge);
			if (laut)
				System.out.printf("Es wurden %d Einheiten der Ware %s bestellt.%n", menge, this.getName());
			return true;
		}
	}

	/**
	 * Erstellt ein neues Bestell-Objekt und reiht es an der richtigen Stelle
	 * im zwei-dimensionalen NonFoodArtikel-Array ein.
	 */
	// TODO: Check, falls alle Slots frei sind einbauen?
	public void bestellungHinzufuegen(int menge) {
		// Hilfsvariablen
		String gesuchterNonFoodArtikel = this.getName();
		// Sucht die Zeile in dem der gesuchte NonFoodArtikel gespeichert ist.
		for (int i=0; i<WARENLIMIT; i++) {
			if (gesuchterNonFoodArtikel == datenNonFoodArtikel[i][0].getName()) {
				// ersten freien Bestellungsslot finden und neues Bestellungsobjekt dort speichern.
				for (int j=1; j<=LIMITBESTELLUNGEN; j++) {
					if (datenNonFoodArtikel[i][j] == null) {
						datenNonFoodArtikel[i][j] = new NonFoodArtikel(menge);
						break;
					}
				}
				// Bestellung hinzugefuegt. Schleife verlassen.
				break;
			}
		}
	}

	public boolean herausgeben(int menge, int slot, boolean statistik) {
		int mengeVorHerausgabe = this.getAnzahl();
		int mengeNachHerausgabe = this.getAnzahl() - menge;

		if (mengeVorHerausgabe >= menge) {

			// Menge in dem Haupt-Objekt der Ware aktualisieren;
			this.setAnzahl(mengeNachHerausgabe);
			System.out.printf("Es wurden %d Einheiten %s entfernt. Das Lager hat nun noch %d Einheiten.%n"
					, menge, this.getName(), this.getAnzahl());
		} else {
			System.out.printf("Es sind nicht mehr genuegend Einheiten %s auf Lager.%n", this.getName());
			String antwort = EinAusgabe.eingabeString(String.format(
						"Moechtest du das Lager fuer %s komplett auf %d Einheiten aufstocken?%n"
						+ "Ja / Nein" , this.getName(), LAGERKAPAZITAET));
			System.out.println();
			if (antwort.toLowerCase().startsWith("j"))
				this.nachbestellen(LAGERKAPAZITAET - mengeVorHerausgabe, false);
			return false;
		}

		// Implementierung des individuellen Anforderungsparts.
		// Verkauf der Ware der Statistik hinzufuegen.
		if (statistik) {
			this.erhoeheVerkaufteMenge(menge);
		}

		int tempMenge = menge;
		int verschiebungsfaktor = 0;
		
		// Anzahl der Waren in den einzelnen gelagerten Bestellungen bezueglich der Herausgabe aktualisieren.
		for (int i=1; i<LIMITBESTELLUNGEN; i++) {
			// Menge ist kleiner als aelteste Bestellung, also Menge abziehen und Schleife verlassen.
			if (tempMenge < datenNonFoodArtikel[slot][i].getAnzahl()) {
				datenNonFoodArtikel[slot][i].setAnzahl(datenNonFoodArtikel[slot][i].getAnzahl() - tempMenge);
				break;
			// Menge ist groesser als aelteste Bestellung, dann Bestellung und zur naechsten gehen.
			} else if (tempMenge > datenNonFoodArtikel[slot][i].getAnzahl()) {
				tempMenge -= datenNonFoodArtikel[slot][i].getAnzahl();
				datenNonFoodArtikel[slot][i] = null;
				// alle Bestellungen werden nach "links" zum Anfang verschoben.
				verschiebungsfaktor++;
				continue;
			// Wenn gleich, dann Bestellung loeschen und den Verschiebungsfaktor um eins erhoehen.
			} else {
				datenNonFoodArtikel[slot][i] = null;
				verschiebungsfaktor++;
				break;
			}
		}

		// verschiebt alle Bestellungen im Array nach links, wenn welche waehrend des vorherigen Schrittes
		// geloescht wurden. Alle alten Bestellungen werden naemlich immer zuerst verbraucht.
		if (verschiebungsfaktor > 0) {
			for (int i=1; i<LIMITBESTELLUNGEN; i++) {
				if (LIMITBESTELLUNGEN - verschiebungsfaktor - 1 < i)
					break;
				datenNonFoodArtikel[slot][i] = datenNonFoodArtikel[slot][i + verschiebungsfaktor];
			}
		}
		return true;
	}

	@Override
	public String toString() {
		// Konstruiert den String mit allen relevanten Informationen und uebergibt ihn.
		return String.format(
				"%nDer Artikel %s aus der Abteilung NonFood kostet pro Menge %.2f Euro.%n"
				+ "%1$s ist Teil der Untergruppe %s. Die Beschreibung lautet:%n\"%s\".%n"
				+ "Es befinden sich %d Einheiten im Lager."
				, this.getName(), this.getPreis(), this.untergruppe, this.beschreibung, this.getAnzahl());
	}


}
