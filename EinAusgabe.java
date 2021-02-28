import java.util.Scanner;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.InputMismatchException;

/**
 * Eine Ansammlung von statischen Methoden rund um Ein- und Ausgabe und Formattierung.
 * @author Louis, Tobi
 * @version 1.0
 */
public class EinAusgabe {

	// Legt den Scanner an.
	private static Scanner sc = new Scanner(System.in).useDelimiter(System.lineSeparator());

	/** Public-Methode zum Schliessen des Scanners. */
	public static void scannerSchliessen() {
		sc.close();
	}

	/**
	 * Eigene Exception, die gerufen wird, wenn der User eine zu hohe oder zu niedrige Auswahl triff.
	 * Bspw: 0 - 4 stehen zur Auswahl, und der User waehlt die 5 oder -1.
	 * @param nachricht Ausgabe, die bei Aufruf der Exception ausgegeben werden soll.
	 */
	@SuppressWarnings("serial")
	static class AuswahlVerfehlt extends Exception {
		public AuswahlVerfehlt(String nachricht) {
			super(nachricht);
		}
	}

	/**
	 * Ruft die eingabeInt-Methode auf und ueberprueft mit Hilfe der eigenen Exception,
	 * dass sich der User an die zur Verfuegung stehenden Auswahlmoeglichkeiten gehalten hat.
	 * @param groessteAuswahl Stellt die hoechst-moeglichste Auswahl dar.
	 * @return gibt die User-Auswahl an die aufrufende Methode zurueck.
	 */
	public static int auswahlTreffen(int groessteAuswahl) {
		int auswahl;
		while (true) {
			try {
				auswahl = eingabeInt(String.format(
							"Bitte treffe deine Auswahl zwischen 0 und %d.", groessteAuswahl));
				if (auswahl < 0 || auswahl > groessteAuswahl)
					throw new AuswahlVerfehlt("\nDu hast eine ungueltige Zahl gewaehlt.");
				else 
					break;
			} catch (AuswahlVerfehlt e) {
				System.out.println(e.getMessage());
				continue;
			}
		}
		return auswahl;
	}

	/**
	 * Gibt eine per Parameter uebergebene Aufforderung aus und nimmt einen Integer auf,
	 * der an die aufrufende Methode zurueck gegeben wird. 
	 * Errorhandling in Form von {@link InputMismatchException} integriert.
	 * @param aufforderung String der Aufforderung an den User.
	 * @return gibt die User-Auswahl zurueck.
	 */
	public static int eingabeInt(String aufforderung) {
		int auswahl;
		while (true) {
			System.out.printf("%n%s%n> ", aufforderung);
			try {
				auswahl = sc.nextInt();
				return auswahl;
			} catch (InputMismatchException e) {
				System.out.println("\nEs werden nur ganze Zahlen akzeptiert.");
				// Buffer loeschen
				sc.next();
				continue;
			}
		}
	}

	/**
	 * Gibt eine per Parameter uebergebene Aufforderung aus und nimmt einen String auf,
	 * der an die aufrufende Methode zurueck gegeben wird. 
	 * Errorhandling in Form von {@link InputMismatchException} integriert.
	 * @param aufforderung String der Aufforderung an den User.
	 * @return gibt die User-Auswahl zurueck.
	 */
	public static String eingabeString(String aufforderung) {
		String auswahl;
		while (true) {
			System.out.printf("%n%s%n> ", aufforderung);
			try {
				auswahl = sc.next();
				return auswahl;
			} catch (InputMismatchException e) {
				System.out.println("\nEs werden nur Buchstaben akzeptiert.");
				// Buffer loeschen
				sc.next();
				continue;
			}
		}
	}

