package morse;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout; // this is the grid layout accompanied by gbc
import java.awt.GridBagConstraints; // this comes with grid layout which helps with position 
import java.awt.Insets;
import java.awt.Toolkit; // link between java swin and os which linked with the copy board
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.geom.RoundRectangle2D;
import java.awt.datatransfer.StringSelection; // basically copy board lang toh
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicTabbedPaneUI; // basically the controller of the tabbed pane and how it behaves
import java.awt.Rectangle;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel morsleToSolvePanel;
    private JTextField[] letterFields;
    private JComboBox<String> difficultyBox;
    private JSlider wpmSlider;
    private JLabel lblTries;
    private int triesLeft = 10;
    private String currentWord = "";

    private static final Color C1 = new Color(0xf1faee);
    private static final Color C2 = new Color(0xa8dadc);
    private static final Color C3 = new Color(0x457b9d);
    private static final Color C4 = new Color(0x1d3557);

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        setSize(850, 600);
        setMinimumSize(new Dimension(800, 550));
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBackground(C4);
        tabbedPane.setForeground(C1);
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 14));
        tabbedPane.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                tabInsets = new Insets(10, 20, 10, 20);
            }
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (isSelected) {
                    g2.setColor(C2);
                } else {
                    java.awt.Point mouse = getMousePosition();
                    boolean hover = (mouse != null && new Rectangle(x, y, w, h).contains(mouse));
                    if (hover) {
                        g2.setColor(C3);
                    } else {
                        g2.setColor(C4);
                    }
                }
                g2.fillRect(x, y, w, h);
                g2.dispose();
            }
            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {}
            @Override
            protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {}
        });
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        // ==================== MAIN MENU ====================
        JPanel MainMenu = createModernMainMenu(tabbedPane);
        tabbedPane.addTab("Main Menu", null, MainMenu, null);

        // ==================== WHAT'S MORSE CODE? (with content) ====================
        JPanel WMC = new JPanel(new BorderLayout());
        JTextArea infoText = new JTextArea();
        infoText.setText("");
        infoText.setFont(new Font("SansSerif", Font.PLAIN, 16));
        infoText.setLineWrap(true);
        infoText.setWrapStyleWord(true);
        infoText.setEditable(false);
        WMC.add(new JScrollPane(infoText), BorderLayout.CENTER);
        tabbedPane.addTab("What's Morse Code?", null, WMC, null);

        // ==================== TRANSLATOR (unchanged) ====================
        JPanel TranslatorPanel = new JPanel();
        tabbedPane.addTab("Translator", null, TranslatorPanel, null);
        TranslatorPanel.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        TranslatorPanel.add(panel, BorderLayout.CENTER);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JLabel lblInput = new JLabel("Input");
        GridBagConstraints gbc_lblInput = new GridBagConstraints();
        gbc_lblInput.insets = new Insets(0, 0, 5, 0);
        gbc_lblInput.gridx = 0;
        gbc_lblInput.gridy = 0;
        panel.add(lblInput, gbc_lblInput);

        JScrollPane scrollPane_Input = new JScrollPane();
        GridBagConstraints gbc_scrollPane_Input = new GridBagConstraints();
        gbc_scrollPane_Input.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPane_Input.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_Input.gridx = 0;
        gbc_scrollPane_Input.gridy = 1;
        panel.add(scrollPane_Input, gbc_scrollPane_Input);

        JTextArea textArea_Input = new JTextArea();
        textArea_Input.setLineWrap(true);
        scrollPane_Input.setViewportView(textArea_Input);

        JPanel panelButtons = new JPanel();
        GridBagConstraints gbc_panelButtons = new GridBagConstraints();
        gbc_panelButtons.insets = new Insets(0, 0, 5, 0);
        gbc_panelButtons.gridx = 0;
        gbc_panelButtons.gridy = 2;
        panel.add(panelButtons, gbc_panelButtons);
        panelButtons.setLayout(new GridLayout(0, 3, 10, 0));

        JLabel lblOutput = new JLabel("Output");
        GridBagConstraints gbc_lblOutput = new GridBagConstraints();
        gbc_lblOutput.insets = new Insets(0, 0, 5, 0);
        gbc_lblOutput.gridx = 0;
        gbc_lblOutput.gridy = 3;
        panel.add(lblOutput, gbc_lblOutput);

        JScrollPane panel_GridBagLayout = new JScrollPane();
        GridBagConstraints gbc_panel_GridBagLayout = new GridBagConstraints();
        gbc_panel_GridBagLayout.fill = GridBagConstraints.BOTH;
        gbc_panel_GridBagLayout.gridx = 0;
        gbc_panel_GridBagLayout.gridy = 4;
        panel.add(panel_GridBagLayout, gbc_panel_GridBagLayout);

        JTextArea textArea_Output = new JTextArea();
        textArea_Output.setEditable(false);
        textArea_Output.setLineWrap(true);
        panel_GridBagLayout.setViewportView(textArea_Output);

        JButton btnTtM = new JButton("Text to MorseCode");
        btnTtM.addActionListener(e -> textArea_Output.setText(Translator.textToMorse(textArea_Input.getText())));
        panelButtons.add(btnTtM);

        JButton btnMtT = new JButton("MorseCode to Text");
        btnMtT.addActionListener(e -> textArea_Output.setText(Translator.morseToText(textArea_Input.getText())));
        panelButtons.add(btnMtT);

        JButton btnCtC = new JButton("Copy to Clipboard");
        btnCtC.addActionListener(e -> {
            String text = textArea_Output.getText();
            StringSelection selection = new StringSelection(text);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
            JOptionPane.showMessageDialog(null, "Copied to clipboard!");
        });
        panelButtons.add(btnCtC);

        // ==================== MINIGAME (enhanced) ====================
        JPanel Minigame = new JPanel();
        tabbedPane.addTab("Minigame", null, Minigame, null);
        GridBagLayout gbl_Minigame = new GridBagLayout();
        gbl_Minigame.columnWidths = new int[]{0, 0};
        gbl_Minigame.rowHeights = new int[]{0, 0, 0, 0};
        gbl_Minigame.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_Minigame.rowWeights = new double[]{0.3, 0.0, 1.0, Double.MIN_VALUE};
        Minigame.setLayout(gbl_Minigame);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.insets = new Insets(10, 10, 5, 10);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.weightx = 1.0;
        gbc_scrollPane.weighty = 0.3;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 0;
        Minigame.add(scrollPane, gbc_scrollPane);

        morsleToSolvePanel = new JPanel();
        scrollPane.setViewportView(morsleToSolvePanel);
        createLetterFields(5);

        JPanel middleButtonsPanel = new JPanel();
        GridBagConstraints gbc_middleButtonsPanel = new GridBagConstraints();
        gbc_middleButtonsPanel.insets = new Insets(0, 0, 5, 0);
        gbc_middleButtonsPanel.fill = GridBagConstraints.BOTH;
        gbc_middleButtonsPanel.gridx = 0;
        gbc_middleButtonsPanel.gridy = 1;
        Minigame.add(middleButtonsPanel, gbc_middleButtonsPanel);
        middleButtonsPanel.setLayout(new BorderLayout(0, 0));

        JPanel FlowLayoutPanel = new JPanel();
        middleButtonsPanel.add(FlowLayoutPanel, BorderLayout.CENTER);

        JPanel LeftSidePanel = new JPanel();
        FlowLayoutPanel.add(LeftSidePanel);

        JPanel panel_2 = new JPanel();
        LeftSidePanel.add(panel_2);
        GridBagLayout gbl_panel_2 = new GridBagLayout();
        gbl_panel_2.columnWidths = new int[]{0, 0, 0};
        gbl_panel_2.rowHeights = new int[]{0, 0, 0};
        gbl_panel_2.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_panel_2.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        panel_2.setLayout(gbl_panel_2);

        difficultyBox = new JComboBox<>();
        difficultyBox.setToolTipText("Select Difficulty");
        difficultyBox.addItem("Short Words");
        difficultyBox.addItem("Medium Words");
        GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
        gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_1.gridx = 0;
        gbc_comboBox_1.gridy = 0;
        panel_2.add(difficultyBox, gbc_comboBox_1);

        JButton btnNewWordButton = new JButton("New Word");
        btnNewWordButton.addActionListener(e -> newGame());
        GridBagConstraints gbc_btnNewWordButton = new GridBagConstraints();
        gbc_btnNewWordButton.insets = new Insets(0, 0, 5, 0);
        gbc_btnNewWordButton.gridx = 1;
        gbc_btnNewWordButton.gridy = 0;
        panel_2.add(btnNewWordButton, gbc_btnNewWordButton);

        JLabel lblTriesRemainingLabel = new JLabel("Tries:");
        GridBagConstraints gbc_lblTriesRemainingLabel = new GridBagConstraints();
        gbc_lblTriesRemainingLabel.insets = new Insets(0, 0, 0, 5);
        gbc_lblTriesRemainingLabel.gridx = 0;
        gbc_lblTriesRemainingLabel.gridy = 1;
        panel_2.add(lblTriesRemainingLabel, gbc_lblTriesRemainingLabel);

        lblTries = new JLabel("10");
        GridBagConstraints gbc_lblTries = new GridBagConstraints();
        gbc_lblTries.insets = new Insets(0, 0, 0, 5);
        gbc_lblTries.gridx = 1;
        gbc_lblTries.gridy = 1;
        panel_2.add(lblTries, gbc_lblTries);

        JButton btnPlaySoundButton = new JButton("Play Sound");
        btnPlaySoundButton.addActionListener(e -> playSound());
        GridBagConstraints gbc_btnPlaySoundButton = new GridBagConstraints();
        gbc_btnPlaySoundButton.gridx = 2;
        gbc_btnPlaySoundButton.gridy = 1;
        panel_2.add(btnPlaySoundButton, gbc_btnPlaySoundButton);

        JPanel RightSidePanel = new JPanel();
        FlowLayoutPanel.add(RightSidePanel);
        GridBagLayout gbl_RightSidePanel = new GridBagLayout();
        gbl_RightSidePanel.columnWidths = new int[]{0, 0, 0};
        gbl_RightSidePanel.rowHeights = new int[]{0, 0};
        gbl_RightSidePanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gbl_RightSidePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        RightSidePanel.setLayout(gbl_RightSidePanel);

        JButton btnClearButton = new JButton("Clear");
        btnClearButton.addActionListener(e -> clearFields());
        GridBagConstraints gbc_btnClearButton = new GridBagConstraints();
        gbc_btnClearButton.insets = new Insets(0, 0, 0, 5);
        gbc_btnClearButton.gridx = 0;
        gbc_btnClearButton.gridy = 0;
        RightSidePanel.add(btnClearButton, gbc_btnClearButton);

        JButton btnNewButton_2 = new JButton("Guess");
        btnNewButton_2.addActionListener(e -> checkGuess());
        GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
        gbc_btnNewButton_2.gridx = 1;
        gbc_btnNewButton_2.gridy = 0;
        RightSidePanel.add(btnNewButton_2, gbc_btnNewButton_2);

        JPanel wpmPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        wpmPanel.add(new JLabel("WPM:"));
        wpmSlider = new JSlider(20, 60, 40);
        wpmSlider.setPreferredSize(new Dimension(150, 40));
        wpmSlider.setMajorTickSpacing(10);
        wpmSlider.setPaintTicks(true);
        wpmSlider.setPaintLabels(true);
        wpmPanel.add(wpmSlider);
        RightSidePanel.add(wpmPanel);

        JPanel lettersMorseTabbedPanel = new JPanel();
        GridBagConstraints gbc_lettersMorseTabbedPanel = new GridBagConstraints();
        gbc_lettersMorseTabbedPanel.fill = GridBagConstraints.BOTH;
        gbc_lettersMorseTabbedPanel.gridx = 0;
        gbc_lettersMorseTabbedPanel.gridy = 2;
        Minigame.add(lettersMorseTabbedPanel, gbc_lettersMorseTabbedPanel);
        lettersMorseTabbedPanel.setLayout(new BorderLayout(0, 0));

        JTabbedPane tabbedPaneInputLetters = new JTabbedPane(JTabbedPane.TOP);
        lettersMorseTabbedPanel.add(tabbedPaneInputLetters);

        JPanel dotsAndDashesPanel = new JPanel();
        tabbedPaneInputLetters.addTab("Dots and Dashes", null, dotsAndDashesPanel, null);

        JPanel lettersPanel = new JPanel();
        lettersPanel.setLayout(new GridLayout(0, 3, 4, 4));
        MorseMap.textToMorse.entrySet().stream()
            .filter(e -> Character.isLetter(e.getKey()))
            .sorted(java.util.Map.Entry.comparingByKey())
            .forEach(e -> {
                String letter = String.valueOf(e.getKey());
                String morse = e.getValue();
                String html = String.format(
                    "<html><table width='160'><td><td align='left'><b>%s</b></td><td align='right'>%s</td></table></html>",
                    letter, morse);
                JButton btn = new JButton(html);
                btn.setFont(btn.getFont().deriveFont(20f));
                btn.setHorizontalAlignment(SwingConstants.CENTER);
                btn.setMargin(new Insets(2, 6, 2, 6));
                lettersPanel.add(btn);
            });
        JScrollPane lettersScrollPane = new JScrollPane(lettersPanel);
        lettersScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tabbedPaneInputLetters.addTab("Letters", null, lettersScrollPane, null);

        contentPane.setPreferredSize(new Dimension(850, 600));
        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);

        newGame();
    }

    private void createLetterFields(int wordLength) {
        morsleToSolvePanel.removeAll();
        letterFields = new JTextField[wordLength];
        morsleToSolvePanel.setLayout(new GridLayout(1, wordLength, 5, 0));
        for (int i = 0; i < wordLength; i++) {
            JTextField field = new JTextField();
            field.setHorizontalAlignment(SwingConstants.CENTER);
            field.setFont(field.getFont().deriveFont(24f));
            letterFields[i] = field;
            morsleToSolvePanel.add(field);
        }
        morsleToSolvePanel.revalidate();
        morsleToSolvePanel.repaint();
    }

    private void newGame() {
        String selectedDifficulty = (String) difficultyBox.getSelectedItem();
        if ("Short Words".equals(selectedDifficulty)) {
            currentWord = RandomWordGenerator.getRandomWordShort().toUpperCase();
        } else {
            currentWord = RandomWordGenerator.getRandomWordMedium().toUpperCase();
        }
        createLetterFields(currentWord.length());
        triesLeft = 10;
        lblTries.setText(String.valueOf(triesLeft));
        clearFields();
    }

    private void playSound() {
        String morse = Translator.textToMorse(currentWord);
        int wpm = wpmSlider.getValue();
        try {
            MorseAudio.playMorse(morse, wpm, 700, 0.8f);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Audio error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        for (JTextField tf : letterFields) {
            tf.setText("");
        }
        if (letterFields.length > 0) letterFields[0].requestFocus();
    }

    private void checkGuess() {
        StringBuilder guess = new StringBuilder();
        for (JTextField tf : letterFields) {
            String txt = tf.getText().trim();
            if (txt.isEmpty()) break;
            guess.append(txt.charAt(0));
        }
        String guessedWord = guess.toString().toUpperCase();
        if (guessedWord.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter some letters first!");
            return;
        }
        if (guessedWord.equals(currentWord)) {
            JOptionPane.showMessageDialog(this, "Correct! The word was " + currentWord);
            newGame();
        } else {
            triesLeft--;
            lblTries.setText(String.valueOf(triesLeft));
            if (triesLeft <= 0) {
                JOptionPane.showMessageDialog(this, "Game over! The word was " + currentWord);
                newGame();
            } else {
                JOptionPane.showMessageDialog(this, "Wrong guess! Tries left: " + triesLeft);
            }
        }
    }

    private JPanel createModernMainMenu(JTabbedPane tabbedPane) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, C4, getWidth(), getHeight(), C3);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.setColor(new Color(255, 255, 255, 20));
                for (int i = -getHeight(); i < getWidth(); i += 25) {
                    g2d.drawLine(i, 0, i + getHeight(), getHeight());
                }
                g2d.dispose();
            }
        };
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 40, 0);
        JLabel title = new JLabel("MORSE TOOL");
        title.setFont(new Font("SansSerif", Font.BOLD, 64));
        title.setForeground(C1);
        panel.add(title, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 20));

        JButton btnTranslator = createStyledButton("TRANSLATOR");
        JButton btnWhats = createStyledButton("WHAT IS MORSE?");
        JButton btnMinigame = createStyledButton("MINIGAME");

        btnTranslator.addActionListener(e -> tabbedPane.setSelectedIndex(2));
        btnWhats.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        btnMinigame.addActionListener(e -> tabbedPane.setSelectedIndex(3));

        buttonPanel.add(btnTranslator);
        buttonPanel.add(btnWhats);
        buttonPanel.add(btnMinigame);

        gbc.gridy = 1;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text) {
        	// Paintcomponent method siya na nagawa ng drawing yung lines
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(C3);
                } else if (getModel().isRollover()) {
                    g2.setColor(C1);
                } else {
                    g2.setColor(C2);
                }
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 35, 35));
                g2.setColor(C4);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btn.setPreferredSize(new Dimension(300, 60));
        btn.setFont(new Font("SansSerif", Font.BOLD, 22));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}