/* Diese Klasse soll Methoden zum Erstellen/Nachbestellen/Ausgeben... aller möglichen
Warenarten beinhalten. Diese Methoden sollen den Nutzer des Programms nach den
Angaben fragen und dann mithilfe der zuvor beschriebenen Klassen die Funktionalität implementieren.

Erstellen Sie weitere Methoden, welche der Strukturierung des Benutzer-Menüs helfen.
*/

// TODO
// Verabschiedungsmethode schliesst den Scanner

public class Supermarkt {

	/* ---------------------------------------------- */
	/* ------ Usermenue und Funktionalitaeten ------ */
	/* -------------------------------------------- */

	// Anzahl der Auswahlmoeglichkeiten im Hauptmenu hier festgelegt,
	// um eine moegliche Erweiterung zu erleichtern.
	private static final int auswahlAnzahlHauptmenue = AuswahlHauptmenue.values().length;

	// TODO gilt das als sinnvoll? :D
	public enum AuswahlHauptmenue {
		BEENDEN,
		LEBENSMITTEL,
		GETRAENKE,
		NONFOOD,
		BACKWAREN,
		LAGER
	}

	public static void trennleiste(String inhalt) {
		System.out.printf("%n------- %s -------%n%n", inhalt);
	}

	public static void beenden() {
		// TODO
		// EinAusgabe.berechne(String vorgang) // mit Rekursion :D
		System.out.println("Programm beendet.");
		System.exit(0);
	}

	public static void einloggen() {
		trennleiste("Login");
		System.out.println("Willkommen!\nBitte logge dich ein.");
		//TODO masked password account, md5 file for fun? 
		hauptmenue();
	}

	public static void hauptmenue() {
		trennleiste("Hauptmenue");
		System.out.println("Bitte waehle die Abteilung aus.");
		// Gibt die Enum Elemente als korrekt formatierte Auswahlmoeglichkeiten aus.
		for (int i = 0; i < auswahlAnzahlHauptmenue; i++) {
			System.out.printf("(%d) %s%n", i
					, EinAusgabe.ersterBuchstabeGross(AuswahlHauptmenue.values()[i].name()));
		}

		// Das hier in aktionHauptmenue umbenennen
		// User-Input
		int input = EinAusgabe.auswahlTreffen(5);
		// Nachfolgendes Untermenue anhand der Auswahl aufrufen.
		switch(input) {
			case 0:
				beenden();
				break;
			case 1:
				untermenueStandard("Lebensmittel");
				break;
			case 2:
				untermenueStandard("Getraenke");
				break;
			case 3:
				untermenueStandard("NonFood");
				break;
			case 4:
				untermenueStandard("Backwaren");
				break;
			case 5:
				// TODO Aufruf
				System.out.println("Lager");
				break;
		}
	}

	public static void untermenueStandard(String abteilung) {
		// TODO input voerst initialisisiert, da kein else.
		int input = 0;
		trennleiste(String.format("Abteilung: %s", abteilung));
		System.out.println("(0) Zurueck");
		System.out.println("(1) Anlegen");
		//System.out.println("(2) Loeschen");
		// TODO Bei anzeigen mit gebeLebensmittelAus die Liste ausgeben und mit
		// der toString-Methode alle Informationen zu dem jeweiligen Produkt ausgeben.
		System.out.println("(2) Anzeigen");
		// TODO Laut Aufgabenstellung alle Waren nachbestellen. Einzeln soll auch moeglich sein.
		System.out.println("(3) Nachbestellen");
		System.out.println("(4) Herausgeben");

		// Anzahl der Auswahlmoeglichkeiten anhand der abteilung variieren. 
		if (abteilung == "Backwaren")
			input = EinAusgabe.auswahlTreffen(5);
		else 
			input = EinAusgabe.auswahlTreffen(4);

		// Naechstes Abteilungsmenu aufrufen und User-Input uebergeben.
		aktionUntermenue(input, abteilung);
	}

