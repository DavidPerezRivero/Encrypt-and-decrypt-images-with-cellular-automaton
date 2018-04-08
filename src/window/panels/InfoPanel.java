package window.panels;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Muestra informacion relativa a la ejecucion del algoritmo
 */
public class InfoPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JTextArea infoText;
	private final static String ROUND_TEXT = "Current Round: ";
	
	/**
	 * Constructor de la clase
	 */
	public InfoPanel() {
		setLayout(new BorderLayout());
		this.setBackground(Color.LIGHT_GRAY);
		infoText = new JTextArea(ROUND_TEXT + 0);
		infoText.setBackground(Color.LIGHT_GRAY);
		add(infoText, BorderLayout.SOUTH);
	}
	
	/**
	 * Actualiza el valor del numero de rondas ejecutadas
	 * @param k
	 */
	public static void updateRound(int k) {
		infoText.setText(ROUND_TEXT + k);
	}
}
