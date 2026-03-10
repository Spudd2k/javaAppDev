package Week4;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class CardLayoutFrame extends JFrame {

    private JPanel cardPanel;
    private JPanel welcomePanel;
    private JPanel usernamePanel;
    private JPanel gamePanel;
    private JPanel resultPanel;

    private JButton startButton;
    private JButton usernameButton;
    private JButton playButton;
    private JButton nextButton;
    private JButton[] optionButtons = new JButton[4];

    private CardLayout cardLayout;

    private JTextField inputField;

    private String username = "";

    private JLabel questionLabel;

    private int currentQuestionIdx;

    private List<List<String>> questionPair;
    private List<Integer> correctAnswers;

    public CardLayoutFrame() {
        currentQuestionIdx = 0;
        importQuestions();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        createWelcomePanel();

        createUsernamePanel();

        createGenrePanel();

        createGamePanel();

        ButtonHandler handler = new ButtonHandler();
        startButton.addActionListener(handler);
        nextButton.addActionListener(handler);
        playButton.addActionListener(handler);
        usernameButton.addActionListener(handler);

        add(cardPanel);
    }

    private void createGenrePanel(){
        genrePanel = new JPanel(new GridBagLayout());
        genrePanel.setBackground(new Color(74,74,74));
    }
    private void createUsernamePanel() {

        usernamePanel = new JPanel(new GridBagLayout());
        usernamePanel.setBackground(new Color(74,74,74));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(new Color(74, 74, 74));

        inputField = new JTextField(10);
        inputField.setFont(new Font("Impact", Font.ITALIC, 24));

        JLabel usernameLabel = new JLabel("Enter a username: ");
        usernameLabel.setForeground(new Color(255,255,255));
        usernameLabel.setFont(new Font("Impact", Font.PLAIN, 24));

        usernameButton = new JButton("Save");

        centerPanel.add(usernameLabel);
        centerPanel.add(inputField);
        centerPanel.add(usernameButton);

        usernamePanel.add(centerPanel);


        JPanel buttonPanel = new JPanel(new FlowLayout());
        playButton = new JButton("Play!");
        buttonPanel.setBackground(new Color(255, 255, 227));
        buttonPanel.add(playButton);
        usernamePanel.add(buttonPanel);

        cardPanel.add(usernamePanel, "U");
    }
    private void CreateResultsPanel() {
        resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setBackground(new Color(74,74,74));

        JLabel resultLabel = new JLabel("Results Screen: Good Job " + username);
        resultLabel.setFont(new Font("Impact", Font.BOLD, 40));
        resultLabel.setForeground(Color.WHITE);

        GridBagConstraints resultsGBC = new GridBagConstraints();
        resultsGBC.anchor = GridBagConstraints.NORTH;
        resultsGBC.weighty = 1.0;
        resultsGBC.gridy = 0;

        resultPanel.add(resultLabel, resultsGBC);
        cardPanel.add(resultPanel, "R");
    }
    private void createGamePanel() {
        gamePanel = new JPanel(new BorderLayout());
        questionLabel = new JLabel("Question #1", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 32));
        gamePanel.add(questionLabel, BorderLayout.NORTH);

        nextButton = new JButton("Go to results");
        gamePanel.add(nextButton, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton("Option " + (i+1));
            buttonPanel.add(optionButtons[i]);
        }
        gamePanel.add(buttonPanel, BorderLayout.CENTER);
        cardPanel.add(gamePanel, "G");
    }
    private void createWelcomePanel() {
        welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(new Color(74, 74, 74)); //dark gray background
        JLabel title = new JLabel("Welcome Screen", SwingConstants.CENTER);
        title.setForeground(new Color(203, 203, 203)); // gold color
        title.setOpaque(false); // make label background transparent
        title.setFont(new Font("Impact", Font.BOLD, 70));
        welcomePanel.add(title, BorderLayout.CENTER);
        startButton = new JButton("Start game!");
        startButton.setBackground(new Color(109, 129, 150));  //soft navy blue
        startButton.setForeground(Color.WHITE);              // white text
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);                 // removes default border for cleaner look
        startButton.setFont(new Font("Impact", Font.BOLD, 30));
        welcomePanel.add(startButton, BorderLayout.SOUTH);
        cardPanel.add(welcomePanel, "W");
    }
    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startButton) {
                cardLayout.show(cardPanel, "U");
            } else if (e.getSource() == playButton) {
                loadNextQuestions();
                cardLayout.show(cardPanel, "G");
            } else if (e.getSource() == nextButton) {
//Results screen
                CreateResultsPanel();
                cardLayout.show(cardPanel, "R");
            } else if (e.getSource() == usernameButton) {
                username = inputField.getText();
                JOptionPane.showMessageDialog(null,
                        "Welcome " + username + "!");
            }
        }
    }
    private class OptionButtonHandler implements ActionListener {
        private int index;
        public OptionButtonHandler(int index) {
            this.index = index;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }
    private void importQuestions() {
        questionPair = new ArrayList<>();
        correctAnswers = new ArrayList<>();
        questionPair.add(List.of(
                "Question 1: What do we refer to BST in 4319?",
                "British Summer Time",
                "Breadth Search Tree",
                "Binary Search Tree",
                "None of above"
        ));
        correctAnswers.add(2);
        questionPair.add(List.of("Which class belongs to Java Swing?",
                "NumberFormatException",
                "String",
                "Graphics",
                "None of above"));
        correctAnswers.add(3);
        questionPair.add(List.of("What is the capital of France?",
                "Paris", "London", "Berlin", "Rome"));
        correctAnswers.add(0);
        questionPair.add(List.of("Which planet is known as the Red Planet?",
                "Earth", "Venus", "Mars", "Jupiter"));
        correctAnswers.add(2);
        questionPair.add(List.of("Recursion always needs a?",
                "Loop", "Base Case", "Queue", "Stack"));
        correctAnswers.add(1);
    }
    private void loadNextQuestions() {
        if (questionPair == null ||
                currentQuestionIdx > questionPair.size() -1) {
            cardLayout.show(cardPanel, "R");
            return;
        }
        questionLabel.setText(
                (questionPair.get(currentQuestionIdx).get(0)));
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(
                    questionPair.get(currentQuestionIdx).get(i + 1));
        }
    }
}