	public static void aktionUntermenue(int input, String abteilung) {
		// Zurueck ins Hauptmenue.
		if (input == 0)
			hauptmenue();

		//trennleiste(String.format("Abteilung: %s", abteilung));

		// naechsten Optionen darlegen und Userauswahl aufnehmen,
		// z.B. welche Waren will ich anlegen, bestellen, anzeigen, etc.
		int auswahl;
		if (abteilung == "Lebensmittel") {
			// TODO Aufgabenstellung beachten, und eine Fehlermeldung ausgeben wenn ueber Warenlimit!
			if (input == 1) {
				trennleiste(String.format("Anlegen: %s", abteilung));
				neuAnlegen(abteilung);
			} 
			else if (input == 2) {
				trennleiste(String.format("Informationen: %s", abteilung));
				auswahlWarenStandard(abteilung);
				System.out.println("(2) Alle Lebensmittel mit kurzem MHD");
				auswahl = EinAusgabe.auswahlTreffen(Lebensmittel.gebeLebensmittelAus(3)); 
				anzeigenWare(abteilung, auswahl);
			}
			else if (input == 3) {
				trennleiste(String.format("Nachbestellen: %s", abteilung));
				auswahlWarenStandard(abteilung);
				auswahl = EinAusgabe.auswahlTreffen(Lebensmittel.gebeLebensmittelAus(2)); 
				nachbestellenWare(abteilung, auswahl);
			}
			else if (input == 4) {
				trennleiste(String.format("Herausgeben: %s", abteilung));
				System.out.println("(0) Zurueck");
				auswahl = EinAusgabe.auswahlTreffen(Lebensmittel.gebeLebensmittelAus(1)); 
				herausgebenWare(abteilung, auswahl);
			}
		}
		else if (abteilung == "Getraenke") {
			// TODO Aufgabenstellung beachten, und eine Fehlermeldung ausgeben wenn ueber Warenlimit!
			if (input == 1) {
				trennleiste(String.format("Anlegen: %s", abteilung));
				neuAnlegen(abteilung);
			} 
			else if (input == 2) {
				trennleiste(String.format("Informationen: %s", abteilung));
				auswahlWarenStandard(abteilung);
				System.out.println("(2) Alle Getraenke ohne Alkohol");
				auswahl = EinAusgabe.auswahlTreffen(Getraenk.gebeGetraenkAus(3)); 
				anzeigenWare(abteilung, auswahl);
			}
			else if (input == 3) {
				trennleiste(String.format("Nachbestellen: %s", abteilung));
				auswahlWarenStandard(abteilung);
				auswahl = EinAusgabe.auswahlTreffen(Getraenk.gebeGetraenkAus(2)); 
				nachbestellenWare(abteilung, auswahl);
			}
			else if (input == 4) {
				trennleiste(String.format("Herausgeben: %s", abteilung));
				System.out.println("(0) Zurueck");
				auswahl = EinAusgabe.auswahlTreffen(Getraenk.gebeGetraenkAus(1)); 
				herausgebenWare(abteilung, auswahl);
			}
		}
		else if (abteilung == "NonFood") {
			// TODO Aufgabenstellung beachten, und eine Fehlermeldung ausgeben wenn ueber Warenlimit!
			if (input == 1) {
				trennleiste(String.format("Anlegen: %s", abteilung));
				neuAnlegen(abteilung);
			} 
			else if (input == 2) {
				trennleiste(String.format("Informationen: %s", abteilung));
				auswahlWarenStandard(abteilung);
				auswahl = EinAusgabe.auswahlTreffen(NonFoodArtikel.gebeNFArtikelAus(2)); 
				anzeigenWare(abteilung, auswahl);
			}
			else if (input == 3) {
				trennleiste(String.format("Nachbestellen: %s", abteilung));
				auswahlWarenStandard(abteilung);
				auswahl = EinAusgabe.auswahlTreffen(NonFoodArtikel.gebeNFArtikelAus(2)); 
				nachbestellenWare(abteilung, auswahl);
			}
			else if (input == 4) {
				trennleiste(String.format("Herausgeben: %s", abteilung));
				System.out.println("(0) Zurueck");
				auswahl = EinAusgabe.auswahlTreffen(NonFoodArtikel.gebeNFArtikelAus(1)); 
				herausgebenWare(abteilung, auswahl);
			}
		}

	}

	public static void auswahlWarenStandard(String abteilung) {
		System.out.println("(0) Zurueck");
		System.out.printf("(1) Alle %s%n", abteilung);
	}

