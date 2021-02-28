/**
 * Implementiert alle Funktionalitaeten der Abteilung Getraenke.
 * Unterklasse von Ware.
 * @author Louis, Tobi
 * @version 1.0
 */
public class Getraenk extends Ware {

	private double alkoholgehalt;

	// Insgesamt X verschiedene Arten und X gleichzeitig gelagerte Bestellungen pro Ware moeglich.
	private static Getraenk datenGetraenk[][] = new Getraenk[WARENLIMIT][LIMITBESTELLUNGEN];

	/**
	 * Konstruktor
	 * @param name Name des Getraenks.
	 * @param preis Preis des Getraenks.
	 * @param alkoholgehalt Alkoholgehalt des Getraenks.
	 */
	public Getraenk(String name, double preis, double alkoholgehalt) {
		super(EinAusgabe.ersterBuchstabeGross(name), preis);
		this.alkoholgehalt = alkoholgehalt;

		// Fuegt das neu erstellte Getraenk in den ersten freien Array-Slot ein.
		for (int i=0; i<WARENLIMIT; i++) {
			if (datenGetraenk[i][0] != null)
				continue;
			datenGetraenk[i][0] = this;
			
			// Maximal Menge direkt bestellen
			datenGetraenk[i][0].nachbestellen(LAGERKAPAZITAET, false);
			break;
		}
	}

	/**
	 * Minimaler Konstruktor, der aufgerufen wird, wenn neue Bestellungen einer bereits
	 * vorhandenen Ware getaetigt werden.
	 * @param anzahl Anzahl der in der Bestellung vorhandenen Ware.
	 */
	public Getraenk(int anzahl) {
		super(anzahl);
	}

	/** Getter */
	public static Getraenk[][] getDatenGetraenk() { return datenGetraenk; }
	public double getAlkoholgehalt() { return this.alkoholgehalt; }

	/**
	 * Gibt an, ob das Getraenk alkholhaltig ist oder nicht.
	 * @return Gibt einen Boolean zurueck, der den Alkoholgehalt bejaht / verneint.
	 */
	public boolean istAlkoholhaltig() {
		if (this.alkoholgehalt > 0)
			return true;
		return false;
	}
	
	/**
	 * Gibt eine aufzaehlende Zahl und alle Getraenke aus, sodass diese Methode als
	 * User-Auswahl benutzt werden kann.
	 * @param counter Gibt die Anzahl der Optionen - 1 an, die bereits ausgeben wurden.
	 * @return gibt zurueck wie viele Getraenke ausgegeben wurden - 1.
	 */
	public static int gebeGetraenkAus(int counter) {
		for (int i=0; i<WARENLIMIT; i++) {
			if (datenGetraenk[i][0] == null) {
				continue;
			}
			System.out.printf("(%d) %s%n", counter, datenGetraenk[i][0].getName());
			// Die Variable Counter hilft bei der Formatierung der Ausgabe.
			counter++;
		}
		return counter-1;
	}

	/**
	 * Gibt eine aufzaehlende Zahl und alle nicht-alkoholischen Getraenke aus,
	 * sodass diese Methode als User-Auswahl benutzt werden kann.
	 * @param counter Gibt die Anzahl der Optionen - 1 an, die bereits ausgeben wurden.
	 * @return gibt zurueck wie viele nicht-alkoholische Getraenke ausgegeben wurden - 1.
	 */
	public static int gebeNonAlkGetraenkAus(int counter) {
		for (int i=0; i<WARENLIMIT; i++) {
			if (datenGetraenk[i][0] == null) {
				continue;
			} else if (datenGetraenk[i][0].alkoholgehalt > 0) {
				System.out.printf("(%d) %s%n", counter, datenGetraenk[i][0].getName());
				// Die Variable Counter hilft bei der Formatierung der Ausgabe.
				counter++;
			}
		}
		return counter-1;
	}

