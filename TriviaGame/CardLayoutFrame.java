package Week4;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// custom ui stuff
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;


public class CardLayoutFrame extends JFrame {

    // panels
    private JPanel cardPanel;
    private JPanel welcomePanel;
    private JPanel usernamePanel;
    private JPanel gamePanel;
    private JPanel resultPanel;
    private JPanel genrePanel;

    // layout
    private CardLayout cardLayout;

    // UI components
    private JButton startButton;
    private JButton usernameButton;
    private JButton playButton;
    private JButton nextButton;

    private JButton[] optionButtons = new JButton[4];
    private JButton[] categoryButtons;

    private JTextField inputField;
    private JLabel questionLabel;

    // game state
    private String username = "Player";
    private int currentQuestionIdx = 0;
    private int score = 0;

    // category stuff
    private Map<String, List<Question>> questionsByCategory;
    private List<String> categories;
    private List<Question> questions; // current chosen set
    private String selectedCategory = "";

    // timer stuff
    private javax.swing.Timer questionTimer;
    private int timeRemaining;
    private JLabel timerLabel;


    public CardLayoutFrame() {

        //pulls questions from .txt file
        QuestionLoader loader = new QuestionLoader("C:/Users/dalto/Downloads/Year 4 Term 2/Java/TriviaGame/questions.txt");
        questionsByCategory = loader.loadByCategory();

        categories = new ArrayList<>(questionsByCategory.keySet());
        questions = new ArrayList<>();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        createWelcomePanel();

        createUsernamePanel();

        createGenrePanel();

        createGamePanel();

        createResultPanel();

        ButtonHandler handler = new ButtonHandler();
        startButton.addActionListener(handler);
        nextButton.addActionListener(handler);
        playButton.addActionListener(handler);
        usernameButton.addActionListener(handler);

        add(cardPanel);
    }

    // all ui panels

    // welcome panel------------------------------------------------------------------------------------------------
    private void createWelcomePanel() {
        welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(new Color(74, 74, 74)); // dark gray background

        // creates title screen welcome text
        JLabel title = new JLabel("Welcome Screen", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Impact", Font.BOLD, 70));
        welcomePanel.add(title, BorderLayout.CENTER);

        // start button
        startButton = new JButton("Start game!");
        startButton.setBackground(new Color(109, 129, 150)); // soft navy blue
        startButton.setForeground(Color.WHITE);
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);
        startButton.setFont(new Font("Impact", Font.BOLD, 30));
        welcomePanel.add(startButton, BorderLayout.SOUTH);

        cardPanel.add(welcomePanel, "W");
    }

    // username panel------------------------------------------------------------------------------------------------
    private void createUsernamePanel() {
        usernamePanel = new JPanel(new GridBagLayout());
        usernamePanel.setBackground(new Color(74, 74, 74));

        // puts inset pixel margins
        GridBagConstraints usernameGBC = new GridBagConstraints();
        usernameGBC.insets = new Insets(10,10,10,10);
        usernameGBC.gridx = 0;
        usernameGBC.gridy = 0;

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(new Color(74, 74, 74));

        JLabel usernameLabel = new JLabel("Enter a username: ");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Impact", Font.PLAIN, 24));

        // username input text field
        inputField = new JTextField(10);
        inputField.setForeground(new Color(64, 64, 64));
        inputField.setFont(new Font("Impact", Font.ITALIC, 24));

        usernameButton = new JButton("Save");

        centerPanel.add(usernameLabel);
        centerPanel.add(inputField);
        centerPanel.add(usernameButton);

        usernamePanel.add(centerPanel, usernameGBC);

        usernameGBC.gridy = 1;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(74, 74, 74));
        playButton = new JButton("Play!");
        playButton.setFont(new Font("Impact", Font.BOLD, 26));
        playButton.setBackground(new Color(107, 129, 150));
        playButton.setForeground(Color.WHITE);
        playButton.setOpaque(true);
        playButton.setBorderPainted(false);

        buttonPanel.add(playButton);
        usernamePanel.add(buttonPanel, usernameGBC);

