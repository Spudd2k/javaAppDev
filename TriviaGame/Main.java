package Week4;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        CardLayoutFrame frame = new CardLayoutFrame();
        frame.setTitle("Trivia Game by Dalton");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        QuestionLoader questionLoader = new QuestionLoader();
        questionLoader.readFile();

    }
}
