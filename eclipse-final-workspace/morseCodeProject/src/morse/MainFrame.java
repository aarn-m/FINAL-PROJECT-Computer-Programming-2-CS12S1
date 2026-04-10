package morse;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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
import javax.swing.SwingWorker;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel morsleToSolvePanel;
	private JTextField[] letterFields;
	private JComboBox<String> difficultyBox;
	private String morsleToSolve = "";

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
		        String text = textArea_Output.getText(); // get text from JTextPane
		        
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

		createLetterFields(5); // default display before game starts
		
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
		gbl_panel_2.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		difficultyBox = new JComboBox<>();
		difficultyBox.setToolTipText("Select Difficulty");
		difficultyBox.addItem("Short Words");
		difficultyBox.addItem("Medium Words");
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 0;
		panel_2.add(difficultyBox, gbc_comboBox_1);
		
		JSlider wpmSlider = new JSlider();
		GridBagConstraints gbc_wpmSlider = new GridBagConstraints();
		gbc_wpmSlider.insets = new Insets(0, 0, 5, 0);
		gbc_wpmSlider.gridx = 2;
		gbc_wpmSlider.gridy = 0;
		panel_2.add(wpmSlider, gbc_wpmSlider);
		
		JLabel lblTriesRemainingLabel = new JLabel("Difficulty");
		GridBagConstraints gbc_lblTriesRemainingLabel = new GridBagConstraints();
		gbc_lblTriesRemainingLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblTriesRemainingLabel.gridx = 1;
		gbc_lblTriesRemainingLabel.gridy = 1;
		panel_2.add(lblTriesRemainingLabel, gbc_lblTriesRemainingLabel);
		
		JLabel lblwpmLabel = new JLabel("WPM: 20");
		GridBagConstraints gbc_lblwpmLabel = new GridBagConstraints();
		gbc_lblwpmLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblwpmLabel.gridx = 2;
		gbc_lblwpmLabel.gridy = 1;
		panel_2.add(lblwpmLabel, gbc_lblwpmLabel);
		
		JButton btnNewWordButton = new JButton("New Word");
		btnNewWordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedDifficulty = (String) difficultyBox.getSelectedItem();

				if ("Short Words".equals(selectedDifficulty)) {
					morsleToSolve = RandomWordGenerator.getRandomWordShort();
				} else {
					morsleToSolve = RandomWordGenerator.getRandomWordMedium();
				}

				createLetterFields(morsleToSolve.length());

				System.out.println("Selected word: " + morsleToSolve);
			}
		});
		GridBagConstraints gbc_btnNewWordButton = new GridBagConstraints();
		gbc_btnNewWordButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewWordButton.gridx = 1;
		gbc_btnNewWordButton.gridy = 2;
		panel_2.add(btnNewWordButton, gbc_btnNewWordButton);
		
		JButton btnPlaySoundButton = new JButton("Play Sound");
		btnPlaySoundButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        if (morsleToSolve.isEmpty()) return;

		        btnPlaySoundButton.setEnabled(false); // disable immediately

		        String morseCode = Translator.textToMorse(morsleToSolve); // ← translate first!

		        new SwingWorker<Void, Void>() {
		            @Override
		            protected Void doInBackground() throws Exception {
		                MorseAudio.playMorse(morseCode, 20, 700, 0.8f);
		                return null;
		            }

		            @Override
		            protected void done() {
		                btnPlaySoundButton.setEnabled(true); // re-enable when done
		            }
		        }.execute();
			}
		});
		GridBagConstraints gbc_btnPlaySoundButton = new GridBagConstraints();
		gbc_btnPlaySoundButton.gridx = 2;
		gbc_btnPlaySoundButton.gridy = 2;
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
		btnClearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    for (int i = 0; i < letterFields.length; i++) {
			        if (letterFields[i] != null) {
			        	letterFields[i].setText(""); // Clears the text
			        }
			    }
			}
		});
		GridBagConstraints gbc_btnClearButton = new GridBagConstraints();
		gbc_btnClearButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnClearButton.gridx = 0;
		gbc_btnClearButton.gridy = 0;
		RightSidePanel.add(btnClearButton, gbc_btnClearButton);
		
		JButton btnNewButton_2 = new JButton("Guess");
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.gridx = 1;
		gbc_btnNewButton_2.gridy = 0;
		RightSidePanel.add(btnNewButton_2, gbc_btnNewButton_2);
		
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

		// Sort the letters A-Z so they appear in order
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
		        btn.addActionListener(action -> {
		            // append the morse code to whatever input you're building
		            // e.g. currentMorseInput += morse;
		        });
		        lettersPanel.add(btn);
		    });

		JScrollPane lettersScrollPane = new JScrollPane(lettersPanel);
		lettersScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPaneInputLetters.addTab("Letters", null, lettersScrollPane, null);
		
		
		
		
		
		contentPane.setPreferredSize(new Dimension(750, 500));	// ideal inside size
		pack();	// resize frame to fit contents
		setMinimumSize(getSize());	// current packed size becomes minimum
		setLocationRelativeTo(null);	// center on screen

	}

	// in morsleToSolvePanel, Clear the old letter boxes, create new letter boxes based on the word length, then refresh the panel
	private void createLetterFields(int wordLength) {
		// Remove the old boxes first
	    morsleToSolvePanel.removeAll();
	    
	    // Store each letter in the array
	    letterFields = new JTextField[wordLength];

	    // Use GridLayout so fields share space equally — no fixed size needed
	    morsleToSolvePanel.setLayout(new GridLayout(1, wordLength, 5, 0));

	    for (int i = 0; i < wordLength; i++) {
	        JTextField field = new JTextField();
	        field.setHorizontalAlignment(SwingConstants.CENTER);
	        field.setFont(field.getFont().deriveFont(20f)); // bigger text instead of bigger box

	        letterFields[i] = field;
	        morsleToSolvePanel.add(field);
	    }


	    // Refresh the UI after changing components
	    morsleToSolvePanel.revalidate();
	    morsleToSolvePanel.repaint();
	}

}
