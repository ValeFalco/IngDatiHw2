import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        String docsPath = "docs";
        String indexPath = "indexDir";

        Path indexDir = Paths.get(indexPath);

        try {
            long start = System.currentTimeMillis();

            Indexer indexer = new Indexer(docsPath, indexPath);
            indexer.createIndex();

            long end = System.currentTimeMillis();
            System.out.printf("Tempo indicizzazione: %.2f secondi%n", (end - start)/1000.0);

            if (!Files.exists(indexDir) || Files.list(indexDir).count() == 0) {
                System.out.println("Nessun indice trovato in " + indexDir.toAbsolutePath());
                return;
            }

            System.out.println("Indicizzazione completata!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
