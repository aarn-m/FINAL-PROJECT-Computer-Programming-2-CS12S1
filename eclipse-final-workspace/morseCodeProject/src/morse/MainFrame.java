package morse;

import java.awt.EventQueue;
import java.awt.FlowLayout;

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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
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
import javax.swing.JScrollBar;
import java.awt.Color;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JToolBar;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import net.miginfocom.swing.MigLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import com.jgoodies.forms.layout.FormSpecs;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel morsleToSolvePanel;
	private JTextField[] letterFields; // Dynamically change the letterFields using this array
	private JComboBox<String> difficultyBox;
	private String morsleToSolve = ""; // Storing what the user will try to solve here
	private int morseAudioWPM = 20;	// Default value for WPM
	private int morseAudioHertz = 450;	// Default value for Hertz
	private float morseAudioVolume = 0.5f;	// Default value for Volume
	private boolean  morsleToSolveAudioIsPlaying = false;
	private JButton btnNewWordButton; // To disable/re-enable this button across different interactions throughout the program
	private JButton btnPlaySoundButton; // To disable/re-enable this button across different interactions throughout the program
	private boolean[] isLetterSolved; // To check if each letter is correct
	private int attemptCounter = 0; // Counter for attempts
	private int solvedCounter = 0; // Counter for solved puzzles
	private boolean isPuzzleSolved = false; // Used so audio won't be playing twice when you guess correctly while a different audio was playing
	private volatile boolean stopTranslatorAudioRequested = false; // Flag to stop audio playback in translator tab
	private volatile boolean stopMinigameAudio = false; // Minigame tab stop flag

	private final JComboBox comboBox = new JComboBox();
	private final Action action = new SwingAction();

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
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(29, 53, 87));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setSize(766, 625);
		setMinimumSize(new Dimension(750, 500));
		setLocationRelativeTo(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(69, 123, 157));
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel MainMenu = new JPanel();
		MainMenu.setBackground(new Color(241, 250, 238));
		tabbedPane.addTab("Main Menu", null, MainMenu, null);
		MainMenu.setLayout(new BorderLayout(0, 0));
		
		JPanel WMC = new JPanel();
		tabbedPane.addTab("What's Morse Code?", null, WMC, null);
		WMC.setLayout(new BorderLayout(0, 0));
		
    JPanel wmcTitlePanel = new JPanel();
		wmcTitlePanel.setBackground(new Color(69, 123, 157));
		WMC.add(wmcTitlePanel, BorderLayout.NORTH);
		wmcTitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblNewLabel_5 = new JLabel("Did you know?");
		lblNewLabel_5.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		lblNewLabel_5.setForeground(new Color(241, 250, 238));
		lblNewLabel_5.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.LEFT);
		wmcTitlePanel.add(lblNewLabel_5);
		
		JPanel wmcFooter = new JPanel();
		wmcFooter.setBackground(new Color(69, 123, 157));
		WMC.add(wmcFooter, BorderLayout.SOUTH);
		
		JPanel wmcContentPanel = new JPanel();
		wmcContentPanel.setBackground(new Color(241, 250, 238));
		FlowLayout flowLayout = (FlowLayout) wmcContentPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		WMC.add(wmcContentPanel, BorderLayout.CENTER);
		
		JScrollPane wmcScrollPane = new JScrollPane();
		wmcContentPanel.add(wmcScrollPane);
		
		JLabel wmcContentText = new JLabel("Lorem ipsum..");
		wmcContentText.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		wmcContentText.setForeground(new Color(29, 53, 87));
		wmcContentText.setBackground(new Color(240, 240, 240));
		wmcContentText.setVerticalAlignment(SwingConstants.TOP);
		wmcContentText.setHorizontalAlignment(SwingConstants.LEFT);
		wmcContentPanel.add(wmcContentText);

		contentPane.setPreferredSize(new Dimension(750, 500));
		
		JPanel sideNavBar = new JPanel();
		sideNavBar.setForeground(new Color(0, 38, 66));
		sideNavBar.setBackground(new Color(29, 53, 87));
		contentPane.add(sideNavBar, BorderLayout.WEST);
		sideNavBar.setLayout(new BorderLayout(0, 0));
		
		JPanel menubtns = new JPanel();
		menubtns.setBorder(null);
		menubtns.setBackground(new Color(29, 53, 87));
		sideNavBar.add(menubtns, BorderLayout.WEST);
		menubtns.setLayout(new GridLayout(10, 1, 0, 0));
		
		JButton menubtnMini_1 = new JButton("Minigame");
		menubtnMini_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(1);
			}
		});
		menubtnMini_1.setHorizontalAlignment(SwingConstants.LEFT);
		menubtnMini_1.setForeground(new Color(0, 38, 66));
		menubtnMini_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		menubtnMini_1.setBackground(new Color(255, 255, 255));
		menubtns.add(menubtnMini_1);
		
		JButton menubtnTran_1 = new JButton("Translator");
		menubtnTran_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(2);
			}
		});
		menubtnTran_1.setHorizontalAlignment(SwingConstants.LEFT);
		menubtnTran_1.setForeground(new Color(0, 38, 66));
		menubtnTran_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		menubtnTran_1.setBackground(new Color(255, 255, 255));
		menubtns.add(menubtnTran_1);
		
		JButton menubtnWmc_1 = new JButton("What's Morse Code?");
		menubtnWmc_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(3);
			}
		});
		menubtnWmc_1.setHorizontalAlignment(SwingConstants.LEFT);
		menubtnWmc_1.setForeground(new Color(0, 38, 66));
		menubtnWmc_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		menubtnWmc_1.setBackground(new Color(255, 255, 255));
		menubtns.add(menubtnWmc_1);
		
		JPanel menuLogo = new JPanel();
		menuLogo.setBackground(new Color(0, 38, 66));
		sideNavBar.add(menuLogo, BorderLayout.NORTH);
		
		JLabel lblNewLabel_4 = new JLabel("App ni Ohwen");
		lblNewLabel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tabbedPane.setSelectedIndex(0);
			}
		});
		lblNewLabel_4.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_4.setForeground(new Color(229, 149, 0));
		menuLogo.add(lblNewLabel_4);
    
		JPanel TranslatorPanel = new JPanel();
		tabbedPane.addTab("Translator", null, TranslatorPanel, null);
		TranslatorPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel gridBagLayoutPanel = new JPanel();
		TranslatorPanel.add(gridBagLayoutPanel, BorderLayout.CENTER);
		GridBagLayout gbl_gridBagLayoutPanel = new GridBagLayout();
		gbl_gridBagLayoutPanel.columnWidths = new int[]{0, 0};
		gbl_gridBagLayoutPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_gridBagLayoutPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_gridBagLayoutPanel.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayoutPanel.setLayout(gbl_gridBagLayoutPanel);
		
		JLabel lblInput = new JLabel("Input");
		GridBagConstraints gbc_lblInput = new GridBagConstraints();
		gbc_lblInput.insets = new Insets(0, 0, 5, 0);
		gbc_lblInput.gridx = 0;
		gbc_lblInput.gridy = 0;
		gridBagLayoutPanel.add(lblInput, gbc_lblInput);
		
		JScrollPane scrollPanelInput = new JScrollPane();
		GridBagConstraints gbc_scrollPanelInput = new GridBagConstraints();
		gbc_scrollPanelInput.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPanelInput.fill = GridBagConstraints.BOTH;
		gbc_scrollPanelInput.gridx = 0;
		gbc_scrollPanelInput.gridy = 1;
		gridBagLayoutPanel.add(scrollPanelInput, gbc_scrollPanelInput);
		
		JTextArea textArea_Input = new JTextArea();
		textArea_Input.setFont(textArea_Input.getFont().deriveFont(14.0f)); // Change font size of text input area while still keeping the font
		textArea_Input.setMargin(new Insets(10, 20, 10, 20)); // Change margin text input area
		textArea_Input.setLineWrap(true);
		scrollPanelInput.setViewportView(textArea_Input);
		
		JPanel panelButtons = new JPanel();
		GridBagConstraints gbc_panelButtons = new GridBagConstraints();
		gbc_panelButtons.fill = GridBagConstraints.VERTICAL;
		gbc_panelButtons.insets = new Insets(0, 0, 5, 0);
		gbc_panelButtons.gridx = 0;
		gbc_panelButtons.gridy = 2;
		gridBagLayoutPanel.add(panelButtons, gbc_panelButtons);
		
		JLabel lblOutput = new JLabel("Output");
		GridBagConstraints gbc_lblOutput = new GridBagConstraints();
		gbc_lblOutput.insets = new Insets(0, 0, 5, 0);
		gbc_lblOutput.gridx = 0;
		gbc_lblOutput.gridy = 3;
		gridBagLayoutPanel.add(lblOutput, gbc_lblOutput);
		
		JScrollPane scrollPanelOutput = new JScrollPane();
		GridBagConstraints gbc_scrollPanelOutput = new GridBagConstraints();
		gbc_scrollPanelOutput.fill = GridBagConstraints.BOTH;
		gbc_scrollPanelOutput.gridx = 0;
		gbc_scrollPanelOutput.gridy = 4;
		gridBagLayoutPanel.add(scrollPanelOutput, gbc_scrollPanelOutput);
		
		JTextArea textArea_Output = new JTextArea();
		textArea_Output.setFont(textArea_Input.getFont().deriveFont(14.0f)); // Change font size of text input area while still keeping the font
		textArea_Output.setMargin(new Insets(10, 20, 10, 20)); // Change margin text input area
		textArea_Output.setEditable(false);
		textArea_Output.setLineWrap(true);
		scrollPanelOutput.setViewportView(textArea_Output);
		GridBagLayout gbl_panelButtons = new GridBagLayout();
		gbl_panelButtons.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panelButtons.rowHeights = new int[]{0, 0, 0};
		gbl_panelButtons.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelButtons.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelButtons.setLayout(gbl_panelButtons);
		
		JButton btnTtM = new JButton("Text to MorseCode");
		btnTtM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_Output.setText(Translator.textToMorse(textArea_Input.getText()));
			}
		});
		GridBagConstraints gbc_btnTtM = new GridBagConstraints();
		gbc_btnTtM.anchor = GridBagConstraints.BASELINE;
		gbc_btnTtM.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnTtM.insets = new Insets(0, 0, 0, 5);
		gbc_btnTtM.gridx = 0;
		gbc_btnTtM.gridy = 0;
		panelButtons.add(btnTtM, gbc_btnTtM);
		
		JButton btnMtT = new JButton("MorseCode to Text");
		btnMtT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_Output.setText(Translator.morseToText(textArea_Input.getText()));
			}
		});
		GridBagConstraints gbc_btnMtT = new GridBagConstraints();
		gbc_btnMtT.anchor = GridBagConstraints.BASELINE;
		gbc_btnMtT.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnMtT.insets = new Insets(0, 0, 0, 5);
		gbc_btnMtT.gridx = 1;
		gbc_btnMtT.gridy = 0;
		panelButtons.add(btnMtT, gbc_btnMtT);
		
		JButton btnCtC = new JButton("Copy to Clipboard");
		btnCtC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
		        String text = textArea_Output.getText();
		        
		        StringSelection selection = new StringSelection(text);
		        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
		        
		        JOptionPane.showMessageDialog(null, "Copied to clipboard!");
			}
		});
		GridBagConstraints gbc_btnCtC = new GridBagConstraints();
		gbc_btnCtC.anchor = GridBagConstraints.BASELINE;
		gbc_btnCtC.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCtC.insets = new Insets(0, 0, 0, 5);
		gbc_btnCtC.gridx = 2;
		gbc_btnCtC.gridy = 0;
		panelButtons.add(btnCtC, gbc_btnCtC);
		
		JButton btnPlayAudio = new JButton("\u25B6 Play");
		btnPlayAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        // Reset stop flag and disable play button before starting
		        stopTranslatorAudioRequested = false;
		        
		        // Disable the play audio button
				btnPlayAudio.setEnabled(false);
				
				// Play audio in background thread
				new SwingWorker<Void, Void>() 
				{
				    @Override
				    protected Void doInBackground() 
				    {
				        try 
				        {
				        	MorseAudio.playMorse(textArea_Output.getText(), morseAudioWPM, morseAudioHertz, morseAudioVolume, () -> stopTranslatorAudioRequested);
				        } 
				        catch (Exception ex) 
				        {
				            ex.printStackTrace();
				        }
				        return null;
				    }

				    @Override
				    protected void done() 
				    {
				        // Re-enable the btnPlayAudio after playing
				        btnPlayAudio.setEnabled(true);
				    }
				}.execute();
				
                }
			}
		);
		GridBagConstraints gbc_btnPlayAudio = new GridBagConstraints();
		gbc_btnPlayAudio.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPlayAudio.anchor = GridBagConstraints.BASELINE;
		gbc_btnPlayAudio.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlayAudio.gridx = 3;
		gbc_btnPlayAudio.gridy = 0;
		panelButtons.add(btnPlayAudio, gbc_btnPlayAudio);
		
		JButton btnStopAudio = new JButton("\u23F9 Stop");
		btnStopAudio.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) 
		    {
		        // Signal the audio to stop
		        stopTranslatorAudioRequested = true;
		        btnPlayAudio.setEnabled(true);
		    }
		});
		GridBagConstraints gbc_btnStopAudio = new GridBagConstraints();
		gbc_btnStopAudio.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnStopAudio.anchor = GridBagConstraints.BASELINE;
		gbc_btnStopAudio.insets = new Insets(0, 0, 5, 0);
		gbc_btnStopAudio.gridx = 4;
		gbc_btnStopAudio.gridy = 0;
		panelButtons.add(btnStopAudio, gbc_btnStopAudio);
		
		JLabel lblPlayAudioLabel = new JLabel("Play Audio");
		GridBagConstraints gbc_lblPlayAudioLabel = new GridBagConstraints();
		gbc_lblPlayAudioLabel.anchor = GridBagConstraints.NORTH;
		gbc_lblPlayAudioLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblPlayAudioLabel.gridx = 3;
		gbc_lblPlayAudioLabel.gridy = 1;
		panelButtons.add(lblPlayAudioLabel, gbc_lblPlayAudioLabel);
		
		JLabel lblStopAudioLabel = new JLabel("Stop Audio");
		GridBagConstraints gbc_lblStopAudioLabel = new GridBagConstraints();
		gbc_lblStopAudioLabel.anchor = GridBagConstraints.NORTH;
		gbc_lblStopAudioLabel.gridx = 4;
		gbc_lblStopAudioLabel.gridy = 1;
		panelButtons.add(lblStopAudioLabel, gbc_lblStopAudioLabel);
		JPanel mainFooterPanel = new JPanel();
		mainFooterPanel.setBackground(new Color(69, 123, 157));
		MainMenu.add(mainFooterPanel, BorderLayout.SOUTH);
		mainFooterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel mainFootercontent = new JPanel();
		mainFootercontent.setBackground(new Color(69, 123, 157));
		mainFooterPanel.add(mainFootercontent);
		mainFootercontent.setLayout(new GridLayout(1, 0, 7, 0));
		
		JLabel lblNewLabel_1 = new JLabel("PlaceHolder@Plugin");
		lblNewLabel_1.setForeground(new Color(241, 250, 238));
		mainFootercontent.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("PlaceHolder@Plugin");
		lblNewLabel_2.setForeground(new Color(241, 250, 238));
		mainFootercontent.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("PlaceHolder@Plugin");
		lblNewLabel_3.setForeground(new Color(241, 250, 238));
		mainFootercontent.add(lblNewLabel_3);
		
		JLabel appTitle_1 = new JLabel("App ni Ohwen");
		appTitle_1.setForeground(new Color(29, 53, 87));
		appTitle_1.setHorizontalAlignment(SwingConstants.LEFT);
		appTitle_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		MainMenu.add(appTitle_1, BorderLayout.NORTH);
		
		JPanel mainContentpanel = new JPanel();
		mainContentpanel.setBackground(new Color(241, 250, 238));
		MainMenu.add(mainContentpanel, BorderLayout.CENTER);
		mainContentpanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel Minigame = new JPanel();
		Minigame.setBackground(new Color(241, 250, 238));
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
		middleButtonsPanel.setBackground(new Color(241, 250, 238));
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
		GridBagLayout gbl_LeftSidePanel = new GridBagLayout();
		gbl_LeftSidePanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_LeftSidePanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_LeftSidePanel.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_LeftSidePanel.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		LeftSidePanel.setLayout(gbl_LeftSidePanel);
		
		JLabel lblWPMLabel = new JLabel("WPM: " + morseAudioWPM);
		GridBagConstraints gbc_lblWPMLabel = new GridBagConstraints();
		gbc_lblWPMLabel.anchor = GridBagConstraints.WEST;
		gbc_lblWPMLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblWPMLabel.gridx = 1;
		gbc_lblWPMLabel.gridy = 0;
		LeftSidePanel.add(lblWPMLabel, gbc_lblWPMLabel);
		
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
		LeftSidePanel.add(wpmSlider, gbc_wpmSlider);
		
		JLabel lblHertzLabel = new JLabel("Tone/Hertz: " + morseAudioHertz);
		GridBagConstraints gbc_lblHertzLabel = new GridBagConstraints();
		gbc_lblHertzLabel.anchor = GridBagConstraints.WEST;
		gbc_lblHertzLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblHertzLabel.gridx = 1;
		gbc_lblHertzLabel.gridy = 1;
		LeftSidePanel.add(lblHertzLabel, gbc_lblHertzLabel);
		
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
		LeftSidePanel.add(hertzSlider, gbc_hertzSlider);
		
		JLabel lblVolumeLabel = new JLabel("Volume: " + (int) Math.round(morseAudioVolume * 100));
		GridBagConstraints gbc_lblVolumeLabel = new GridBagConstraints();
		gbc_lblVolumeLabel.anchor = GridBagConstraints.WEST;
		gbc_lblVolumeLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblVolumeLabel.gridx = 1;
		gbc_lblVolumeLabel.gridy = 2;
		LeftSidePanel.add(lblVolumeLabel, gbc_lblVolumeLabel);
		
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
		LeftSidePanel.add(volumeSlider, gbc_volumeSlider);
		
		JPanel RightSidePanel = new JPanel();
		FlowLayoutPanel.add(RightSidePanel);
		GridBagLayout gbl_RightSidePanel = new GridBagLayout();
		gbl_RightSidePanel.columnWidths = new int[]{75, 50, 75, 0};
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
		gbc_btnPlaySoundButton.fill = GridBagConstraints.HORIZONTAL;
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
		lettersMorseTabbedPanel.setBackground(new Color(241, 250, 238));
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
		            
		            // Reset flag
		            stopMinigameAudio = false;

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
		        		            MorseAudio.playMorse(morseToPlay, morseAudioWPM, morseAudioHertz, morseAudioVolume, () -> stopMinigameAudio);
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
		
		contentPane.setPreferredSize(new Dimension(1250, 700));	// ideal inside size
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
        
        stopMinigameAudio = false; // reset flag
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
            		MorseAudio.playMorse(morseCodeSplitBySpacing[i], morseAudioWPM, morseAudioHertz, morseAudioVolume, () -> stopMinigameAudio);
            		
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
	
	private class SwingAction extends AbstractAction 
	{
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