	// Punkt 1
	public static void neuAnlegen(String abteilung) {
		// Standard-Infos geben.
		System.out.println("Hier kannst du neue Ware in das Sortiment aufnehmen:");
		System.out.println("\n(Bitte beachte: Die neue Ware wird automatisch bis auf Kapazitaet bestellt.)");

		// Standard-Userinput aufnehmen.
		String name = EinAusgabe.eingabeString("Wie heisst die Ware? ");
		double preis = EinAusgabe.eingabeDouble("Welchen Preis hat die Ware? Format = X.XX");

		// die jeweiligen Zusatzoptionen fuer die speziellen Waren abfragen und alles an die Konstruktoren uebergeben.
		if (abteilung == "Lebensmittel") {
			double gewicht = EinAusgabe.eingabeDouble("Wie schwer ist die Ware? Format = X.XX");
			int haltbarkeit;
			// Sicherstellen, dass Haltbarkeit groesser als 0 ist.
			// Eine eigene Exception hier waere "Overkill".
			while (true) {
				haltbarkeit = EinAusgabe.eingabeInt("Wie viele Tage ist die Ware haltbar?");
				if (haltbarkeit > 0)
					break;
				System.out.println("\nDie Haltbarkeit muss groesser als 0 sein!");
			}
			boolean kuehlung =  EinAusgabe.eingabeBoolean("Ist eine Kuehlung notwendig? Format = true / false");
			// neues Lebensmittel erstellen.
			new Lebensmittel(name, preis, gewicht, haltbarkeit, kuehlung);
		}

		else if (abteilung == "Getraenke") {
			double alkoholgehalt;
			while (true) {
				alkoholgehalt = EinAusgabe.eingabeDouble("Wie viel Alkoholgehalt hat die Ware? Format = X.XX");
				if (alkoholgehalt >= 0)
					break;
				System.out.println("\nDer Alkoholgehalt kann nicht negativ sein!");
			}
			// neues Getraenk erstellen.
			new Getraenk(name, preis, alkoholgehalt);
		}

		else if (abteilung == "NonFood") {
			String beschreibung = EinAusgabe.eingabeString("Wie lautet die Kurzbeschreibung der Ware?");
			int untergruppeOptionen = 0;
			while (untergruppeOptionen < 1 || untergruppeOptionen > 3) {
				untergruppeOptionen = EinAusgabe.eingabeInt(String.format(
							"Welcher Untergruppe gehoert der NonFood-Artikel an?%n"
							+ "(1) Medien%n(2) Kleidung%n(3) Drogerie"));
			}
			String untergruppe = "";
			switch(untergruppeOptionen) {
				case 1:
					untergruppe = "Medien";
					break;
				case 2:
					untergruppe = "Kleidung";
					break;
				case 3:
					untergruppe = "Drogerie";
					break;
			}
			// neuen NonFood-Artikel erstellen.
			new NonFoodArtikel(name, preis, beschreibung, untergruppe);
		}

		// TODO hier Ladezeit mit Rekursion simulieren.
		System.out.printf("%nNeues %s %s erfolgreich angelegt.%n", abteilung, name);
		// Zurueck zur Abteilung.
		EinAusgabe.mitEnterBestaetigen();
		untermenueStandard(abteilung);
	}

