import org.apache.lucene.analysis.it.ItalianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;

/*
 * Classe per l'indicizzazione dei file di testo.
 * Campi:
 *   - "nome": nome del file
 *   - "contenuto": testo interno
 */

public class Indexer {

    private final Path docsDir;   // directory con i file da indicizzare
    private final Path indexDir;  // directory dove salvare l'indice

    public Indexer(String docsPath, String indexPath) {
        this.docsDir = Paths.get(docsPath);
        this.indexDir = Paths.get(indexPath);
    }

    /**
     * Esegue l’indicizzazione dei file .txt presenti in docsDir.
     */
    public void createIndex() throws IOException {
        // Analyzer italiano
        var analyzer = new ItalianAnalyzer();

        // Directory dove salvare l’indice
        Directory directory = FSDirectory.open(indexDir);

        // Configurazione e creazione dell’IndexWriter
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        try (IndexWriter writer = new IndexWriter(directory, config)) {

            // Scansiona la cartella e indicizza i file .txt
            Files.walk(docsDir)
                    .filter(p -> p.toString().endsWith(".txt"))
                    .forEach(file -> {
                        try {
                            String nome = file.getFileName().toString();
                            String contenuto = Files.readString(file, StandardCharsets.UTF_8);

                            Document doc = new Document();
                            doc.add(new StringField("nome", nome, Field.Store.YES));
                            doc.add(new TextField("contenuto", contenuto, Field.Store.YES));

                            writer.addDocument(doc);
                            System.out.println("Indicizzato: " + nome);
                        } catch (IOException e) {
                            System.err.println("Errore nell'indicizzare " + file + ": " + e.getMessage());
                        }
                    });

            writer.commit();
            System.out.println("Indicizzazione completata!");
        }
    }
}
