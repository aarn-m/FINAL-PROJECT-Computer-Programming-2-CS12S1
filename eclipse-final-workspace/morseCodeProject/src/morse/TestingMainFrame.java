package morse;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public class TestingMainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestingMainFrame frame = new TestingMainFrame();
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
	public TestingMainFrame() {
		setTitle("Translator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMorseTranslation = new JLabel("Translation");
		lblMorseTranslation.setBounds(10, 98, 296, 18);
		contentPane.add(lblMorseTranslation);
		
		textField = new JTextField();
		textField.setBounds(10, 22, 716, 36);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JTextPane txtpnHelloWorld = new JTextPane();
		txtpnHelloWorld.setEditable(false);
		txtpnHelloWorld.setText("Translation will appear here.");
		txtpnHelloWorld.setBounds(10, 120, 716, 133);
		contentPane.add(txtpnHelloWorld);

		JButton btnNewButton_1 = new JButton("Copy");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        String text = txtpnHelloWorld.getText(); // get text from JTextPane
		        
		        StringSelection selection = new StringSelection(text);
		        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
		        
		        JOptionPane.showMessageDialog(null, "Copied to clipboard!");
			}
		});
		btnNewButton_1.setBounds(330, 68, 84, 20);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("Text to Morse code");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtpnHelloWorld.setText(Translator.textToMorse(textField.getText()));
			}
		});
		btnNewButton.setBounds(10, 68, 150, 20);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("Morse code to Text");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				txtpnHelloWorld.setText(Translator.morseToText(textField.getText()));
			}
		});
		btnNewButton_2.setBounds(170, 68, 150, 20);
		contentPane.add(btnNewButton_2);
		
	}
}