	/**
	 * Implementiert das Nachbestellen des darauf angewandten Waren-Objekts.
	 * Ruft waehrend der Laufzeit die Methode bestellungHinzufuegen auf.
	 * @param menge Menge, die nachbestellt werden soll.
	 * @param laut Soll bestaetigt werden, dass nachbestellt worden ist?
	 * @return Gibt an, ob der Vorgang erfolgreich abgeschlossen wurde.
	 */
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
	 * im zwei-dimensionalen Getraenke-Array ein.
	 * @param menge Menge, die der jeweiligen Bestellung hinzugefuegt werden soll.
	 */
	public void bestellungHinzufuegen(int menge) {
		// Hilfsvariablen
		String gesuchtesGetraenk = this.getName();
		// Sucht die Zeile in dem das gesuchte Getraenk gespeichert ist.
		for (int i=0; i<WARENLIMIT; i++) {
			if (gesuchtesGetraenk == datenGetraenk[i][0].getName()) {
				// ersten freien Bestellungsslot finden und neues Bestellungsobjekt dort speichern.
				for (int j=1; j<=LIMITBESTELLUNGEN; j++) {
					if (datenGetraenk[i][j] == null) {
						datenGetraenk[i][j] = new Getraenk(menge);
						break;
					}
				}
				// Bestellung hinzugefuegt. Schleife verlassen.
				break;
			}
		}
	}

	/**
	 * Implementiert das Herausgeben, also den Verkauf des darauf angewandten Waren-Objekts.
	 * Loescht alle Bestellungen deren Anzahl durch diese Methode auf 0 gesetzt wird und 
	 * verschiebt alle Bestellungen nach links wenn welche geloescht wurden.
	 * @param menge Menge, die herausgegeben werden soll.
	 * @param slot Index der behandelten Ware in dem jeweiligen Daten-Array.
	 * @param statistik Entscheidet, ob dieser Vorgang in die Statistik einfliessen soll.
	 * @return Gibt an, ob der Vorgang erfolgreich abgeschlossen wurde.
	 */
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
				this.nachbestellen(LAGERKAPAZITAET - mengeVorHerausgabe, true);
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
			if (tempMenge < datenGetraenk[slot][i].getAnzahl()) {
				datenGetraenk[slot][i].setAnzahl(datenGetraenk[slot][i].getAnzahl() - tempMenge);
				break;
			// Menge ist groesser als aelteste Bestellung, dann Bestellung und zur naechsten gehen.
			} else if (tempMenge > datenGetraenk[slot][i].getAnzahl()) {
				tempMenge -= datenGetraenk[slot][i].getAnzahl();
				datenGetraenk[slot][i] = null;
				// alle Bestellungen werden nach "links" zum Anfang verschoben.
				verschiebungsfaktor++;
				continue;
			// Wenn gleich, dann Bestellung loeschen und den Verschiebungsfaktor um eins erhoehen.
			} else {
				datenGetraenk[slot][i] = null;
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
				datenGetraenk[slot][i] = datenGetraenk[slot][i + verschiebungsfaktor];
			}
		}
		return true;
	}

	/**
	 * Gibt einen String zurueck, der alle wichtigen Informationen zu der jeweiligen Ware beinhaltet.
	 * Kann anschliessend im Benutzermenue aufgerufen werden. Ueberschreibt die String.toString() Methode.
	 * @return Formatierter String mit Informationen zur Ware.
	 */
	@Override
	public String toString() {
		// Hilfsvariable
		String alkoholgehaltJaNein = (this.istAlkoholhaltig()) ? "" : " nicht";

		// Konstruiert den String mit allen relevanten Informationen und uebergibt ihn.
		return String.format(
				"%nEine Menge %s aus der Abteilung Getraenke kostet %.2f Euro.%n"
				+ "%1$s ist mit %.2f Promille%s alkoholhaltig. Es befinden sich %d Einheiten im Lager."
				, this.getName(), this.getPreis(), this.alkoholgehalt, alkoholgehaltJaNein, this.getAnzahl());
	}

}
