import java.time.LocalDate;
import java.time.Period;

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

	// TODO
	// public boolean backWare() {}


	// TODO vererbt!
	public String haltbarBis() {
		LocalDate mhd = this.getAnlegedatum().plusDays(this.haltbarkeit);
		return EinAusgabe.datumFormatiert(mhd);
	}

	/**
	 * Gibt die Anzahl der Tage zwischen jetzt und MHD als Integer zurueck.
	 * @return Differenz der Tage als Integer. -1, falls ein ueberschritten
	 */
	// TODO vererbt!
	public int istHaltbar() {
		LocalDate mhd = this.getAnlegedatum().plusDays(this.haltbarkeit);
		int tageDifferenz = Period.between(LocalDate.now(), mhd).getDays();
		// laut Aufgabenstellung soll immer -1 bei negativen Werten 
		// zurueck gegeben werden und nicht -2, -3, ... 
		if (tageDifferenz >= 0)
			return tageDifferenz;
		else
			return -1;
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
				datenBackware[i][0].herausgeben(nochZuLoeschen, i);
			}

			// Eine Backware erfolgreich durchlaufen, Daten dem Array hinzufuegen.
			rueckgabeArray[index] = new Backware(datenBackware[i][0].getName(), anzahl);
			// Resetten der Hilfsvariable, da jetzt eine neue Backware durchlaufen wird.
			anzahl = 0;
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

	// Aufruf der Methode:
	// datenBackware[1][0].nachbestellen(20);
	// TODO vererbt!
	public boolean nachbestellen(int menge) {
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
			System.out.printf("Es wurden %d Einheiten der Ware %s bestellt.%n", menge, this.getName());
			return true;
		}
	}



	/**
	 * Erstellt ein neues Bestell-Objekt und reiht es an der richtigen Stelle
	 * im zwei-dimensionalen Backwaren-Array ein.
	 */
	// TODO: Check, falls alle Slots frei sind einbauen?
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

	@Override
	public boolean herausgeben(int menge, int slot) {
		int mengeVorHerausgabe = this.getAnzahl();
		int mengeNachHerausgabe = this.getAnzahl() - menge;

		if (mengeVorHerausgabe >= menge) {

			// Menge in dem Haupt-Objekt der Ware aktualisieren;
			this.setAnzahl(mengeNachHerausgabe);
			System.out.printf("Es wurden %d Einheiten %s herausgegeben. Das Lager hat nun noch %d Einheiten.%n"
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