	// Punkt 2
	public static void anzeigenWare(String abteilung, int input) {
		if (input == 0) {
			untermenueStandard(abteilung);
		}

		// alle Waren ausgewaehlt.
		if (input == 1)
			System.out.println("\nFuer genauere Informationen bitte die jeweilige Ware auswaehlen.\n");

		if (abteilung == "Lebensmittel") {
			Lebensmittel[][] daten = Lebensmittel.getDatenLebensmittel();
			// Hilfsvariable um Array-Zugriffe zu minimieren.
			Lebensmittel ware;
			// kurze Information fuer alle vorhandenen Lebensmittel geben.
			if (input == 1) {
				for (int i=0; i<Ware.WARENLIMIT; i++) {
					if (daten[i][0] == null)
						continue;
					ware = daten[i][0];
					System.out.printf("Die Ware %s ist mit %d Einheiten eingelagert. Im Bestand seit %s.%n"
							, ware.getName(), ware.getAnzahl()
							, EinAusgabe.datumFormatiert(ware.getAnlegedatum()));
				}
			// Auskunft ueber Waren mit kurzem MHD.
			}
			else if (input == 2) {
				// Aufruf der Lebensmittel-Methode und das zurueckgegeben Array speichern.
				Lebensmittel[] arrayKurzesMHD = Lebensmittel.kurzesMHD();

				// Kurze Ausgabe mit Bezug, ob ueberhaupt Waren mit kurzem MHD vorhanden sind.
				if (arrayKurzesMHD.length > 0)
					System.out.println("\nFuer genauere Informationen bitte die jeweilige Ware auswaehlen.\n");
				else
					System.out.println("\nKeine Waren, die zeitnah ablaufen eingelagert.\n");

				// Alle Waren mit kurzem MHD mitsamt Anzahl ausgeben.
				for (int i=0; i<arrayKurzesMHD.length; i++) {
					if (arrayKurzesMHD[i] == null)
						break;
					System.out.printf("Von der Ware %s sind %d Einheiten mit MHD zwischen 0 und 2 Tagen eingelagert.%n"
							, arrayKurzesMHD[i].getName(), arrayKurzesMHD[i].getAnzahl());
				}
			// ausfuehrliche Informationen fuer einzeln ausgewaehlte Lebensmittel ausgeben.
			}
			else if (input > 2) {
				// jeweilige toString Methode wird zur Ausgabe hinzugezogen.
				System.out.println(daten[input-3][0]);
				System.out.println("\nDie folgenden Lieferungen sind aktuell eingelagert:");
				for (int i=1; i<Ware.LIMITBESTELLUNGEN; i++) {
					if (daten[input-3][i] == null)
						continue;
					ware = daten[input-3][i];
					// einzelne Bestellungen des jeweiligen Lebensmittels ausgeben.
					System.out.printf(
							"%s vom %s mit noch %d eingelagerten Einheiten. MHD = %s, noch %d Tage.%n", ware.getName()
							, EinAusgabe.datumFormatiert(ware.getAnlegedatum())
							, ware.getAnzahl(), ware.haltbarBis(), ware.istHaltbar());
				}
			}	
		}

		else if (abteilung == "Getraenke") {
			Getraenk[][] daten = Getraenk.getDatenGetraenk();
			// Hilfsvariable um Array-Zugriffe zu minimieren.
			Getraenk ware;
			// kurze Information fuer alle vorhandenen Getraenk geben.
			if (input == 1) {
				System.out.println("Alle Getraenke, sowohl mit als auch ohne Alkohol:\n");
				for (int i=0; i<Ware.WARENLIMIT; i++) {
					if (daten[i][0] == null)
						continue;
					ware = daten[i][0];
					System.out.printf("Die Ware %s ist mit %d Einheiten eingelagert. Im Bestand seit %s.%n"
							, ware.getName(), ware.getAnzahl()
							, EinAusgabe.datumFormatiert(ware.getAnlegedatum()));
				}
			}
			else if (input == 2) {
				System.out.println("\nAlle Getraenke ohne Alkohol:\n");
				// kurze Information fuer alle vorhandenen Getraenk geben.
				for (int i=0; i<Ware.WARENLIMIT; i++) {
					if (daten[i][0] == null)
						continue;
					if (daten[i][0].istAlkoholhaltig())
						continue;
					ware = daten[i][0];
					System.out.printf("Die Ware %s ist mit %d Einheiten eingelagert. Im Bestand seit %s.%n"
							, ware.getName(), ware.getAnzahl()
							, EinAusgabe.datumFormatiert(ware.getAnlegedatum()));
				}

			// ausfuehrliche Informationen fuer einzeln ausgewaehlte Getraenk ausgeben.
			}
			else if (input > 2) {
				// jeweilige toString Methode wird zur Ausgabe hinzugezogen.
				System.out.println(daten[input-3][0]);
				System.out.println("\nDie folgenden Lieferungen sind aktuell eingelagert:");
				for (int i=1; i<Ware.LIMITBESTELLUNGEN; i++) {
					if (daten[input-3][i] == null)
						continue;
					ware = daten[input-3][i];
					// einzelne Bestellungen des jeweiligen Getraenk ausgeben.
					System.out.printf(
							"%s vom %s mit noch %d eingelagerten Einheiten.%n"
							, ware.getName(), EinAusgabe.datumFormatiert(ware.getAnlegedatum()), ware.getAnzahl());
				}
			}	
		}

		else if (abteilung == "NonFood") {
			NonFoodArtikel[][] daten = NonFoodArtikel.getDatenNonFoodArtikel();
			// Hilfsvariable um Array-Zugriffe zu minimieren.
			NonFoodArtikel ware;
			// kurze Information fuer alle vorhandenen NonFoodArtikel geben.
			if (input == 1) {
				for (int i=0; i<Ware.WARENLIMIT; i++) {
					if (daten[i][0] == null)
						continue;
					ware = daten[i][0];
					System.out.printf("Die Ware %s ist mit %d Einheiten eingelagert. Im Bestand seit %s.%n"
							, ware.getName(), ware.getAnzahl()
							, EinAusgabe.datumFormatiert(ware.getAnlegedatum()));
				}
			}
			// ausfuehrliche Informationen fuer einzeln ausgewaehlte NonFoodArtikel ausgeben.
			else if (input > 1) {
				// jeweilige toString Methode wird zur Ausgabe hinzugezogen.
				System.out.println(daten[input-2][0]);
				System.out.println("\nDie folgenden Lieferungen sind aktuell eingelagert:");
				for (int i=1; i<Ware.LIMITBESTELLUNGEN; i++) {
					if (daten[input-2][i] == null)
						continue;
					ware = daten[input-2][i];
					// einzelne Bestellungen des jeweiligen NonFoodArtikel ausgeben.
					System.out.printf(
							"%s vom %s mit noch %d eingelagerten Einheiten.%n", ware.getName()
							, EinAusgabe.datumFormatiert(ware.getAnlegedatum()), ware.getAnzahl());
				}
			}	
		}

		// TODO Kurz verzoegern, damit die Ausgabe gelesen werden kann, dann zurueck zum letzten Schritt
		EinAusgabe.mitEnterBestaetigen();
		// hier also wieder Wartezeit 
		aktionUntermenue(2, abteilung);
	}


