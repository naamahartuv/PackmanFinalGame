package SQL;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * this class is the gui of the SQL table
 * @author yael hava and naama hartuv
 *
 */

public class SQLFrame extends JFrame {
	
	private JTextArea textArea;
	
	/**
	 * constructor
	 */
	
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
