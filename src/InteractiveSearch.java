import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.Scanner;

/*
 * Classe per la ricerca interattiva nei documenti txt.
 */

public class InteractiveSearch {

    public static void main(String[] args) {
        String indexPath = "indexDir";

        try {
            Searcher searcher = new Searcher(indexPath);
            Scanner scanner = new Scanner(System.in);

            System.out.println("=== Lucene Interactive Search ===");
            System.out.println("Digita 'exit' per uscire.");

            while (true) {
                System.out.print("\nSe vuoi uscire, digita exit.\nInserisci la query (es: nome tesi.txt oppure contenuto lucene): ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) break;

                String[] parts = input.split("\\s+", 2);
                if (parts.length < 2) {
                    System.out.println("Query non valida. Usa: nome <file> oppure contenuto <term>");
                    continue;
                }

                String field = parts[0].toLowerCase();
                String queryText = parts[1];

                searcher.search(field, queryText);
                System.out.println("-----------------------------");
            }

            System.out.println("Uscita da Lucene Interactive Search.");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
