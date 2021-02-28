import java.util.Scanner;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.InputMismatchException;

/**
 * Eine Ansammlung von statischen Methoden rund um
 * Ein- und Ausgabe und Formattierung.
 */
public class EinAusgabe {

	private static Scanner sc = new Scanner(System.in).useDelimiter(System.lineSeparator());

	public static void scannerSchliessen() {
		sc.close();
	}

	@SuppressWarnings("serial")
	static class AuswahlVerfehlt extends Exception {
		public AuswahlVerfehlt(String nachricht) {
			super(nachricht);
		}
	}

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

	public static String eingabeString(String aufforderung) throws InputMismatchException {
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

	// TODO hier nochmal richtig formatierte Double zurueck geben.
	public static double eingabeDouble(String aufforderung) throws InputMismatchException {
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

	public static boolean eingabeBoolean(String aufforderung) throws InputMismatchException {
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

	public static void mitEnterBestaetigen() {
		System.out.print("\nDruecke \"Enter\" zum bestaetigen.");
		// gleich zwei mal, weil es so schoen ist. Nein, der Buffer muss erst geleert werden.
		sc.nextLine();
		sc.nextLine();
	}

	public static String ersterBuchstabeGross(String input) {
		return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
	}

    public static String datumFormatiert(LocalDate datum) {
		DateTimeFormatter f = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.GERMAN);
		return f.format(datum);
    }

	public static void ausgabeZuruecksetzen(){
		try {
			// Implementation fuer Windows
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			// Implementation fuer Linux
			else
				System.out.print("\033\143");
		} catch (IOException | InterruptedException ex) {
			// diese gesammte Funktionalitaet ueberspringen, sollte ein Fehler auftreten.
		}
	}

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

	public static void zeichnePunkte(int n) throws InterruptedException {
		if (n > 0) {
			System.out.print(".");
			Thread.sleep(850);
			zeichnePunkte(n - 1);
		} 
	}


}
