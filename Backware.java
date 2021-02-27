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

	public boolean backeWare() {
		// bereits gebacken.
		if (this.gebacken) {
			System.out.printf("Die komplette Ware %s ist bereits aufgebacken.", this.getName());
			return false;
		// noch nicht gebacken.
		} else {
			System.out.printf("Die Ware %s wird aufgebacken. Einen Moment bitte.", this.getName());
			return true;
		}
	}

	public static Backware[] kurzesMHD() {
		// Backwaren Array, der zurueck gegeben wird.
		// Groesse anhand von aktuellen Programmeinstellungen berechnet.
		Backware[] rueckgabeArray = new Backware[WARENLIMIT*LIMITBESTELLUNGEN/3];
		// Anzahl von einzelnen Backwaren mit MHD 0-2 Tage.
		int anzahl = 0;
		// Hilfsvariable fuer weniger Array-Zugriffe.
		Backware ware;
		// Hilfsvariable um Array-Indexe zu verwalten.
		int index = -1;
		// Zu loeschende abgelaufene Menge
		int nochZuLoeschen = 0;
		// durch einzelne Waren iterieren.
		for (int i=0; i<WARENLIMIT; i++) {
			// Slot skippen, falls leer.
			if (datenBackware[i][0] == null)
				continue;
			// durch einzelne Bestellungen der jeweiligen Ware iterieren.
			for (int j=1; j<=LIMITBESTELLUNGEN; j++) {
				ware = datenBackware[i][j]; 
				if (ware == null) {
					break;
				}
				index++;
				if (ware.istHaltbar() >= 0 && ware.istHaltbar() <= 2) {
					// Anzahl mit der Anzahl der Waren der Bestellung mit kurzem MHD addieren.
					anzahl += ware.getAnzahl();
				// Wenn eine eingelagerte Bestellung einer Ware abgelaufen ist, wird diese automatisch geloescht.
				} else if (ware.istHaltbar() < 0) {
					System.out.printf("%nMHD abgelaufen: %s von Ware %s automatisch geloescht!"
							, ware.getName(), datenBackware[i][0].getName());
					nochZuLoeschen += ware.getAnzahl();
				}
			}
			
			// Alle abgelaufenen Bestellungen der Ware herausgeben / loeschen.
			if (nochZuLoeschen > 0) {
				System.out.println();
				// false Parameter gibt an, dass hier nicht geprueft werden soll, dass aufgebacken ist.
				datenBackware[i][0].herausgeben(nochZuLoeschen, i, false);
			}

			if (index >= 0) {
				// Eine Backware erfolgreich durchlaufen, Daten dem Array hinzufuegen.
				rueckgabeArray[index] = new Backware(datenBackware[i][0].getName(), anzahl);
			}

			// Resetten der Hilfsvariablen, da jetzt eine neue Backware durchlaufen wird.
			anzahl = 0;
			nochZuLoeschen = 0;
		}
		return rueckgabeArray;
	}

	// Fuer User-Auswahl-Liste gedacht
	public static int gebeBackwarenAus(int counter) {
		for (int i=0; i<WARENLIMIT; i++) {
			if (datenBackware[i][0] == null) {
				continue;
			}
			System.out.printf("(%d) %s%n", counter, datenBackware[i][0].getName());
			// Die Variable Counter hilft bei der Formatierung der Ausgabe.
			counter++;
		}
		return counter-1;
	}


	/**
	 * Erstellt ein neues Bestell-Objekt und reiht es an der richtigen Stelle
	 * im zwei-dimensionalen Backwaren-Array ein.
	 */
	@Override
	public void bestellungHinzufuegen(int menge) {
		// Hilfsvariablen
		String gesuchteBackware = this.getName();
		int tageHaltbar;
		// Sucht die Zeile in dem die gesuchte Backware gespeichert ist.
		for (int i=0; i<WARENLIMIT; i++) {
			if (gesuchteBackware == datenBackware[i][0].getName()) {
				tageHaltbar = datenBackware[i][0].haltbarkeit;
				// ersten freien Bestellungsslot finden und neues Bestellungsobjekt dort speichern.
				for (int j=1; j<=LIMITBESTELLUNGEN; j++) {
					if (datenBackware[i][j] == null) {
						datenBackware[i][j] = new Backware(menge, tageHaltbar);
						break;
					}
				}
				// Bestellung hinzugefuegt. Schleife verlassen.
				break;
			}
		}
	}

	public boolean herausgeben(int menge, int slot, boolean aufbackenPruefen) {

		// Folgende Abfrage ueberspringen, wenn Aufbacken-Ueberpruefung nicht gewuenscht.
		if (aufbackenPruefen) {
			// Prueft, ob die gesamte spezielle Backware vor Verkauf noch aufgebacken werden muss.
			if (this.backeWare()) {
				// TODO Wartezeit aufbacken simulieren
				System.out.printf("Alle Einheiten der Ware %s wurden erfolgreich aufgebacken. Verkauf fortgesetzt.%n"
						, this.getName());
			} else
				System.out.printf("Alle Einheiten der Ware %s sind bereits erfolgreich aufgebacken. Verkauf fortgesetzt.%n"
						, this.getName());
		}

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
				this.nachbestellen(LAGERKAPAZITAET - mengeVorHerausgabe);
			return false;
		}

		int tempMenge = menge;
		int verschiebungsfaktor = 0;
		
		// Anzahl der Waren in den einzelnen gelagerten Bestellungen bezueglich der Herausgabe aktualisieren.
		for (int i=1; i<LIMITBESTELLUNGEN; i++) {
			// Menge ist kleiner als aelteste Bestellung, also Menge abziehen und Schleife verlassen.
			if (tempMenge < datenBackware[slot][i].getAnzahl()) {
				datenBackware[slot][i].setAnzahl(datenBackware[slot][i].getAnzahl() - tempMenge);
				break;
			// Menge ist groesser als aelteste Bestellung, dann Bestellung und zur naechsten gehen.
			} else if (tempMenge > datenBackware[slot][i].getAnzahl()) {
				tempMenge -= datenBackware[slot][i].getAnzahl();
				datenBackware[slot][i] = null;
				// alle Bestellungen werden nach "links" zum Anfang verschoben.
				verschiebungsfaktor++;
				continue;
			// Wenn gleich, dann Bestellung loeschen und den Verschiebungsfaktor um eins erhoehen.
			} else {
				datenBackware[slot][i] = null;
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
				datenBackware[slot][i] = datenBackware[slot][i + verschiebungsfaktor];
			}
		}
		return true;
	}

	@Override
	public String toString() {
		// Benoetigt um nach Bedarf ein "nicht" bei "Bedarf Kuehlung" in den String einzufuegen.
		String kuehlungJaNein = (this.bedarfKuehlung) ? "" : " nicht";

		// Erneute Hilfsvariable fuer eine grammatikalisch korrekte Ausgabe.
		String tagOderTage = (this.haltbarkeit != 1) ? "Tage" : "Tag";

		// Konstruiert den String mit allen relevanten Informationen und uebergibt ihn.
		return String.format(
				"%nEine Menge %s aus der Abteilung Backwaren kostet %.2f Euro und hat ein Gewicht von %.2fg.%n"
				+ "Die Haltbarkeit betraegt %d %s. Eine Kuehlung wird%s benoetigt.%n"
				+ "Es befinden sich %d Einheiten im Lager."
				, this.getName(), this.getPreis(), this.gewicht, this.haltbarkeit, tagOderTage, kuehlungJaNein, this.getAnzahl());
	}

}