	//Punkt 3
	public static void nachbestellenWare(String abteilung, int input) {

		if (input == 0) {
			untermenueStandard(abteilung);
		}

		int menge;
		while (true) {
			menge = EinAusgabe.eingabeInt("Wie viel moechtest du bestellen?");
			if (menge <= 0)
				System.out.println("\nDie Bestellmenge muss groesser als 0 sein!");
			else if (menge > Ware.LAGERKAPAZITAET)
				System.out.printf("%nDie Bestellung darf die Lagerkapazitaet von %d nicht ueberschreiten!%n"
						, Ware.LAGERKAPAZITAET);
			else 
				break;
		}
		// Leerzeile
		System.out.println();

		if (abteilung == "Lebensmittel") {
			Lebensmittel[][] daten = Lebensmittel.getDatenLebensmittel();
			// Alle Lebensmittel sollen nachbestellt werden. Also durch das Array iterieren und ueberall
			// Bestellungen anstossen. Alle leeren Slots werden per Exception ignoriert.
			if (input == 1) {
				for (int i=0; i<Lebensmittel.WARENLIMIT; i++) {
					try {
						daten[i][0].nachbestellen(menge);
					} catch (NullPointerException e) {
						continue;
					} 
				}
			} else {
				// Funktion aufrufen und return-Wert als Bestellstatus interpretieren.
				if (daten[input-2][0].nachbestellen(menge))
					System.out.println("\nBestellung erfolgreich im System gespeichert.");
				else
					System.out.println("\nBestellung abgebrochen.");
			}
		}

		else if (abteilung == "Getraenke") {
			Getraenk[][] daten = Getraenk.getDatenGetraenk();
			// Alle Getraenke sollen nachbestellt werden. Also durch das Array iterieren und ueberall
			// Bestellungen anstossen. Alle leeren Slots werden per Exception ignoriert.
			if (input == 1) {
				for (int i=0; i<Getraenk.WARENLIMIT; i++) {
					try {
						daten[i][0].nachbestellen(menge);
					} catch (NullPointerException e) {
						continue;
					} 
				}
			} else {
				// Funktion aufrufen und return-Wert als Bestellstatus interpretieren.
				if (daten[input-2][0].nachbestellen(menge))
					System.out.println("\nBestellung erfolgreich im System gespeichert.");
				else
					System.out.println("\nBestellung abgebrochen.");
			}
		}

		if (abteilung == "NonFood") {
			NonFoodArtikel[][] daten = NonFoodArtikel.getDatenNonFoodArtikel();
			// Alle NonFoodArtikel sollen nachbestellt werden. Also durch das Array iterieren und ueberall
			// Bestellungen anstossen. Alle leeren Slots werden per Exception ignoriert.
			if (input == 1) {
				for (int i=0; i<NonFoodArtikel.WARENLIMIT; i++) {
					try {
						daten[i][0].nachbestellen(menge);
					} catch (NullPointerException e) {
						continue;
					} 
				}
			} else {
				// Funktion aufrufen und return-Wert als Bestellstatus interpretieren.
				if (daten[input-2][0].nachbestellen(menge))
					System.out.println("\nBestellung erfolgreich im System gespeichert.");
				else
					System.out.println("\nBestellung abgebrochen.");
			}
		}

		// Zurueck zum letzten Schritt nach Abarbeitung.
		EinAusgabe.mitEnterBestaetigen();
		aktionUntermenue(3, abteilung);
	}