	/**
	 * Gibt eine per Parameter uebergebene Aufforderung aus und nimmt einen Double auf,
	 * der an die aufrufende Methode zurueck gegeben wird. 
	 * Errorhandling in Form von {@link InputMismatchException} integriert.
	 * @param aufforderung String der Aufforderung an den User.
	 * @return gibt die User-Auswahl zurueck.
	 */
	public static double eingabeDouble(String aufforderung) {
		double auswahl;
		while (true) {
			System.out.printf("%n%s%n> ", aufforderung);
			try {
				auswahl = sc.nextDouble();
				return auswahl;
			} catch (InputMismatchException e) {
				System.out.println("\nEs werden nur Zahlen akzeptiert.");
				// Buffer loeschen
				sc.next();
				continue;
			}
		}
	}

	/**
	 * Gibt eine per Parameter uebergebene Aufforderung aus und nimmt einen Boolean auf,
	 * der an die aufrufende Methode zurueck gegeben wird. 
	 * Errorhandling in Form von {@link InputMismatchException} integriert.
	 * @param aufforderung String der Aufforderung an den User.
	 * @return gibt die User-Auswahl zurueck.
	 */
	public static boolean eingabeBoolean(String aufforderung) {
		boolean auswahl;
		while (true) {
			System.out.printf("%n%s%n> ", aufforderung);
			try {
				auswahl = sc.nextBoolean();
				return auswahl;
			} catch (InputMismatchException e) {
				System.out.println("\nEs werden nur \"true\" oder \"false\" akzeptiert.");
				// Buffer loeschen
				sc.next();
				continue;
			}
		}
	}

	/**
	 * Bittet den User die Enter-Taste zu druecken um im Programm fortzufahren. 
	 * Soll dann eingesetzt werden, wenn dem User Informationen bereitsgestellt werden,
	 * sodass diese nicht untergehen.
	 */
	public static void mitEnterBestaetigen() {
		System.out.print("\nDruecke \"Enter\" zum bestaetigen.");
		// gleich zwei mal, weil es so schoen ist. Nein, der Buffer muss erst geleert werden.
		sc.nextLine();
		sc.nextLine();
	}

	/**
	 * Formatiert den uebergebenen String so, dass der erste Buchstabe gross, der Rest klein ist.
	 * @param input uebergebener String
	 * @return fertig formatierter String
	 */
	public static String ersterBuchstabeGross(String input) {
		return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
	}

	/**
	 * Formatiert das uebergebene LocalDate im Locale des Nutzers und gibt einen String zurueck.
	 * @param datum Datum, dass formatiert werden soll.
	 * @return nach dem User-Locale formatierter String.
	 */
    public static String datumFormatiert(LocalDate datum) {
		DateTimeFormatter f = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.GERMAN);
		return f.format(datum);
    }

	/**
	 * Bereinigt das Terminal, sodass das Programm nicht alle Ausgaben untereinander taetigt.
	 * Soll nach jedem Menuepunkt und deren Ausgabe aufgerufen werden.
	 */
	public static void ausgabeZuruecksetzen(){
		try {
			// Implementation fuer Windows
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			// Implementation fuer Unix
			else
				System.out.print("\033\143");
		} catch (IOException | InterruptedException e) {
			// diese gesammte Funktionalitaet ueberspringen, sollte ein Fehler auftreten.
		}
	}

	/**
	 * Taeuscht dem User vor, dass der Rechner schwer beschaeftigt sei. ;)
	 * @param text Text, der die Taetigkeiten des Systems waehrend des Wartevorgangs beschreibt.
	 */
	public static void rechenzeitSimulieren(String text) {
		System.out.printf("%n%s ", text);

		// rekursive Methode aufrufen und IE auffangen.
		try {
			zeichnePunkte(3);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		// Leerzeile
		System.out.println();
	}

	/** 
	 * Rekursive Funktion, die in gewissen Zeitabstaenden Punkte auf die Konsole zeichnet,
	 * um so die Illusion eines fortgeschrittenen Management-Systems und nicht den Code
	 * eines Erstsemesters zu simulieren.
	 * @param n Anzahl der auszugebenden Punkte.
	 * @throws InterruptedException da Sleep implementiert.
	 */
	public static void zeichnePunkte(int n) throws InterruptedException {
		if (n > 0) {
			System.out.print(".");
			Thread.sleep(850);
			zeichnePunkte(n - 1);
		} 
	}


}
