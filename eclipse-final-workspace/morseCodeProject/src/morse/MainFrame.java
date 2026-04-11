package morse;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel morsleToSolvePanel;
	private JTextField[] letterFields; // Dynamically change the letterFields using this array
	private JComboBox<String> difficultyBox;
	private String morsleToSolve = ""; // Storing what the user will try to solve here
	private int morseAudioWPM = 20;	// Default value for WPM
	private int morseAudioHertz = 440;	// Default value for Hertz
	private float morseAudioVolume = 0.2f;	// Default value for Volume
	private boolean  morsleToSolveAudioIsPlaying = false;
	private JButton btnNewWordButton; // To disable/re-enable this button across different interactions throughout the program
	private JButton btnPlaySoundButton; // To disable/re-enable this button across different interactions throughout the program
	private boolean[] isLetterSolved; // To check if each letter is correct
	private int attemptCounter = 0; // Counter for attempts
	private int solvedCounter = 0; // Counter for solved puzzles
	private boolean isPuzzleSolved = false; // Used so audio won't be playing twice when you guess correctly while a different audio was playing
	
	

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setSize(750, 500);
		setMinimumSize(new Dimension(750, 500));
		setLocationRelativeTo(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel MainMenu = new JPanel();
		tabbedPane.addTab("Main Menu", null, MainMenu, null);
		MainMenu.setLayout(new BorderLayout(0, 0));
		
		JPanel WMC = new JPanel();
		tabbedPane.addTab("What's Morse Code?", null, WMC, null);
		WMC.setLayout(new BorderLayout(0, 0));
		
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
		btnTtM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_Output.setText(Translator.textToMorse(textArea_Input.getText()));
			}
		});
		panelButtons.add(btnTtM);
		
		JButton btnMtT = new JButton("MorseCode to Text");
		btnMtT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_Output.setText(Translator.morseToText(textArea_Input.getText()));
			}
		});
		panelButtons.add(btnMtT);
		
		JButton btnCtC = new JButton("Copy to Clipboard");
		btnCtC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
		        String text = textArea_Output.getText();
		        
		        StringSelection selection = new StringSelection(text);
		        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
		        
		        JOptionPane.showMessageDialog(null, "Copied to clipboard!");
			}
		});
		panelButtons.add(btnCtC);
		
		JPanel Minigame = new JPanel();
		tabbedPane.addTab("Minigame", null, Minigame, null);
		GridBagLayout gbl_Minigame = new GridBagLayout();
		gbl_Minigame.columnWidths = new int[]{0, 0};
		gbl_Minigame.rowHeights = new int[]{0, 0, 0, 0};
		gbl_Minigame.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_Minigame.rowWeights = new double[]{0.2, 0.0, 1.0, Double.MIN_VALUE};
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

		morsleToSolve = RandomWordGenerator.getRandomWordShort().toUpperCase();
		createLetterFields(morsleToSolve.length()); // default display before game starts
		System.out.println("Selected word: " + morsleToSolve);
		System.out.println("Selected word in Morse code: " + Translator.textToMorse(morsleToSolve));
		
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
		gbl_panel_2.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblWPMLabel = new JLabel("WPM: " + morseAudioWPM);
		GridBagConstraints gbc_lblWPMLabel = new GridBagConstraints();
		gbc_lblWPMLabel.anchor = GridBagConstraints.WEST;
		gbc_lblWPMLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblWPMLabel.gridx = 1;
		gbc_lblWPMLabel.gridy = 0;
		panel_2.add(lblWPMLabel, gbc_lblWPMLabel);
		
		JSlider wpmSlider = new JSlider();
		wpmSlider.setSnapToTicks(true);
		wpmSlider.setMinorTickSpacing(5);
		wpmSlider.setPaintTicks(true);
		wpmSlider.setPaintLabels(true);
		wpmSlider.setMajorTickSpacing(10);
		wpmSlider.setMaximum(60);
		wpmSlider.setMinimum(5);
		wpmSlider.setValue(morseAudioWPM);
		GridBagConstraints gbc_wpmSlider = new GridBagConstraints();
		gbc_wpmSlider.insets = new Insets(0, 0, 5, 5);
		gbc_wpmSlider.gridx = 0;
		gbc_wpmSlider.gridy = 0;
		wpmSlider.addChangeListener(_ -> {
		    morseAudioWPM = wpmSlider.getValue(); // Get the value and assign to the class private variable
		    lblWPMLabel.setText("WPM: " + morseAudioWPM); // Update text
		});
		panel_2.add(wpmSlider, gbc_wpmSlider);
		
		JLabel lblHertzLabel = new JLabel("Tone/Hertz: " + morseAudioHertz);
		GridBagConstraints gbc_lblHertzLabel = new GridBagConstraints();
		gbc_lblHertzLabel.anchor = GridBagConstraints.WEST;
		gbc_lblHertzLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblHertzLabel.gridx = 1;
		gbc_lblHertzLabel.gridy = 1;
		panel_2.add(lblHertzLabel, gbc_lblHertzLabel);
		
		JSlider hertzSlider = new JSlider();
		hertzSlider.setMinorTickSpacing(50);
		hertzSlider.setSnapToTicks(true);
		hertzSlider.setMajorTickSpacing(100);
		hertzSlider.setPaintLabels(true);
		hertzSlider.setPaintTicks(true);
		hertzSlider.setMinimum(300);
		hertzSlider.setMaximum(800);
		hertzSlider.setValue(morseAudioHertz);
		GridBagConstraints gbc_hertzSlider = new GridBagConstraints();
		gbc_hertzSlider.insets = new Insets(0, 0, 5, 5);
		gbc_hertzSlider.gridx = 0;
		gbc_hertzSlider.gridy = 1;
		hertzSlider.addChangeListener(_ -> {
		    morseAudioHertz = hertzSlider.getValue(); // Get the value and assign to the class private variable
		    lblHertzLabel.setText("Tone/Hertz: " + morseAudioHertz); // Update text
		});
		panel_2.add(hertzSlider, gbc_hertzSlider);
		
		JLabel lblVolumeLabel = new JLabel("Volume: " + (int) Math.round(morseAudioVolume * 100));
		GridBagConstraints gbc_lblVolumeLabel = new GridBagConstraints();
		gbc_lblVolumeLabel.anchor = GridBagConstraints.WEST;
		gbc_lblVolumeLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblVolumeLabel.gridx = 1;
		gbc_lblVolumeLabel.gridy = 2;
		panel_2.add(lblVolumeLabel, gbc_lblVolumeLabel);
		
		JSlider volumeSlider = new JSlider();
		volumeSlider.setSnapToTicks(true);
		volumeSlider.setPaintTicks(true);
		volumeSlider.setMajorTickSpacing(10);
		GridBagConstraints gbc_volumeSlider = new GridBagConstraints();
		gbc_volumeSlider.insets = new Insets(0, 0, 0, 5);
		gbc_volumeSlider.gridx = 0;
		gbc_volumeSlider.gridy = 2;
		volumeSlider.setValue((int) Math.round(morseAudioVolume * 100));
		volumeSlider.addChangeListener(_ -> {
		    morseAudioVolume = volumeSlider.getValue() / 100.0f; // Convert 0-100 to 0.0-1.0, then get the value and assign to the class private variable
		    lblVolumeLabel.setText("Volume: " + volumeSlider.getValue()); // Update text
		});
		panel_2.add(volumeSlider, gbc_volumeSlider);
		
		JPanel RightSidePanel = new JPanel();
		FlowLayoutPanel.add(RightSidePanel);
		GridBagLayout gbl_RightSidePanel = new GridBagLayout();
		gbl_RightSidePanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_RightSidePanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_RightSidePanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_RightSidePanel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		RightSidePanel.setLayout(gbl_RightSidePanel);
				
		JLabel lblDifficultyLabel = new JLabel("Difficulty");
		GridBagConstraints gbc_lblDifficultyLabel = new GridBagConstraints();
		gbc_lblDifficultyLabel.anchor = GridBagConstraints.SOUTH;
		gbc_lblDifficultyLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblDifficultyLabel.gridx = 0;
		gbc_lblDifficultyLabel.gridy = 0;
		RightSidePanel.add(lblDifficultyLabel, gbc_lblDifficultyLabel);
		
		JLabel lblAttemptsLabel = new JLabel("Attempts: 0");
		GridBagConstraints gbc_lblAttemptsLabel = new GridBagConstraints();
		gbc_lblAttemptsLabel.anchor = GridBagConstraints.EAST;
		gbc_lblAttemptsLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblAttemptsLabel.gridx = 2;
		gbc_lblAttemptsLabel.gridy = 0;
		RightSidePanel.add(lblAttemptsLabel, gbc_lblAttemptsLabel);
		
		difficultyBox = new JComboBox<>();
		GridBagConstraints gbc_difficultyBox = new GridBagConstraints();
		gbc_difficultyBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_difficultyBox.insets = new Insets(0, 0, 5, 5);
		gbc_difficultyBox.gridx = 0;
		gbc_difficultyBox.gridy = 1;
		RightSidePanel.add(difficultyBox, gbc_difficultyBox);
		difficultyBox.setToolTipText("Select Difficulty");
		difficultyBox.addItem("Short Words");
		difficultyBox.addItem("Medium Words");
		
				
		JButton btnClearButton = new JButton("Clear");
		GridBagConstraints gbc_btnClearButton = new GridBagConstraints();
		gbc_btnClearButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClearButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnClearButton.gridx = 1;
		gbc_btnClearButton.gridy = 1;
		RightSidePanel.add(btnClearButton, gbc_btnClearButton);
		btnClearButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	// Loop for all letterFields
		        for (int i = 0; i < letterFields.length; i++)
		        {
		        	// Only clear if it not solved and not empty
		            if (letterFields[i] != null && !isLetterSolved[i])
		            {
		                letterFields[i].setText("");
		                letterFields[i].setBackground(Color.WHITE);
		            }
		        }
		    }
		});
		
		JLabel lblScoreLabel = new JLabel("Solved: 0");
		GridBagConstraints gbc_lblScoreLabel = new GridBagConstraints();
		gbc_lblScoreLabel.anchor = GridBagConstraints.EAST;
		gbc_lblScoreLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblScoreLabel.gridx = 2;
		gbc_lblScoreLabel.gridy = 1;
		RightSidePanel.add(lblScoreLabel, gbc_lblScoreLabel);
		
		btnPlaySoundButton = new JButton("Play Sound");
		GridBagConstraints gbc_btnPlaySoundButton = new GridBagConstraints();
		gbc_btnPlaySoundButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlaySoundButton.gridx = 1;
		gbc_btnPlaySoundButton.gridy = 2;
		RightSidePanel.add(btnPlaySoundButton, gbc_btnPlaySoundButton);
		
		JButton btnGuessButton = new JButton("Guess");
		btnGuessButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		    	// Convert the morsleToSolve into a character array
		    	final char[] textToCharArray = morsleToSolve.toUpperCase().trim().toCharArray();
		    	String currentLetterInTextField;
		    	
				// Loop for every letter in letterFields
			    for (int i = 0; i < letterFields.length; i++) {
		            // Skip already-solved positions
		            if (isLetterSolved[i])
	            	{
		            	continue;
	            	}

		            // Set the currentLetterInTextField
		            currentLetterInTextField = letterFields[i].getText().toUpperCase().trim();

		            // If currentLetterInTextField is empty then skip
		            if (currentLetterInTextField.isEmpty()) 
	            	{
	            		continue;
	            	}

		            // If correct letter and placement, 
		            // then update the corresponding boolean index value and change bg to green
		            if (textToCharArray[i] == currentLetterInTextField.charAt(0))
		            {
		                isLetterSolved[i] = true;
		                letterFields[i].setBackground(Color.decode("#48B3AF")); // Green
		            }
		            // If correct letter but wrong placement, 
		            // then change bg to yellow
		            else if (morsleToSolve.contains(currentLetterInTextField))
		            {
		                letterFields[i].setBackground(Color.decode("#F6FF99")); // Yellow
		            }
		            // Reset bg color if wrong
		            else
		            {
		                letterFields[i].setBackground(Color.WHITE);
		            }
			    }
			    
			    // Check if the Morsle is solved by checking each value in isLetterSolved array
			    boolean isMorsleSolved = true;
			    for (boolean b : isLetterSolved) 
			    {
			        if (!b) 
			        {
			            isMorsleSolved = false;
			            break;
			        }
			    }
			    
			    // Update attempt counter
			    attemptCounter++;
			    lblAttemptsLabel.setText("Attempts: " + attemptCounter);
			    
			    // If Morsle solved then
			    if (isMorsleSolved) {
			    	// Update solved counter
		            solvedCounter++;
		            isPuzzleSolved = true;
		            lblScoreLabel.setText("Solved: " + solvedCounter);
		            
		            // Wait for any currently-playing letter audio to finish, then play morsle audio
		            // We do this by scheduling on the EDT after current workers settle
		            SwingUtilities.invokeLater(() -> {
		                // Play morsle audio only if nothing is currently playing
		                if (!morsleToSolveAudioIsPlaying) {
		                    playMorsleAudio(btnGuessButton);
		                }

		                // Message pop up
		                JOptionPane.showMessageDialog(
		                    null,
		                    "Congratulations!\n\nThe word was: " + morsleToSolve +
		                    "\nMorsles Solved: " + solvedCounter +
		                    "\nTotal Attempts: " + attemptCounter,
		                    "Ohwen is proud of you.",
		                    JOptionPane.INFORMATION_MESSAGE
		                );

		                // Lock buttons after popup dismissed
		                btnGuessButton.setEnabled(false);
		                btnPlaySoundButton.setEnabled(false);
		                btnNewWordButton.setEnabled(true); // New Word button stays enabled
		            });
		        }
			}
		});
		
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_2.gridx = 2;
		gbc_btnNewButton_2.gridy = 2;
		RightSidePanel.add(btnGuessButton, gbc_btnNewButton_2);
		btnPlaySoundButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playMorsleAudio(btnGuessButton);
			}
		});
		
		btnNewWordButton = new JButton("New Word");
		GridBagConstraints gbc_btnNewWordButton = new GridBagConstraints();
		gbc_btnNewWordButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewWordButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewWordButton.gridx = 0;
		gbc_btnNewWordButton.gridy = 2;
		RightSidePanel.add(btnNewWordButton, gbc_btnNewWordButton);
		btnNewWordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Return if Morsle is playing
				if (morsleToSolveAudioIsPlaying) 
				{
					return;
				}

				// Get selected difficulty
				String selectedDifficulty = (String) difficultyBox.getSelectedItem();

				// Generate word depending on difficulty
				if ("Short Words".equals(selectedDifficulty)) 
				{
					morsleToSolve = RandomWordGenerator.getRandomWordShort().toUpperCase();
				} 
				else 
				{
					morsleToSolve = RandomWordGenerator.getRandomWordMedium().toUpperCase();
				}

				// Create the letter fields, also resets isLetterSolved
				createLetterFields(morsleToSolve.length());
				
				// Reset attempt text
			    lblAttemptsLabel.setText("Attempts: " + attemptCounter);
				
		        // Re-enable buttons that were disabled after a solve
				btnGuessButton.setEnabled(true);
		        btnPlaySoundButton.setEnabled(true);

				// Print in console what was selected
				System.out.println("Selected word: " + morsleToSolve);
				System.out.println("Selected word in Morse code: " + Translator.textToMorse(morsleToSolve));

			}
		});
				
		JPanel lettersMorseTabbedPanel = new JPanel();
		GridBagConstraints gbc_lettersMorseTabbedPanel = new GridBagConstraints();
		gbc_lettersMorseTabbedPanel.fill = GridBagConstraints.BOTH;
		gbc_lettersMorseTabbedPanel.gridx = 0;
		gbc_lettersMorseTabbedPanel.gridy = 2;
		Minigame.add(lettersMorseTabbedPanel, gbc_lettersMorseTabbedPanel);
		lettersMorseTabbedPanel.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPaneInputLetters = new JTabbedPane(JTabbedPane.TOP);
		lettersMorseTabbedPanel.add(tabbedPaneInputLetters);
		
		JPanel lettersPanel = new JPanel();
		lettersPanel.setLayout(new GridLayout(0, 3, 4, 4));

		// Track all letter buttons so we can disable/enable them all
		List<JButton> letterButtons = new ArrayList<>();

		MorseMap.textToMorse.entrySet().stream()
		    .filter(e -> Character.isLetter(e.getKey()))
		    .sorted(java.util.Map.Entry.comparingByKey())
		    .forEach(e -> {
		        String letter = String.valueOf(e.getKey());
		        String morse = e.getValue();
		        String html = String.format(
		            "<html><table width='160'><tr>" +
		            "<td align='left'><b>%s</b></td>" +
		            "<td align='right'>%s</td>" +
		            "</tr></table></html>",
		            letter, morse
		        );
		        JButton btn = new JButton(html);
		        btn.setFont(btn.getFont().deriveFont(20f));
		        btn.setHorizontalAlignment(SwingConstants.CENTER);
		        btn.setMargin(new Insets(2, 6, 2, 6));
		        btn.addActionListener(_ -> {
		            // Find an empty AND unsolved field and set that as the targetIndex
		            int targetIndex = -1;
		            for (int i = 0; i < letterFields.length; i++) 
		            {
		                if (!isLetterSolved[i] && letterFields[i].getText().trim().isEmpty()) 
		                {
		                    targetIndex = i;
		                    break;
		                }
		            }
		            
		            // If no empty+unsolved field, then clear all non-solved fields and start from first non-solved
		            if (targetIndex == -1) 
		            {
		                for (int i = 0; i < letterFields.length; i++) 
		                {
		                    if (!isLetterSolved[i]) 
		                    {
		                        letterFields[i].setText("");
		                        letterFields[i].setBackground(Color.WHITE); // reset color too
		                        if (targetIndex == -1) targetIndex = i;    // first non-solved becomes target
		                    }
		                }
		            }
		            
		            // If it reaches here and targetIndex is still -1,
		            // then all positions must be solved
		            if (targetIndex == -1) return;

		            // Make the targetIndex constant since it will be used by worker
		            final int insertAt = targetIndex;

		            // Disable all letter buttons while audio plays
		            for (JButton b : letterButtons) 
	            	{
	            		b.setEnabled(false);
	            	}
		            
		            // Capture for use in worker
		            final String morseToPlay = morse;
		            final String letterToShow = letter;

		            // Multithreading or basically multitasking using SwingWorker
		            // Play the audio in Background thread so the audio can play while the GUI in Event Dispatch Thread still works
		            new SwingWorker<Void, Void>() {
		                @Override
		                protected Void doInBackground() {
		                    // Show the letter immediately when audio starts
		                    SwingUtilities.invokeLater(() -> letterFields[insertAt].setText(letterToShow));
		                    
		                    try {
		                    	// Only play audio if the morseToSolve audio is not playing
		                    	if (morsleToSolveAudioIsPlaying == false)
		                    	{
		                    		// Disable the New Word and Play Sound buttons while the letter audio plays
		        		            btnNewWordButton.setEnabled(false);
		        		            btnPlaySoundButton.setEnabled(false);

		        		            // Play the Morse letter audio
			                        MorseAudio.playMorse(morseToPlay, morseAudioWPM, morseAudioHertz, morseAudioVolume);
		                    	}
		                    } catch (Exception ex) {
		                        ex.printStackTrace();
		                    }
		                    return null;
		                }

		                @Override
		                protected void done() {
		                	
				            for (JButton b : letterButtons) 
			            	{
			            		b.setEnabled(true);
			            	}
				            
				            // Update these if the morseToSolve audio is still not playing and if puzzle is not solved
				            if (!morsleToSolveAudioIsPlaying && !isPuzzleSolved)
				            {
				                btnNewWordButton.setEnabled(true);
				                btnPlaySoundButton.setEnabled(true);
				            }
		                }
		            }.execute();
		        });
		        
		        letterButtons.add(btn);
		        lettersPanel.add(btn);
		    });

		JScrollPane lettersScrollPane = new JScrollPane(lettersPanel);
		lettersScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPaneInputLetters.addTab("Letters", null, lettersScrollPane, null);
		
		contentPane.setPreferredSize(new Dimension(825, 700));	// ideal inside size
		pack();	// resize frame to fit contents
		setMinimumSize(getSize());	// current packed size becomes minimum
		setLocationRelativeTo(null);	// center on screen

	}

	// in morsleToSolvePanel, Clear the old letter boxes, create new letter boxes based on the word length, then refresh the panel
	private void createLetterFields(int wordLength) {
		// Remove the old boxes first
	    morsleToSolvePanel.removeAll();
	    
	    // Reset attempts and isPuzzleSolved
    	attemptCounter = 0;
    	isPuzzleSolved = false;
	    
	    // Store each letter in the array
	    letterFields = new JTextField[wordLength];

	    // Create boolean array to check answers
	    isLetterSolved = new boolean[wordLength];
	    
	    // Use GridLayout so fields share space equally
	    morsleToSolvePanel.setLayout(new GridLayout(1, wordLength, 5, 0));

	    for (int i = 0; i < wordLength; i++) {
	        JTextField field = new JTextField();
	        field.setHorizontalAlignment(SwingConstants.CENTER);
	        field.setFont(field.getFont().deriveFont(50f)); // big text
	        field.setEnabled(false);
	        field.setBackground(Color.WHITE);
	        field.setDisabledTextColor(field.getForeground());
	        
	        letterFields[i] = field;
	        morsleToSolvePanel.add(field);
	    }


	    // Refresh the UI after changing components
	    morsleToSolvePanel.revalidate();
	    morsleToSolvePanel.repaint();
	}

	private void playMorsleAudio(JButton btnGuessButton)
	{
		// Only play the morsle puzzle audio if the the morsleToSolve string has a word
		// or if letter audio isn't playing
        if (morsleToSolve.isEmpty() || !btnNewWordButton.isEnabled()) return;

        btnPlaySoundButton.setEnabled(false); // disable immediately
        btnNewWordButton.setEnabled(false); // disable the new word button
        morsleToSolveAudioIsPlaying = true; // morsleToSolveAudio is now playing

        // Get the Morse code translation and an array of the Morse split by spacing
        String morseCode = Translator.textToMorse(morsleToSolve);
        String[] morseCodeSplitBySpacing = morseCode.split("\\s+");
        
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
            	// Loop for every morse code letter
            	for (int i = 0; i < morseCodeSplitBySpacing.length; i++)
            	{
            		// Add a space back to each letter
            		morseCodeSplitBySpacing[i] = morseCodeSplitBySpacing[i] + " ";
            		
            		// Change letter field border to black when that current letter is being played
            		Border originalBorder = letterFields[i].getBorder();
            		letterFields[i].setBorder(new LineBorder(Color.BLACK, 5));
            		
            		// Play the current letter audio
            		MorseAudio.playMorse(morseCodeSplitBySpacing[i], morseAudioWPM, morseAudioHertz, morseAudioVolume);
            		
            		// Revert the letter field border back to its original
            		letterFields[i].setBorder(originalBorder);
            	}
            
                return null;
            }

            @Override
            protected void done() {
                try 
                {
                    get(); // this re-throws any exception from doInBackground
                } 
                catch (Exception ex) 
                {
                    JOptionPane.showMessageDialog(null, "Playback error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                morsleToSolveAudioIsPlaying = false; // morsleToSolveAudio finished playing
                
                // Only re-enable buttons if the puzzle wasn't just solved
                if (!isPuzzleSolved) 
                {
	                btnPlaySoundButton.setEnabled(true); // re-enable the new word button
	                btnNewWordButton.setEnabled(true); // re-enable regardless of success or failure
                }
            }
        }.execute();
	}
}
