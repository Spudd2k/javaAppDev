package Week4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class QuestionLoader {
    public void readFile() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("/Users/claykim/Desktop/ECE4319/Week_1/src/Week4/questions.txt");

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int readerCount = 0;

            String line = "";
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                readerCount++;
                System.out.println(readerCount + ": " + line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    HashMap<Integer, ArrayList<String>> map = new HashMap<>();

    public void readQuestions() {
        try {
            FileReader fileReader = null;
            fileReader = new FileReader("/Users/claykim/Desktop/ECE4319/Week_1/src/Week4/questions.txt");

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int readerCount = 0;

            String line = "";
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(readerCount + ": " + line);
                int questionNumber = readerCount / 5 + 1;
                if (questionNumber % 5 == 0) {
                    map.put(questionNumber, new ArrayList<>());
                }
                map.get(questionNumber).add(line);

                readerCount++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
