package Week4;

import java.util.List;

public class Question {
    private final String text;
    private final List<String> options; // must be size 4
    private final int correctIndex;     // 0..3
    private final String category;      // optional (can be null)

    public Question(String text, List<String> options, int correctIndex, String category) {
        if (options == null || options.size() != 4) {
            throw new IllegalArgumentException("Question must have exactly 4 options.");
        }
        if (correctIndex < 0 || correctIndex > 3) {
            throw new IllegalArgumentException("correctIndex must be 0..3");
        }
        this.text = text;
        this.options = options;
        this.correctIndex = correctIndex;
        this.category = category;
    }

    public String getText() { return text; }
    public List<String> getOptions() { return options; }
    public int getCorrectIndex() { return correctIndex; }
    public String getCategory() { return category; }
}
