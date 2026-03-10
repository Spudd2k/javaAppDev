package Week4;

import java.io.*;
import java.util.*;

public class QuestionLoader {

    private final String filePath;

    public QuestionLoader(String filePath) {
        this.filePath = filePath;
    }

    // category -> list of questions (in file order)
    public Map<String, List<Question>> loadByCategory() {
        Map<String, List<Question>> map = new LinkedHashMap<>(); // keeps category order
        String currentCategory = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while (true) {
                String line = nextNonEmptyLine(br);
                if (line == null) break;

                if (line.startsWith("Category:")) {
                    currentCategory = line.substring("Category:".length()).trim();
                    map.putIfAbsent(currentCategory, new ArrayList<>());
                    continue;
                }

                // If we get here, line is a question text
                if (currentCategory == null) {
                    throw new RuntimeException("Question found before any Category: line.");
                }

                String qText = line;
                String o1 = nextNonEmptyLine(br);
                String o2 = nextNonEmptyLine(br);
                String o3 = nextNonEmptyLine(br);
                String o4 = nextNonEmptyLine(br);
                String correctLine = nextNonEmptyLine(br);

                if (o1 == null || o2 == null || o3 == null || o4 == null || correctLine == null) {
                    throw new RuntimeException("Malformed question block under category: " + currentCategory);
                }

                int correctIndex = Integer.parseInt(correctLine.trim());

                map.get(currentCategory).add(
                        new Question(qText, List.of(o1, o2, o3, o4), correctIndex, currentCategory)
                );
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read: " + filePath, e);
        }

        return map;
    }

    private String nextNonEmptyLine(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) return line;
        }
        return null;
    }
}