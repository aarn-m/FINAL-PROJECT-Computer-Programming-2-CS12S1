package morse;

// ============================================================
// IMPORT STATEMENTS – mga library na ginamit (may paliwanag)
// ============================================================

import java.awt.EventQueue;        // Pina-prioritize ang GUI tasks para hindi mag-hang ang app
import java.awt.BorderLayout;      // Layout na naghahati sa hilaga, timog, silangan, kanluran, gitna
import java.awt.Dimension;         // Para sa lapad at taas ng components
import java.awt.GridBagLayout;     // Flexible layout, pwedeng mag-customize ng position
import java.awt.GridBagConstraints;// Settings para sa GridBagLayout (position, padding, etc.)
import java.awt.Insets;            // Margin ng components (top, left, bottom, right)
import java.awt.Toolkit;           // Access sa system clipboard at iba pang system tools
import java.awt.FlowLayout;        // Layout na pahalang ang pagkakasunod ng components
import java.awt.GridLayout;        // Layout na grid (rows at columns)
import java.awt.Color;             // Kulay (RGB)
import java.awt.Font;              // Font style (arial, bold, size, etc.)
import java.awt.GradientPaint;     // Gradient background (Combination ng dalawang kulay)
import java.awt.Graphics;          // Pag-drawing ng mga shapes at lines
import java.awt.Graphics2D;        // Mas advanced na drawing ( rounded corners)
import java.awt.RenderingHints;    // Pampakinis ng drawing 
import java.awt.Cursor;            // Nagpapalit ng itsura ng mouse pointer (hand cursor)
import java.awt.FontMetrics;       // Size ng text para ma-center
import java.awt.geom.RoundRectangle2D; // Rectangle na may rounded corners
import java.awt.datatransfer.StringSelection; // Para makapag-copy ng text sa clipboard

import javax.swing.*;              // Lahat ng Swing components (JFrame, JButton, etc.)
import javax.swing.border.Border; // Interface para sa border ng components
import javax.swing.border.EmptyBorder; // Border na walang laman (pang-padding lang)
import javax.swing.border.LineBorder;  // Border na solid line
import javax.swing.plaf.basic.BasicTabbedPaneUI; // Para i-customize ang itsura ng tabs
import java.awt.Rectangle;         // Pang-check kung nasa loob ng area ang mouse (hover)
import java.util.ArrayList;        // Dynamic array para sa listahan ng buttons
import java.util.List;             // Interface ng ArrayList

