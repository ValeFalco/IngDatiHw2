import org.apache.lucene.analysis.it.ItalianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Classe Searcher - interroga un indice Lucene già creato.
 * Supporta query sui campi:
 *   - "nome"
 *   - "contenuto"
 */
public class Searcher {

    private final Path indexDir; // directory dell'indice Lucene

    public Searcher(String indexPath) {
        this.indexDir = Paths.get(indexPath);
    }

    /**
     * Esegue una ricerca su un campo specifico dell’indice.
     *
     * @param field il campo su cui cercare ("nome" o "contenuto")
     * @param queryText il testo da cercare
     */
    public void search(String field, String queryText) throws IOException, ParseException {
        Directory directory = FSDirectory.open(indexDir);
        var analyzer = new ItalianAnalyzer();

        // apre l’indice in sola lettura
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        // parser della query (usa lo stesso analyzer usato nell’indicizzazione)
        QueryParser parser = new QueryParser(field, analyzer);
        Query query = parser.parse(queryText);

        // esegue la ricerca (max 10 risultati)
        TopDocs results = searcher.search(query, 10);

        System.out.println("Query: " + queryText);
        System.out.println("Trovati " + results.totalHits.value() + " risultati:\n");

        for (ScoreDoc hit : results.scoreDocs) {
            Document doc = searcher.storedFields().document(hit.doc);
            String nome = doc.get("nome");
            System.out.printf("• %s (score: %.2f)%n", nome, hit.score);
        }

        reader.close();
        directory.close();
    }
}
