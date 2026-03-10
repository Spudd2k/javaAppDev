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

//all my added UI imports
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class CardLayoutFrame extends JFrame {

    private JPanel cardPanel;
    private JPanel welcomePanel;
    private JPanel usernamePanel;
    private JPanel gamePanel;
    private JPanel resultPanel;
    private JPanel genrePanel;

    private JButton startButton;
    private JButton usernameButton;
    private JButton playButton;
    private JButton nextButton;
    private JButton[] optionButtons = new JButton[4];

    private JButton historyButton;
    private JButton sportsButton;
    private JButton entertainmentButton;
    private JButton generalButton;


    private CardLayout cardLayout;

    private JTextField inputField;

    private String username = "";

    private JLabel questionLabel;

    private int currentQuestionIdx;

    private List<List<String>> questionPair;
    private List<Integer> correctAnswers;

    public CardLayoutFrame() {
        currentQuestionIdx = 0;

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

        historyButton.addActionListener(handler);
        sportsButton.addActionListener(handler);
        entertainmentButton.addActionListener(handler);
        generalButton.addActionListener(handler);

        add(cardPanel);
    }

    private void createGenrePanel(){
        genrePanel = new JPanel(new GridBagLayout());
        genrePanel.setBackground(new Color(74,74,74));

        GridBagConstraints genreGBC = new GridBagConstraints();
        genreGBC.insets = new Insets(10, 10, 10, 10);

        // Title label
        JLabel genreLabel = new JLabel("Select a Genre:");
        genreLabel.setFont(new Font("Impact", Font.BOLD, 36));
        genreLabel.setForeground(Color.WHITE);
        genreGBC.gridy = 0;
        genrePanel.add(genreLabel, genreGBC);

        // Genre buttons
        historyButton = new JButton("History");
        sportsButton = new JButton("Sports");
        entertainmentButton = new JButton("Entertainment");
        generalButton = new JButton("General");

        // Style each button
        for (JButton btn : new JButton[]{historyButton, sportsButton, entertainmentButton, generalButton}) {
            btn.setFont(new Font("Impact", Font.PLAIN, 24));
            btn.setPreferredSize(new Dimension(250, 60));
            btn.setBackground(new Color(107, 129, 150)); // blue
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
        }

        // Add buttons vertically
        genreGBC.gridy = 1; genrePanel.add(historyButton, genreGBC);
        genreGBC.gridy = 2; genrePanel.add(sportsButton, genreGBC);
        genreGBC.gridy = 3; genrePanel.add(entertainmentButton, genreGBC);
        genreGBC.gridy = 4; genrePanel.add(generalButton, genreGBC);

        cardPanel.add(genrePanel, "P");
    }
    private void createUsernamePanel() {

        usernamePanel = new JPanel(new GridBagLayout());
        usernamePanel.setBackground(new Color(74,74,74));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(new Color(74, 74, 74));

        inputField = new JTextField(10);
        inputField.setForeground(new Color(64,64,64));
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

        JLabel resultLabel = new JLabel("Results Screen: Good Job, " + username);
        resultLabel.setFont(new Font("Impact", Font.BOLD, 40));
        resultLabel.setForeground(Color.WHITE);

        GridBagConstraints resultsGBC = new GridBagConstraints();
        resultsGBC.anchor = GridBagConstraints.NORTH;
        resultsGBC.weighty = 1.0;
        resultsGBC.gridy = 0;

        // Play again button
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Impact", Font.PLAIN, 24));
        playAgainButton.setPreferredSize(new Dimension(200, 60));
        playAgainButton.setBackground(new Color(107, 129, 150));
        playAgainButton.setForeground(Color.WHITE);
        playAgainButton.setOpaque(true);
        playAgainButton.setBorderPainted(false);

        // Change username button
        JButton changeUsernameButton = new JButton("Change Username");
        changeUsernameButton.setFont(new Font("Impact", Font.PLAIN, 24));
        changeUsernameButton.setPreferredSize(new Dimension(250, 60));
        changeUsernameButton.setBackground(new Color(107, 129, 150));
        changeUsernameButton.setForeground(Color.WHITE);
        changeUsernameButton.setOpaque(true);
        changeUsernameButton.setBorderPainted(false);

        // Button panel at bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(new Color(74, 74, 74));
        bottomPanel.add(playAgainButton);
        bottomPanel.add(changeUsernameButton);

        GridBagConstraints bottomGBC = new GridBagConstraints();
        bottomGBC.anchor = GridBagConstraints.SOUTH;
        bottomGBC.weighty = 1.0;
        bottomGBC.gridy = 1;
        resultPanel.add(bottomPanel, bottomGBC);

        // Button actions
        playAgainButton.addActionListener(e -> cardLayout.show(cardPanel, "P"));
        changeUsernameButton.addActionListener(e -> cardLayout.show(cardPanel, "U"));

        cardPanel.add(resultPanel, "R");

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
        title.setForeground(new Color(203, 203, 203));
        title.setOpaque(false);
        title.setFont(new Font("Impact", Font.BOLD, 70));
        welcomePanel.add(title, BorderLayout.CENTER);
        startButton = new JButton("Start game!");
        startButton.setBackground(new Color(109, 129, 150));  //soft navy blue
        startButton.setForeground(Color.WHITE);
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);
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
                cardLayout.show(cardPanel, "P");
            } else if (e.getSource() == nextButton) {
//Results screen
                CreateResultsPanel();
                cardLayout.show(cardPanel, "R");
            } else if (e.getSource() == usernameButton) {
                username = inputField.getText();
                JOptionPane.showMessageDialog(null,
                        "Welcome " + username + "!");
            }
            //This else if is temporary so i can see the results panel before we add all the questions
            else if (e.getSource() == historyButton){
                CreateResultsPanel();
                cardLayout.show(cardPanel, "R");
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
