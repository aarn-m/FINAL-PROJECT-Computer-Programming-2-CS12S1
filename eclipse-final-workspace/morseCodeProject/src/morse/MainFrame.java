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
	/**
	 * @wbp.nonvisual location=-95,348
	 */
	private final JComboBox comboBox = new JComboBox();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
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
		gbl_Minigame.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		Minigame.setLayout(gbl_Minigame);
		
		JPanel morsleToSolvePanel = new JPanel();
		morsleToSolvePanel.setBackground(new Color(241, 250, 238));
		GridBagConstraints gbc_morsleToSolvePanel = new GridBagConstraints();
		gbc_morsleToSolvePanel.insets = new Insets(0, 0, 5, 0);
		gbc_morsleToSolvePanel.gridx = 0;
		gbc_morsleToSolvePanel.gridy = 0;
		Minigame.add(morsleToSolvePanel, gbc_morsleToSolvePanel);
		morsleToSolvePanel.setLayout(new GridLayout(1, 0, 10, 0));
		
		textField = new JTextField();
		textField.setForeground(new Color(29, 53, 87));
		morsleToSolvePanel.add(textField);
		textField.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setForeground(new Color(29, 53, 87));
		morsleToSolvePanel.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setForeground(new Color(29, 53, 87));
		morsleToSolvePanel.add(textField_4);
		textField_4.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setForeground(new Color(29, 53, 87));
		morsleToSolvePanel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setForeground(new Color(29, 53, 87));
		morsleToSolvePanel.add(textField_2);
		textField_2.setColumns(10);
		
		Dimension tfSize = new Dimension(100, 100);
		textField.setPreferredSize(tfSize);
		textField_1.setPreferredSize(tfSize);
		textField_2.setPreferredSize(tfSize);
		textField_3.setPreferredSize(tfSize);
		textField_4.setPreferredSize(tfSize);
		
		JPanel middleButtonsPanel = new JPanel();
		middleButtonsPanel.setBackground(new Color(241, 250, 238));
		GridBagConstraints gbc_middleButtonsPanel = new GridBagConstraints();
		gbc_middleButtonsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_middleButtonsPanel.fill = GridBagConstraints.BOTH;
		gbc_middleButtonsPanel.gridx = 0;
		gbc_middleButtonsPanel.gridy = 1;
		Minigame.add(middleButtonsPanel, gbc_middleButtonsPanel);
		middleButtonsPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(69, 123, 157));
		middleButtonsPanel.add(panel_1, BorderLayout.CENTER);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(new Color(69, 123, 157));
		panel_1.add(panel_7);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(69, 123, 157));
		panel_7.add(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JButton btnNewButton = new JButton("Play at 40 wpm");
		btnNewButton.setForeground(new Color(29, 53, 87));
		btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btnNewButton.setBackground(new Color(168, 218, 220));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		panel_2.add(btnNewButton, gbc_btnNewButton);
		
		JLabel lblNewLabel = new JLabel("10 Tries Remaining");
		lblNewLabel.setForeground(new Color(241, 250, 238));
		lblNewLabel.setBackground(new Color(69, 123, 157));
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panel_2.add(lblNewLabel, gbc_lblNewLabel);
		
		JSlider slider = new JSlider();
		slider.setForeground(new Color(29, 53, 87));
		slider.setBackground(new Color(69, 123, 157));
		panel_7.add(slider);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(69, 123, 157));
		panel_1.add(panel_6);
		GridBagLayout gbl_panel_6 = new GridBagLayout();
		gbl_panel_6.columnWidths = new int[]{0, 0, 0};
		gbl_panel_6.rowHeights = new int[]{0, 0};
		gbl_panel_6.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_6.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_6.setLayout(gbl_panel_6);
		
		JButton btnNewButton_1 = new JButton("Backspace");
		btnNewButton_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btnNewButton_1.setBackground(new Color(168, 218, 220));
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 0;
		panel_6.add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Guess");
		btnNewButton_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btnNewButton_2.setBackground(new Color(168, 218, 220));
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.gridx = 1;
		gbc_btnNewButton_2.gridy = 0;
		panel_6.add(btnNewButton_2, gbc_btnNewButton_2);
		
		JPanel lettersMorseTabbedPanel = new JPanel();
		lettersMorseTabbedPanel.setBackground(new Color(241, 250, 238));
		GridBagConstraints gbc_lettersMorseTabbedPanel = new GridBagConstraints();
		gbc_lettersMorseTabbedPanel.fill = GridBagConstraints.BOTH;
		gbc_lettersMorseTabbedPanel.gridx = 0;
		gbc_lettersMorseTabbedPanel.gridy = 2;
		Minigame.add(lettersMorseTabbedPanel, gbc_lettersMorseTabbedPanel);
		lettersMorseTabbedPanel.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBackground(new Color(69, 123, 157));
		lettersMorseTabbedPanel.add(tabbedPane_1);
		
		JPanel dotsAndDashesPanel = new JPanel();
		dotsAndDashesPanel.setBackground(new Color(241, 250, 238));
		tabbedPane_1.addTab("Dots and Dashes", null, dotsAndDashesPanel, null);
		
		JPanel lettersPanel = new JPanel();
		lettersPanel.setForeground(new Color(29, 53, 87));
		lettersPanel.setBackground(new Color(241, 250, 238));
		tabbedPane_1.addTab("Letters", null, lettersPanel, null);
		
		JPanel TranslatorPanel = new JPanel();
		tabbedPane.addTab("Translator", null, TranslatorPanel, null);
		TranslatorPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(69, 123, 157));
		TranslatorPanel.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblInput = new JLabel("Input");
		lblInput.setForeground(new Color(241, 250, 238));
		lblInput.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
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
		textArea_Input.setForeground(new Color(29, 53, 87));
		textArea_Input.setBackground(new Color(241, 250, 238));
		textArea_Input.setLineWrap(true);
		scrollPane_Input.setViewportView(textArea_Input);
		
		JPanel panelButtons = new JPanel();
		panelButtons.setBackground(new Color(69, 123, 157));
		GridBagConstraints gbc_panelButtons = new GridBagConstraints();
		gbc_panelButtons.insets = new Insets(0, 0, 5, 0);
		gbc_panelButtons.gridx = 0;
		gbc_panelButtons.gridy = 2;
		panel.add(panelButtons, gbc_panelButtons);
		panelButtons.setLayout(new GridLayout(0, 3, 10, 0));
		
		JLabel lblOutput = new JLabel("Output");
		lblOutput.setForeground(new Color(241, 250, 238));
		lblOutput.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
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
		textArea_Output.setForeground(new Color(29, 53, 87));
		textArea_Output.setBackground(new Color(241, 250, 238));
		textArea_Output.setEditable(false);
		textArea_Output.setLineWrap(true);
		panel_GridBagLayout.setViewportView(textArea_Output);
		
		JButton btnTtM = new JButton("Text to MorseCode");
		btnTtM.setForeground(new Color(29, 53, 87));
		btnTtM.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btnTtM.setBackground(new Color(168, 218, 220));
		btnTtM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_Output.setText(Translator.textToMorse(textArea_Input.getText()));
			}
		});
		panelButtons.add(btnTtM);
		
		JButton btnMtT = new JButton("MorseCode to Text");
		btnMtT.setForeground(new Color(29, 53, 87));
		btnMtT.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btnMtT.setBackground(new Color(168, 218, 220));
		btnMtT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_Output.setText(Translator.morseToText(textArea_Input.getText()));
			}
		});
		panelButtons.add(btnMtT);
		
		JButton btnCtC = new JButton("Copy to Clipboard");
		btnCtC.setForeground(new Color(29, 53, 87));
		btnCtC.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btnCtC.setBackground(new Color(168, 218, 220));
		btnCtC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
		        String text = textArea_Output.getText(); // get text from JTextPane
		        
		        StringSelection selection = new StringSelection(text);
		        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
		        
		        JOptionPane.showMessageDialog(null, "Copied to clipboard!");
			}
		});
		panelButtons.add(btnCtC);
		
		JPanel WMC = new JPanel();
		WMC.setBackground(new Color(69, 123, 157));
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
		pack();                                                // resize frame to fit contents
		setMinimumSize(getSize());                             // current packed size becomes minimum
		setLocationRelativeTo(null);                           // center on screen

	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