        cardPanel.add(usernamePanel, "U");
    }

    // category panel------------------------------------------------------------------------------------------------
    private void createGenrePanel() {
        genrePanel = new JPanel(new GridBagLayout());
        genrePanel.setBackground(new Color(74, 74, 74));

        GridBagConstraints genreGBC = new GridBagConstraints();
        genreGBC.insets = new Insets(10, 10, 10, 10);
        genreGBC.gridx = 0;

        JLabel genreLabel = new JLabel("Select a category:");
        genreLabel.setFont(new Font("Impact", Font.BOLD, 36));
        genreLabel.setForeground(Color.WHITE);
        genreGBC.gridy = 0;
        genrePanel.add(genreLabel, genreGBC);

        categoryButtons = new JButton[categories.size()];

        // loop creates a button for each category
        // categories can be changed via .txt file
        for (int i = 0; i < categories.size(); i++) {
            String categoryName = categories.get(i);

            JButton btn = new JButton(categoryName);

            btn.setFont(new Font("Impact", Font.PLAIN, 24));
            btn.setPreferredSize(new Dimension(250, 60));
            btn.setBackground(new Color(107, 129, 150));
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setBorderPainted(false);

            btn.addActionListener(new CategoryButtonHandler(categoryName));

            categoryButtons[i] = btn;
            genreGBC.gridy = i + 1;
            genrePanel.add(btn, genreGBC);
        }

        cardPanel.add(genrePanel, "P");
    }

    // game panel------------------------------------------------------------------------------------------------
    private void createGamePanel() {
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBackground(new Color(74, 74, 74));

        // html text-align since our questions kept trailing off the page
        questionLabel = new JLabel("<html><div style='text-align: center;'>Question</div></html>", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Impact", Font.PLAIN, 26));
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        gamePanel.add(questionLabel, BorderLayout.NORTH);

        // timer label. starts at 10 seconds
        timerLabel = new JLabel("Time: 10", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Impact", Font.PLAIN, 28));
        timerLabel.setForeground(new Color(100, 200, 100)); // green
        timerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(new Color(74, 74, 74));
        northPanel.add(questionLabel, BorderLayout.CENTER);
        northPanel.add(timerLabel, BorderLayout.SOUTH);
        gamePanel.add(northPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 12, 12));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        buttonPanel.setBackground(new Color(74, 74, 74));

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton("Option " + (i + 1));
            optionButtons[i].setFont(new Font("Impact", Font.PLAIN, 20));
            optionButtons[i].addActionListener(new OptionButtonHandler(i));
            buttonPanel.add(optionButtons[i]);
        }

        gamePanel.add(buttonPanel, BorderLayout.CENTER);

        // override to end game early
        nextButton = new JButton("Go to Results");
        nextButton.setFont(new Font("Impact", Font.PLAIN, 22));
        gamePanel.add(nextButton, BorderLayout.SOUTH);

        cardPanel.add(gamePanel, "G");
    }

    // timer method------------------------------------------------------------------------------------------------
    private void startTimer() {
        if (questionTimer != null && questionTimer.isRunning()) {
            questionTimer.stop();
        }

        timeRemaining = 10;
        timerLabel.setText("Time: " + timeRemaining);
        timerLabel.setForeground(new Color(100, 200, 100)); // reset to green

        // 1 second timer for time remaining minus 1
        questionTimer = new javax.swing.Timer(1000, e -> {timeRemaining--; timerLabel.setText("Time: " + timeRemaining);

            // turns orange at 5 seconds left
            if (timeRemaining <= 5) {
                timerLabel.setForeground(new Color(255, 165, 0));
            }
            // red at 3 seconds left
            if (timeRemaining <= 3) {
                timerLabel.setForeground(new Color(220, 50, 50));
            }

            if (timeRemaining <= 0) {
                questionTimer.stop();
                JOptionPane.showMessageDialog(null, "Time's up!");
                currentQuestionIdx++;
                loadNextQuestion();
            }
        });

        questionTimer.start();
    }

    // result panel------------------------------------------------------------------------------------------------
    private void createResultPanel() {
        resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setBackground(new Color(74, 74, 74));

        GridBagConstraints resultsGBC = new GridBagConstraints();
        resultsGBC.insets = new Insets(10, 10, 10, 10);
        resultsGBC.gridx = 0;

        JLabel resultLabel = new JLabel(); // set dynamically in endGame()
        resultLabel.setFont(new Font("Impact", Font.BOLD, 40));
        resultLabel.setForeground(Color.WHITE);

        JLabel scoreLabel = new JLabel(); // set dynamically in endGame()
        scoreLabel.setFont(new Font("Impact", Font.PLAIN, 28));
        scoreLabel.setForeground(Color.WHITE);

        resultsGBC.gridy = 0;
        resultPanel.add(resultLabel, resultsGBC);

        resultsGBC.gridy = 1;
        resultPanel.add(scoreLabel, resultsGBC);

        // play again button takes you to categories panel
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Impact", Font.PLAIN, 24));
        playAgainButton.setPreferredSize(new Dimension(200, 60));
        playAgainButton.setBackground(new Color(107, 129, 150));
        playAgainButton.setForeground(Color.WHITE);
        playAgainButton.setOpaque(true);
        playAgainButton.setBorderPainted(false);

        // change username allows you to change username and play again
        JButton changeUsernameButton = new JButton("Change Username");
        changeUsernameButton.setFont(new Font("Impact", Font.PLAIN, 24));
        changeUsernameButton.setPreferredSize(new Dimension(250, 60));
        changeUsernameButton.setBackground(new Color(107, 129, 150));
        changeUsernameButton.setForeground(Color.WHITE);
        changeUsernameButton.setOpaque(true);
        changeUsernameButton.setBorderPainted(false);

        // panel for both buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(new Color(74, 74, 74));
        bottomPanel.add(playAgainButton);
        bottomPanel.add(changeUsernameButton);

        resultsGBC.gridy = 2;
        resultPanel.add(bottomPanel, resultsGBC);

        // actions
        playAgainButton.addActionListener(e -> cardLayout.show(cardPanel, "P"));
        changeUsernameButton.addActionListener(e -> cardLayout.show(cardPanel, "U"));

        // store labels so endGame can update them
        this.resultTitleLabel = resultLabel;
        this.resultScoreLabel = scoreLabel;

        cardPanel.add(resultPanel, "R");
    }

    // post-runtime label updates
    private JLabel resultTitleLabel;
    private JLabel resultScoreLabel;


    //logic stuff------------------------------------------------------------------------------------------------
    private void loadNextQuestion() {
        if (questions == null || questions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pick a category first.");
            cardLayout.show(cardPanel, "P");
            return;
        }

        if (currentQuestionIdx >= questions.size()) {
            endGame();
            return;
        }

        Question q = questions.get(currentQuestionIdx);
        questionLabel.setText("<html><div style='text-align: center;'>[" + selectedCategory + "] Q"
                + (currentQuestionIdx + 1) + ": " + q.getText() + "</div></html>");

        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(q.getOptions().get(i));
            optionButtons[i].setEnabled(true);
        }

        startTimer();
    }

    private void endGame() {
        for (JButton b : optionButtons) b.setEnabled(false);

        // update result labels
        if (resultTitleLabel != null) {
            resultTitleLabel.setText("Results: Good Job, " + username);
        }
        if (resultScoreLabel != null) {
            resultScoreLabel.setText("Score: " + score + " / " + (questions == null ? 0 : questions.size()));
        }

        cardLayout.show(cardPanel, "R");
    }

    // event handlers------------------------------------------------------------------------------------------------

    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startButton) {
                cardLayout.show(cardPanel, "U");

            } else if (e.getSource() == playButton) {
                cardLayout.show(cardPanel, "P"); // FIX: genre panel is "P"

            } else if (e.getSource() == usernameButton) {
                String entered = inputField.getText().trim();
                username = entered.isEmpty() ? "Player" : entered;
                JOptionPane.showMessageDialog(null, "Welcome " + username + "!");

            } else if (e.getSource() == nextButton) {
                endGame();
            }
        }
    }

    private class CategoryButtonHandler implements ActionListener {
        private final String category;

        public CategoryButtonHandler(String category) {
            this.category = category;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            selectedCategory = category;

            List<Question> set = questionsByCategory.get(category);
            if (set == null || set.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No questions found for: " + category);
                return;
            }

            questions = set;
            currentQuestionIdx = 0;
            score = 0;

            loadNextQuestion();
            cardLayout.show(cardPanel, "G");
        }
    }

    private class OptionButtonHandler implements ActionListener {
        private final int index;

        public OptionButtonHandler(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // stops the timer if an answer is selected
            if (questionTimer != null && questionTimer.isRunning()) {
                questionTimer.stop();
            }
            if (questions == null || currentQuestionIdx >= questions.size()) {
                endGame();
                return;
            }

            int correct = questions.get(currentQuestionIdx).getCorrectIndex();

            if (index == correct) {
                JOptionPane.showMessageDialog(null, "Correct!");
                score++;
            } else {
                JOptionPane.showMessageDialog(null, "Wrong answer!");
            }

            currentQuestionIdx++;
            loadNextQuestion();
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CardLayoutFrame::new);
    }
}
