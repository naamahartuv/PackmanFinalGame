package SQL;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class SQLFrame extends JFrame {
	
	private JTextArea textArea;
	
	public SQLFrame() {
		JPanel panel = new JPanel();
		add(panel);
		
		textArea = new JTextArea();
		textArea.setBackground(Color.pink);
		
		panel.add(textArea);
		
		setSize(962, 400);
		
		setVisible(true);
	}
	
	public JTextArea getTextArea() {
		return textArea; 
	}
}
