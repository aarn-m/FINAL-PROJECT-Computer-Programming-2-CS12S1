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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.JScrollBar;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/**
	 * @wbp.nonvisual location=-95,348
	 */
	private final JComboBox comboBox = new JComboBox();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

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
		gbl_Minigame.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		Minigame.setLayout(gbl_Minigame);
		
		JPanel morsleToSolvePanel = new JPanel();
		GridBagConstraints gbc_morsleToSolvePanel = new GridBagConstraints();
		gbc_morsleToSolvePanel.insets = new Insets(0, 0, 5, 0);
		gbc_morsleToSolvePanel.gridx = 0;
		gbc_morsleToSolvePanel.gridy = 0;
		Minigame.add(morsleToSolvePanel, gbc_morsleToSolvePanel);
		morsleToSolvePanel.setLayout(new GridLayout(1, 0, 10, 0));
		
		textField = new JTextField();
		morsleToSolvePanel.add(textField);
		textField.setColumns(10);
		
		textField_3 = new JTextField();
		morsleToSolvePanel.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		morsleToSolvePanel.add(textField_4);
		textField_4.setColumns(10);
		
		textField_1 = new JTextField();
		morsleToSolvePanel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		morsleToSolvePanel.add(textField_2);
		textField_2.setColumns(10);
		
		Dimension tfSize = new Dimension(100, 100);
		textField.setPreferredSize(tfSize);
		textField_1.setPreferredSize(tfSize);
		textField_2.setPreferredSize(tfSize);
		textField_3.setPreferredSize(tfSize);
		textField_4.setPreferredSize(tfSize);
		
		JPanel middleButtonsPanel = new JPanel();
		GridBagConstraints gbc_middleButtonsPanel = new GridBagConstraints();
		gbc_middleButtonsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_middleButtonsPanel.fill = GridBagConstraints.BOTH;
		gbc_middleButtonsPanel.gridx = 0;
		gbc_middleButtonsPanel.gridy = 1;
		Minigame.add(middleButtonsPanel, gbc_middleButtonsPanel);
		middleButtonsPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		middleButtonsPanel.add(panel_5, BorderLayout.EAST);
		
		JPanel panel_1 = new JPanel();
		middleButtonsPanel.add(panel_1, BorderLayout.CENTER);
		
		JPanel panel_7 = new JPanel();
		panel_1.add(panel_7);
		
		JPanel panel_2 = new JPanel();
		panel_7.add(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JButton btnNewButton = new JButton("Play at 40 wpm");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		panel_2.add(btnNewButton, gbc_btnNewButton);
		
		JLabel lblNewLabel = new JLabel("10 Tries Remaining");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panel_2.add(lblNewLabel, gbc_lblNewLabel);
		
		JSlider slider = new JSlider();
		panel_7.add(slider);
		
		JPanel panel_6 = new JPanel();
		panel_1.add(panel_6);
		GridBagLayout gbl_panel_6 = new GridBagLayout();
		gbl_panel_6.columnWidths = new int[]{0, 0, 0};
		gbl_panel_6.rowHeights = new int[]{0, 0};
		gbl_panel_6.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_6.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_6.setLayout(gbl_panel_6);
		
		JButton btnNewButton_1 = new JButton("Backspace");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 0;
		panel_6.add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Guess");
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.gridx = 1;
		gbc_btnNewButton_2.gridy = 0;
		panel_6.add(btnNewButton_2, gbc_btnNewButton_2);
		
		JPanel lettersMorseTabbedPanel = new JPanel();
		GridBagConstraints gbc_lettersMorseTabbedPanel = new GridBagConstraints();
		gbc_lettersMorseTabbedPanel.fill = GridBagConstraints.BOTH;
		gbc_lettersMorseTabbedPanel.gridx = 0;
		gbc_lettersMorseTabbedPanel.gridy = 2;
		Minigame.add(lettersMorseTabbedPanel, gbc_lettersMorseTabbedPanel);
		lettersMorseTabbedPanel.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		lettersMorseTabbedPanel.add(tabbedPane_1);
		
		JPanel dotsAndDashesPanel = new JPanel();
		tabbedPane_1.addTab("Dots and Dashes", null, dotsAndDashesPanel, null);
		
		JPanel lettersPanel = new JPanel();
		tabbedPane_1.addTab("Letters", null, lettersPanel, null);
		
		
		
		
		
		contentPane.setPreferredSize(new Dimension(750, 500)); // ideal inside size
		pack();                                                // resize frame to fit contents
		setMinimumSize(getSize());                             // current packed size becomes minimum
		setLocationRelativeTo(null);                           // center on screen

	}

}