	//Punkt 4
	public static void herausgebenWare(String abteilung, int input) {
		if (input == 0) {
			untermenueStandard(abteilung);
		}

		int menge = EinAusgabe.eingabeInt("Wie viele Einheiten sollen verkauft werden?");
		// Leerzeile
		System.out.println();

		// TODO hier pruefen, dass keine Werte ueber der Anzahl der Auswahlmoeglichkeiten angegeben werden koenen.
		if (abteilung == "Lebensmittel") {
			Lebensmittel[][] daten = Lebensmittel.getDatenLebensmittel(); 
			// Herausgeben-Funktion aus der Klasse Lebensmittel auf das ausgewaehlte Lebensmittel anwenden.
			if (daten[input-1][0].herausgeben(menge, input-1))
					System.out.println("Verkauf erfolgreich im System gespeichert.");
				else
					System.out.println("Verkauf voerst abgebrochen. Bitte erneut versuchen.");
		}
		else if (abteilung == "Getraenke") {
			Getraenk[][] daten = Getraenk.getDatenGetraenk(); 
			// Herausgeben-Funktion aus der Klasse Getraenk auf das ausgewaehlte Getraenk anwenden.
			if (daten[input-1][0].herausgeben(menge, input-1))
					System.out.println("Verkauf erfolgreich im System gespeichert.");
				else
					System.out.println("Verkauf voerst abgebrochen. Bitte erneut versuchen.");
		}
		else if (abteilung == "NonFood") {
			NonFoodArtikel[][] daten = NonFoodArtikel.getDatenNonFoodArtikel(); 
			// Herausgeben-Funktion aus der Klasse NonFoodArtikel auf das ausgewaehlte NonFood anwenden.
			if (daten[input-1][0].herausgeben(menge, input-1))
					System.out.println("Verkauf erfolgreich im System gespeichert.");
				else
					System.out.println("Verkauf voerst abgebrochen. Bitte erneut versuchen.");
		}
		// Zurueck zum letzten Schritt nach Abarbeitung.
		EinAusgabe.mitEnterBestaetigen();
		aktionUntermenue(4, abteilung);
	}




	/* ---------------------------- */
	/* ------ Main-Funktion ------ */
	/* -------------------------- */

	public static void main(String[] args) {

		/*
		Lebensmittel a = new Lebensmittel("Banane", 1.20, 0.5, -1, false);
		Lebensmittel[][] temp = Lebensmittel.getDatenLebensmittel();
		temp[0][0].herausgeben(40,0);
		temp[0][0].nachbestellen(30);
		*/
		//Getraenk b = new Getraenk("Wasser", 1.43123, 0);
		//Getraenk c = new Getraenk("Alster", 1.43123, 3);

		//NonFoodArtikel d = new NonFoodArtikel("Shirt", 1.23, "Dingens zum Anziehen", "Kleidung");

		//Lebensmittel b = new Lebensmittel("Canane", 1.20, 1.5, 7, false);
		//System.out.println(b.nachbestellen(53));
		einloggen();
		//Lebensmittel.ausgebenAlles();
	}
}
