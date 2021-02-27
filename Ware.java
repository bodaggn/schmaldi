import java.time.LocalDate;

public abstract class Ware {

	private String name;
	private int anzahl;
	private double preis;
	private boolean imBestand;
	private LocalDate anlegedatum;

	// wie viele spezielle Waren pro Abteilung gleichzeitig angelegt sein koennen.
	public static final int WARENLIMIT = 30;
	// wie viele Bestellungen pro spezieller Ware gleichzeitig eingelagert sein koennen.
	public static final int LIMITBESTELLUNGEN = 20;
	// wie viel Lagerplatz pro spezieller Ware pro Abteilung existiert.
	public static final int LAGERKAPAZITAET = 100;

	private static long bestellnummer = 1; 

	public Ware(String name, double preis) {
		this.name = name;
		this.preis = preis;
		this.anzahl = 0;
		this.imBestand = true;
		this.anlegedatum = LocalDate.now();
	}

	/**
	 * Minimaler Konstruktor um die Bestellungen der jeweiligen Ware zu speichern,
	 * kann auf zusaetzliche Attribute verzichten, die bereits im Haupt-Objekt gespeichert sind.
	 */
	public Ware(int anzahl) {
		this.name = String.format("Bestellung%08d", bestellnummer);
		this.anzahl = anzahl;
		this.anlegedatum = LocalDate.now();
		bestellnummer++;
	}

	/**
	 * Minimaler Konstruktor um ein Objekt zu erzeugen, dass die Anzahl der geringfuegig
	 * haltbaren jeweiligen speziellen Waren anzeigt.
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

	/** Setter-Methoden */
	public void setAnzahl(int anzahl) { this.anzahl = anzahl; }


	public abstract boolean nachbestellen(int menge, boolean laut);

	public abstract boolean herausgeben(int menge, int slot); 
}
