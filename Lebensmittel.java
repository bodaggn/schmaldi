import java.time.LocalDate;
import java.time.Period;

/** 
 * Implementiert alle Funktionalitaeten der Abteilung Lebensmittel.
 * Unterklasse von Ware.
 * @author Louis, Tobi
 * @version 1.0
 */
public class Lebensmittel extends Ware {

	/* Objektattribute, die weitere Funktionalitaeten in Verbindung mit den hier
	 * implementierten Methoden bereitstellen.
	 * Werden an die Klasse Backwaren direkt vererbt, sodass ebenfalls vererbte Methoden
	 * auf diese Zugriff haben. */
	protected double gewicht;
	protected int haltbarkeit;
	protected boolean bedarfKuehlung;

	// Insgesamt X verschiedene Arten und X gleichzeitig gelagerte Bestellungen pro Ware moeglich.
	private static Lebensmittel datenLebensmittel[][] = new Lebensmittel[WARENLIMIT][LIMITBESTELLUNGEN];

	// Schaltet den Hinzufuege-Vorgang zum Array fuer Lebensmittel aus,
	// sollte der Konstruktor ueber eine erbende Klasse aufgerufen werden.
	private static boolean trigger = true;

	/**
	 * Setter fuer den Trigger, der erbenden Klassen zur Verfuegung steht,
	 * sodass nicht versehentlich mehre Objekte gleichzeitig in ihre jeweiligen Daten-Arrays
	 * eingelagert werden. Wenn eine neue Backware erstellt wird, wird dies auf "false" gesetzt.
	 * @param wert bestimmt ob ein Lebensmittel-Objekt eingelagert werden soll oder nicht.
	 */
	protected static void setTrigger(boolean wert) { trigger = wert; }

	/**
	 * Konstruktor
	 * @param name Name des Lebensmittels.
	 * @param preis Preis des Lebensmittels.
	 * @param gewicht Gewicht des Lebensmittel.
	 * @param haltbarkeit Haltbarkeit des Lebensmittel in Tagen.
	 * @param bedarfKuehlung Boolean, ob das Lebensmittel gekuehlt werden muss.
	 */
	public Lebensmittel(String name, double preis, double gewicht, int haltbarkeit, boolean bedarfKuehlung) {
		super(EinAusgabe.ersterBuchstabeGross(name), preis);
		this.gewicht = gewicht;
		this.haltbarkeit = haltbarkeit;
		this.bedarfKuehlung = bedarfKuehlung;

		if (trigger) {
			// Fuegt das neu erstellte Lebensmittel in den ersten freien Array-Slot ein.
			for (int i=0; i<WARENLIMIT; i++) {
				if (datenLebensmittel[i][0] != null)
					continue;
				datenLebensmittel[i][0] = this;
				
				// Maximal Menge direkt bestellen
				datenLebensmittel[i][0].nachbestellen(LAGERKAPAZITAET, false);
				break;
			}
		}
		// Trigger zuruecksetzen.
		trigger = true;

	}

	/**
	 * Minimaler Konstruktor, der aufgerufen wird, wenn neue Bestellungen einer bereits
	 * vorhandenen Ware getaetigt werden.
	 * @param anzahl Anzahl der in der Bestellung vorhandenen Ware.
	 * @param haltbarkeit Haltbarkeit der Ware in Tagen.
	 */
	public Lebensmittel(int anzahl, int haltbarkeit) {
		super(anzahl);
		this.haltbarkeit = haltbarkeit;
	}

	/**
	 * Minimaler Konstruktor um ein Objekt zu erzeugen, dass die Anzahl der geringfuegig
	 * haltbaren jeweiligen speziellen Lebensmittel beinhaltet.
	 * @param name Name der Ware.
	 * @param anzahl Anzahl der Ware.
	 */
	public Lebensmittel(String name, int anzahl) {
		super(name, anzahl);
	}

	/** Getter fuer das Array mit allen Lebensmittel-Daten. */
	public static Lebensmittel[][] getDatenLebensmittel() {
		return datenLebensmittel;
	}

	/**
	 * Berechnet das Mindesthalbarkeitsdatum und gibt es als formatierten String zurueck.
	 * @return MHD als String.
	 */
	public String haltbarBis() {
		LocalDate mhd = this.getAnlegedatum().plusDays(this.haltbarkeit);
		return EinAusgabe.datumFormatiert(mhd);
	}

	/**
	 * Gibt die Anzahl der Tage zwischen jetzt und MHD als Integer zurueck.
	 * @return Differenz der Tage als Integer. -1, falls ein ueberschritten.
	 */
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

	/**
	 * Generiert eine neues Array, dass alle Lebensmittel beinhaltet, die ein MHD
	 * von 0 - 2 Tagen haben und loescht alle Bestellungen, die ein negatives MHD haben.
	 * @return Das erzeugte und befuellte Lebensmittel-Array.
	 */
	public static Lebensmittel[] kurzesMHD() {
		// Lebensmittel Array, der zurueck gegeben wird.
		// Groesse anhand von aktuellen Programmeinstellungen berechnet.
		Lebensmittel[] rueckgabeArray = new Lebensmittel[WARENLIMIT*LIMITBESTELLUNGEN/3];
		// Anzahl von einzelnen Lebensmittel mit MHD 0-2 Tage.
		int anzahl = 0;
		// Hilfsvariable fuer weniger Array-Zugriffe.
		Lebensmittel ware;
		// Hilfsvariable um Array-Indexe zu verwalten.
		int index = -1;
		// Zu loeschende abgelaufene Menge
		int nochZuLoeschen = 0;
		// durch einzelne Waren iterieren.
		for (int i=0; i<WARENLIMIT; i++) {
			// Slot skippen, falls leer.
			if (datenLebensmittel[i][0] == null)
				continue;
			// durch einzelne Bestellungen der jeweiligen Ware iterieren.
			for (int j=1; j<=LIMITBESTELLUNGEN; j++) {
				ware = datenLebensmittel[i][j]; 
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
							, ware.getName(), datenLebensmittel[i][0].getName());
					nochZuLoeschen += ware.getAnzahl();
				}
			}
			
