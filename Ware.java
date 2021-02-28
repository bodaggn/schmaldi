import java.time.LocalDate;

/** Abstrakte Super-Klasse, die Attribute und Methoden bereitsstellt,
 * die sich die erbenden Unterklassen teilen.
 * @author Louis, Tobi
 * @version 1.0
 */
public abstract class Ware {

	/** Private Objektattribute */
	private String name;
	private int anzahl;
	private double preis;
	private boolean imBestand;
	private LocalDate anlegedatum;
	private int verkaufteMengen;

	// wie viele spezielle Waren pro Abteilung gleichzeitig angelegt sein koennen.
	public static final int WARENLIMIT = 30;
	// wie viele Bestellungen pro spezieller Ware gleichzeitig eingelagert sein koennen.
	public static final int LIMITBESTELLUNGEN = 20;
	// wie viel Lagerplatz pro spezieller Ware pro Abteilung existiert.
	public static final int LAGERKAPAZITAET = 100;
	// wird benutzt um die bestellnummer zu iterieren.
	private static long bestellnummer = 1; 

	/**
	 * Konstruktor fuer die Hauptobjekte der jeweiligen Waren.
	 * @param name Name der Ware.
	 * @param preis Preis der Ware.
	 */
	public Ware(String name, double preis) {
		this.name = name;
		this.preis = preis;
		this.anzahl = 0;
		this.imBestand = true;
		this.anlegedatum = LocalDate.now();
		this.verkaufteMengen = 0;
	}

	/**
	 * Minimaler Konstruktor, der aufgerufen wird, wenn neue Bestellungen einer bereits
	 * vorhandenen Ware getaetigt werden.
	 * @param anzahl Anzahl der in der Bestellung vorhandenen Ware.
	 */
	public Ware(int anzahl) {
		this.name = String.format("Bestellung%08d", bestellnummer);
		this.anzahl = anzahl;
		this.anlegedatum = LocalDate.now();
		bestellnummer++;
	}

	/**
	 * Minimaler Konstruktor um ein Objekt zu erzeugen, dass die Anzahl der geringfuegig
	 * haltbaren jeweiligen speziellen Waren beinhaltet.
	 * @param name Name der Ware.
	 * @param anzahl Anzahl der Ware.
	 */
	public Ware(String name, int anzahl) {
		this.name = name;
		this.anzahl = anzahl;
	}

	/** Getter-Methoden */
	public String getName() { return this.name; }
	public int getAnzahl() { return this.anzahl; }
	public double getPreis() { return this.preis; }
	public boolean getImBestand() { return this.imBestand; }
	public LocalDate getAnlegedatum() { return this.anlegedatum; }
	public int getVerkaufteMengen() { return this.verkaufteMengen; }

	/** Setter-Methoden */
	public void setAnzahl(int anzahl) { this.anzahl = anzahl; }

	/** Objektmethode, die ermoeglicht, dass die bereits verkaufte Menge
	 * fuer jedes Hauptobjekt der jeweiligen speziellen Ware fuer die
	 * Statistik gesammelt werden kann.
	 * @param menge Die Menge, die soeben herausgegeben wurde.
	 */
	public void erhoeheVerkaufteMenge(int menge) {
		this.verkaufteMengen += menge;
	}

	/**
	 * Abstrakte Methode, die innerhalb der erbenden Klassen das Nachbestellen von Waren erlaubt.
	 * @param menge Menge, die nachbestellt werden soll.
	 * @param laut Soll bestaetigt werden, dass nachbestellt worden ist?
	 * @return Gibt an, ob der Vorgang erfolgreich abgeschlossen wurde.
	 */
	public abstract boolean nachbestellen(int menge, boolean laut);

	/** Abstrakte Methode, die innerhalb der erbenden Klassen das Herausgeben von Waren erlaubt.
	 * @param menge Menge, die herausgegeben werden soll.
	 * @param slot Index der behandelten Ware in dem jeweiligen Daten-Array.
	 * @param statistik Entscheidet, ob dieser Herausgabe-Vorgang mit in die Statistik einfliessen soll.
	 * @return Gibt an, ob der Vorgang erfolgreich abgeschlossen wurde.
	 */
	public abstract boolean herausgeben(int menge, int slot, boolean statistik); 

}
