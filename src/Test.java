import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;

/*
 * Classe Test per testare il funzionamento delle queries.
 */

public class Test {
        public static void main(String[] args) {
        String indexPath = "indexDir"; // stessa directory dell'indice creato da Indexer

        // Array di query di esempio (almeno 10)
        String[] queries = {
                "nome tesi.txt",
                "nome guida.txt",
                "nome articolo.txt",
                "nome prova.txt",
                "contenuto data engineer",
                "contenuto ingegneria dati",
                "contenuto lucene",
                "contenuto big data",
                "contenuto open source",
                "contenuto analisi"
        };

        try {
            Searcher searcher = new Searcher(indexPath);

            System.out.println("=== Esecuzione test queries ===\n");

            for (String q : queries) {
                String[] parts = q.split("\\s+", 2);
                if (parts.length < 2) continue;

                String field = parts[0].toLowerCase();
                String queryText = parts[1];

                System.out.println("Query: " + q);
                searcher.search(field, queryText);
                System.out.println("-----------------------------\n");
            }

            System.out.println("=== Test completati ===");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

}