			// Alle abgelaufenen Bestellungen der Ware herausgeben / loeschen.
			if (nochZuLoeschen > 0) {
				System.out.println();
				datenLebensmittel[i][0].herausgeben(nochZuLoeschen, i, false);
			}

			if (index >= 0) {
				// Ein Lebensmittel erfolgreich durchlaufen, Daten dem Array hinzufuegen.
				rueckgabeArray[index] = new Lebensmittel(datenLebensmittel[i][0].getName(), anzahl);
			}

			// Resetten der Hilfsvariablen, da jetzt ein neues Lebensmittel durchlaufen wird.
			anzahl = 0;
			nochZuLoeschen = 0;
		}
		return rueckgabeArray;
	}

	/**
	 * Gibt eine aufzaehlende Zahl und alle Lebensmittel aus, sodass diese Methode als
	 * User-Auswahl benutzt werden kann.
	 * @param counter Gibt die Anzahl der Optionen - 1 an, die bereits ausgeben wurden.
	 * @return gibt zurueck wie viele Lebensmittel ausgegeben wurden - 1.
	 */
	public static int gebeLebensmittelAus(int counter) {
		for (int i=0; i<WARENLIMIT; i++) {
			if (datenLebensmittel[i][0] == null) {
				continue;
			}
			System.out.printf("(%d) %s%n", counter, datenLebensmittel[i][0].getName());
			// Die Variable Counter hilft bei der Formatierung der Ausgabe.
			counter++;
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
			// wird von der Klasse Lager auf "leise" gesetzt,
			// sodass man beim Start des Programms nicht vollgespammt wird.
			if (laut)
				System.out.printf("Es wurden %d Einheiten der Ware %s bestellt.%n", menge, this.getName());
			return true;
		}
	}


	/**
	 * Erstellt ein neues Bestell-Objekt und reiht es an der richtigen Stelle
	 * im zwei-dimensionalen Lebensmittel-Array ein.
	 * @param menge Menge, die der jeweiligen Bestellung hinzugefuegt werden soll.
	 */
	public void bestellungHinzufuegen(int menge) {
		// Hilfsvariablen
		String gesuchtesLebensmittel = this.getName();
		int tageHaltbar;
		// Sucht die Zeile in dem das gesuchte Lebensmittel gespeichert ist.
		for (int i=0; i<WARENLIMIT; i++) {
			if (gesuchtesLebensmittel == datenLebensmittel[i][0].getName()) {
				tageHaltbar = datenLebensmittel[i][0].haltbarkeit;
				// ersten freien Bestellungsslot finden und neues Bestellungsobjekt dort speichern.
				for (int j=1; j<=LIMITBESTELLUNGEN; j++) {
					if (datenLebensmittel[i][j] == null) {
						datenLebensmittel[i][j] = new Lebensmittel(menge, tageHaltbar);
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
	 * Wir leben in einer Supermarkt-Utopie, in der immer die aeltesten Waren herausgegeben werden.
	 * @param menge Menge, die herausgegeben werden soll.
	 * @param slot Index der behandelten Ware in dem jeweiligen Daten-Array.
	 * @param statistik Entscheidet, ob dieser Herausgabe-Vorgang mit in die Statistik einfliessen soll.
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
			if (tempMenge < datenLebensmittel[slot][i].getAnzahl()) {
				datenLebensmittel[slot][i].setAnzahl(datenLebensmittel[slot][i].getAnzahl() - tempMenge);
				break;
			// Menge ist groesser als aelteste Bestellung, dann Bestellung und zur naechsten gehen.
			} else if (tempMenge > datenLebensmittel[slot][i].getAnzahl()) {
				tempMenge -= datenLebensmittel[slot][i].getAnzahl();
				datenLebensmittel[slot][i] = null;
				// alle Bestellungen werden nach "links" zum Anfang verschoben.
				verschiebungsfaktor++;
				continue;
			// Wenn gleich, dann Bestellung loeschen und den Verschiebungsfaktor um eins erhoehen.
			} else {
				datenLebensmittel[slot][i] = null;
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
				datenLebensmittel[slot][i] = datenLebensmittel[slot][i + verschiebungsfaktor];
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
		// Benoetigt um nach Bedarf ein "nicht" bei "Bedarf Kuehlung" in den String einzufuegen.
		String kuehlungJaNein = (this.bedarfKuehlung) ? "" : " nicht";

		// Erneute Hilfsvariable fuer eine grammatikalisch korrekte Ausgabe.
		String tagOderTage = (this.haltbarkeit != 1) ? "Tage" : "Tag";

		// Konstruiert den String mit allen relevanten Informationen und uebergibt ihn.
		return String.format(
				"%nEine Menge %s aus der Abteilung Lebensmittel kostet %.2f Euro und hat ein Gewicht von %.2fg.%n"
				+ "Die Haltbarkeit betraegt %d %s. Eine Kuehlung wird%s benoetigt.%n"
				+ "Es befinden sich %d Einheiten im Lager."
				, this.getName(), this.getPreis(), this.gewicht, this.haltbarkeit, tagOderTage, kuehlungJaNein, this.getAnzahl());
	}

}