// ============================================================
// MAIN CLASS
// ============================================================
public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    
    // === MGA VARIABLE (state ng app) ===
    private JPanel contentPane;                    // Main container ng buong frame
    private JPanel morsleToSolvePanel;             // Lalagyan ng mga letter boxes (text fields)
    private JTextField[] letterFields;             // Array ng text fields – dito lumalabas ang mga letra
    private JComboBox<String> difficultyBox;       // Dropdown para sa hirap ng laro (Short/Medium Words)
    private String morsleToSolve = "";             // Salitang hahanapin ng player
    private int morseAudioWPM = 20;                // Bilis ng Morse code audio (words per minute)
    private int morseAudioHertz = 450;             // Frequency ng tunog (mas mataas = mas pino)
    private float morseAudioVolume = 0.5f;         // Volume (0.0 = tahimik, 1.0 = napakalakas)
    private boolean morsleToSolveAudioIsPlaying = false; // Flag kung may tumutugtog na audio sa minigame
    private JButton btnNewWordButton;              // Button para sa bagong salita
    private JButton btnPlaySoundButton;            // Button para i-play ang audio ng buong salita
    private boolean[] isLetterSolved;             
    private int attemptCounter = 0;                // pANG BILANG NG ATTEMPTS
    private int solvedCounter = 0;                 //  bilang ng mga nasolve na WORDS
    private boolean isPuzzleSolved = false;        // Flag kung tapos na ang puzzle
    private volatile boolean stopTranslatorAudioRequested = false; // Pampahinto ng audio sa Translator tab
    private volatile boolean stopMinigameAudio = false; // Pampahinto ng audio sa Minigame tab

    // === MGA KULAY (Oceanic theme) ===
    private static final Color OCEAN_LIGHT = new Color(0xa8dadc);  
    private static final Color OCEAN_MEDIUM = new Color(0x457b9d); 
    private static final Color OCEAN_DARK = new Color(0x1d3557);   
    private static final Color TEXT_LIGHT = new Color(0xf1faee);   
    private static final Color TEXT_DARK = new Color(0x1d3557);     

    // Iisang kulay para sa background ng tatlong tabs (deep blue)
    private static final Color BG_WHATS_MORSE = new Color(0x1d3557);
    private static final Color BG_TRANSLATOR = new Color(0x1d3557);
    private static final Color BG_MINIGAME = new Color(0x1d3557);

    // Mga kulay para sa puting panel at buttons
    private static final Color WHITE_PANEL = new Color(0xf0f0f0);      // Off-white
    private static final Color BUTTON_BG_WHITE = Color.WHITE;          // Puting background ng buttons
    private static final Color BUTTON_FG_DARK = new Color(0x1d3557);    // Madilim na text sa buttons
    private static final Color BUTTON_HOVER = new Color(0xe0e0e0);      // Kulay kapag naka-hover ang mouse
    private static final Color BUTTON_PRESSED = new Color(0xc0c0c0);    // Kulay kapag pinindot ang button

    // === MGA REFERENCE SA UI NG MINIGAME ===
    private JLabel lblAttempts;   // Label para sa bilang ng attempts
    private JLabel lblSolved;     // Label para sa bilang ng solved
    private JButton guessButton;  // Button para mag-guess

    // ============================================================
    // MAIN METHOD – dito nagsisimula ang app
    // ============================================================
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Startup error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // ============================================================
    // CONSTRUCTOR – dito binubuo ang frame
    // ============================================================
    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        setSize(1000, 750);
        setMinimumSize(new Dimension(900, 650));
        setLocationRelativeTo(null);

        showMainMenu();
    }

    // Ipinapakita ang main menu (walang sidebar tabs)
    private void showMainMenu() {
        contentPane.removeAll();
        JPanel mainMenu = createMainMenuPanel();
        contentPane.add(mainMenu, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();
    }

    // Gumagawa ng main menu panel na may gradient background at diagonal lines
    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, OCEAN_DARK, getWidth(), getHeight(), OCEAN_MEDIUM);
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
        title.setForeground(TEXT_LIGHT);
        panel.add(title, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 20));

        JButton btnWhats = createMainMenuButton("WHAT IS MORSE CODE?");
        JButton btnTranslator = createMainMenuButton("TRANSLATOR");
        JButton btnMinigame = createMainMenuButton("MINIGAME");

        btnWhats.addActionListener(e -> switchToSidebar(0));
        btnTranslator.addActionListener(e -> switchToSidebar(1));
        btnMinigame.addActionListener(e -> switchToSidebar(2));

        buttonPanel.add(btnWhats);
        buttonPanel.add(btnTranslator);
        buttonPanel.add(btnMinigame);

        gbc.gridy = 1;
        panel.add(buttonPanel, gbc);
        return panel;
    }

    private JButton createMainMenuButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(OCEAN_MEDIUM);
                } else if (getModel().isRollover()) {
                    g2.setColor(TEXT_LIGHT);
                } else {
                    g2.setColor(OCEAN_LIGHT);
                }
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 35, 35));
                g2.setColor(OCEAN_DARK);
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

    private void switchToSidebar(int selectedIndex) {
        contentPane.removeAll();

        JTabbedPane sideTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        sideTabbedPane.setBackground(OCEAN_LIGHT);
        sideTabbedPane.setForeground(OCEAN_DARK);
        sideTabbedPane.setFont(new Font("SansSerif", Font.BOLD, 22));
        sideTabbedPane.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                tabInsets = new Insets(20, 30, 20, 30);
            }
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg;
                if (isSelected) {
                    bg = OCEAN_DARK;
                } else {
                    java.awt.Point mouse = getMousePosition();
                    boolean hover = (mouse != null && new Rectangle(x, y, w, h).contains(mouse));
                    bg = hover ? OCEAN_MEDIUM : OCEAN_LIGHT;
                }
                g2.setColor(bg);
                g2.fill(new RoundRectangle2D.Double(x, y, w, h, 15, 15));
                g2.dispose();
            }
            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {}
            @Override
            protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {}
            @Override
            protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(isSelected ? TEXT_LIGHT : OCEAN_DARK);
                g2.setFont(font);
                FontMetrics fm = g2.getFontMetrics();
                int x = textRect.x + (textRect.width - fm.stringWidth(title)) / 2;
                int y = textRect.y + (textRect.height - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(title, x, y);
            }
        });

        JPanel sidebarBg = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, OCEAN_DARK, getWidth(), getHeight(), OCEAN_MEDIUM);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.setColor(new Color(255, 255, 255, 15));
                for (int i = -getHeight(); i < getWidth(); i += 25) {
                    g2d.drawLine(i, 0, i + getHeight(), getHeight());
                }
                g2d.dispose();
            }
        };
        sidebarBg.setLayout(new BorderLayout());
        sidebarBg.add(sideTabbedPane, BorderLayout.CENTER);

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, OCEAN_DARK, getWidth(), getHeight(), OCEAN_MEDIUM);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.setColor(new Color(255, 255, 255, 15));
                for (int i = -getHeight(); i < getWidth(); i += 25) {
                    g2d.drawLine(i, 0, i + getHeight(), getHeight());
                }
                g2d.dispose();
            }
        };
        topBar.setOpaque(false);
        JButton backButton = createSmallRoundedButton("← Back to Main Menu", OCEAN_DARK, TEXT_LIGHT);
        backButton.addActionListener(e -> showMainMenu());
        topBar.add(backButton);

        JPanel whatsMorsePanel = createWhatsMorsePanel();
        sideTabbedPane.addTab("What's Morse Code?", null, whatsMorsePanel, null);
        JPanel translatorPanel = createTranslatorPanel();
        sideTabbedPane.addTab("Translator", null, translatorPanel, null);
        JPanel minigamePanel = createMinigamePanel();
        sideTabbedPane.addTab("Minigame", null, minigamePanel, null);

        sideTabbedPane.setSelectedIndex(selectedIndex);

        contentPane.add(topBar, BorderLayout.NORTH);
        contentPane.add(sidebarBg, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();
    }

    private JButton createWhiteRoundedButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(BUTTON_PRESSED);
                } else if (getModel().isRollover()) {
                    g2.setColor(BUTTON_HOVER);
                } else {
                    g2.setColor(BUTTON_BG_WHITE);
                }
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                g2.setColor(BUTTON_FG_DARK);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 35));
        return btn;
    }

    private JButton createSmallRoundedButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.addPropertyChangeListener(evt -> btn.repaint());
        return btn;
    }

    private JPanel createLinesBackgroundPanel(Color bgColor) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(bgColor);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.setColor(new Color(255, 255, 255, 20));
                for (int i = -getHeight(); i < getWidth(); i += 25) {
                    g2d.drawLine(i, 0, i + getHeight(), getHeight());
                }
                g2d.dispose();
            }
        };
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        return panel;
    }

    // ============================================================
    // WHAT'S MORSE CODE? TAB – text only (no image)
    // ============================================================
    private JPanel createWhatsMorsePanel() {
        JPanel outer = createLinesBackgroundPanel(BG_WHATS_MORSE);

        RoundedPanel contentPanel = new RoundedPanel(WHITE_PANEL, 30);
        contentPanel.setLayout(new BorderLayout(10, 10));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Simple text label instead of image
        JLabel infoLabel = new JLabel("International Morse Code Chart", SwingConstants.CENTER);
        infoLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        infoLabel.setForeground(BUTTON_FG_DARK);
        infoLabel.setBorder(BorderFactory.createLineBorder(BUTTON_FG_DARK, 2));
        infoLabel.setPreferredSize(new Dimension(400, 200));

        // Full article text
        String articleText = "<html><body style='font-family: SansSerif; font-size: 18px; color: #1d3557;'>"
                + "<h2 style='border-bottom: 2px solid #1d3557; padding-bottom: 5px; font-size: 28px;'>What is Morse code?</h2>"
                + "<p>Morse code is a method of encoding words that was invented in the 19th Century as a way of transmitting messages quickly over long distances.</p>"
                + "<p>Morse code is adapted by Samuel Morse and Alfred Vail from a telegraph which is an early communication device which involved using electrical pulses to send messages via electrical wires. They devised a code now known as ‘Morse code’ and an electrical switch (used to manually control the signals) then sends signals through electrical wires like a telegraph in dots and dashes (also known as dits and dahs) to represent each letter of the alphabet and later included other symbols like digits and punctuations such as question marks and periods.</p>"
                + "<p>The electrical signals are sent by a sounder and received by a receiver which produces sounds or beeps that represent the dots and dashes through varying durations (quick for dots and prolonged for dashes) and consistent intervals or periods of time before the next sound is produced.</p>"
                + "<h3 style='border-bottom: 1px solid #1d3557; padding-bottom: 3px; font-size: 22px;'>How to learn Morse code?</h3>"
                + "<p>You could practice doing it now by tapping something out on your desk – e.g. the Morse code for ‘H-E-L-L-O’ is: …. / . / .-.. .-.. / ---  in which the dots are quick taps and the dashes are longer taps with slash serving as the interval.</p>"
                + "<p>A graphic showing the morse code alphabet, with dots and dashes corresponding to all letters of the alphabet, numbers 0-9, other characters and sample words:</p>"
                + "<h3 style='border-bottom: 1px solid #1d3557; padding-bottom: 3px; font-size: 22px;'>How is Morse Code used nowadays?</h3>"
                + "<p>As Morse code died and became old-fashioned in 1999 when replaced by satellite technology, it continued to be a standard mode of communication in maritime communication and became a secret language for seeking help when in danger now known as SOS Morse code.</p>"
                + "<h3 style='border-bottom: 1px solid #1d3557; padding-bottom: 3px; font-size: 22px;'>Five fun facts about Morse code</h3>"
                + "<ol><li>The first message transmitted by Samuel Morse in 1844 is reportedly ‘What hath God wrought?’</li>"
                + "<li>Queen Victoria sent the first transatlantic telegram via an underwater cable in 1858.</li>"
                + "<li>By 1900, following the invention of Morse code, The Eastern Telegraph Company operated a network of over 100,000 miles of undersea cables.</li>"
                + "<li>Professional operators can often spot who’s sending a message by the little quirks in their dits and dahs.</li>"
                + "<li>'I love you’ in Morse code is: .. / .-.. --- ...- . / -.-- --- ..-</li></ol>"
                + "</body></html>";

        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText(articleText);
        textPane.setEditable(false);
        textPane.setBackground(WHITE_PANEL);
        textPane.setOpaque(true);
        textPane.setBorder(null);

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(WHITE_PANEL);
        scrollPane.setOpaque(true);

        contentPanel.add(infoLabel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        outer.add(contentPanel, BorderLayout.CENTER);
        return outer;
    }

    // ============================================================
    // TRANSLATOR TAB
    // ============================================================
    private JPanel createTranslatorPanel() {
        JPanel outer = createLinesBackgroundPanel(BG_TRANSLATOR);

        RoundedPanel contentPanel = new RoundedPanel(WHITE_PANEL, 30);
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextArea inputArea = new JTextArea(8, 40);
        inputArea.setLineWrap(true);
        inputArea.setBackground(Color.WHITE);
        JScrollPane inputScroll = new JScrollPane(inputArea);
        inputScroll.setBorder(BorderFactory.createLineBorder(BUTTON_FG_DARK));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.4;
        contentPanel.add(inputScroll, gbc);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.insets = new Insets(2, 5, 2, 5);

        JButton btnTtM = createWhiteRoundedButton("Text to MorseCode");
        JButton btnMtT = createWhiteRoundedButton("MorseCode to Text");
        JButton btnCopy = createWhiteRoundedButton("Copy to Clipboard");
        JButton btnPlay = createWhiteRoundedButton("Play");
        JButton btnStop = createWhiteRoundedButton("Stop");

        JTextArea outputArea = new JTextArea(8, 40);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setBackground(Color.WHITE);
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setBorder(BorderFactory.createLineBorder(BUTTON_FG_DARK));

        btnTtM.addActionListener(e -> outputArea.setText(Translator.textToMorse(inputArea.getText())));
        btnMtT.addActionListener(e -> outputArea.setText(Translator.morseToText(inputArea.getText())));
        btnCopy.addActionListener(e -> {
            StringSelection ss = new StringSelection(outputArea.getText());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
            JOptionPane.showMessageDialog(outer, "Copied to clipboard!");
        });
        btnPlay.addActionListener(e -> {
            String morse = outputArea.getText();
            if (morse.isEmpty()) return;
            stopTranslatorAudioRequested = false;
            btnPlay.setEnabled(false);
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    MorseAudio.playMorse(morse, morseAudioWPM, morseAudioHertz, morseAudioVolume, () -> stopTranslatorAudioRequested);
                    return null;
                }
                @Override
                protected void done() {
                    btnPlay.setEnabled(true);
                }
            }.execute();
        });
        btnStop.addActionListener(e -> {
            stopTranslatorAudioRequested = true;
            btnPlay.setEnabled(true);
        });

        gbcButtons.gridx = 0; gbcButtons.gridy = 0;
        buttonPanel.add(btnTtM, gbcButtons);
        gbcButtons.gridx = 1;
        buttonPanel.add(btnMtT, gbcButtons);
        gbcButtons.gridx = 2;
        buttonPanel.add(btnCopy, gbcButtons);
        gbcButtons.gridx = 3;
        buttonPanel.add(btnPlay, gbcButtons);
        gbcButtons.gridx = 4;
        buttonPanel.add(btnStop, gbcButtons);

        JLabel lblPlayLabel = new JLabel("Play Audio");
        JLabel lblStopLabel = new JLabel("Stop Audio");
        lblPlayLabel.setForeground(BUTTON_FG_DARK);
        lblStopLabel.setForeground(BUTTON_FG_DARK);
        gbcButtons.gridx = 3; gbcButtons.gridy = 1;
        buttonPanel.add(lblPlayLabel, gbcButtons);
        gbcButtons.gridx = 4;
        buttonPanel.add(lblStopLabel, gbcButtons);

        gbc.gridy = 1;
        gbc.weighty = 0;
        contentPanel.add(buttonPanel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.4;
        contentPanel.add(outputScroll, gbc);

        outer.add(contentPanel, BorderLayout.CENTER);
        return outer;
    }

    // Helper: RoundedPanel
    private static class RoundedPanel extends JPanel {
        private Color bgColor;
        private int cornerRadius;
        public RoundedPanel(Color bgColor, int radius) {
            this.bgColor = bgColor;
            this.cornerRadius = radius;
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ============================================================
    // MINIGAME TAB
    // ============================================================
    private JPanel createMinigamePanel() {
        JPanel minigame = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(BG_MINIGAME);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.setColor(new Color(255, 255, 255, 20));
                for (int i = -getHeight(); i < getWidth(); i += 25) {
                    g2d.drawLine(i, 0, i + getHeight(), getHeight());
                }
                g2d.dispose();
            }
        };
        minigame.setOpaque(false);
        minigame.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.fill = GridBagConstraints.BOTH;
        mainGbc.weightx = 1.0;
        mainGbc.insets = new Insets(5, 5, 5, 5);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        morsleToSolvePanel = new JPanel();
        scrollPane.setViewportView(morsleToSolvePanel);
        try {
            morsleToSolve = RandomWordGenerator.getRandomWordShort().toUpperCase();
        } catch (Exception e) {
            morsleToSolve = "MORSE";
        }
        createLetterFields(morsleToSolve.length());
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.weighty = 0.35;
        minigame.add(scrollPane, mainGbc);

        JPanel middlePanel = new JPanel(new GridBagLayout());
        middlePanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel slidersPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        slidersPanel.setOpaque(false);

        // WPM slider
        RoundedPanel wpmWhiteBg = new RoundedPanel(Color.WHITE, 20);
        wpmWhiteBg.setLayout(new BorderLayout(5, 0));
        wpmWhiteBg.setBorder(new EmptyBorder(5, 10, 5, 10));
        JLabel wpmLabel = new JLabel("WPM: " + morseAudioWPM);
        wpmLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        wpmLabel.setForeground(BUTTON_FG_DARK);
        JSlider wpmSlider = new JSlider(5, 60, morseAudioWPM);
        wpmSlider.setMajorTickSpacing(10);
        wpmSlider.setMinorTickSpacing(5);
        wpmSlider.setPaintTicks(true);
        wpmSlider.setPaintLabels(true);
        wpmSlider.setSnapToTicks(true);
        java.util.Hashtable<Integer, JLabel> wpmLabels = new java.util.Hashtable<>();
        for (int i = 5; i <= 55; i += 10) {
            JLabel label = new JLabel(String.valueOf(i));
            label.setFont(new Font("SansSerif", Font.BOLD, 10));
            wpmLabels.put(i, label);
        }
        wpmSlider.setLabelTable(wpmLabels);
        wpmSlider.addChangeListener(e -> {
            morseAudioWPM = wpmSlider.getValue();
            wpmLabel.setText("WPM: " + morseAudioWPM);
        });
        wpmWhiteBg.add(wpmLabel, BorderLayout.WEST);
        wpmWhiteBg.add(wpmSlider, BorderLayout.CENTER);
        slidersPanel.add(wpmWhiteBg);

        // Tone/Hertz slider
        RoundedPanel hertzWhiteBg = new RoundedPanel(Color.WHITE, 20);
        hertzWhiteBg.setLayout(new BorderLayout(5, 0));
        hertzWhiteBg.setBorder(new EmptyBorder(5, 10, 5, 10));
        JLabel hertzLabel = new JLabel("Tone/Hertz: " + morseAudioHertz);
        hertzLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        hertzLabel.setForeground(BUTTON_FG_DARK);
        JSlider hertzSlider = new JSlider(300, 800, morseAudioHertz);
        hertzSlider.setMajorTickSpacing(100);
        hertzSlider.setMinorTickSpacing(50);
        hertzSlider.setPaintTicks(true);
        hertzSlider.setPaintLabels(true);
        hertzSlider.setSnapToTicks(true);
        hertzSlider.addChangeListener(e -> {
            morseAudioHertz = hertzSlider.getValue();
            hertzLabel.setText("Tone/Hertz: " + morseAudioHertz);
        });
        hertzWhiteBg.add(hertzLabel, BorderLayout.WEST);
        hertzWhiteBg.add(hertzSlider, BorderLayout.CENTER);
        slidersPanel.add(hertzWhiteBg);

        // Volume slider
        RoundedPanel volWhiteBg = new RoundedPanel(Color.WHITE, 20);
        volWhiteBg.setLayout(new BorderLayout(5, 0));
        volWhiteBg.setBorder(new EmptyBorder(5, 10, 5, 10));
        JLabel volLabel = new JLabel("Volume: " + (int)(morseAudioVolume * 100));
        volLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        volLabel.setForeground(BUTTON_FG_DARK);
        JSlider volSlider = new JSlider(0, 100, (int)(morseAudioVolume * 100));
        volSlider.setMajorTickSpacing(20);
        volSlider.setMinorTickSpacing(10);
        volSlider.setPaintTicks(true);
        volSlider.setPaintLabels(true);
        volSlider.addChangeListener(e -> {
            morseAudioVolume = volSlider.getValue() / 100f;
            volLabel.setText("Volume: " + volSlider.getValue());
        });
        volWhiteBg.add(volLabel, BorderLayout.WEST);
        volWhiteBg.add(volSlider, BorderLayout.CENTER);
        slidersPanel.add(volWhiteBg);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        middlePanel.add(slidersPanel, gbc);

        JPanel controlsPanel = new JPanel(new GridBagLayout());
        controlsPanel.setOpaque(false);
        GridBagConstraints cgbc = new GridBagConstraints();
        cgbc.insets = new Insets(5, 10, 5, 10);
        cgbc.fill = GridBagConstraints.HORIZONTAL;
        cgbc.gridx = 0;
        cgbc.gridy = 0;

        JPanel diffRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        diffRow.setOpaque(false);
        JLabel diffLabel = new JLabel("Difficulty");
        diffLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        diffLabel.setForeground(TEXT_LIGHT);
        diffRow.add(diffLabel);
        difficultyBox = new JComboBox<>(new String[]{"Short Words", "Medium Words"});
        diffRow.add(difficultyBox);
        JButton clearButton = createWhiteRoundedButton("Clear");
        clearButton.addActionListener(e -> clearFields());
        diffRow.add(clearButton);
        controlsPanel.add(diffRow, cgbc);

        cgbc.gridy++;
        JPanel newWordPlayRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        newWordPlayRow.setOpaque(false);
        btnNewWordButton = createWhiteRoundedButton("New Word");
        btnNewWordButton.addActionListener(e -> newGame());
        newWordPlayRow.add(btnNewWordButton);
        btnPlaySoundButton = createWhiteRoundedButton("Play Sound");
        btnPlaySoundButton.addActionListener(e -> playMorsleAudio());
        newWordPlayRow.add(btnPlaySoundButton);
        controlsPanel.add(newWordPlayRow, cgbc);

        cgbc.gridy++;
        JPanel attemptsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        attemptsPanel.setOpaque(false);
        JLabel attemptsText = new JLabel("Attempts:");
        attemptsText.setFont(new Font("SansSerif", Font.BOLD, 14));
        attemptsText.setForeground(TEXT_LIGHT);
        attemptsPanel.add(attemptsText);
        lblAttempts = new JLabel("0");
        lblAttempts.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblAttempts.setForeground(TEXT_LIGHT);
        attemptsPanel.add(lblAttempts);
        controlsPanel.add(attemptsPanel, cgbc);

        cgbc.gridy++;
        JPanel solvedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        solvedPanel.setOpaque(false);
        JLabel solvedText = new JLabel("Solved:");
        solvedText.setFont(new Font("SansSerif", Font.BOLD, 14));
        solvedText.setForeground(TEXT_LIGHT);
        solvedPanel.add(solvedText);
        lblSolved = new JLabel("0");
        lblSolved.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblSolved.setForeground(TEXT_LIGHT);
        solvedPanel.add(lblSolved);
        controlsPanel.add(solvedPanel, cgbc);

        cgbc.gridy++;
        JPanel guessRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        guessRow.setOpaque(false);
        guessButton = createWhiteRoundedButton("Guess");
        guessButton.addActionListener(e -> checkGuess());
        guessRow.add(guessButton);
        controlsPanel.add(guessRow, cgbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        middlePanel.add(controlsPanel, gbc);

        mainGbc.gridy = 1;
        mainGbc.weighty = 0.12;
        minigame.add(middlePanel, mainGbc);

        JPanel lettersMorsePanel = new JPanel(new BorderLayout());
        JTabbedPane lettersTab = new JTabbedPane();
        lettersMorsePanel.add(lettersTab);

        JPanel lettersPanel = new JPanel(new GridLayout(0, 3, 8, 8));
        lettersPanel.setBackground(BG_MINIGAME);
        List<JButton> letterButtons = new ArrayList<>();
        MorseMap.textToMorse.entrySet().stream()
            .filter(e -> Character.isLetter(e.getKey()))
            .sorted(java.util.Map.Entry.comparingByKey())
            .forEach(e -> {
                String letter = String.valueOf(e.getKey());
                String morse = e.getValue();
                JButton btn = createWhiteRoundedButton(letter + "  " + morse);
                btn.setFont(btn.getFont().deriveFont(24f));
                btn.setPreferredSize(new Dimension(140, 50));
                btn.addActionListener(ev -> {
                    int target = -1;
                    for (int i = 0; i < letterFields.length; i++) {
                        if (!isLetterSolved[i] && letterFields[i].getText().trim().isEmpty()) {
                            target = i;
                            break;
                        }
                    }
                    if (target == -1) {
                        for (int i = 0; i < letterFields.length; i++) {
                            if (!isLetterSolved[i]) {
                                letterFields[i].setText("");
                                letterFields[i].setBackground(Color.WHITE);
                                if (target == -1) target = i;
                            }
                        }
                    }
                    if (target == -1) return;
                    final int insertAt = target;
                    stopMinigameAudio = false;
                    for (JButton b : letterButtons) b.setEnabled(false);
                    new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() {
                            SwingUtilities.invokeLater(() -> letterFields[insertAt].setText(letter));
                            try {
                                if (!morsleToSolveAudioIsPlaying) {
                                    btnNewWordButton.setEnabled(false);
                                    btnPlaySoundButton.setEnabled(false);
                                    MorseAudio.playMorse(morse, morseAudioWPM, morseAudioHertz, morseAudioVolume, () -> stopMinigameAudio);
                                }
                            } catch (Exception ex) { ex.printStackTrace(); }
                            return null;
                        }
                        @Override
                        protected void done() {
                            for (JButton b : letterButtons) b.setEnabled(true);
                            if (!morsleToSolveAudioIsPlaying && !isPuzzleSolved) {
                                btnNewWordButton.setEnabled(true);
                                btnPlaySoundButton.setEnabled(true);
                            }
                        }
                    }.execute();
                });
                letterButtons.add(btn);
                lettersPanel.add(btn);
            });
        JScrollPane lettersScroll = new JScrollPane(lettersPanel);
        lettersScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        lettersScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        lettersTab.addTab("Letters", lettersScroll);

        mainGbc.gridy = 2;
        mainGbc.weighty = 0.53;
        minigame.add(lettersMorsePanel, mainGbc);

        return minigame;
    }

    private void createLetterFields(int wordLength) {
        morsleToSolvePanel.removeAll();
        attemptCounter = 0;
        isPuzzleSolved = false;
        letterFields = new JTextField[wordLength];
        isLetterSolved = new boolean[wordLength];
        
        GridBagLayout gbl = new GridBagLayout();
        morsleToSolvePanel.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridheight = 1;
        
        for (int i = 0; i < wordLength; i++) {
            JTextField field = new JTextField();
            field.setHorizontalAlignment(SwingConstants.CENTER);
            field.setFont(field.getFont().deriveFont(70f));
            field.setEnabled(false);
            field.setBackground(Color.WHITE);
            letterFields[i] = field;
            gbc.gridx = i;
            morsleToSolvePanel.add(field, gbc);
        }
        morsleToSolvePanel.revalidate();
        morsleToSolvePanel.repaint();
    }

    private void newGame() {
        String selected = (String) difficultyBox.getSelectedItem();
        try {
            if ("Short Words".equals(selected)) {
                morsleToSolve = RandomWordGenerator.getRandomWordShort().toUpperCase();
            } else {
                morsleToSolve = RandomWordGenerator.getRandomWordMedium().toUpperCase();
            }
        } catch (Exception e) {
            morsleToSolve = "MORSE";
        }
        createLetterFields(morsleToSolve.length());
        if (lblAttempts != null) lblAttempts.setText("0");
        if (lblSolved != null) lblSolved.setText(String.valueOf(solvedCounter));
        if (btnPlaySoundButton != null) btnPlaySoundButton.setEnabled(true);
        if (btnNewWordButton != null) btnNewWordButton.setEnabled(true);
        if (guessButton != null) guessButton.setEnabled(true);
    }

    private void clearFields() {
        for (int i = 0; i < letterFields.length; i++) {
            if (!isLetterSolved[i]) {
                letterFields[i].setText("");
                letterFields[i].setBackground(Color.WHITE);
            }
        }
    }

    private void checkGuess() {
        char[] target = morsleToSolve.toCharArray();
        for (int i = 0; i < letterFields.length; i++) {
            if (isLetterSolved[i]) continue;
            String text = letterFields[i].getText().trim().toUpperCase();
            if (text.isEmpty()) continue;
            char guess = text.charAt(0);
            if (guess == target[i]) {
                isLetterSolved[i] = true;
                letterFields[i].setBackground(Color.decode("#48B3AF"));
            } else if (morsleToSolve.contains(String.valueOf(guess))) {
                letterFields[i].setBackground(Color.decode("#F6FF99"));
            } else {
                letterFields[i].setBackground(Color.WHITE);
            }
        }
        boolean allSolved = true;
        for (boolean b : isLetterSolved) if (!b) { allSolved = false; break; }
        attemptCounter++;
        lblAttempts.setText(String.valueOf(attemptCounter));
        if (allSolved) {
            solvedCounter++;
            isPuzzleSolved = true;
            lblSolved.setText(String.valueOf(solvedCounter));

            if (!morsleToSolveAudioIsPlaying) {
                playMorsleAudio();
            }

            JOptionPane.showMessageDialog(this,
                "Congratulations!\n\nThe word was: " + morsleToSolve +
                "\nMorsles Solved: " + solvedCounter +
                "\nTotal Attempts: " + attemptCounter,
                "Ohwen is proud of you.",
                JOptionPane.INFORMATION_MESSAGE);

            guessButton.setEnabled(false);
            btnPlaySoundButton.setEnabled(false);
            btnNewWordButton.setEnabled(true);
        }
    }

    private void playMorsleAudio() {
        if (morsleToSolve.isEmpty()) return;
        stopMinigameAudio = false;
        btnPlaySoundButton.setEnabled(false);
        btnNewWordButton.setEnabled(false);
        morsleToSolveAudioIsPlaying = true;
        String morseCode = Translator.textToMorse(morsleToSolve);
        String[] letters = morseCode.split("\\s+");
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i < letters.length; i++) {
                    String letterMorse = letters[i] + " ";
                    Border original = letterFields[i].getBorder();
                    letterFields[i].setBorder(new LineBorder(Color.BLACK, 5));
                    MorseAudio.playMorse(letterMorse, morseAudioWPM, morseAudioHertz, morseAudioVolume, () -> stopMinigameAudio);
                    letterFields[i].setBorder(original);
                }
                return null;
            }
            @Override
            protected void done() {
                morsleToSolveAudioIsPlaying = false;
                if (!isPuzzleSolved) {
                    btnPlaySoundButton.setEnabled(true);
                    btnNewWordButton.setEnabled(true);
                }
            }
        }.execute();
    }
}
